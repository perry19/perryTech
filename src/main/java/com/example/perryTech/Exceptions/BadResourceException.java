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
import java.util.ArrayList;
import java.util.List;
 
public class BadResourceException extends Exception {
 
    private List<String> errorMessages = new ArrayList<>();
            
    public BadResourceException() {
    }
 
    public BadResourceException(String msg) {
        super(msg);
    }
    
    /**
     * @return the errorMessages
     */
    public List<String> getErrorMessages() {
        return errorMessages;
    }
 
    /**
     * @param errorMessages the errorMessages to set
     */
    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }
 
    public void addErrorMessage(String message) {
        this.errorMessages.add(message);
    }
}