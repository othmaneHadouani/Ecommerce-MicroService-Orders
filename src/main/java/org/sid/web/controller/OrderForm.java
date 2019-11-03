package org.sid.web.controller;

import lombok.Data;
import org.sid.entities.Client;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderForm {
    private Client client=new Client();
    private List<OrderProduct> products=new ArrayList<>();
}

@Data
class OrderProduct{
    private Long id;
    private int quantity;

}

@Data
class User{
    private String username;
    private String walo ;

}
