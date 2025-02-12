package hr.shopingcart.cart.service;

import hr.shopingcart.cart.entity.Cart;
import hr.shopingcart.cart.entity.CartItem;
import hr.shopingcart.cart.model.CartItemStatistics;
import hr.shopingcart.cart.entity.ItemPrice;
import hr.shopingcart.cart.model.dto.CartDTO;
import hr.shopingcart.cart.model.dto.CartItemDTO;
import hr.shopingcart.cart.repository.CartItemRepo;
import hr.shopingcart.cart.repository.CartRepo;
import hr.shopingcart.cart.repository.ItemPriceRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class CartService {

    final String ADD_ITEM = "ADD";
    final String MODIFY_ITEM = "MODIFY";
    final String DELETE_ITEM = "DELETE";

    private ModelMapper modelMapper;

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final ItemPriceRepo itemPriceRepo;

    // Map CartItem entity to CartItemDTO
    public CartItemDTO mapToCartItemDTO(CartItem cartItem) {
        return modelMapper.map(cartItem, CartItemDTO.class);
    }

    // Map CartItemDTO to CartItem entity
    public CartItem mapToCartItem(CartItemDTO cartItemDTO) {
        return modelMapper.map(cartItemDTO, CartItem.class);
    }

    public List<CartItem> mapToCartItemList(List<CartItemDTO> cartItemDTOs) {
        return cartItemDTOs.stream()
                .map(this::mapToCartItem)
                .collect(Collectors.toList());
    }

    public List<CartItemDTO> mapToCartItemDTOList(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(this::mapToCartItemDTO)
                .collect(Collectors.toList());
    }

    public List<CartItemDTO> findAllByUserId(Long userId) {
        Cart cart = cartRepo.findByUserId(userId);
        try{
            List<CartItem> cartItems = cartItemRepo.findAllByCartIdAndActionTypeIn(cart.getId(), List.of(ADD_ITEM, MODIFY_ITEM));
            return mapToCartItemDTOList(cartItems);
        }catch (NullPointerException e){
            log.error("Cart doesn't exists for given userID!");
            throw e;
        }
    }

    @Transactional
    public void addItemsToCart(CartDTO cartDTO, Long userId){

        Cart cart = cartRepo.findByUserId(userId);
        List<CartItem> cartItems = mapToCartItemList(cartDTO.getCartItems());

        if(Objects.isNull(cart)){
            cart = new Cart();
            cart.setUserId(userId);
        }

        for(CartItem cartItem : cartItems){
            for(ItemPrice itemPrice : cartItem.getItemPrice()){
                itemPrice.setCartItem(cartItem);
            }
            cartItem.setCart(cart);
            cartItem.setActionType(ADD_ITEM);
        }

        cart.setCartItems(cartItems);
        cartRepo.save(cart);
    }

    @Transactional
    public void updateItemsToCart(CartDTO updatedCartDTO){

        List<CartItem> updatedCartItems = mapToCartItemList(updatedCartDTO.getCartItems());

        for (CartItem updatedItem : updatedCartItems) {
            CartItem existingItem = cartItemRepo.findById(updatedItem.getId()).orElseThrow();

            existingItem.setQuantity(updatedItem.getQuantity());
            existingItem.setActionType(MODIFY_ITEM);
            existingItem.setOfferId(updatedItem.getOfferId());

            if (updatedItem.getItemPrice() != null) {
                for (ItemPrice updatedPrice : updatedItem.getItemPrice()) {
                    ItemPrice existingPrice = itemPriceRepo.findById(updatedPrice.getId()).orElseThrow();

                    existingPrice.setPrice(updatedPrice.getPrice());
                    existingPrice.setPriceType(updatedPrice.getPriceType());
                    existingPrice.setRecurrences(updatedPrice.getRecurrences());

                    itemPriceRepo.save(existingPrice);
                }
            }

            cartItemRepo.save(existingItem);
        }
    }

    @Transactional
    public void deleteItemsFromCart(Long cartItemId){

        CartItem cartItem = cartItemRepo.findById(cartItemId).orElseThrow();
        cartItem.setActionType(DELETE_ITEM);
        cartItemRepo.save(cartItem);
    }

    @Transactional
    public void evictCart(Long userId){
        cartRepo.deleteByUserId(userId);
    }

    public CartItemStatistics calculateItemsStatistic() {

        CartItemStatistics cartItemStatistics = new CartItemStatistics();

        cartItemStatistics.setItemsSold(cartItemRepo.countByActionType(ADD_ITEM));
        cartItemStatistics.setItemsModified(cartItemRepo.countByActionType(MODIFY_ITEM));
        cartItemStatistics.setItemsRemoved(cartItemRepo.countByActionType(DELETE_ITEM));

        return cartItemStatistics;
    }
}