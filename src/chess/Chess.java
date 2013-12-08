package chess;

import UI.ChessFrame;

public class Chess 
{
    public static void main(String[] args) 
    {
    	Board board = new Board();
    	new ChessFrame(new Judge(board));
    }
}