package com.grupo2.parteyreparte.dtos;

import com.grupo2.parteyreparte.models.ProductState;
import lombok.Getter;
import lombok.Setter;

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
    private Date deadline;
    private int maxPeople;
    private int minPeople;
    private Double totalCost;
    private ProductState state;
    private List<UserDTO> suscribers;

}
