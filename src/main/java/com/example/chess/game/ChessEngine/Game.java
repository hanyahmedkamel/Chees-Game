package com.example.chess.game.ChessEngine;

import com.example.chess.game.GameDeserializer;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.parameters.P;

import java.util.*;

@JsonDeserialize(using = GameDeserializer.class)
public class Game {

    private UUID gameId;
    private Board board;
    private Player playerTurn;
    private  Player player1;
    private  Player player2;
    private boolean check1=false;
    private boolean check2=false;
    @JsonIgnore
    public Player winner=null;
    private  int counter=0;
    public Game() {
    }

    public Game(Player player1, Player player2) {
        this.gameId=UUID.randomUUID();
        playerTurn=player1;


        board=new Board();



        for (int i = 0; i <8; i++) {
            for (int j = 0; j < 8; j++) {
                board.tiles.get(i).add(new Tile(new Pair<>(i, j)));
            }
        }

        this.player1=player1;
        this.player2=player2;

        set_pieces();


    }

    private void set_pieces(){

        player1.setPieces(player1pieces());
        player2.setPieces(player2pieces());

    }


    private void removePiece(Piece piece){

        piece.getTile().removePiece();

        piece.getPlayer().getPieces().remove(piece.getPieceName());
    }

    private void setPiece(Piece piece, Pair<Integer,Integer> coordinate){



        Tile tile=board.tiles.get(coordinate.first).get(coordinate.second);

        piece.getTile().removePiece();
        tile.setPiece(piece);

        piece.setTile(tile);


    }


    private Map<String, Piece>player1pieces(){

        Map<String, Piece>pieces=new HashMap<>();


        for (int i =0; i <8; i++) {
           Tile x=board.tiles.get(1).get(i);
           x.setPiece(new Pawn("s"+(i+1),player1,board.tiles.get(1).get(i),board));
           pieces.put(x.getPiece().getPieceName(),x.getPiece());
        }



        Tile x=board.tiles.get(0).get(1);
        x.setPiece(new Knight("k"+1,player1,board.tiles.get(0).get(1),board));
        pieces.put(x.getPiece().getPieceName(),x.getPiece());

         x=board.tiles.get(0).get(6);
        x.setPiece(new Knight("k"+2,player1,board.tiles.get(0).get(6),board));
        pieces.put(x.getPiece().getPieceName(),x.getPiece());





        x=board.tiles.get(0).get(0);
        x.setPiece(new Rock("r"+1,player1,board.tiles.get(0).get(0),board));
        pieces.put(x.getPiece().getPieceName(),x.getPiece());

        x=board.tiles.get(0).get(7);
        x.setPiece(new Rock("r"+2,player1,board.tiles.get(0).get(7),board));
        pieces.put(x.getPiece().getPieceName(),x.getPiece());


        x=board.tiles.get(0).get(2);
        x.setPiece(new Bishop("b"+1,player1,board.tiles.get(0).get(2),board));
        pieces.put(x.getPiece().getPieceName(),x.getPiece());

        x=board.tiles.get(0).get(5);
        x.setPiece(new Bishop("b"+2,player1,board.tiles.get(0).get(5),board));
        pieces.put(x.getPiece().getPieceName(),x.getPiece());

        x=board.tiles.get(0).get(4);
        x.setPiece(new Queen("q",player1,board.tiles.get(0).get(4),board));
        pieces.put(x.getPiece().getPieceName(),x.getPiece());


        x=board.tiles.get(0).get(3);
        x.setPiece(new King("k",player1,board.tiles.get(0).get(3),board));
        pieces.put(x.getPiece().getPieceName(),x.getPiece());
        return pieces;


    }


    private Map<String, Piece>player2pieces(){

        Map<String, Piece>pieces=new HashMap<>();


        for (int i =0; i <8; i++) {
           Tile x=board.tiles.get(6).get(i);
           x.setPiece(new Pawn("S"+(1+i),player2,board.tiles.get(6).get(i),board));
           pieces.put(x.getPiece().getPieceName(),x.getPiece());
        }



        Tile x=board.tiles.get(7).get(1);
        x.setPiece(new Knight("K"+1,player2,board.tiles.get(7).get(1),board));
        pieces.put(x.getPiece().getPieceName(),x.getPiece());

         x=board.tiles.get(7).get(6);
        x.setPiece(new Knight("K"+2,player2,board.tiles.get(7).get(6),board));
        pieces.put(x.getPiece().getPieceName(),x.getPiece());




        x=board.tiles.get(7).get(0);
        x.setPiece(new Rock("R"+1,player2,board.tiles.get(7).get(0),board));
        pieces.put(x.getPiece().getPieceName(),x.getPiece());

        x=board.tiles.get(7).get(7);
        x.setPiece(new Rock("R"+2,player2,board.tiles.get(7).get(7),board));
        pieces.put(x.getPiece().getPieceName(),x.getPiece());



        x=board.tiles.get(7).get(2);
        x.setPiece(new Bishop("B"+1,player2,board.tiles.get(7).get(2),board));
        pieces.put(x.getPiece().getPieceName(),x.getPiece());

        x=board.tiles.get(7).get(5);
        x.setPiece(new Bishop("B"+2,player2,board.tiles.get(7).get(5),board));
        pieces.put(x.getPiece().getPieceName(),x.getPiece());


        x=board.tiles.get(7).get(4);
        x.setPiece(new Queen("Q",player2,board.tiles.get(7).get(4),board));
        pieces.put(x.getPiece().getPieceName(),x.getPiece());


        x=board.tiles.get(7).get(3);
        x.setPiece(new King("K",player2,board.tiles.get(7).get(3),board));
        pieces.put(x.getPiece().getPieceName(),x.getPiece());




        return pieces;


    }




