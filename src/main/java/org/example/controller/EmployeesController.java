package org.example.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.infractructure.configuration.database.entity.EmployeeEntity;
import org.example.infractructure.configuration.database.repository.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeesController {

    private final EmployeeRepository employeeRepository;

    @PostMapping("/add")
    public String addEmployees(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "surname") String surname,
            @RequestParam(value = "salary") String salary) {

        EmployeeEntity newEmployee = EmployeeEntity.builder()
                .name(name)
                .surname(surname)
                .salary(new BigDecimal(salary))
                .build();
        employeeRepository.save(newEmployee);
        return "redirect:/employees" ;
    }

    @GetMapping
    public String employees(Model model){
        List<EmployeeEntity> employees= employeeRepository.findAll();
        model.addAttribute("employees", employees);
        model.addAttribute("updateEmployeeDTO", new EmployeeDTO());
        return "employees";
    }

    @GetMapping("/show/{employeeId}")
    public String showEmployeeDetails(@PathVariable Integer employeeId, Model model){
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("EmployeeEntity not found, employeeId: [%s]".formatted(employeeId)
                ));
//        do modelu dodajemy pracownika i pod tym kluczem przechowujemy encję
        model.addAttribute("employee", employeeEntity);
//        zwracam nazwę widoku
        return "employeeDetails";
    }



    @PutMapping("/update")
    public String updateEmployee(
            @ModelAttribute("updateEmployeeDTO") EmployeeDTO updateEmployeeDTO){
//        najpierw znalezc pracownika:
        EmployeeEntity employeeEntity = employeeRepository.findById(updateEmployeeDTO.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("EmployeeEntity not found, employeeId: [%s]".formatted(updateEmployeeDTO.getEmployeeId())
                ));
//        potem zmienic
        employeeEntity.setName(updateEmployeeDTO.getName());
        employeeEntity.setSurname(updateEmployeeDTO.getSurname());
        employeeEntity.setSalary(updateEmployeeDTO.getSalary());
        employeeRepository.save(employeeEntity);
        return "redirect:/employees";

    }
    @DeleteMapping("/delete/{employeeId}")
    public String deleteEmployee(@PathVariable Integer employeeId){
        employeeRepository.deleteById(employeeId);
        return "redirect:/employees";
    }
}
