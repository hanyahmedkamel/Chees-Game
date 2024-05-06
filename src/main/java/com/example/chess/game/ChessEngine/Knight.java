package com.example.chess.game.ChessEngine;

import java.util.*;

public class Knight extends Piece{


    
    public Knight(String PieceName,Player Player,Tile tile,Board board) {
        super(PieceName,Player,tile,board);
    }

    @Override
    public Set<Pair<Integer, Integer>> getAllMoves() {



        Set<Pair<Integer,Integer>>allmoves=new HashSet<Pair<Integer,Integer>>();


        List<Integer>X= new ArrayList<>();
        X.add(1);
        X.add(-1);
        List<Integer>Y= new ArrayList<>();
        Y.add(2);
        Y.add(-2);


        Pair<Integer,Integer> newcor;
        for(int x:X){
            for (int y:Y){
                 newcor=new Pair<Integer,Integer>(x+getTile().coordinate.first,y+getTile().coordinate.second);
                if (isValidMove(newcor)){
                    allmoves.add(newcor);
                }
            }


        }
        for(int y:X){
            for (int x:Y){
                 newcor=new Pair<Integer,Integer>(x+getTile().coordinate.first,y+getTile().coordinate.second);
                if (isValidMove(newcor)){
                    allmoves.add(newcor);
                }
            }
        }

        for (Pair<Integer,Integer>p  : allmoves) {

            System.err.print(p.first+" "+p.second);
            System.err.println("");
            
        }

        return allmoves;
    }






}
