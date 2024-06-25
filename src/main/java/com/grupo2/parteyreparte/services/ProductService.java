package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.exceptions.EntityNotFoundException;
import com.grupo2.parteyreparte.exceptions.ProductClosedException;
import com.grupo2.parteyreparte.exceptions.ProductFullException;
import com.grupo2.parteyreparte.exceptions.UnauthorizedOperationException;
import com.grupo2.parteyreparte.mappers.ProductMapper;
import com.grupo2.parteyreparte.mappers.UserMapper;
import com.grupo2.parteyreparte.models.Notification;
import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.ProductState;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.repositories.mongo.NotificationRepository;
import com.grupo2.parteyreparte.repositories.mongo.ProductRepository;
import dev.failsafe.Failsafe;
import dev.failsafe.RetryPolicy;
import dev.failsafe.function.CheckedRunnable;
import dev.failsafe.function.CheckedSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private static final String PRODUCT_IS_FULL = "Product is full";
    private static final String USER_DOES_NOT_HAVE_PERMISSION = "User does not have permission to close this product";

    private static final String USER_ALREADY_SUBSCRIBED = "User is already subscribed to this product";

    private static final String PRODUCT_BAD_DATE = "Product deadline must be later than now";

    private static final String PRODUCT_WITH_ID = "Product with id = ";
    private static final String ALREADY_EXISTS = " already exists";
    private static final String DOES_NOT_EXIST = " does not exist";
    private static final int MaxRetries = 3;
    private static final int RetryDelayInMs = 100;

    private static final String PRODUCT_CLOSED = "Product is already closed";


    private final ProductRepository productRepository;
    private final UserService userService;
    private final ProductMapper productMapper;
    private final UserMapper  userMapper;
    private final NotificationRepository notificationRepository;
    private final RetryPolicy<Object> optimisticLockRetry;


    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper, UserMapper userMapper, UserService userService, NotificationRepository notificationRepository, RetryPolicy<Object> optimisticLockRetry) {
        this.productMapper = productMapper;
        this.userMapper = userMapper;
        this.userService = userService;
        this.productRepository = productRepository;
        this.notificationRepository = notificationRepository;
        this.optimisticLockRetry = optimisticLockRetry;
    }

    public List<ProductDTO> getAll() {

        return this.productRepository.findAll().stream().map(this.productMapper::mapToProductDTO).collect(Collectors.toList());

    }

    public Integer countProducts() {
        return Math.toIntExact(productRepository.count());
    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        User loggedUser = this.userService.getLoggedUser();
        Product product = this.productMapper.mapToProduct(productDTO);
        product.setOwner(loggedUser);
        product.initiateSubscribers();

        if (product.getDeadline().isBefore(LocalDateTime.now())) {
            throw new ProductFullException("PRODUCT_BAD_DATE");
        }

        this.productRepository.insert(product);
        this.userService.publishProduct(product);


        return this.productMapper.mapToProductDTO(product);
    }

    public ProductDTO getProductDTOById(String id) {
        return this.productMapper.mapToProductDTO(this.getProductById(id));
    }

    public Product getProductById(String id) {
        return this.productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PRODUCT_WITH_ID + id + DOES_NOT_EXIST));
    }

    public ProductDTO updateProduct(String id, ProductDTO productDTO) {

        return Failsafe.with(optimisticLockRetry).get((CheckedSupplier<ProductDTO>) () -> {
            Product product = this.getProductById(id);
            product.patchProduct(productMapper.mapToProduct(productDTO));
            return this.productMapper.mapToProductDTO(this.productRepository.save(product));
        });

    }
/* PARA mi con el update ya estaria xq hacen lo mismo
    public ProductDTO patchProduct(String id, ProductDTO productDTO) {

        Product existingProduct = this.getProductById(id);

        Product product = this.productMapper.mapToProduct(productDTO);

        existingProduct.patchProduct(product);

        return this.productMapper.mapToProductDTO(this.productRepository.update(id, existingProduct));
    }*/

    public ProductDTO subscribeLoggedUser(String productId) {

        Product product = this.getProductById(productId);

        User user = userService.getLoggedUser();
        if (product.isFull()) {
            throw new ProductFullException(PRODUCT_IS_FULL);
        }

        if (product.getSuscribers().stream().map(User::getId)
                .anyMatch(id -> id.equals((String) user.getId() )) ) {
            throw new ProductFullException(USER_ALREADY_SUBSCRIBED);
        }
        product.subscribeUser(user);
        Failsafe.with(optimisticLockRetry).run((CheckedRunnable) () -> {
            this.productRepository.save(product);
        });
        userService.subscribeToProduct(product);
        return this.productMapper.mapToProductDTO(product);
    }

    public void unsubscribeLoggedUser(String productId){

        Product product = this.getProductById(productId);
        ProductState state = product.getState();
        if (state == ProductState.OPEN) {
            User user = userService.getLoggedUser();
            product.unsubscribe(user.getId());
            Failsafe.with(optimisticLockRetry).run((CheckedRunnable) () -> {
                this.productRepository.save(product);
                });
            } else {
                throw new ProductClosedException(PRODUCT_CLOSED);
            }
        this.userService.deleteUserProductById(productId);
    }

    public List<UserDTO> getParticipants(String id) {
        Product product = this.getProductById(id);
        return product.getSuscribers().stream().map(this.userMapper::mapToUserDTO).collect(Collectors.toList());
    }

    public ProductDTO closeProduct(String id) {
        Product product = this.getProductById(id);
        User loggedUser = this.userService.getLoggedUser();

        if(!product.isOwner(loggedUser)){
            throw new UnauthorizedOperationException(USER_DOES_NOT_HAVE_PERMISSION);
        }
        product.close();
        Failsafe.with(optimisticLockRetry).run((CheckedRunnable) () -> {
            this.productRepository.save(product);
        });
        this.notifyUsers(product);
        return this.productMapper.mapToProductDTO(product);
    }
    public void notifyUsers(Product product) {
        Notification notification = new Notification("Product closed", LocalDateTime.now(), product);
        this.notificationRepository.save(notification);
        for (User user : product.getSuscribers()) {
            this.userService.notifyUser(user, notification);
        }
    }
}
