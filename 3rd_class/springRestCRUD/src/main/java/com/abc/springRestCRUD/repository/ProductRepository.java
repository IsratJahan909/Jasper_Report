package com.abc.springRestCRUD.repository;

import com.abc.springRestCRUD.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByOrderByNameDesc();
    List<Product> findByStockQuantityGreaterThanOrderByNameDesc(Integer stockQuantity);
//    List<Product> findByNameContaining(String text);
    @Query(value = "Select * from products where LOWER(name) like LOWER(CONCAT('%', :text, '%'))", nativeQuery = true)
    List<Product> getByName(@Param("text") String name);


    @Query(value = "Select * from products where LOWER(name) like LOWER(CONCAT('%', :text, '%'))" +
            "and price between :minPrice and :maxPrice ", nativeQuery = true)
    List<Product> getAllByNameAndPrice(@Param("text") String name, @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);


    @Query("""
            SELECT p FROM Product p
            WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))
            AND (:categoryId IS NULL OR p.category.id = :categoryId)
            AND (:minPrice IS NULL OR p.price >= :minPrice)
            AND (:maxPrice IS NULL OR p.price <= :maxPrice)
            """)
    List<Product> searchProducts(@Param("name") String name,
                                 @Param("categoryId") Long categoryId,
                                 @Param("minPrice") BigDecimal minPrice,
                                 @Param("maxPrice") BigDecimal maxPrice);



//    @Query(value = "Select * from products WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))\n" +
//            "            AND (:categoryId IS NULL OR p.category.id = :categoryId)\n" +
//            "            AND (:minPrice IS NULL OR p.price >= :minPrice)\n" +
//            "            AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
//    List<Product> searchProducts(@Param("name") String name,
//                                 @Param("categoryId") Long categoryId,
//                                 @Param("minPrice") BigDecimal minPrice,
//                                 @Param("maxPrice") BigDecimal maxPrice);


    @Query("""
            SELECT p FROM Product p
            WHERE (:name IS NULL OR LOWER(p.name) LIKE(CONCAT('%', :name, '%')))
            AND (:categoryId IS NULL OR p.category.id = :category)
            AND (:minPrice IS NULL OR p.price >= :minPrice)
            AND (:maxPrice IS NULL OR p.price <= :maxPrice)
            """)
    Page<Product> advancedSearch(@Param("name") String name, @Param("categoryId") Long categoryId, @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice, Pageable pageable);


    @Query("""
            SELECT p FROM Product p
            WHERE(:text IS NULL OR LOWER(p.name) LIKE(CONCAT('%', :text, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :text, '%')))
            AND (:rating IS NULL OR p.rating >= :rating)
            """)
    Page<Product> searchNameAndRating(@Param("text") String name, @Param("rating") Double rating, Pageable pageable);


}
