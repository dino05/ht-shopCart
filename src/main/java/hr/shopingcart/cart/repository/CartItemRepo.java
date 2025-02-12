package hr.shopingcart.cart.repository;

import hr.shopingcart.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByCartIdAndActionTypeIn(@Param("cartId") Long cartId, @Param("actionType") List<String> actionTypes);
    int countByActionType(@Param("actionType") String actionType);
}