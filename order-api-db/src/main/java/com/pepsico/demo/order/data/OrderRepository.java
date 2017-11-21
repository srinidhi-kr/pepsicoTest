package com.pepsico.demo.order.data;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.pepsico.demo.order.model.CustOrder;

public interface OrderRepository extends CrudRepository<CustOrder, String>{

}
