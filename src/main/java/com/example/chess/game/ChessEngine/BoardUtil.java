package com.example.chess.game.ChessEngine;

public abstract class BoardUtil {

    public static Boolean isValidCoordinate(Pair<Integer,Integer> coordinate){
        if(coordinate.first<0||coordinate.first>7|coordinate.second<0||coordinate.second>7)
            return false;

        return true;
    }

}
