package Pieces;

import Utilities.*;
import chess.Board;
import chess.Judge;
import chess.Square;

public abstract class Piece
{
	protected boolean white, moved = false;
	protected Square square;
	private Board board;
	
	
	protected Piece(boolean white, Square square, Board board)
	{
		this.board = board;
		this.white = white;
		this.square = square;
	}
	
	public Piece(){}
	
	public int getRow()
	{
		return square.getRow();
	}
	
	public boolean isMoved() {
		return moved;
	}
	
	public void setMoved(boolean moved) {
		this.moved = moved;
	}
	
	public int getColumn()
	{
		return square.getColumn();
	}
	
	public abstract SetOf2dCoords getSetOfPossibleMoves();
	
	public abstract char getSign();
	
	public abstract String getName();
	
	public Square getSquare()
	{
		return square;
	}
	
	public void setSquare(Square square)
	{
		this.square = square;
	}
	
	public void moveTo(Coordinate coordinate)
	{
		Square newSquare = board.getSquareAt(coordinate);
		square.removePiece();
		setSquare(newSquare);
		newSquare.setPiece(this);
		board.judge.updateThreats();
	}
	
	public boolean isWhite()
	{
		return white;
	}
	
	public boolean isLegalMoveTo(int row, int column)
	{
		return isLegalMoveTo(new Coordinate(row, column));
	}
	
	public boolean isLegalMoveTo(Coordinate coordinate)
	{
		/* Betingelser for lovlig trekk: du flytter et sted
		 * A. p� brettet
		 * B. som er tomt
		 * C. med en motspillers brikke
		 * sannehetsfunksjonen er: A(B + C)
		 */
		
		if(coordinate.isInsideBoard())
		{
			Piece piece = Board.getPieceAt(coordinate);
			return (piece != null) ? this.isOtherPlayersPiece(piece) : true;
		}
		else return false;
	}

	public boolean isOtherPlayersPiece(Piece otherPlayersPiece)
	{
		return (otherPlayersPiece == null) ? false : isWhite() != otherPlayersPiece.isWhite();
	}
	
	@Override
	public String toString()
	{
		if(this == null)
			return "null";
		
		String s = isWhite() ? "White " : "Black ";
		s += getName(); 
		s += " on a " + square;
		
		SetOf2dCoords threats = getSquare().getSetOfThreats();
		s += threats.size() == 0 ? " is not under threat" : " is threatened by: " + threats;

		String whereItCanMove = " and can move to the coords: " + getSetOfPossibleMoves();
		String itCannotMove =  " and cannot move anywhere.";
		boolean cannotMove = getSetOfPossibleMoves().isEmpty();
		return s +=  cannotMove ? itCannotMove : whereItCanMove;
	}
	
	protected SetOf2dCoords helpFunction(int dr, int dc)
	{
		//er veldig kjekk for � bla gjennom brettet i en gitt retning
		//og legge til mulige trekk, som henger etter hverandre
		//trenger navnendring
		SetOf2dCoords list = new SetOf2dCoords();
		Coordinate coordinate = new Coordinate(getRow() + dr, getColumn() + dc);
		while(isLegalMoveTo(coordinate))
		{
			list.addCoordinate(coordinate);
			if(isOtherPlayersPiece(board.getPieceAt(coordinate)))
				break;
			coordinate = new Coordinate(coordinate.getRow() + dr, coordinate.getColumn() + dc);
		}
		return list;
	}
	
	public boolean isKing()
	{
		return this instanceof King;
	}
	
	public boolean isPawn()
	{
		return this instanceof Pawn;
	}
	
	public boolean isRook()
	{
		return this instanceof Rook;
	}
	
	public boolean isBishop()
	{
		return this instanceof Bishop;
	}
	
	public boolean isQueen()
	{
		return this instanceof Queen;
	}
	
	public boolean isKnight()
	{
		return this instanceof Knight;
	}
	
	public boolean isColor(boolean white)
	{
		return isWhite() == white;
	}
	
	public boolean isSafeFromAttack()
	{
		return square.isSafeFromAttack();
	}

	public abstract Piece clone(boolean white, Square square2, Board board);
}