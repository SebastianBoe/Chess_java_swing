package chess;


import Pieces.Piece;
import Utilities.*;

/*
 * Kravspesifikasjon:
 * Skal kunne inneholde en brikke.
 * Skal vite hva slags farge den er
 * public boolean hasAPiece()
 * public Piece getPiece()
 * public boolean hasWhitePiece()
 * public void setPiece()
 * public void removePiece()
 * public boolean isWhite()
 * Muligens etterhvert vite hvem som klarer � bevege seg dit,
 * hjelper meg med bla. annet � flytte kongen og boten
 */

public class Square implements Cloneable 
{
	private final boolean white;
	private final Coordinate coordinate;
	private Piece piece;
	private SetOf2dCoords underThreatBy = new SetOf2dCoords();
	
	public Square clone()
	{
		return new Square(this);
	}
	
	public Square(boolean white, Coordinate coordinate)
	{
		this.coordinate = coordinate;
		this.white = white;
	}
	
	public Square(Square square)
	{
		white = square.isWhite();
		coordinate = square.getCoordinate().clone();
		piece = square.piece.clone(square.piece.isWhite(), this);
		piece.setMoved(square.piece.isMoved());
		underThreatBy = square.underThreatBy.clone();
	}

	
	
	@Override
	public String toString()
	{
		if(this == null)
			return "null";
		String s = "";
		s += isWhite() ? "White" : "Black";
		s += " square at " + coordinate;
		return s;
	}
	
	public SetOf2dCoords getSetOfThreats()
	{
		return underThreatBy;
	}	
	
	public int getRow()
	{
		return coordinate.getRow();
	}
	
	public int getColumn()
	{
		return coordinate.getColumn();
	}
	
	public Coordinate getCoordinate()
	{
		return coordinate;
	}
	
	public void addThreat(Coordinate coordinate)
	{
		underThreatBy.addCoordinate(coordinate);
	}
	
	public void clearThreats()
	{
		underThreatBy.clear();
	}
		
	public boolean isEmpty()
	{
		return piece == null;
	}
	
	public boolean hasAPiece()
	{
		return !isEmpty();
	}
	
	public Piece getPiece()
	{
		return piece;
	}
	
	public void setPiece(Piece piece)
	{
		this.piece = piece;
	}
	
	public void removePiece()
	{
		piece = null;
	}
	
	public boolean isWhite()
	{
		return white;
	}
	
	public String getSign()
	{
		String iconName = "";
		if(hasAPiece())
		{
			iconName += piece.getSign();
			iconName += piece.isWhite() ? "l" : "d";
		}
		return iconName += isWhite() ? "l" : "d";
	}
	
	public boolean isSafeFromAttack()
	{
		return getSetOfThreats().isEmpty();
	}
}