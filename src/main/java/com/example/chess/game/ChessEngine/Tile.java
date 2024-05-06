package com.example.chess.game.ChessEngine;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;


public class Tile {


    private Piece piece;
    public Pair<Integer,Integer> coordinate;
    private  Boolean isOccuopied;




    public Tile(Pair<Integer,Integer> coordinate){
        piece=null;
        this.coordinate=coordinate;
        isOccuopied=false;
    }

    public Boolean isOccuopied(){

        return isOccuopied;
    }
    
    public Piece getPiece(){

        return piece;
    }
    public void setPiece(Piece piece) {

        this.piece = piece;
        isOccuopied=true;
    }

    public void removePiece(){

        piece=null;
        isOccuopied=false;

    }

    public Pair<Integer, Integer> getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Pair<Integer, Integer> coordinate) {
        this.coordinate = coordinate;
    }

    public Boolean getOccuopied() {
        return isOccuopied;
    }

    public void setOccuopied(Boolean occuopied) {
        isOccuopied = occuopied;
    }


}
