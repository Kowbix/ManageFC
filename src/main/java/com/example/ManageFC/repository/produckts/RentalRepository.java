package com.example.ManageFC.repository.produckts;

import com.example.ManageFC.entity.produckts.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query("SELECT count(*) FROM Rental r JOIN r.product p WHERE p.id= :productId AND r.date= :date")
    int isProductOccupied(Long productId, LocalDate date);

    @Query("SELECT r FROM Rental r JOIN r.user u WHERE u.id= :userId AND r.date= :date")
    List<Rental> findRentByUserAndDate(Long userId, LocalDate date);

    @Query("SELECT r FROM Rental r JOIN r.user u WHERE r.date>= :dateFrom AND r.date< :dateTo AND u.id= :userId")
    List<Rental> findRentalByCompartmentDateAndUserId(LocalDate dateFrom, LocalDate dateTo, Long userId);

    @Query("SELECT r FROM Rental r JOIN r.user u WHERE u.id= :userId")
    List<Rental> findRentalByUserId(Long userId);
}
