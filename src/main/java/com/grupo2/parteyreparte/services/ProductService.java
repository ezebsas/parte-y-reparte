package com.grupo2.parteyreparte.services;

import com.grupo2.parteyreparte.dtos.ProductDTO;
import com.grupo2.parteyreparte.dtos.UserDTO;
import com.grupo2.parteyreparte.exceptions.EntityNotFoundException;
import com.grupo2.parteyreparte.exceptions.ProductFullException;
import com.grupo2.parteyreparte.exceptions.UnauthorizedOperationException;
import com.grupo2.parteyreparte.mappers.ProductMapper;
import com.grupo2.parteyreparte.mappers.UserMapper;
import com.grupo2.parteyreparte.models.Product;
import com.grupo2.parteyreparte.models.User;
import com.grupo2.parteyreparte.repositories.ProductRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private static final String PRODUCT_IS_FULL = "Product is full";
    private static final String USER_DOES_NOT_HAVE_PERMISSION = "User does not have permission to close this product";


    private final ProductRepository productRepository;
    private final UserService userService;
    private final ProductMapper productMapper;
    private final UserMapper  userMapper;


    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper productMapper, UserMapper userMapper, UserService userService) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    public List<ProductDTO> getAll() {

        return this.productRepository.getAll().stream().map(this.productMapper::mapToProductDTO).collect(Collectors.toList());

    }

    public ProductDTO createProduct(ProductDTO productDTO) {
        User loggedUser = this.userService.getLoggedUser();
        Product product = this.productMapper.mapToProduct(productDTO);
        product.setOwner(loggedUser);

        loggedUser.publishProduct(product);
        //TODO: save user

        return this.productMapper.mapToProductDTO(this.productRepository.createProduct(product));
    }

    public ProductDTO getProductDTOById(String id) {
        return this.productMapper.mapToProductDTO(this.productRepository.findById(id));
    }

    public Product getProductById(String id) {
        return this.productRepository.findById(id);
    }

    public ProductDTO updateProduct(String id, ProductDTO productDTO) {
        return this.productMapper.mapToProductDTO(this.productRepository.update(id, this.productMapper.mapToProduct(productDTO)));
    }

    public ProductDTO patchProduct(String id, ProductDTO productDTO) {

        Product existingProduct = this.productRepository.findById(id);

        Product product = this.productMapper.mapToProduct(productDTO);

        if (product.getName() != null) {
            existingProduct.setName(product.getName());
        }

        if (product.getImage() != null) {
            existingProduct.setImage(product.getImage());
        }

        if (product.getLink() != null) {
            existingProduct.setLink(product.getLink());
        }

        if (product.getDeadline() != null) {
            existingProduct.setDeadline(product.getDeadline());
        }

        if (product.getMaxPeople() != 0) {
            existingProduct.setMaxPeople(product.getMaxPeople());
        }

        if (product.getMinPeople() != 0) {
            existingProduct.setMinPeople(product.getMinPeople());
        }

        Double totalCost = product.getTotalCost();
        if (totalCost != null) {
            existingProduct.setTotalCost(totalCost);
        }


        if (product.getSuscribers() != null) {
            existingProduct.setSuscribers(product.getSuscribers());
        }

        if (product.getState() != null) {
            existingProduct.setState(product.getState());
        }

        return this.productMapper.mapToProductDTO(this.productRepository.update(id, existingProduct));
    }

    public ProductDTO subscribeLoggedUser(String productId) {
        Product product = this.getProductById(productId);

        if (product.isFull()) {
            throw new ProductFullException(PRODUCT_IS_FULL);
        }

        User user = userService.getLoggedUser();
        product.subscribeUser(user);
        user.susbribeProduct(product);

        this.productRepository.update(productId, product);
        return this.productMapper.mapToProductDTO(product);
    }

    public List<UserDTO> getParticipants(String id) {
        Product product = this.getProductById(id);
        return product.getSuscribers().stream().map(this.userMapper::mapToUserDTO).collect(Collectors.toList());
    }

    public ProductDTO closeProduct(String id) {
        Product product = this.getProductById(id);
        User loggedUser = this.userService.getLoggedUser();

        if (product.isOwner(loggedUser)) {
            product.close();
            this.productRepository.update(id, product);
            return this.productMapper.mapToProductDTO(product);
        } else {
            throw new UnauthorizedOperationException(USER_DOES_NOT_HAVE_PERMISSION);
        }
    }
}
