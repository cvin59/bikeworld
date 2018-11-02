package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.entity.*;
import com.team17.bikeworld.model.ProductModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.ProductImageRepository;
import com.team17.bikeworld.repositories.ProductRepository;
import com.team17.bikeworld.transformer.ProductTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.LinkedList;
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

    public List<Product> getProductByCate(int id, Pageable pageable) {
        Category category = new Category();
        category.setId(id);
        List<Product> products = productRepository.findByCategoryId(category, pageable);
        return products;
    }

    public List<Product> getProductByBrand(int id, Pageable pageable) {
        Brand brand = new Brand();
        brand.setId(id);
        List<Product> products = productRepository.findByBrandId(brand, pageable);
        return products;
    }

    public List<Product> getProductBySeller(String username, Pageable pageable) {
        Account seller = new Account();
        seller.setUsername(username);
        List<Product> products = productRepository.findByAccountUsename(seller, pageable);
        return products;
    }

    public Response<Product> createProduct(ProductModel newProduct, MultipartFile[] images) {
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
                Product productEntity = new Product();
                productEntity = productTransformer.ProductModelToEntity(newProduct, productEntity);

                // Save Product
                LOGGER.info("Save product: " + productEntity.toString());
                productEntity = productRepository.save(productEntity);

                // Save images
                if (images != null) {
                    List<String> imageList = new LinkedList<>();
                    newProduct.setImages(imageList);

                    for (MultipartFile image : images) {
                        handleImage(newProduct, image);
                    }

                    List<ProductImage> savedImage = productTransformer.ImageModelToEntity(productEntity, newProduct);
                    for (ProductImage image : savedImage) {
                        productImageRepository.save(image);
                    }

                }
                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, productEntity);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e.getCause());
                response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            }
        }
        return response;
    }

    public Response<Product> updateProduct(ProductModel updatedProduct, MultipartFile[] images) {
        Response<Product> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        if (updatedProduct != null) {

            try {
                // Transform Model to Entity
                Integer id = updatedProduct.getId();
                Optional<Product> optionalProduct = productRepository.findById(id);

                Product productEntity = optionalProduct.get();

                productEntity = productTransformer.ProductModelToEntity(updatedProduct, productEntity);
                //test
                LOGGER.info("Edit product: " + productEntity.toString());
                productEntity = productRepository.save(productEntity);

                // Save images
                if (images != null) {
                    List<String> imageList = new LinkedList<>();
                    updatedProduct.setImages(imageList);

                    for (MultipartFile image : images) {
                        handleImage(updatedProduct, image);
                    }

                    List<ProductImage> savedImage = productTransformer.ImageModelToEntity(productEntity, updatedProduct);
                    for (ProductImage image : savedImage) {
                        productImageRepository.save(image);
                    }

                }

                response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, productEntity);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e.getCause());
                response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            }
        }
        return response;
    }

    public Product getProductById(int id) {
        Optional<Product> entity = productRepository.findById(id);
        return entity.orElse(null);
    }

    public boolean activateTradeItem(int id) {
        Integer count = productRepository.activateTradeItem(id);
        if (count > 0) {
            return true;
        }
        return false;
    }

    public List<Product> searchByName(String searchValue) {
        List<Product> products = productRepository.searchByName(searchValue);
        return products;
    }

    private void handleImage(ProductModel model, MultipartFile image) throws IOException {
        if (image != null) {
            String fileName = image.getOriginalFilename();
            Files.createDirectories(rootLocation);
            Files.copy(image.getInputStream(), rootLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            model.getImages().add("/images/" + fileName);
            LOGGER.info("file name:" + fileName);
        }
    }

    public List<ProductImage> getImagesByProduct(Product product) {
        Optional<List<ProductImage>> images = productImageRepository.findByProductId(product);
        return images.orElse(null);
    }

    public void deleteImage(List<Integer> deleteList) {
        for (int i = 0; i < deleteList.size(); i++) {
            Integer deleteId = deleteList.get(i);
            productImageRepository.deleteById(deleteId);
        }
    }
}
