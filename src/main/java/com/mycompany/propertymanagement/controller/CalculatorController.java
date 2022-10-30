package com.mycompany.propertymanagement.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/calculator")
public class CalculatorController {

    @GetMapping("/add/{num3}")
    public Double add(@RequestParam("num1") Double num1, @RequestParam("num2") Double num2, @PathVariable("num3") Double num3) {
        return num1 + num2;
    }

    @GetMapping("/sub/{num1}/{num2}")
    public Double substract(@PathVariable("num1") Double num1, @PathVariable("num2") Double num2) {
        Double result;
        if(num1 > num2) {
            result = num1 - num2;
        } else {
            result = num2 - num1;
        }
        return result;
    }

}
