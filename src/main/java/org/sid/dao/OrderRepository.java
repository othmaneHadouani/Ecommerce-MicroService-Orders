package org.sid.dao;


import org.sid.entities.Client;
import org.sid.entities.Order;
import org.sid.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin("*")
@RepositoryRestResource
public interface OrderRepository extends JpaRepository<Order, Long> {



    @Query("select o from Order o inner join o.client c where c.username =:x")
    public Page<Order> chercherOrdersByUsername(@Param("x") String username, Pageable pageable);


}
