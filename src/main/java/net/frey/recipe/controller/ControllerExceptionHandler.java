package net.frey.recipe.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {
    //    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //    @ExceptionHandler(NumberFormatException.class)
    //    public ModelAndView handleBadNumberFormat(Exception e) {
    //        log.error("Handling number format exception: " + e.getMessage());
    //
    //        ModelAndView modelAndView = new ModelAndView();
    //        modelAndView.setViewName("400error");
    //        modelAndView.addObject("exception", e);
    //
    //        return modelAndView;
    //    }
}
