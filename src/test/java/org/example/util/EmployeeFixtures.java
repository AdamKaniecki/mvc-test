package org.example.util;

import lombok.experimental.UtilityClass;
import org.example.infractructure.configuration.database.entity.EmployeeEntity;

import java.math.BigDecimal;

@UtilityClass
public class EmployeeFixtures {

public static EmployeeEntity someEmployee1(){
    return EmployeeEntity.builder()
            .name("Agnieszka")
            .surname("Zajavkowa")
            .salary(new BigDecimal("5230.00"))
            .phone("+48 666 545 666")
            .email("zajavka@zajavka.pl")
            .build();
}

    public static EmployeeEntity someEmployee2(){
        return EmployeeEntity.builder()
                .name("Tomasz")
                .surname("Pasieka")
                .salary(new BigDecimal("6430.00"))
                .phone("+48 600 545 666")
                .email("zajavka2@zajavka.pl")
                .build();
    }

    public static EmployeeEntity someEmployee3(){
        return EmployeeEntity.builder()
                .name("Bartosz")
                .surname("Nowak")
                .salary(new BigDecimal("7230.00"))
                .phone("+48 500 545 666")
                .email("zajavka3@zajavka.pl")
                .build();
    }
}
