package Utilities;

import java.util.Iterator;

import chess.Board;
import chess.Square;

public class SquareIterator implements Iterator<Square>
{
	private static final int boardMaxRowIndex = 7, boardMaxColumnIndex = 7;
	private int row = 0, column = 0;
	private Board chessboard;
	
	public SquareIterator(Board chessboard)
	{
		this.chessboard = chessboard;
	}
	
	@Override
	public boolean hasNext()
	{
		return row <= boardMaxRowIndex;
	}

	@Override
	public Square next()
	{
		Square ans = chessboard.getSquareAt(row, column);
		column++;
		boolean columnIsOutsideOfBoard = column > boardMaxColumnIndex;
		if(columnIsOutsideOfBoard)
		{
			column = 0;
			row++;
		}
		return ans;
	}

	@Override
	public void remove() {
	System.out.println("boarditerator.remove() is unimplemented");	
	}

}
