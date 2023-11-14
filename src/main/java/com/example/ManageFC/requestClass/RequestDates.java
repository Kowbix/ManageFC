package com.example.ManageFC.requestClass;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@ToString
public class RequestDates {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate dateFrom;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate dateTo;


    public RequestDates(LocalDate dateFrom, LocalDate dateTo) {
        this.dateFrom = beforeDate(dateFrom, dateTo);
        this.dateTo = afterDate(dateFrom, dateTo);
    }

    private LocalDate beforeDate(LocalDate dateFrom, LocalDate dateTo) {
        if(dateFrom.isAfter(dateTo)){
            return dateTo;
        }
        return dateFrom;
    }

    private LocalDate afterDate(LocalDate dateFrom, LocalDate dateTo) {
        if(dateTo.isBefore(dateFrom)){
            return dateFrom;
        }

        return dateTo;
    }
}
