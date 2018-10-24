package com.team17.bikeworld.repositories;


import com.team17.bikeworld.entity.Brand;
import com.team17.bikeworld.entity.Category;
import com.team17.bikeworld.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

<<<<<<< HEAD
public interface ProductRepository extends JpaRepository<Product, Integer> {

//    @Query("SELECT u FROM Product u WHERE u.category_id = ?1")
//    List<Product> findProductByCategoryId(int cateId);
//
//
//    @Modifying
//    @Query(value = "INSERT INTO `product` ( `name`, `price`, `description`, `longitude`, `latitude`, `address`,`postDate`, `brandId`, `categoryId`) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8,?9)", nativeQuery = true)
//    Product addNew(String name,
//                   Double price,
//                   String description,
//                   Double longitude,
//                   Double latitude,
//                   String address,
//                   Date postDate,
//                   Brand brandId,
//                   Category categoryId);
//
//    @Modifying
//    @Query(value = "UPDATE `product` SET status = '1' WHERE id = ?1", nativeQuery = true)
//    Integer disableProduct(int id);
//
//    List<Product> findAllByCate(int cateId);
//
//    List<Product> searchByName(String searchValue);
=======
public interface ProductRepository<Pro> extends JpaRepository<Product, Integer> {

    @Query("SELECT u FROM Product u WHERE u.categoryId = ?1")
    List<Product> findProductByCategoryId(Category categoryId);


    @Modifying
    @Query(value = "INSERT INTO `product` ( `name`, `price`, `description`, `longitude`, `latitude`, `address`,`postDate`, `brandId`, `categoryId`) VALUES (?1, ?2, ?3, ?4, ?5, ?6, ?7, ?8,?9)", nativeQuery = true)
    Product addNew(String name,
                   Double price,
                   String description,
                   Double longitude,
                   Double latitude,
                   String address,
                   Date postDate,
                   Brand brandId,
                   Category categoryId);

    @Modifying
    @Query(value = "UPDATE `product` SET status = '2' WHERE id = ?1", nativeQuery = true)
    Integer disableProduct(int id);



    @Query("SELECT u FROM Product u WHERE u.name like  %?1%")
    List<Product> searchByName(String name);


    @Modifying
    @Query(value = "UPDATE `product` SET status = '1' WHERE id = ?1", nativeQuery = true)
    Integer activateTradeItem(int id);

>>>>>>> master
}
