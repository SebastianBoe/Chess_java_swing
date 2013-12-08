package Utilities;

import java.util.Iterator;

import chess.Board;
import chess.Square;

import Pieces.Piece;

public class PieceIterator implements Iterator<Piece>
{
	int PiecesIterated;
	int PiecesOnBoard;
	SquareIterator squareIterator;
	
	public PieceIterator(Board chessboard)
	{
		PiecesIterated = 0;
		PiecesOnBoard = chessboard.getNumberOfPieces();
		squareIterator = new SquareIterator(chessboard);
	}
	
	@Override
	public boolean hasNext() 
	{
		return PiecesIterated < PiecesOnBoard; 
	}

	@Override
	public Piece next() 
	{
		while(squareIterator.hasNext())
		{
			Square square = squareIterator.next();
			if(square.hasAPiece())
			{
				PiecesIterated++;
				return square.getPiece();
			}
		}
		throw new RuntimeException();
	}

	@Override
	public void remove() {
		System.out.println("unimplementedmethod");
	}

}
