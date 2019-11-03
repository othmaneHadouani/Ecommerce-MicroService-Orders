package org.sid.web.controller;


import org.sid.dao.*;
import org.sid.entities.*;
import org.sid.proxies.MicroserviceProduitsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin("*")
@RestController
public class PaymentController {



    @Autowired
    MicroserviceProduitsProxy produitsProxy;


    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private PaymentRepository paymentRepository;
    @PostMapping("/payOrder")
    public Payment saveOrder(@RequestBody PaymentForm paymentForm
    ){
        Payment payment =new Payment();
        payment.setCardNumber(paymentForm.getCardNumber());
        payment.setCardType(paymentForm.getType());
        payment.setDatePayment(new Date());
        payment.setOrder(orderRepository.findById(paymentForm.getIdOrder()).get());


        paymentRepository.save(payment);

        Order order = orderRepository.findById(paymentForm.getIdOrder()).get();
        order.setPayment(payment);
        orderRepository.save(order);


        return payment;
    }




}
