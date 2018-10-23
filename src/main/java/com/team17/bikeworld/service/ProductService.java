package com.team17.bikeworld.service;

import com.team17.bikeworld.entity.Product;
import com.team17.bikeworld.entity.ProductImage;
import com.team17.bikeworld.model.ProductModel;
import com.team17.bikeworld.repositories.ProductImageRepository;
import com.team17.bikeworld.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    private final Path rootLocation = Paths.get("src/main/resources/static/images").toAbsolutePath().normalize();


    public ProductService(ProductRepository productRepository, ProductImageRepository productImageRepository) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
    }

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    public List<Product> findProductByCate(int cateId) {
        List<Product> products = productRepository.findProductByCategoryId(cateId);
        return products;
    }

    public boolean addProduct(ProductModel mpro, MultipartFile image) {
        try {
            if (mpro != null) {

                Product product = productRepository.addNew(mpro.getName(), mpro.getPrice(), mpro.getDescription(), mpro.getLongitude(), mpro.getLatitude(), mpro.getAddress(), new Date(), mpro.getBrandId(), mpro.getCategoryId());


                if (image != null) {
                    String fileName = image.getOriginalFilename() + "_" + product.getId() + ".jpg";
                    Files.createDirectories(rootLocation);
                    Files.copy(image.getInputStream(), this.rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                    ProductImage productImage = productImageRepository.addNew(fileName, product);
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean disableProduct(int id) {
        Integer count = productRepository.disableProduct(id);
        if (count > 0) {
            return true;
        }
        return false;
    }

    public List<Product> getByCate(int cateId){
        List<Product> products = productRepository.findAllByCate(cateId);
        return products;
    }

    public List<Product> searchByName(String searchValue){
        List<Product> products = productRepository.searchByName(searchValue);
        return products;
    }

    public boolean editProduct(ProductModel mpro) {

        Integer count = productRepository.editProduct(mpro.getName(), mpro.getPrice(), mpro.getDescription(), mpro.getLongitude(), mpro.getLatitude(), mpro.getAddress(), new Date(), mpro.getBrandId(), mpro.getCategoryId());
        if (count > 0) {
            return true;
        }
        return false;
    }

}
