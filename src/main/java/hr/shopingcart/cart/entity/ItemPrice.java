package hr.shopingcart.cart.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@AllArgsConstructor
@Table(name = "item_price")
public class ItemPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;
    @Column(name = "price_type")
    private String priceType;
    private Integer recurrences;

    public ItemPrice(){}

    @ManyToOne
    @JoinColumn(name = "cart_item_id")
    @JsonIgnore
    private CartItem cartItem;
}
