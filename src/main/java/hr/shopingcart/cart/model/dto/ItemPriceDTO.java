package hr.shopingcart.cart.model.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ItemPriceDTO {

    private Long id;
    private BigDecimal price;
    private String priceType;
    private Integer recurrences;
}
