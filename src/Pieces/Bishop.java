package Pieces;

import chess.Square;
import Utilities.SetOf2dCoords;

public class Bishop extends Piece
{	
	public Bishop(boolean white, Square square)
	{
		super(white,square);
	}
	
	public Bishop(){}
	
	public char getSign()
	{
		return 'b';
	}
	
	public String getName()
	{
		return "Bishop";
	}
	
	public SetOf2dCoords getSetOfPossibleMoves()
	{
		SetOf2dCoords list = new SetOf2dCoords();
		for (int dr = -1; dr < 2; dr += 2)
		{
			for (int dc = -1; dc < 2; dc += 2)
			{
				list.addList(helpFunction(dr, dc));
			}
		}
		return list;
	}

	@Override
	public Piece clone(boolean white, Square square2)
	{
		return new Bishop(white, square2);
	}
}