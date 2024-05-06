package com.example.chess.game;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


public class Message {

    private String pieceName;
    private int x;
    private int y;

    Message(String pieceName, int x, int y){
        this.pieceName=pieceName;
        this.x=x;
        this.y=y;

    }
    public String getPieceName() {
        return pieceName;
    }

    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
