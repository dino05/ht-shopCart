package hr.shopingcart.cart.controller;

import hr.shopingcart.cart.model.CartItemStatistics;
import hr.shopingcart.cart.model.dto.CartDTO;
import hr.shopingcart.cart.model.dto.CartItemDTO;
import hr.shopingcart.cart.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/cart")
@Tag(name = "Cart controller", description = "Cart controller for actions on cart items.")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get cart items with prices", description = "Returns a list of cart items with prices that are added or modified")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of cart items")
    public ResponseEntity<List<CartItemDTO>> findAll(@PathVariable Long userId){
        return ResponseEntity.ok(cartService.findAllByUserId(userId));
    }

    @PostMapping("/update-cart/{userId}")
    @Operation(summary = "Add cart items with their prices", description = "Adds cart items with their prices and retrievs only http status.")
    @ApiResponse(responseCode = "200", description = "Successfully added given cart items")
    public ResponseEntity<Void> addCartItems(@RequestBody CartDTO cartDTO, @PathVariable Long userId){
        cartService.addItemsToCart(cartDTO, userId);
        return ResponseEntity.ok().body(null);
    }

    @PatchMapping("/update-cart")
    @Operation(summary = "Update cart items with their prices", description = "Updates cart items with their prices and retrievs only http status.")
    @ApiResponse(responseCode = "200", description = "Successfully updated given cart items")
    public ResponseEntity<Void> updateCart(@RequestBody CartDTO cartDTO){
        cartService.updateItemsToCart(cartDTO);
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/update-cart/{cartItemId}")
    @Operation(summary = "Delete cart items with their prices", description = "Deletes cart items with their prices and retrievs only http status.")
    @ApiResponse(responseCode = "200", description = "Successfully deleted cart item for the given id")
    public ResponseEntity<Void> deleteCartItems(@PathVariable Long cartItemId){
        cartService.deleteItemsFromCart(cartItemId);
        return ResponseEntity.ok().body(null);
    }

    @DeleteMapping("/{userId}/evict")
    @Operation(summary = "Delete whole cart", description = "Delete cart by userId and retrievs only http status.")
    @ApiResponse(responseCode = "200", description = "Successfully deleted cart")
    public ResponseEntity<Void> evictCart(@PathVariable Long userId){
        cartService.evictCart(userId);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/statistic")
    @Operation(summary = "Get the statistics of cart items", description = "Retrieve statistic properties od sold, modified and deleted cart items and retrievs statistic object.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved cart items statistic")
    public ResponseEntity<CartItemStatistics> getItemsStatistics(){
        return ResponseEntity.ok(cartService.calculateItemsStatistic());
    }
}
