package hr.shopingcart.cart.repository;

import hr.shopingcart.cart.entity.ItemPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPriceRepo extends JpaRepository<ItemPrice, Long> {

}