package com.example.midterm.services;

import com.example.midterm.model.OrderDetail;
import com.example.midterm.repos.OrderDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    @Autowired
    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<OrderDetail> getOrderDetail(Long id) {
        return orderDetailRepository.findByOrderId(id);
    }

}
