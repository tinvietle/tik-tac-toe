/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vgu.pe2026.ttt.basic;

/**
 *
 * @author clavel
 */
public class TTTException extends Exception { // Extends Exception for a checked exception
    public TTTException(String message) {
        super(message); // Passes the message to the parent class constructor
    }

    public TTTException(String message, Throwable cause) {
        super(message, cause); // Constructor for exception chaining
    }
}