    private Boolean takepalyer1move(String piecename ,int x,int y ){


        if (check1&&(piecename!="k"||piecename=="k"&&!player2.requestMove(piecename,new Pair<Integer,Integer>(x, y)))) {

            return false;
            
        }


        if ((!player1.requestMove(piecename, new Pair<Integer,Integer>(x, y)))) {

            return false;
        }

        makemove(player1.getPieces().get(piecename),new Pair<Integer,Integer>(x, y));
        check1=false;


        check2=checkplayer1(piecename);


        return true;








    }

    private Boolean takepalyer2move(String piecename ,int x,int y){




        while (check2&&(piecename!="K"||piecename=="K"&&!player2.requestMove(piecename,new Pair<Integer,Integer>(x, y)))) {

            return false;
        }


        while (!player2.requestMove(piecename, new Pair<Integer,Integer>(x, y))) {

            return false;
        }

        makemove(player2.getPieces().get(piecename),new Pair<Integer,Integer>(x, y));
        check2=false;

        check1=checkplayer2(piecename);

        return true;




    }

    private void makemove(Piece piece, Pair<Integer,Integer> coordinate){


        if (board.tiles.get(coordinate.first).get(coordinate.second).isOccuopied()) {


            removePiece(board.tiles.get(coordinate.first).get(coordinate.second).getPiece());            
        }

        setPiece(piece, coordinate);

    }




    private boolean checkplayer1(String piecename){

      boolean check=player1.getAllMoves().contains(player2.getPieces().get("K").getTile().coordinate);

        Set<Pair<Integer, Integer>>f= player2.getPieces().get("K").getAllMoves();
        Set<Pair<Integer, Integer>>q=player1.getAllMoves();

        Set<Pair<Integer, Integer>> diffSet = new HashSet<>(f);
        diffSet.removeAll(q);


        if (diffSet.isEmpty()&&!f.isEmpty())
            winner= player1;







        return check;

    }


    private boolean checkplayer2(String piecename){

        boolean check=player2.getPieces().get(piecename).getAllMoves().contains(player1.getPieces().get("k").getTile().coordinate);

        Set<Pair<Integer, Integer>>f= player1.getPieces().get("k").getAllMoves();
        Set<Pair<Integer, Integer>>q=player2.getAllMoves();

        Set<Pair<Integer, Integer>> diffSet = new HashSet<>(f);
        diffSet.removeAll(q);


        if (diffSet.isEmpty()&&!f.isEmpty())
            winner= player2;



        return check;
  
      }


    public String startGame(String playername,String piecename , int x, int y){

        System.out.println(playerTurn.getName()+"turn");

        if (!playername.equals(playerTurn.getName())){
            System.out.println(playername+" != "+playerTurn.getName());
            return "not your turn";
        }

        if (winner!=null){

            return "winner is" +winner.getName();
        }
        boolean c=false;


        if (playerTurn==player1){
            c=takepalyer1move(piecename,x,y);


        }else {
            c=takepalyer2move(piecename,x,y);

        }

        if (c) {
            if (playerTurn == player1) {
                System.out.println("change2");

                playerTurn = player2;
            }else{
                System.out.println("change1");
                playerTurn=player1;}
        }else {
            return "Enter Valid Move";
        }
        System.out.println(playerTurn.getName()+"turn");
        return display();

    }


    public String display() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("      ");
        for (int i = 0; i < 8; i++) {
            stringBuilder.append(i).append("  ");
        }
        stringBuilder.append("\n");

        for (int i = 0; i < 8; i++) {
            stringBuilder.append(i).append("    ");
            for (int j = 0; j < 8; j++) {
                if (board.tiles.get(i).get(j).isOccuopied()) {
                    stringBuilder.append(board.tiles.get(i).get(j).getPiece().getPieceName()).append(" ");
                } else {
                    stringBuilder.append(".  ");
                }
            }
            stringBuilder.append("\n");
        }

        String result = stringBuilder.toString();
        //System.out.print(result); // Optional: You can still print the result to the console if needed
        return result;
    }


    public Map<String, Object> displayBoard() {
        Map<String, Object> response = new HashMap<>();

        List<List<String>> boardState = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            List<String> row = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                if (board.tiles.get(i).get(j).isOccuopied()) {
                    row.add(board.tiles.get(i).get(j).getPiece().getPieceName());
                } else {
                    row.add(".");
                }
            }
            boardState.add(row);
        }

        response.put("message", "Chess board state:");
        response.put("board", boardState);

        return response;
    }

    public Player getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(Player playerTurn) {
        this.playerTurn = playerTurn;
    }

    public UUID getGameId() {
        return gameId;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public boolean isCheck1() {
        return check1;
    }

    public void setCheck1(boolean check1) {
        this.check1 = check1;
    }

    public boolean isCheck2() {
        return check2;
    }

    public void setCheck2(boolean check2) {
        this.check2 = check2;
    }

    public  int getCounter() {
        return counter;
    }

    public  void setCounter(int counter) {
        this.counter = counter;
    }
}



