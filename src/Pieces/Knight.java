package Pieces;

import chess.Square;
import Utilities.SetOf2dCoords;

public class Knight extends Piece
{
	public Knight(Boolean white, Square square)
	{
		super(white, square);
	}
	
	@Override
	public Piece clone(boolean white, Square square2)
	{
		return new Knight(white, square2);
	}
	
	public Knight(){}
	
	public char getSign()
	{
		return 'n';
	}
	
	public String getName()
	{
		return "Knight";
	}
	
	public SetOf2dCoords getSetOfPossibleMoves()
	{
		SetOf2dCoords list = new SetOf2dCoords();
		for (int r = getRow() - 2; r < getRow() + 3; r += 4) 
		{
			for (int c = getColumn() - 1; c < getColumn() + 2; c += 2)
			{
				if(isLegalMoveTo(r, c))
					list.addCoordinate(r, c);
			}
		}
		
		for (int r = getRow() - 1; r < getRow() + 2; r += 2) 
		{
			for(int c = getColumn() - 2; c < getColumn() + 3; c += 4)
			{
				if(isLegalMoveTo(r, c))
					list.addCoordinate(r, c);
			}
		}
		return list;
	}
	
}
