package com.example.chess.game.ChessEngine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bishop extends Piece {

    public Bishop(String PieceName, Player Player, Tile tile, Board board) {
        super(PieceName, Player, tile, board);
    }

    @Override
    public Set<Pair<Integer, Integer>> getAllMoves() {

         Set<Pair<Integer,Integer>>allmoves=new HashSet<Pair<Integer,Integer>>();



        List<Integer>X= new ArrayList<>();
        X.add(1);
        X.add(-1);
        List<Integer>Y= new ArrayList<>();
        Y.add(1);
        Y.add(-1);

        int xcor =getTile().coordinate.first;
        int ycor =getTile().coordinate.second;
        for (Integer x : X) {
            for (Integer y : Y) {
                int counter=1;

                Pair<Integer,Integer> pair=new Pair<Integer,Integer>(xcor+(counter*x),ycor+(counter*y));
                while (isValidMove(pair)) {

                    allmoves.add(pair);

                    if (getTile().getPiece().getBoard().tiles.get(pair.first).get(pair.second).isOccuopied()) {

                        break;
                    }
                    counter++;
                    pair=new Pair<Integer,Integer>(xcor+(counter*x),ycor+(counter*y));
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
