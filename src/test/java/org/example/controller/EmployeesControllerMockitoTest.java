package org.example.controller;

import org.example.infractructure.configuration.database.entity.EmployeeEntity;
import org.example.infractructure.configuration.database.repository.EmployeeRepository;
import org.example.util.EmployeeFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ExtendedModelMap;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EmployeesControllerMockitoTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeesController employeesController;

    @Test
    void thatRetrievingEmployeeDetailsWorkCorrectly() {
//        given
        Integer employeeId = 10;

        EmployeeEntity employeeEntity = EmployeeFixtures.someEmployee1();
//        nie mamy tu kontekstu webowego więc jakoś instancje trzeba potworzyć więc(zamiast Modelu):
        ExtendedModelMap modelMap = new ExtendedModelMap();
        Mockito.when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(employeeEntity));
//        when
        String result = employeesController.showEmployeeDetails(employeeId, modelMap);

//        then
        assertThat(result).isEqualTo("employeeDetails");
        assertThat(modelMap.getAttribute("employee")).isEqualTo(employeeEntity);
    }

}