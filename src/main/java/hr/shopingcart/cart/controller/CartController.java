package hr.shopingcart.cart.controller;

import hr.shopingcart.cart.model.CartItemStatistics;
import hr.shopingcart.cart.model.dto.CartDTO;
import hr.shopingcart.cart.model.dto.CartItemDTO;
import hr.shopingcart.cart.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItemDTO>> findAll(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.findAllByUserId(userId));
    }

    @PostMapping("/update-cart/{userId}")
    public ResponseEntity<Void> addCartItems(@RequestBody CartDTO cartDTO, @PathVariable Long userId){
        cartService.addItemsToCart(cartDTO, userId);
        return ResponseEntity.ok().body(null);
    }

    @PatchMapping("/update-cart")
    public ResponseEntity<Void> updateCart(@RequestBody CartDTO cartDTO){
        cartService.updateItemsToCart(cartDTO);
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/update-cart/{cartItemId}")
    public ResponseEntity<Void> deleteCartItems(@PathVariable Long cartItemId){
        cartService.deleteItemsFromCart(cartItemId);
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/{userId}/evict")
    public ResponseEntity<Void> evictCart(@PathVariable Long userId){
        cartService.evictCart(userId);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/statistic")
    public ResponseEntity<CartItemStatistics> getItemsStatistics(){
        return ResponseEntity.ok(cartService.calculateItemsStatistic());
    }
}
