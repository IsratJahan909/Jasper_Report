package com.abc.springRestCRUD.controller;

import com.abc.springRestCRUD.entity.Product;
import com.abc.springRestCRUD.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
//@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAll();
    }

//    @GetMapping("/nameDesc")
//    public List<Product> getProductByName(){
//        return productService.getAllByName();
//    }

    @GetMapping("/searchByNameAndPrice")
    public List<Product> getBySearch(@RequestParam(required = false) String name,
                                     @RequestParam(required = false) BigDecimal minPrice,
                                     @RequestParam BigDecimal maxPrice){
        return productService.getAllByNameAndPrice(name, minPrice, maxPrice);
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam(required = false) String name,
                                        @RequestParam(required = false) Long categoryId,
                                        @RequestParam(required = false) BigDecimal minPrice,
                                        @RequestParam(required = false) BigDecimal maxPrice){
        return productService.searchProducts(name, categoryId, minPrice, maxPrice);
    }

    @GetMapping("/advancedSearch")
    public Page<Product> advancedSearch(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) Long categoryId,
                                       @RequestParam(required = false) BigDecimal minPrice,
                                       @RequestParam(required = false) BigDecimal maxPrice,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "id") String sortBy,
                                       @RequestParam(defaultValue = "asc") String direction
                                       ){
        return productService.advancedSearch(name, categoryId, minPrice, maxPrice, page, size, sortBy, direction);
    }

    @GetMapping("/searchNameAndRating")
    public Page<Product> searchNameAndRating(@RequestParam(required = false) String name,
                                             @RequestParam(required = false) Double rating,
                                             @RequestParam(defaultValue = "0") int page,
                                             @RequestParam(defaultValue = "10") int size){
        return productService.searchNameAndRating(name, rating, page, size);
    }

    @GetMapping("/Quantity/{qt}")
    public List<Product> getProductByStockQuantity(@PathVariable  Integer qt){
        return productService.getAllByStockQuantity(qt);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return productService.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public List<Product> getProductByName(@PathVariable String name){
        return productService.getAllByName(name);
    }



    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product p){
        return ResponseEntity.ok(productService.create(p));
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProductNew(
            @RequestPart("product") Product p,
            @RequestPart("files")MultipartFile[] multipartFile) throws IOException {
        return ResponseEntity.ok(productService.createWithImage(p,multipartFile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product p){
        return ResponseEntity.ok(productService.update(id, p));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
