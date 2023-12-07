package com.example.midterm.services;

import com.example.midterm.dto.AddToCartDTO;
import com.example.midterm.model.*;
import com.example.midterm.repos.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ShoppingCartService {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public void addProductToCart(AddToCartDTO cartItemDTO, User user) throws Exception {
        Product product = productRepository.findById(cartItemDTO.getProductId()).orElseThrow();

        CartItem cartItem = cartItemRepository.findByProductAndUser(product, user).orElse(new CartItem());

        cartItem.setProduct(product);
        cartItem.setUser(user);
        cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.getQuantity());

        if (!validateQuantity(cartItemDTO.getProductId(), cartItem.getQuantity())) {
            throw new IllegalArgumentException("Not enough product");
        }

        cartItemRepository.save(cartItem);
    }

    public void addToCart(AddToCartDTO cartItemDTO) throws Exception {
        Product product = productRepository.findById(cartItemDTO.getProductId()).orElseThrow();
        CartItem cartItem = cartItemRepository.findByProduct(product).orElse(new CartItem());
        cartItem.setProduct(product);
        cartItem.setQuantity(cartItem.getQuantity() + cartItemDTO.getQuantity());
        User user = userRepository.findByUsername(cartItemDTO.getUsername()).orElseThrow();
        cartItem.setUser(user);
        if (cartItem.getQuantity() > product.getQuantity()) {
            cartItem.setQuantity(product.getQuantity());
        }

        if(product.getQuantity() == 0){
            return;
        }

        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void removeProductFromCart(String productId, String username) {

        Product product = productRepository.findById(Long.valueOf(productId)).orElseThrow();
        User user = userRepository.findByUsername(username).orElseThrow();

        cartItemRepository.deleteByProductAndUser(product,user);
    }

    private boolean validateQuantity(Long prodId, Long quantity) {
        return productRepository
                .findById(prodId)
                .orElseThrow()
                .getQuantity()
                .compareTo(quantity) >= 0;
    }

    public List<CartItem> getCartItems() {
        return cartItemRepository.findAll();
    }

    public Long getTotalPrice() {
        List<CartItem> cartItemList = cartItemRepository.findAll();
        return cartItemList
                .stream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getProduct().getPrice())
                .reduce(0L, Long::sum);
    }

    public void updateProductToCart(AddToCartDTO addToCartDTO) {
        Product product = productRepository.findById(addToCartDTO.getProductId()).orElseThrow();

        CartItem cartItem = cartItemRepository.findByProduct(product).orElseThrow();

        System.out.println(cartItem.getQuantity());
        cartItem.setQuantity(addToCartDTO.getQuantity());
        System.out.println(addToCartDTO.getQuantity());

        if (!validateQuantity(addToCartDTO.getProductId(), cartItem.getQuantity())) {
            throw new IllegalArgumentException("Not enough product");
        }

        cartItemRepository.save(cartItem);
    }


    @Transactional
    public void checkout(String name, String email, String address, String message, String username) {
        Set<CartItem> allCartItems = new HashSet<>(getCartItems());
        Long total = getTotalPrice();
        System.out.println("-----------------Creating Order-------------");
        Order order = new Order();
        User user = userRepository.findByUsername(username).orElseThrow();
        order.setAddress(address);
        order.setName(name);
        order.setEmail(email);
        order.setMessage(message);
        order.setTotal(total);
        order.setOrderDetails(new HashSet<>());
        order.setUser(user);
        orderRepository.save(order);

        System.out.println("-----------------Add Order Detail-------------");
        allCartItems.forEach((cartItem -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setQuantity(cartItem.getQuantity());
            orderDetail.setProduct(cartItem.getProduct());
            orderDetail.setOrder(order);
            orderDetailRepository.save(orderDetail);
            long updateproduct = cartItem.getProduct().getQuantity() - cartItem.getQuantity();
            Product product = cartItem.getProduct();
            product.setQuantity(updateproduct);
            productRepository.save(product);

            removeProductFromCart(cartItem.getProduct().getId().toString(),username);
        }));
    }
}
