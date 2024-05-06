package com.example.chess.game;

public class InternalMessage {

    private String destination;
    private String data;

    public InternalMessage() {
    }

    public InternalMessage(String destination, String data) {
        this.destination = destination;
        this.data = data;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
