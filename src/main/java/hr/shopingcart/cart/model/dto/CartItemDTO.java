package hr.shopingcart.cart.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CartItemDTO {

    private Long id;
    private int quantity;
    private LocalDate createDate;
    private String actionType;
    private Long offerId;
    private List<ItemPriceDTO> itemPrice;
}
