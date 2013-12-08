package Pieces;

import chess.Board;
import chess.Square;
import Utilities.SetOf2dCoords;

public class Rook extends Piece
{
	public Rook(boolean white, Square square, Board board)
	{
		super(white, square, board);
	}
	
	public Rook(){}
	
	public char getSign()
	{
		return 'r';
	}

	public String getName()
	{
		return "Rook";
	}
	
	public SetOf2dCoords getSetOfPossibleMoves()
	{
		SetOf2dCoords list = new SetOf2dCoords();
		for(int i = -1; i < 2; i += 2)
		{
			list.addList(helpFunction(i, 0));
			list.addList(helpFunction(0, i));
		}
		return list;
	}
	
	public Piece clone(boolean white, Square square2, Board board)
	{
		return new Rook(white, square2, board);
	}
}