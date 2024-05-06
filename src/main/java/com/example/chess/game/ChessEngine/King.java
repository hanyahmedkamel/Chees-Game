package com.example.chess.game.ChessEngine;

import java.util.HashSet;
import java.util.Set;

public class King extends Piece {

    public King(String PieceName, Player Player, Tile tile, Board board) {
        super(PieceName, Player, tile, board);
    }

    @Override
    public Set<Pair<Integer, Integer>> getAllMoves() {

        Set<Pair<Integer, Integer>> allmoves=new HashSet<Pair<Integer,Integer>>();

        Pair<Integer,Integer> p=new Pair<Integer,Integer>( getTile().coordinate.first+1,  getTile().coordinate.second);

        if (isValidMove(p)) {

            allmoves.add(p);


            
        }

         p=new Pair<Integer,Integer>( getTile().coordinate.first-1,  getTile().coordinate.second);

        if (isValidMove(p)) {

            allmoves.add(p);
            
        }


        p=new Pair<Integer,Integer>( getTile().coordinate.first,  getTile().coordinate.second+1);

        if (isValidMove(p)) {

            allmoves.add(p);
            
        }


       p=new Pair<Integer,Integer>( getTile().coordinate.first,  getTile().coordinate.second-1);

        if (isValidMove(p)) {

            allmoves.add(p);
            
        }



         p=new Pair<Integer,Integer>( getTile().coordinate.first+1,  getTile().coordinate.second+1);

        if (isValidMove(p)) {

            allmoves.add(p);
            
        }




         p=new Pair<Integer,Integer>( getTile().coordinate.first+1,  getTile().coordinate.second-1);

        if (isValidMove(p)) {

            allmoves.add(p);
            
        }




        p=new Pair<Integer,Integer>( getTile().coordinate.first-1,  getTile().coordinate.second+1);

        if (isValidMove(p)) {

            allmoves.add(p);
            
        }




         p=new Pair<Integer,Integer>( getTile().coordinate.first-1,  getTile().coordinate.second-1);

        if (isValidMove(p)) {

            allmoves.add(p);
            
        }





        return allmoves;

    }

    
    
}
