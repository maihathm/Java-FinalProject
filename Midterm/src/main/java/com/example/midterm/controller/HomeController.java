package com.example.midterm.controller;

import com.example.midterm.dto.AddToCartDTO;
import com.example.midterm.model.CartItem;
import com.example.midterm.model.Product;
import com.example.midterm.repos.CartItemRepository;
import com.example.midterm.repos.RoleRepository;
import com.example.midterm.services.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final ProductService productService;
    private final BrandService brandService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final CartItemRepository cartItemRepository;
    private final CartItemSerivce cartItemSerivce;

    @GetMapping("/")
    public String home(Model model, Principal principal) throws Exception {
        if(principal != null){
            List<CartItem> list = cartItemSerivce.getCartItemByUser(principal.getName());
            model.addAttribute("cartItems",list);
            model.addAttribute("user",principal.getName());
        }
        model.addAttribute("hotProducts", productService.getAllHotProducts());
        model.addAttribute("allBrands", brandService.getAllBrand());
        model.addAttribute("allCategories", categoryService.getAllCategories());
        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model, Principal principal,
                       @RequestParam(value = "page", required = false, defaultValue = "0") int page) throws Exception {
        if(principal != null){
            List<CartItem> list = cartItemSerivce.getCartItemByUser(principal.getName());
            model.addAttribute("cartItems",list);
            model.addAttribute("user",principal.getName());
        }
        model.addAttribute("allCategories",  categoryService.getAllCategories());
        model.addAttribute("allBrands", brandService.getAllBrand());
        int pageSize = 8; // Số sản phẩm trên mỗi trang
        System.out.println("Successfully page");
        model.addAttribute("currentPage", page);
        Page<Product> productPage = productService.getProductsByShop(page, pageSize);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("products", productPage.getContent());
        return "shop";
    }

    @GetMapping("/brand")
    public String brand(Principal principal,
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            Model model
    ) throws Exception {
        if(principal != null){
            List<CartItem> list = cartItemSerivce.getCartItemByUser(principal.getName());
            model.addAttribute("cartItems",list);
            model.addAttribute("user",principal.getName());
        }
        model.addAttribute("allCategories",  categoryService.getAllCategories());
        model.addAttribute("allBrands", brandService.getAllBrand());

        if (id != null && !id.isEmpty()) {
            int pageSize = 5; // Số sản phẩm trên mỗi trang
            System.out.println("Successfully page");
            model.addAttribute("currentPage", page);
            model.addAttribute("brand", brandService.getBrandById(Long.valueOf(id)));
            Page<Product> productPage = productService.getProductsByBrandId(Long.valueOf(id), page, pageSize);
            model.addAttribute("totalPages", productPage.getTotalPages());
            model.addAttribute("totalItems", productPage.getTotalElements());
            model.addAttribute("products", productPage.getContent());
            return "brand";
        }

        return "brand";
    }

    @GetMapping("/category")
    public String category(Principal principal,
            @RequestParam(value = "id", required = false) String id,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            Model model
    ) throws Exception {
        if(principal != null){
            List<CartItem> list = cartItemSerivce.getCartItemByUser(principal.getName());
            model.addAttribute("cartItems",list);
            model.addAttribute("user",principal.getName());
        }
        model.addAttribute("allCategories",  categoryService.getAllCategories());
        model.addAttribute("allBrands", brandService.getAllBrand());

        if (id != null && !id.isEmpty()) {
            int pageSize = 5; // Số sản phẩm trên mỗi trang
            System.out.println("Successfully page");
            model.addAttribute("currentPage", page);
            Page<Product> productPage = productService.getProductsByCategoryId(Long.valueOf(id), page, pageSize);
            model.addAttribute("totalPages", productPage.getTotalPages());
            model.addAttribute("totalItems", productPage.getTotalElements());
            model.addAttribute("products", productPage.getContent());
            model.addAttribute("category", categoryService.getCategoryById(Long.valueOf(id)));
            return "category";
        }

        return "category";
    }

    @GetMapping("/add-to-cart")
    @ResponseBody
    public ResponseEntity<Integer> addToCart(
            @RequestParam("productId") Long productId,
            @RequestParam("quantity") int quantity, Principal principal) {
        System.out.println(productId);

        try {
            // Thực hiện thêm sản phẩm vào giỏ hàng
            AddToCartDTO addToCartDTO = new AddToCartDTO();
            addToCartDTO.setProductId(Long.valueOf(productId));
            addToCartDTO.setQuantity(Long.valueOf(quantity));
            addToCartDTO.setUsername(principal.getName());
            shoppingCartService.addToCart(addToCartDTO);
            System.out.println(productId);

            // Trả về một phản hồi thành công
            return ResponseEntity.ok(cartItemSerivce.getCartItemByUser(principal.getName()).size());

        } catch (Exception e) {
            System.out.println(productId);
            // Trả về một phản hồi lỗi nếu có lỗi xảy ra
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(0);
        }
    }
}
