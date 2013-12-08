package Pieces;

import chess.Square;
import Utilities.SetOf2dCoords;

public class King extends Piece
{
	public King(boolean white, Square square) 
	{
		super(white,square);
	}
	
	@Override
	public Piece clone(boolean white, Square square2)
	{
		return new King(white, square2);
	}
	
	public King(){}

	public char getSign()
	{
		return 'k';
	}
	
	public String getName()
	{
		return "King";
	}
	
	public SetOf2dCoords getSetOfPossibleMoves()
	{
		SetOf2dCoords list = new SetOf2dCoords();
		
		for(int row = getRow() - 1; row < getRow() + 2; row++)
			for (int column = getColumn() - 1; column < getColumn() + 2; column++)
				if(isLegalMoveTo(row, column))
					list.addCoordinate(row, column);
		
		return list;
	}
}

