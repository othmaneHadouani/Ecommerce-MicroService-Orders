package org.sid.proxies;
import org.sid.entities.Product;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@FeignClient(name = "server-proxy")

public interface MicroserviceProduitsProxy{

    @RequestMapping(value = "/microservice-produits/products" ,method = RequestMethod.GET)
    public ArrayList<Product> getProductsPerMotClee();

    @GetMapping(value = "/microservice-produits/products/{id}")
    public Product getProductById(@PathVariable("id") Long id);

}
