package Utilities;

import Pieces.Piece;
import chess.Square;

public class Move 
{
	private Square fromSquare, toSquare;
	private boolean isCastlingMove = false;
	
	public Move(Square fromSquare, Square toSquare) {
		this.fromSquare = fromSquare;
		this.toSquare = toSquare;
	}
	
	public boolean isCastlingMove()
	{
		 return isCastlingMove;
	}
	
	public void setIsCastlingMove()
	{
		isCastlingMove = true;
	}
	
	public boolean isInsideBoard()
	{
		return fromSquare.getCoordinate().isInsideBoard() && toSquare.getCoordinate().isInsideBoard();
	}
	
	public Coordinate getToCoordinate()
	{
		return toSquare.getCoordinate();
	}
	
	public Coordinate getFromCoordinate()
	{
		return fromSquare.getCoordinate();
	}
	
	public Piece getToPiece()
	{
		return toSquare.getPiece();
	}
	
	public Piece getFromPiece()
	{
		return fromSquare.getPiece();
	}
	
	public Square getFromSquare() {
		return fromSquare;
	}

	public void setFromSquare(Square fromSquare) {
		this.fromSquare = fromSquare;
	}

	public Square getToSquare() {
		return toSquare;
	}

	public void setToSquare(Square toSquare) {
		this.toSquare = toSquare;
	}
	
	
}
