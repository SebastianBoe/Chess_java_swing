package Pieces;

import chess.Square;
import Utilities.SetOf2dCoords;

public class Queen extends Piece
{
	public Queen(boolean white, Square square)
	{
		super(white, square);
	}
	
	@Override
	public Piece clone(boolean white, Square square2)
	{
		return new Queen(white, square2);
	}
	
	public Queen(){}
	
	public char getSign()
	{
		return 'q';
	}
	
	public String getName()
	{
		return "Queen";
	}
	
	public SetOf2dCoords getSetOfPossibleMoves()
	{
		SetOf2dCoords list = new SetOf2dCoords();

		//bruker t�rnets bevegelses algoritme
		for(int i = -1; i < 2; i += 2)
		{
			list.addList(helpFunction(i, 0));
			list.addList(helpFunction(0, i));
		}
		
		//bruker l�perens bevegelses algoritme
		for (int dr = -1; dr < 2; dr += 2)
			for (int dc = -1; dc < 2; dc += 2)
				list.addList(helpFunction(dr, dc));
		
		//summen av disse er damens bevegelses algoritme
		return list;
	}
}
