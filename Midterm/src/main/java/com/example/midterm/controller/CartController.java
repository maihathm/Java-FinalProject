package com.example.midterm.controller;

import com.example.midterm.dto.AddToCartDTO;
import com.example.midterm.model.CartItem;
import com.example.midterm.repos.CartItemRepository;
import com.example.midterm.services.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final CartItemRepository cartItemRepository;
    private final  CartItemSerivce cartItemSerivce;

    @GetMapping()
    public String cart(Model model, Principal principal) throws Exception {
        List<CartItem> cartItemList = cartItemSerivce.getCartItemByUser(principal.getName());
        AtomicReference<Long> totalPrice = new AtomicReference<>(0L);
        model.addAttribute("cartItems", cartItemList);
        cartItemList.forEach(cartItem -> {
            totalPrice.updateAndGet(v -> v + cartItem.getQuantity() * cartItem.getProduct().getPrice());
        });
        model.addAttribute("totalPrice", totalPrice.get());
        model.addAttribute("user",principal.getName());
        return "cart";
    }

//    @PostMapping()
//    public String addToCart(
//            @RequestParam String productId,
//            @RequestParam String quantity,
//            Principal principal,
//            Model model
//    ) throws Exception {
//        AddToCartDTO addToCartDTO = new AddToCartDTO();
//        addToCartDTO.setProductId(Long.valueOf(productId));
//        addToCartDTO.setQuantity(Long.valueOf(quantity));
//        addToCartDTO.setUsername(principal.getName());
//        shoppingCartService.addToCart(addToCartDTO);
//
//        return "redirect:/cart";
//    }

    @PostMapping("/delete")
    public String deleteProduct(Principal principal,
            @RequestParam String productId,
            Model model) throws Exception {
        shoppingCartService.removeProductFromCart(productId,principal.getName());

        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateProduct(Principal principal,
            @RequestParam String productId,
            @RequestParam String quantity,
            Model model
    ) throws Exception {
        AddToCartDTO addToCartDTO = new AddToCartDTO();
        addToCartDTO.setProductId(Long.valueOf(productId));
        addToCartDTO.setQuantity(Long.valueOf(quantity));
        addToCartDTO.setUsername(principal.getName());

        shoppingCartService.updateProductToCart(addToCartDTO);

        return "redirect:/cart";
    }
}
