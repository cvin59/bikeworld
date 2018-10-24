package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.Category;
import com.team17.bikeworld.entity.Product;
import com.team17.bikeworld.entity.ProductImage;
import com.team17.bikeworld.model.ProductModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.ProductImageRepository;
import com.team17.bikeworld.repositories.ProductRepository;
import com.team17.bikeworld.transformer.ProductTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final ProductTransformer productTransformer;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private final Path rootLocation = Paths.get("src/main/resources/static/images").toAbsolutePath().normalize();


    public ProductService(ProductRepository productRepository, ProductImageRepository productImageRepository, ProductTransformer productTransformer) {
        this.productRepository = productRepository;
        this.productImageRepository = productImageRepository;
        this.productTransformer = productTransformer;
    }

    public List<Product> findAll() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    public List<Product> findProductByCate(Category cateId) {
        List<Product> products = productRepository.findProductByCategoryId(cateId);
        return products;
    }

    public boolean addProduct(ProductModel mpro, MultipartFile image) {
        try {
            if (mpro != null) {

                Product product = productRepository.addNew(mpro.getName(), mpro.getPrice(), mpro.getDescription(), mpro.getLongtitude(), mpro.getLatitude(), mpro.getAddress(), new Date(), mpro.getBrandId(), mpro.getCategoryId());
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


//    public boolean disableProduct(int id) {
//        Integer count = productRepository.disableProduct(id);
//        if (count > 0) {
//            return true;
//        }
//        return false;
//    }
//
//    public List<Product> getByCate(int cateId){
//        List<Product> products = productRepository.findAllByCate(cateId);
//        return products;
//    }
//
//    public List<Product> searchByName(String searchValue){
//        List<Product> products = productRepository.searchByName(searchValue);
//        return products;
//    }
  
    public Response<Product> createProduct(ProductModel newProduct, MultipartFile images) {
        Response<Product> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        if (newProduct != null) {
            // Add new attributes
            newProduct.setId(0);
            // Add Postdate
            Date date = new Date();
            date.getTime();
            newProduct.setPostDate(date);

            // Add Rating
            newProduct.setTotalRatePoint(0);
            newProduct.setTotalRater(0);

            // Add Status;
            newProduct.setStatus(CoreConstant.STATUS_PRODUCT_AVAILABLE);
            try {
                // Transform Model to Entity
                Product productEntity = productTransformer.ProductModelToEntity(newProduct);

                // Lưu DB
                productRepository.save(productEntity);
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, productEntity);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e.getCause());
                response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            }
        }

        return response;
    }

    public Response<Product> updateProduct(ProductModel updatedProduct, MultipartFile images) {
        Response<Product> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        if (updatedProduct != null) {

            try {
                // Transform Model to Entity
                Integer id = updatedProduct.getId();
                Optional<Product> optionalProduct = productRepository.findById(id);
                Product productEntity = optionalProduct.get();
                productEntity.setDescription(updatedProduct.getDescription());

                //test
                productRepository.save(productEntity);
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, productEntity);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e.getCause());
                response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            }
        }

        return response;
    }

    public boolean activateTradeItem(int id) {
        Integer count = productRepository.activateTradeItem(id);
        if (count > 0) {
            return true;
        }
        return false;
    }

//    public List<Product> getByCate(int cateId) {
//        List<Product> products = productRepository.findProductByCategoryId(cateId);
//        return products;
//    }

    public List<Product> searchByName(String searchValue) {
        List<Product> products = productRepository.searchByName(searchValue);
        return products;
    }

    public boolean editProduct(ProductModel mpro) {

        Optional<Product> proById = productRepository.findById(mpro.getId());

        if (proById != null) {
            Product product = proById.get();
            product.setName(mpro.getName());
            product.setBrandId(mpro.getBrandId());
            product.setDescription(mpro.getDescription());
            product.setLatitude(mpro.getLatitude());
            product.setLongitude(mpro.getLongtitude());
            product.setAddress(mpro.getAddress());
            product.setPostDate(new Date());
            productRepository.save(product);
            return true;
        }
        return false;
    }
}
