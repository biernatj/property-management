package com.mycompany.propertymanagement.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorModel>> handleFieldValidation(MethodArgumentNotValidException argumentNotValidException) {
        List<FieldError> fieldErrorList = argumentNotValidException.getBindingResult().getFieldErrors();
        List<ErrorModel> errorModelList = new ArrayList<>();
        for(FieldError fieldError : fieldErrorList) {
            logger.debug("Inside field validation -level-debug {} - {}", fieldError.getField(), fieldError.getDefaultMessage());
            logger.info("Inside field validation -level-info {} - {}", fieldError.getField(), fieldError.getDefaultMessage());
            ErrorModel errorModel = new ErrorModel();
            errorModel.setCode(fieldError.getField());
            errorModel.setMessage(fieldError.getDefaultMessage());
            errorModelList.add(errorModel);
        }
        return new ResponseEntity<List<ErrorModel>>(errorModelList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<List<ErrorModel>> handleBusinessException(BusinessException businessException) {
        for(ErrorModel errorModel : businessException.getErrors()) {
            logger.debug("Business exception is thrown -level-debug: {} - {}", errorModel.getCode(), errorModel.getMessage());
            logger.info("Business exception is thrown -level-info: {} - {}", errorModel.getCode(), errorModel.getMessage());
            logger.warn("Business exception is thrown -level-warn: {} - {}", errorModel.getCode(), errorModel.getMessage());
            logger.error("Business exception is thrown -level-error: {} - {}", errorModel.getCode(), errorModel.getMessage());
        }
        return new ResponseEntity<List<ErrorModel>>(businessException.getErrors(), HttpStatus.BAD_REQUEST);
    }
}
