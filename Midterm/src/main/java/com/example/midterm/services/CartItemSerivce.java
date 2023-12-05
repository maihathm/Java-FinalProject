package com.example.midterm.services;
import com.example.midterm.model.CartItem;
import com.example.midterm.model.User;
import com.example.midterm.repos.CartItemRepository;
import com.example.midterm.repos.UserRepository;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.List;

@Service
@AllArgsConstructor
public class CartItemSerivce {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public List<CartItem> getCartItemByUser(String username){
        User user = userRepository.findByUsername(username).orElseThrow();
        return cartItemRepository.findAllByUser(user);
    }
}
