package com.abc.springRestCRUD.service;

import com.abc.springRestCRUD.entity.Product;
import com.abc.springRestCRUD.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class  ProductService {

    @Autowired
    ProductRepository productRepository;

//    private final ProductRepository productRepository;
    private final FileStorageService fileStorageService;

//    public ProductService(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    public List<Product> getAllByName(String name){
        return productRepository.getByName(name);
    }

    public List<Product> getAll(){
        return productRepository.findAll();
    }

    public List<Product> getAllByName(){
        return productRepository.findByOrderByNameDesc();
    }

    public List<Product> getAllByStockQuantity(Integer stockQuantity){
        return productRepository.findByStockQuantityGreaterThanOrderByNameDesc(stockQuantity);
    }

    public Optional<Product> getById(Long id){
        return  productRepository.findById(id);
    }

    public Product create(Product p){
        return productRepository.save(p);
    }

    public Product update(Long id, Product updatedProduct){
        Product existing = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with ID:" + id));
//        existing.setName(updatedProduct.getName());
//        existing.setDescription(updatedProduct.getDescription());
//        existing.setPrice(updatedProduct.getPrice());
//        existing.setStockQuantity(updatedProduct.getStockQuantity());
//        existing.setCategory(updatedProduct.getCategory());
//        existing.setAvailable(updatedProduct.getAvailable());
//        existing.setReleaseDate(updatedProduct.getReleaseDate());
//        existing.setImageUrls(updatedProduct.getImageUrls());
//        existing.setRating(updatedProduct.getRating());

        BeanUtils.copyProperties(updatedProduct, existing);
        return  productRepository.save(existing);
    }

    public void delete(Long id){
        productRepository.deleteById(id);
    }


    public List<Product> getAllByNameAndPrice(String name, BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.getAllByNameAndPrice(name, minPrice, maxPrice);
    }

    public List<Product> searchProducts(String name, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice) {
        return productRepository.searchProducts(name, categoryId, minPrice, maxPrice);
    }

    public Page<Product> advancedSearch(String name, Long categoryId, BigDecimal minPrice, BigDecimal maxPrice, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.advancedSearch(name, categoryId, minPrice, maxPrice, pageable);
    }


    public Page<Product> searchNameAndRating(String name, Double rating, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        return productRepository.searchNameAndRating(name, rating, pageable);
    }

    public Product createWithImage(Product p, MultipartFile[] multipartFile) throws IOException{
        List<String> urls = new ArrayList<>();
        for(MultipartFile file: multipartFile){
            urls.add(fileStorageService.saveFile(file));
        }
        p.setImageUrls(urls);
        return productRepository.save(p);
    }
}
