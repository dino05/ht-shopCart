package hr.shopingcart.cart.repository;

import hr.shopingcart.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

    Cart findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
