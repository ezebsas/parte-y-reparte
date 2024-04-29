package com.grupo2.parteyreparte.dtos;

import com.grupo2.parteyreparte.models.ProductState;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Getter
@Setter
public class ProductDTO {
    @Getter
    private String id;
    private String name;
    private String image;
    private String link;
    private LocalDateTime deadline;
    private int maxPeople;
    private int minPeople;
    private Double totalCost;
    private ProductState state;
    private List<UserDTO> subscribers;
    private UserDTO owner;

    public ProductDTO() {}

    public ProductDTO(String name, String image, int maxPeople, int minPeople, double totalCost) {
        this.name = name;
        this.image = image;
        this.link = "";
        this.maxPeople = maxPeople;
        this.minPeople = minPeople;
        this.totalCost = totalCost;
        this.state = ProductState.OPEN;
    }
}
