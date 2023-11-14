package com.example.ManageFC.repository.produckts;

import com.example.ManageFC.entity.User;
import com.example.ManageFC.entity.produckts.Product;
import com.example.ManageFC.entity.produckts.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductById(Long productId);

    @Query("SELECT p FROM Product p JOIN p.team t WHERE t.id = :teamId AND p.isActive = true")
    List<Product> findActiveProductsByTeamId(Long teamId);

    @Query("SELECT p FROM Product p JOIN p.team t WHERE t.id = :teamId AND p.isActive = false")
    List<Product> findInactiveProductsByTeamId(Long teamId);

}
