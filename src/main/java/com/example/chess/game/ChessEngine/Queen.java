package com.example.chess.game.ChessEngine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Queen extends Piece {

    public Queen(String PieceName, Player Player, Tile tile, Board board) {
        super(PieceName, Player, tile, board);
    }

    @Override
    public Set<Pair<Integer, Integer>> getAllMoves() {


        int x=getTile().coordinate.first;
        int y=getTile().coordinate.second;

        Set<Pair<Integer,Integer>>allmoves=new HashSet<>();
        Pair<Integer,Integer> pair=new Pair<Integer,Integer>(++x, y);

        while (isValidMove(pair)) {

            allmoves.add(pair);

            if (getTile().getPiece().getBoard().tiles.get(x).get(y).isOccuopied()) {

                break;
            }

            pair=new Pair<Integer,Integer>(x++, y);
            
        }
        x=getTile().coordinate.first;
        y=getTile().coordinate.second;

        pair=new Pair<Integer,Integer>(--x, y);
        while (isValidMove(pair)) {

            allmoves.add(pair);

            if (getTile().getPiece().getBoard().tiles.get(x).get(y).isOccuopied()) {

                break;
            }

            pair=new Pair<Integer,Integer>(x--, y);

            
        }


        x=getTile().coordinate.first;
        y=getTile().coordinate.second;

        pair=new Pair<Integer,Integer>(x, y++);


        while (isValidMove(pair)) {

            allmoves.add(pair);

            if (getTile().getPiece().getBoard().tiles.get(x).get(y).isOccuopied()) {

                break;
            }

            pair=new Pair<Integer,Integer>(x, y++);

            
        }


        x=getTile().coordinate.first;
        y=getTile().coordinate.second;

        pair=new Pair<Integer,Integer>(x, y--);


        while (isValidMove(pair)) {

            allmoves.add(pair);

            if (getTile().getPiece().getBoard().tiles.get(x).get(y).isOccuopied()) {

                break;
            }

            pair=new Pair<Integer,Integer>(x, y--);

            
        }



       List<Integer>XL= new ArrayList<>();
       XL.add(1);
       XL.add(-1);
        List<Integer>YL= new ArrayList<>();
        YL.add(1);
        YL.add(-1);

        int xcor =getTile().coordinate.first;
        int ycor =getTile().coordinate.second;
        for (Integer xl : XL) {
            for (Integer yl : YL) {
                int counter=1;

                pair=new Pair<Integer,Integer>(xcor+(counter*xl),ycor+(counter*yl));
                while (isValidMove(pair)) {

                    allmoves.add(pair);

                    if (getTile().getPiece().getBoard().tiles.get(pair.first).get(pair.second).isOccuopied()) {

                        break;
                    }
                    counter++;
                    pair=new Pair<Integer,Integer>(xcor+(counter*xl),ycor+(counter*yl));

                    
                }
                
            }
            
        }
    
    

        List<Integer>X= new ArrayList<>();
        X.add(1);
        X.add(-1);
        List<Integer>Y= new ArrayList<>();
        Y.add(2);
        Y.add(-2);


        Pair<Integer,Integer> newcor;
        for(int xk:X){
            for (int yk:Y){
                 newcor=new Pair<Integer,Integer>(xk+getTile().coordinate.first,yk+getTile().coordinate.second);
                if (isValidMove(newcor)){
                    allmoves.add(newcor);
                }
            }


        }
        
        
        for(int yk:X){
            for (int xk:Y){
                 newcor=new Pair<Integer,Integer>(xk+getTile().coordinate.first,yk+getTile().coordinate.second);
                if (isValidMove(newcor)){
                    allmoves.add(newcor);
                }
            }
        }

        

        for (Pair<Integer,Integer> p  : allmoves) {

            System.err.print(p.first+" "+p.second);
            System.err.println("");
            
        }
        return allmoves;
    
    
    }
    
}
