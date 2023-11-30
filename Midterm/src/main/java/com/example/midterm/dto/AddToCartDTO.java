package com.example.midterm.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartDTO {
    private Long productId;
    private Long quantity;
}
