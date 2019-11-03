package org.sid.web.controller;


import lombok.Data;
import org.sid.dao.ClientRepository;
import org.sid.dao.OrderItemRepository;
import org.sid.dao.OrderRepository;
import org.sid.dao.ProductRepository;
import org.sid.entities.Client;
import org.sid.entities.Order;
import org.sid.entities.OrderItem;
import org.sid.entities.Product;
import org.sid.proxies.MicroserviceProduitsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin("*")
@RestController
public class OrderController {



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
    @PostMapping("/orders")
    public Order saveOrder(@RequestBody OrderForm orderForm){
        Client client=new Client();
        client.setName(orderForm.getClient().getName());
        client.setEmail(orderForm.getClient().getEmail());
        client.setAddress(orderForm.getClient().getAddress());
        client.setPhoneNumber(orderForm.getClient().getPhoneNumber());
        client.setUsername(orderForm.getClient().getUsername());
        client=clientRepository.save(client);
        System.out.println(client.getId());

        Order order=new Order();
        order.setClient(client);
        order.setDate(new Date());
        order=orderRepository.save(order);
        double total=0;
        for(OrderProduct p:orderForm.getProducts()){
            OrderItem orderItem=new OrderItem();
            orderItem.setOrder(order);
            Product product=produitsProxy.getProductById(p.getId());
            productRepository.save(product);
            orderItem.setProduct(product);
            orderItem.setPrice(product.getCurrentPrice());
            orderItem.setQuantity(p.getQuantity());
            orderItemRepository.save(orderItem);
            total+=p.getQuantity()*product.getCurrentPrice();
        }
        order.setTotalAmount(total);
        return orderRepository.save(order);
    }


    @PostMapping("/getOrdersUser")
    public Page<Order> getOrdersUser( @RequestParam(name = "size",defaultValue = "8")int size,
                                      @RequestParam(name = "page",defaultValue = "0")int page,
                                      @RequestBody User user
                                      ){

        Page<Order> orders;
        orders=  orderRepository.chercherOrdersByUsername(user.getUsername(),new PageRequest(page,size));
        return orders;
    }

    @PostMapping("/editUserOrders")
    public void editUserOrders(@RequestBody FormEditUser formEditUser){

        ArrayList<Client> clients = clientRepository.findByUsername(formEditUser.getLastUserName());

        for (Client c:clients){

            c.setUsername(formEditUser.getNewUserName());

            clientRepository.save(c);
        }
    }

   /*   @GetMapping("/getOrdersUser/{username}")
    public Page<Order> getOrdersUser( @RequestParam(name = "size",defaultValue = "8")int size,
                                      @RequestParam(name = "page",defaultValue = "0")int page,
                                      @PathVariable("username") String username
                                      ){

        Page<Order> orders;
        orders=  orderRepository.chercherOrdersByUsername(username,new PageRequest(page,size));
        return orders;
    }*/



}

@Data
class FormEditUser{

    private String lastUserName;
    private String newUserName;
}
