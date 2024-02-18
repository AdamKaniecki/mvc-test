package org.example.controller;


import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

//wszystkie inne wyjatki
    @ExceptionHandler(Exception.class)
    public ModelAndView handlerException(Exception ex){
        String message = "Unexpected exception occurred: [%s]".formatted(ex.getMessage());
        log.error(message, ex);
//        tu trzeba zdefiniowac widok w ktorym bedzie ustawiony ladny szablon z errorem
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage",message);
        return modelAndView;
    }

//    konkretny wyjatek
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handlerException(EntityNotFoundException ex){
        String message = "Could not find a resource: [%s]".formatted(ex.getMessage());
        log.error(message, ex);
        //        tu trzeba zdefiniowac widok w ktorym bedzie ustawiony ladny szablon z errorem
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("errorMessage",message);
        return modelAndView;
    }

}
