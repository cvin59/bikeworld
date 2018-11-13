package com.team17.bikeworld.service;

import com.team17.bikeworld.common.CoreConstant;
import com.team17.bikeworld.common.CrawlDistance;
import com.team17.bikeworld.common.Damerau;
import com.team17.bikeworld.common.RelevantProduct;
import com.team17.bikeworld.entity.*;
import com.team17.bikeworld.model.ProductModel;
import com.team17.bikeworld.model.ProductRatingModel;
import com.team17.bikeworld.model.Response;
import com.team17.bikeworld.repositories.ProductImageRepository;
import com.team17.bikeworld.repositories.ProductRatingRepository;
import com.team17.bikeworld.repositories.ProductRepository;
import com.team17.bikeworld.transformer.ProductRatingTransformer;
import com.team17.bikeworld.transformer.ProductTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import java.util.*;

@Service
public class ProductService {

    @Autowired
    ProductRatingRepository productRatingRepository;

    @Autowired
    ProductRatingTransformer productRatingTransformer;

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

    public Page<Product> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
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

    public Page<Product> getProductBySeller(String username, Pageable pageable) {
        Account seller = new Account();
        seller.setUsername(username);
        Page<Product> products = productRepository.findByAccountUsename(seller, pageable);
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
            newProduct.setStatusId(CoreConstant.STATUS_PRODUCT_AVAILABLE);
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

    public boolean subtractQuantity(int productId, int orderQuantity) {
        Product product = productRepository.getOne(productId);
        if (product.getQuantity() < orderQuantity) {
            return false;
        } else {
            int newQuantity = product.getQuantity() - orderQuantity;
            product.setQuantity(newQuantity);
            if (newQuantity == 0) {
                product = changeStatus(product, CoreConstant.STATUS_PRODUCT_SOLDOUT);
                return true;
            } else {
                productRepository.save(product);
                return true;
            }
        }
    }

    public void addQuantity(Product product, int orderQuantity) {
        int newQuantity = product.getQuantity() + orderQuantity;

        if (product.getStatusId().getId() == CoreConstant.STATUS_PRODUCT_SOLDOUT) {
            ProductStatus status = new ProductStatus();
            status.setId(CoreConstant.STATUS_PRODUCT_AVAILABLE);
            product.setStatusId(status);
        }

        product.setQuantity(newQuantity);
        productRepository.save(product);
    }

    public Product changeStatus(Product product, int statusId) {
        ProductStatus productStatus = new ProductStatus();
        productStatus.setId(statusId);
        product.setStatusId(productStatus);
        return productRepository.save(product);
    }

    public Product getProductById(int id) {
        Optional<Product> entity = productRepository.findById(id);
        return entity.orElse(null);
    }


    public Page<Product> searchByName(String searchValue, Pageable pageable) {
        Page<Product> products = productRepository.findByNameIgnoreCaseContaining(searchValue, pageable);
        return products;
    }

    public Page<Product> searchByNameAndSeller(String searchValue, String seller, Pageable pageable) {
        Account account = new Account();
        account.setUsername(seller);
        return productRepository.findByNameIgnoreCaseContainingAndAccountUsename(searchValue, account, pageable);
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

    public ProductRating rateProduct(ProductRatingModel model) {
        model.setId(0);

        Date date = new Date();
        date.getTime();
        model.setRateDate(date);

        Product product = productRepository.getOne(model.getProductId());
        int rate = product.getTotalRates() + 1;
        double ratePoint = product.getTotalRatePoint() + model.getPoint();

        product.setTotalRates(rate);
        product.setTotalRatePoint(ratePoint);
        productRepository.save(product);

        ProductRating rating = new ProductRating();
        rating = productRatingTransformer.RatingModelToEntity(rating, model);
        return productRatingRepository.save(rating);
    }


    public Page<ProductRating> getRates(int productId, Pageable pageable) {
        Product product = new Product();
        product.setId(productId);
        Page<ProductRating> ratings = productRatingRepository.getByProductId(product, pageable);
        return ratings;
    }

    public ProductRating getRate(int productId, String rater) {
        Product product = new Product();
        product.setId(productId);

        Account account = new Account();
        account.setUsername(rater);

        return productRatingRepository.getByProductIdAndAccountUsename(product, account);
    }

    public List<Product> findRelevant(String productName, int categoryId) {
        Category category = new Category();
        category.setId(categoryId);
        List<Product> categorizedProducts = productRepository.findByCategoryId(category);

        if (categorizedProducts.size() <= CoreConstant.MAX_RELEVANT_PRODUCT) {
            return categorizedProducts;
        } else {
            Damerau damerau = new Damerau();
            List<RelevantProduct> distanceList = new ArrayList<>();
            for (int i = 0; i < categorizedProducts.size(); i++) {
                Product product = categorizedProducts.get(i);
                distanceList.add(new RelevantProduct(damerau.distance(product.getName(), productName), product));
            }

            Collections.sort(distanceList, new Comparator<RelevantProduct>() {
                @Override
                public int compare(RelevantProduct o1, RelevantProduct o2) {
                    int d1 = o1.getDistance();
                    int d2 = o2.getDistance();
                    if (d1 == d2)
                        return 0;
                    else if (d1 < d2)
                        return -1;
                    else
                        return 1;
                }
            });
            List<Product> productList = new ArrayList<>();
            for (int i = 0; i < CoreConstant.MAX_RELEVANT_PRODUCT && i < distanceList.size(); i++) {
                productList.add(distanceList.get(i).getProduct());
            }
            return productList;
        }
    }

    public void editStatus(int productId) {
        Product product = productRepository.getOne(productId);
        int statusId = product.getStatusId().getId();

        if (statusId == CoreConstant.STATUS_PRODUCT_AVAILABLE || statusId == CoreConstant.STATUS_PRODUCT_SOLDOUT) {
            statusId = CoreConstant.STATUS_PRODUCT_HIDDEN;
        } else if (statusId == CoreConstant.STATUS_PRODUCT_HIDDEN) {
            int quantity = product.getQuantity();
            if (quantity > 0) {
                statusId = CoreConstant.STATUS_PRODUCT_AVAILABLE;
            } else {
                statusId = CoreConstant.STATUS_PRODUCT_SOLDOUT;
            }
        }

        changeStatus(product, statusId);
    }

    public List<Product> findProductHome() {
        return productRepository.randomProduct();
    }
}
