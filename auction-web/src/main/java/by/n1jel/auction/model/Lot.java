package by.n1jel.auction.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Lot {
    private Long id;
    private String name;
    private String type;
    private BigDecimal price;
}
