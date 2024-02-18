package org.example.infrastructure.database;

import lombok.AllArgsConstructor;
import org.assertj.core.api.Assertions;
import org.example.infractructure.configuration.database.entity.EmployeeEntity;
import org.example.infractructure.configuration.database.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.example.util.EmployeeFixtures.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeeRepositoryDataJpaTest {

    private EmployeeRepository employeeRepository;

    @Test
    void thatEmployeeCanBeSavedCorrectly(){
//        given
      var employees = List.of(someEmployee1(), someEmployee2(), someEmployee3());
      employeeRepository.saveAllAndFlush(employees);
//        when
        List<EmployeeEntity> employeesFound = employeeRepository.findAll();
//        then
        assertThat(employeesFound.size()).isEqualTo(3);
    }


}
