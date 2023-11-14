package com.example.ManageFC.requestClass;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserUpdateRequest {

    private final String firstName;
    private final String lastName;
    private final String position;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final LocalDate birthDate;

}
