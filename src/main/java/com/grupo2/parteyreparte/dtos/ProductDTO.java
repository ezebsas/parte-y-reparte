package com.grupo2.parteyreparte.dtos;

import com.grupo2.parteyreparte.models.ProductState;
import com.grupo2.parteyreparte.models.ProductUnit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Data
@Schema(description = "DTO of a product")
public class ProductDTO {
    @Getter
    @Schema(name = "id", description = "product's id", example = "507f191e810c19729de860ea", type = "string")
    private String id;

    @Schema(name = "name", description = "product's name", example = "Coca-Cola", type = "string")
    private String name;

    @Schema(name = "image", description = "product's image url", example = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/2048px-No_image_available.svg.png", type = "string")
    private String image;

    @Schema(name = "link", description = "product's link", example = "https://purr.objects-us-east-1.dream.io/i/TETrj.jpg", type = "string")
    private String link;
    private LocalDateTime deadline;

    @Schema(name = "maxPeople", description = "product's maximum people to subscribe", example = "5", type = "int")
    private int maxPeople;

    @Schema(name = "maxPeople", description = "product's minimum people to subscribe", example = "2", type = "int")
    private int minPeople;

    @Schema(name = "maxPeople", description = "product's price", example = "299.9", type = "double")
    private Double totalCost;
    private ProductState state;
    private ProductUnit unit;

    @Schema(name = "quantity", description = "product's quantity", example = "10", type = "int")
    private Double quantity;
    private List<UserDTO> subscribers;
    private UserDTO owner;

    public ProductDTO() {}

    public ProductDTO(String name, String image, int maxPeople, int minPeople, double totalCost, double quantity, ProductUnit unit) {
        this.name = name;
        this.image = image;
        this.link = "";
        this.maxPeople = maxPeople;
        this.minPeople = minPeople;
        this.totalCost = totalCost;
        this.state = ProductState.OPEN;
        this.quantity = quantity;
        this.unit = unit;
    }
}
