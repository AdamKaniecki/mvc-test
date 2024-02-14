package org.example.infractructure.configuration;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.example.infractructure.configuration.database.entity.EmployeeEntity;
import org.example.infractructure.configuration.database.repository.EmployeeRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

//Dzieki tej implementacji mozna aktywowac metode przed uruchomieniem sie aplikacji

@Component
@AllArgsConstructor
public class BootstrapApplicationComponent implements ApplicationListener<ContextRefreshedEvent> {

    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public void onApplicationEvent(@NonNull ContextRefreshedEvent event) {
        employeeRepository.deleteAll();

        employeeRepository.save(EmployeeEntity.builder()
                .name("Stefan")
                .surname("Zajavka")
                .salary(new BigDecimal("52322.00"))
                .build());


        employeeRepository.save(EmployeeEntity.builder()
                .name("Agnieszka")
                .surname("Spring")
                .salary(new BigDecimal("62341.00"))
                .build());

        employeeRepository.save(EmployeeEntity.builder()
                .name("Tomasz")
                .surname("Hibernate")
                .salary(new BigDecimal("52231.00"))
                .build());

    }
}

//przy każdym starcie aplikacjibedą zapisywane te same dane, tak dla pokazania
