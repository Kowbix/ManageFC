package com.example.ManageFC.service.produckts;

import com.example.ManageFC.entity.produckts.Product;
import com.example.ManageFC.repository.produckts.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> getActiveProductsByTeamId(Long teamId){
        return productRepository.findActiveProductsByTeamId(teamId);
    }

    public List<Product> getInactivateProducts(Long teamId){
        return productRepository.findInactiveProductsByTeamId(teamId);
    }

    public Product getProductById(Long productId) {
        return productRepository.findProductById(productId);
    }

}
