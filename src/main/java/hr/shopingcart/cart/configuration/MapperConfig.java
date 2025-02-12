package hr.shopingcart.cart.configuration;

import hr.shopingcart.cart.entity.Cart;
import hr.shopingcart.cart.entity.CartItem;
import hr.shopingcart.cart.entity.ItemPrice;
import hr.shopingcart.cart.model.dto.CartDTO;
import hr.shopingcart.cart.model.dto.CartItemDTO;
import hr.shopingcart.cart.model.dto.ItemPriceDTO;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        // Configure Cart -> CartDTO mapping
        modelMapper.typeMap(Cart.class, CartDTO.class).addMappings(mapper -> {
            mapper.map(Cart::getUserId, CartDTO::setUserId);
            mapper.map(Cart::getCartItems, CartDTO::setCartItems);
        });

        // Configure CartItem -> CartItemDTO mapping
        modelMapper.typeMap(CartItem.class, CartItemDTO.class).addMappings(mapper -> {
            mapper.map(CartItem::getCreateDate, CartItemDTO::setCreateDate);
            mapper.map(CartItem::getActionType, CartItemDTO::setActionType);
            mapper.map(CartItem::getOfferId, CartItemDTO::setOfferId);
            mapper.map(CartItem::getItemPrice, CartItemDTO::setItemPrice);
        });

        // Configure ItemPrice -> ItemPriceDTO mapping
        modelMapper.typeMap(ItemPrice.class, ItemPriceDTO.class).addMappings(mapper -> {
            mapper.map(ItemPrice::getPrice, ItemPriceDTO::setPrice);
            mapper.map(ItemPrice::getPriceType, ItemPriceDTO::setPriceType);
            mapper.map(ItemPrice::getRecurrences, ItemPriceDTO::setRecurrences);
        });

        return new ModelMapper();
    }
}
