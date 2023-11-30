package com.example.midterm.controller;

import com.example.midterm.dto.AddToCartDTO;
import com.example.midterm.model.CartItem;
import com.example.midterm.repos.CartItemRepository;
import com.example.midterm.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final CartItemRepository cartItemRepository;

    @GetMapping()
    public String cart(Model model) throws Exception {
        List<CartItem> cartItemList = cartItemRepository.findAll();
        AtomicReference<Long> totalPrice = new AtomicReference<>(0L);
        model.addAttribute("cartItems", cartItemList);
        cartItemList.forEach(cartItem -> {
            totalPrice.updateAndGet(v -> v + cartItem.getQuantity() * cartItem.getProduct().getPrice());
        });
        model.addAttribute("totalPrice", totalPrice.get());
        return "cart";
    }

    @PostMapping()
    public String addToCart(
            @RequestParam String productId,
            @RequestParam String quantity,
            Model model
    ) throws Exception {
        AddToCartDTO addToCartDTO = new AddToCartDTO();
        addToCartDTO.setProductId(Long.valueOf(productId));
        addToCartDTO.setQuantity(Long.valueOf(quantity));
        shoppingCartService.addToCart(addToCartDTO);

        return "redirect:/cart";
    }

    @PostMapping("/delete")
    public String deleteProduct(
            @RequestParam String productId,
            Model model) throws Exception {
        shoppingCartService.removeProductFromCart(productId);

        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateProduct(
            @RequestParam String productId,
            @RequestParam String quantity,
            Model model
    ) throws Exception {
        AddToCartDTO addToCartDTO = new AddToCartDTO();
        addToCartDTO.setProductId(Long.valueOf(productId));
        addToCartDTO.setQuantity(Long.valueOf(quantity));

        shoppingCartService.updateProductToCart(addToCartDTO);

        return "redirect:/cart";
    }
}
