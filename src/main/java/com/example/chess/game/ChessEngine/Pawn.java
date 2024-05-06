package com.example.chess.game.ChessEngine;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {

    //private int firstMove;
    public Pawn(String PieceName, Player Player, Tile tile, Board board) {
        super(PieceName, Player, tile, board);
        //firstMove=1;
    }

    @Override
    public Set<Pair<Integer, Integer>> getAllMoves() {

        HashSet<Pair<Integer,Integer>> allMoves=new HashSet<>();

        for (int i = 1; i <=1 ; i++) {
            Pair<Integer,Integer> cor=new Pair<>((getTile().coordinate.first+(1+(this.getPlayer().getTeam()?0:1)*-2)),getTile().coordinate.second);

            



            if (isValidMove(cor)){

                allMoves.add(cor);
            }
        }
        //firstMove=0;
        return allMoves;

    }
}
