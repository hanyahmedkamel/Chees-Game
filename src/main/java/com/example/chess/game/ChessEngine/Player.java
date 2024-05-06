package com.example.chess.game.ChessEngine;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


public class Player {
    private  String name;
    private  Boolean team;


    @JsonIgnore
    private  Map<String, Piece> pieces;

    public Player() {
    }

    public Player(String name, Boolean team) {
        this.name = name;
        this.team=team;
    }
    public String getName() {
        return name;
    }
    public Map<String, Piece> getPieces() {
        return pieces;
    }
    public void setPieces(Map<String, Piece> pieces) {
        this.pieces = pieces;
    }
    public Boolean getTeam() {
        return team;
    }
    public boolean requestMove(String piecename, Pair<Integer,Integer> coordinate){

        Piece piece = null ;

        if (pieces.containsKey(piecename)) {


            piece = pieces.get(piecename);

            if(piece.getAllMoves().contains(coordinate)){

                return true;
            }

        }
        return false;
    }
    @JsonIgnore
    public Set<Pair<Integer,Integer>> getAllMoves(){
        Set<Pair<Integer,Integer>>getAllMoves=new HashSet<>();
        for (Piece piece:pieces.values()) {
            getAllMoves.addAll(piece.getAllMoves());

        }

        return getAllMoves;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTeam(Boolean team) {
        this.team = team;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
