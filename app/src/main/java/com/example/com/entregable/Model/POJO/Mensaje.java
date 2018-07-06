package com.example.com.entregable.Model.POJO;

public class Mensaje {
    private String mensaje;
    private String sender;

    public Mensaje(String mensaje, String sender) {
        this.mensaje = mensaje;
        this.sender = sender;
    }

    public Mensaje(){

    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
