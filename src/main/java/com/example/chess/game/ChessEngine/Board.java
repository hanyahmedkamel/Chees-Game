package com.example.chess.game.ChessEngine;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Board {


    public List<List<Tile>> tiles;
    private UUID boardId;

    public UUID getBoardId() {
        return boardId;
    }

    public Board() {
        boardId = UUID.randomUUID();
        tiles = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            tiles.add(new ArrayList<>(8));
        }
    }

    public List<List<Tile>> getTiles() {
        return tiles;
    }

    public void setTiles(List<List<Tile>> tiles) {
        this.tiles = tiles;
    }

    public void setBoardId(UUID boardId) {
        this.boardId = boardId;
    }


}



