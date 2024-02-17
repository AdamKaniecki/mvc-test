package org.example.controller;

import lombok.AllArgsConstructor;
import org.example.infractructure.configuration.database.repository.EmployeeRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//najlepiej narzucić że chcę podnieść Webkontekst tylko na podstawie wybranego kontrollera a nie na podstawie
//całej aplikacji
@WebMvcTest(controllers = EmployeesController.class)
//to jest potrzebne żeby móc wstrzyknąć pola MockMvc oraz EmployeeRepository do konstruktora
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EmployeesControllerWebMvcTest {

//    symuluje wywołania przeglądarki
    private MockMvc mockMvc;

    @MockBean
    private EmployeeRepository employeeRepository;


//w tym teście sprawdzam jednocześnie status odpowiedzi, model i zwracany widok:
    @ParameterizedTest
    @MethodSource
    void thatPhoneValidationWorksCorrectly(Boolean correctPhone, String phone) throws Exception {

//        given
//        tworzenie parametrów:
        LinkedMultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        Map<String,String> parametersMap = EmployeeDTO.builder().phone(phone).build().asMap();
        parametersMap.forEach(parameters::add);


//        when, then
//        jeśli numer telefonu jest poprawny:
        if(correctPhone) {

//       symulacja gdy klient wysyła posta na serwer i oczekuję że serwer tego posta w testach obsłuży
//        i można sobie wykorzystać zainicjowane wczesniej parametry
            mockMvc.perform(MockMvcRequestBuilders.post("/employees/add").params(parameters))
                    .andExpect(MockMvcResultMatchers.status().isFound())
//                tu mowię o tym że jeśli ten request będzie obsługiwany przez serwer poprawnie to nie będę miał
//                modelu errorMessage w tym requescie bo nie będę miał błędów
                    .andExpect(model().attributeDoesNotExist("errorMessage"))
                    .andExpect(view().name("redirect:/employees"));

        } else{
            mockMvc.perform(MockMvcRequestBuilders.post("/employees/add").params(parameters))
//                    jesli numer nie jest poprawny to zwróć BadRequest
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
//                    modell errorMessage powinien istnieć
                    .andExpect(model().attributeExists("errorMessage"))
//     korzystam z Matchers (hamcrest), w wiadomości errorMessage powinien zawierać się wprowadzony
//                    błędny numer telefonu symulowanego klienta

                    .andExpect(model().attribute("errorMessage", Matchers.containsString(phone)))
//                    zwróć widok błędu
                    .andExpect(view().name("error"));
        }
    }

    public static Stream<Arguments> thatPhoneValidationWorksCorrectly() {
        return   Stream.of(
                Arguments.of(false, ""),
                Arguments.of(false, "+48 504 203 260@@"),
                Arguments.of(false, "+48.504.203.260"),
                Arguments.of(false, "+55(123) 456-78-90-"),
                Arguments.of(false, "+55(123) - 456-78-90"),
                Arguments.of(false, "504.203.260"),
                Arguments.of(false, " "),
                Arguments.of(false, "-"),
                Arguments.of(false, "()"),
                Arguments.of(false, "() + ()"),
                Arguments.of(false, "(21 7777"),
                Arguments.of(false, "+48 (21)"),
                Arguments.of(false, "+"),
                Arguments.of(false, " 1"),
                Arguments.of(false, "1"),
                Arguments.of(false, "4812504203260)"),
                Arguments.of(true, "+48 504 203 260"),
                Arguments.of(false, "+48 (12) 504 203 260"),
                Arguments.of(false, "+48 (12) 504-203-260"),
                Arguments.of(false, "+48(12)504203260"),
                Arguments.of(false, "+4812504203260"),
                Arguments.of(false, "555-5555-555")
                );

    }
}
