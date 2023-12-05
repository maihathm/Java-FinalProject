package com.example.midterm.controller;

import com.example.midterm.model.CartItem;
import com.example.midterm.repos.CartItemRepository;
import com.example.midterm.services.ShoppingCartService;
import com.example.midterm.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Controller
@AllArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final CartItemRepository cartItemRepository;

    @GetMapping()
    public String checkout(Model model) throws Exception {
        List<CartItem> cartItemList = cartItemRepository.findAll();
        AtomicReference<Long> totalPrice = new AtomicReference<>(0L);
        model.addAttribute("cartItems", cartItemList);
        cartItemList.forEach(cartItem -> {
            totalPrice.updateAndGet(v -> v + cartItem.getQuantity() * cartItem.getProduct().getPrice());
        });
        model.addAttribute("totalPrice", totalPrice.get());
        return "checkout";
    }

    @PostMapping
    public String createOrder(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam String message,
            Model model,
            Principal principal
    ) throws Exception {

        shoppingCartService.checkout(name, email, address + city, message, principal.getName());

        return "success";
    }
}
