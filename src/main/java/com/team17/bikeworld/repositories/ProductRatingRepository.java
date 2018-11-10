package com.team17.bikeworld.repositories;

import com.team17.bikeworld.entity.Account;
import com.team17.bikeworld.entity.Product;
import com.team17.bikeworld.entity.ProductRating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRatingRepository extends JpaRepository<ProductRating, Integer> {
    Page<ProductRating> getByProductId(Product product, Pageable pageable);

    ProductRating getByProductIdAndAccountUsename(Product product, Account account);
}
