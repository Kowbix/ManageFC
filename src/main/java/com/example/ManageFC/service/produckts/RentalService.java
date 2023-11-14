package com.example.ManageFC.service.produckts;

import com.example.ManageFC.entity.produckts.Product;
import com.example.ManageFC.entity.produckts.Rental;
import com.example.ManageFC.repository.produckts.ProductRepository;
import com.example.ManageFC.repository.produckts.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final ProductRepository productRepository;

    public Rental rentProduct(Rental rental) {
        Product product = productRepository.findProductById(rental.getProduct().getId());
        product.getRental().add(rental);
        productRepository.save(product);

        return rentalRepository.save(rental);
    }

    public boolean isProductOccupied(Long productId, LocalDate date){

        if(rentalRepository.isProductOccupied(productId, date) == 0){
            return false;
        }

        return true;
    }

    public List<Rental> getRentByUserAndDate(LocalDate date, Long userId) {
        return rentalRepository.findRentByUserAndDate(userId, date);
    }

    public List<Rental> getRentalByCompartmentDateAndUserId(LocalDate dateFrom, LocalDate dateTo, Long userId) {
        List<Rental> rental = rentalRepository.findRentalByCompartmentDateAndUserId(dateFrom, dateTo, userId);

        Comparator<Rental> comparator = Comparator
                .comparing((Rental r) -> r.getDate())
                .thenComparing((Rental r) -> r.getProduct().getName());

        Collections.sort(rental, comparator);

        return rental;
    }

    public List<Rental> getRentalByUserId(Long userId) {
        List<Rental> rental = rentalRepository.findRentalByUserId(userId);

        Comparator<Rental> comparator = Comparator
                .comparing((Rental r) -> r.getDate())
                .thenComparing((Rental r) -> r.getProduct().getName());

        Collections.sort(rental, comparator);

        return rental;
    }

    public Optional<Rental> getRentalById(Long rentalId) {
        return rentalRepository.findById(rentalId);
    }

    public void cancelRental(Rental rental) {
        Product product = productRepository.findProductById(rental.getProduct().getId());
        product.getRental().remove(rental);
        productRepository.save(product);
        rentalRepository.delete(rental);
    }
}
