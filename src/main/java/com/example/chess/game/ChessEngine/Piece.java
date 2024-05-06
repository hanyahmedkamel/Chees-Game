package com.example.chess.game.ChessEngine;

import com.fasterxml.jackson.annotation.*;

import java.util.Set;

public abstract class Piece {

    private String pieceName;
    private Player Player;
    @JsonIgnore
    private Board board;
    @JsonIgnore
    private Tile tile;

    public Piece() {
    }

    public  Piece(String PieceName, Player Player, Tile tile, Board board ){

        this.pieceName =PieceName;
        this.Player =Player;
        this.board=board;
        this.tile=tile;
    }

    public final Boolean isValidMove(Pair<Integer,Integer> cordinate){

        if (BoardUtil.isValidCoordinate(cordinate)&&(!board.tiles.get(cordinate.first).get(cordinate.second).isOccuopied()||(board.tiles.get(cordinate.first).get(cordinate.second).isOccuopied()&&board.tiles.get(cordinate.first).get(cordinate.second).getPiece().Player != Player)))
            return true;
        return false;
    }
    public final String getPieceName(){

        return pieceName;
    }
    public final Player getPlayer(){


        return Player;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile(){
        return tile;
    }

    @JsonIgnore
    public  abstract Set<Pair<Integer, Integer>> getAllMoves();


    Board getBoard(){

        return board;
    }

    public void setPieceName(String pieceName) {
        this.pieceName = pieceName;
    }

    public void setPlayer(com.example.chess.game.ChessEngine.Player player) {
        Player = player;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
