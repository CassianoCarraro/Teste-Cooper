package com.cooper.dc.testecooper;

public class GPSException extends Exception {
    public String getMessage() {
        return "Pemita acesso ao GPS para realizar o teste.";
    }
}