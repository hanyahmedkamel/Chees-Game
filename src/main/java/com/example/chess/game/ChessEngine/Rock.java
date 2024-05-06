package com.example.chess.game.ChessEngine;

import java.util.HashSet;
import java.util.Set;

public class Rock extends Piece {

    public Rock(String PieceName, Player Player, Tile tile, Board board) {
        super(PieceName, Player, tile, board);

    }

    @Override
    public Set<Pair<Integer, Integer>> getAllMoves() {

        int x=getTile().coordinate.first;
        int y=getTile().coordinate.second;

        Set<Pair<Integer,Integer>>results=new HashSet<>();
        Pair<Integer,Integer> pair=new Pair<Integer,Integer>(++x, y);

        while (isValidMove(pair)) {

            results.add(pair);

            if (getTile().getPiece().getBoard().tiles.get(x).get(y).isOccuopied()) {

                break;
            }

            pair=new Pair<Integer,Integer>(x++, y);
            
        }
        x=getTile().coordinate.first;
        y=getTile().coordinate.second;

        pair=new Pair<Integer,Integer>(--x, y);
        while (isValidMove(pair)) {

            results.add(pair);

            if (getTile().getPiece().getBoard().tiles.get(x).get(y).isOccuopied()) {

                break;
            }

            pair=new Pair<Integer,Integer>(x--, y);

            
        }


        x=getTile().coordinate.first;
        y=getTile().coordinate.second;

        pair=new Pair<Integer,Integer>(x, y++);


        while (isValidMove(pair)) {

            results.add(pair);

            if (getTile().getPiece().getBoard().tiles.get(x).get(y).isOccuopied()) {

                break;
            }

            pair=new Pair<Integer,Integer>(x, y++);

            
        }


        x=getTile().coordinate.first;
        y=getTile().coordinate.second;

        pair=new Pair<Integer,Integer>(x, y--);


        while (isValidMove(pair)) {

            results.add(pair);

            if (getTile().getPiece().getBoard().tiles.get(x).get(y).isOccuopied()) {

                break;
            }

            pair=new Pair<Integer,Integer>(x, y--);

            
        }


        for (Pair<Integer,Integer> p  : results) {

            System.err.print(p.first+" "+p.second);
            System.err.println("");
            
        }

        return results;
    }
    
}
