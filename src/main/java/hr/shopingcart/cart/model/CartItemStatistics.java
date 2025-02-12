package hr.shopingcart.cart.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CartItemStatistics {

    private int itemsSold;
    private int itemsModified;
    private int itemsRemoved;
}
