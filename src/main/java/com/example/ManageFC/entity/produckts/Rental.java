package com.example.ManageFC.entity.produckts;

import com.example.ManageFC.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@ToString
@Entity
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private LocalDate date;

    @ManyToOne
    private User user;

    public Rental(Product product, LocalDate date, User user) {
        this.product = product;
        this.date = date;
        this.user = user;
    }
}
