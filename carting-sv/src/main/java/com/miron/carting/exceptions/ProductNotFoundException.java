package com.miron.carting.exceptions;

import com.miron.security_lib.handler.ServiceEntityNotFoundException;

public class ProductNotFoundException extends ServiceEntityNotFoundException {
    public ProductNotFoundException(String message) {
        super(message);
    }
    public ProductNotFoundException() {
        super("No such product found");
    }
}
