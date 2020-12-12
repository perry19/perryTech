/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.perryTech.Exceptions;

/**
 *
 * @author compilerarmelovic
 */
public class ResourceNotFoundException extends Exception {
 
    public ResourceNotFoundException() {
    }
 
    public ResourceNotFoundException(String msg) {
        super(msg);
    }    
}
