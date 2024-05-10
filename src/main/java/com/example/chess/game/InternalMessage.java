package com.example.chess.game;

public class InternalMessage {

    private String destination;
    private String data;

    private String toUser;

    public InternalMessage() {
    }

    public InternalMessage(String destination, String data) {
        this.destination = destination;
        this.data = data;
        toUser="null";
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


    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
}
