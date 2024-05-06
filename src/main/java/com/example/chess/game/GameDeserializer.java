package com.example.chess.game;

import com.example.chess.game.ChessEngine.*;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@JsonComponent
public class GameDeserializer extends JsonDeserializer<Game> {

    @Override
    public Game deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode jsonNode=jsonParser.getCodec().readTree(jsonParser);
        JsonNode board=jsonNode.get("board");

        JsonNode counter=jsonNode.get("counter");


        Player player1=new Player(jsonNode.get("player1").get("name").asText(),jsonNode.get("player1").get("team").asBoolean());
        Player player2=new Player(jsonNode.get("player2").get("name").asText(),jsonNode.get("player2").get("team").asBoolean());

        Map<String, Piece> player1pieces=new HashMap<>();
        Map<String, Piece> player2pieces=new HashMap<>();

        Board boardgame=new Board();

        for (int i = 0; i <8; i++) {
            for (int j = 0; j < 8; j++) {
                boardgame.tiles.get(i).add(new Tile(new Pair<>(i, j)));
            }
        }
        for (JsonNode i:board) {

            for (JsonNode l:i) {

                for (JsonNode tile:l) {

                    JsonNode coordinate=tile.get("coordinate");
                    Tile tempTile= boardgame.tiles.get(coordinate.get("first").asInt()).get(coordinate.get("second").asInt());

                    if (tile.get("piece").asText()!="null"){
                        String picename=tile.get("piece").get("pieceName").asText();

                        Piece piece=null;

                        Player player=new Player(tile.get("piece").get("player").get("name").asText(),tile.get("piece").get("player").get("team").asBoolean());

                        if (player.equals(player1)){
                        if (picename.contains("r"))
                            piece=new Rock(picename,player1,tempTile,boardgame);
                        else if (picename.contains("q"))
                            piece=new Queen(picename,player1,tempTile,boardgame);
                        else if (picename.equals("k"))
                            piece=new King(picename,player1,tempTile,boardgame);
                        else if (picename.contains("k"))
                            piece=new Knight(picename,player1,tempTile,boardgame);
                        else if (picename.contains("s"))
                            piece=new Pawn(picename,player1,tempTile,boardgame);
                        else if (picename.contains("b"))
                            piece=new Bishop(picename,player1,tempTile,boardgame);
                        player1pieces.put(picename,piece);
                        }else {
                            if (picename.contains("R"))
                                piece=new Rock(picename,player2,tempTile,boardgame);
                            else if (picename.contains("Q"))
                                piece=new Queen(picename,player2,tempTile,boardgame);
                            else if (picename.equals("K"))
                                piece=new King(picename,player2,tempTile,boardgame);
                            else if (picename.contains("K"))
                                piece=new Knight(picename,player2,tempTile,boardgame);
                            else if (picename.contains("S"))
                                piece=new Pawn(picename,player2,tempTile,boardgame);
                            else if (picename.contains("B"))
                                piece=new Bishop(picename,player2,tempTile,boardgame);
                            player2pieces.put(picename,piece);
                        }
                        tempTile.setOccuopied(true);
                        tempTile.setPiece(piece);


                    }
                }

            }
            
        }


        player1.setPieces(player1pieces);
        player2.setPieces(player2pieces);


        Game game=new Game();
        game.setPlayer1(player1);
        game.setPlayer2(player2);
        game.setBoard(boardgame);
        game.setGameId(UUID.fromString(jsonNode.get("gameId").asText()));
        game.setCounter(counter.asInt());

        if (jsonNode.get("playerTurn").get("name").asText().equals(player1.getName())) {
            System.out.println("#########");
            System.out.println(jsonNode.get("playerTurn").get("name").asText());
            game.setPlayerTurn(player1);
        }else{
            System.out.println("&&&&&&&&&&&");
            System.out.println(jsonNode.get("playerTurn").get("name").asText());
            game.setPlayerTurn(player2);}
        System.out.println("éééééé"+game.getPlayerTurn().getName());

        return game;
    }

}
