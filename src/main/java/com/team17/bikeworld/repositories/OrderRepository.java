package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.Account;
import com.team17.bikeworld.entity.Order;
import com.team17.bikeworld.entity.Product;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findBySellerUsername(Account seller, Pageable pageable);

    Page<Order> findByBuyerUsername(Account buyer, Pageable pageable);

    List<Order> findByProductId(Product product);

    List<Order> getByProductIdAndBuyerUsername(Product product, Account account);
}
