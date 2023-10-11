package com.p2p.p2p_lending_application.exceptions;

public class Duplicate409Exception extends RuntimeException{
    public Duplicate409Exception(String message){
        super(message);
    }
}
