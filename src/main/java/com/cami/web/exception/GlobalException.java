/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cami.web.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author naoussi
 */
@ControllerAdvice
public class GlobalException {
    
    @ExceptionHandler(NumberFormatException.class)
    public String handleNumberFormatException(){
        return "Nombre incorrect";
    }
}
