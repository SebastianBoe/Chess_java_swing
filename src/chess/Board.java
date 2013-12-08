package chess;

import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import Pieces.Pawn;
import Pieces.Piece;
import Pieces.Queen;
import Pieces.Rook;
import Utilities.*;

public class Board
{
	private final static boolean white = true;
	private final static boolean black = false;
	private Square[][] board = new Square[8][8];
	private Judge judge;
	

	Board(Judge judge)
	{
		this.judge = judge;
		initializeSquares();
		initializePieces();
	}
	
	public Board(Board board)
	{
		Square[][] new_board = new Square[8][8];
		
		for (int y = 0; y < 8; y++)
		{
			for (int x = 0; x < 8; x++)
			{
				new_board[y][x] = board.board[y][x].clone();
			}
		}
		this.board = new_board;
		this.judge = judge;
	}
	
	public Board clone()
	{
		return new Board(this);
	}
	
	private void initializeSquares()
	{
		boolean tempWhite = white;
		for (int row = 0; row < 8; row++)
		{
			for(int column = 0; column < 8; column++)
			{
				board[row][column] = new Square(tempWhite, new Coordinate(row, column));
				tempWhite = !tempWhite;
			}
			tempWhite = !tempWhite;
		}
	}
	
	public int getNumberOfPieces()
	{
		int numberOfPieces = 0;
		SquareIterator it =  new SquareIterator(this);
		while(it.hasNext())
		{
			if(it.next().hasAPiece())
				numberOfPieces++;
		}
		return numberOfPieces;
	}

	private void initializePieces()
	{
		board[0][0].setPiece(new Rook(black, board[0][0], this));
		board[0][1].setPiece(new Knight(black, board[0][1], this));
		board[0][2].setPiece(new Bishop(black, board[0][2], this));
		board[0][3].setPiece(new Queen(black, board[0][3], this));
		board[0][4].setPiece(new King(black, board[0][4], this));
		board[0][5].setPiece(new Bishop(black, board[0][5], this));
		board[0][6].setPiece(new Knight(black, board[0][6], this));
		board[0][7].setPiece(new Rook(black, board[0][7], this));
		
		for (int i = 0; i < 8; i++)
			board[1][i].setPiece(new Pawn(black, board[1][i], this));
		
		for (int i = 0; i < 8; i++)
			board[6][i].setPiece(new Pawn(white, board[6][i], this));			
		
		board[7][0].setPiece(new Rook(white, board[7][0], this));
		board[7][1].setPiece(new Knight(white, board[7][1], this));
		board[7][2].setPiece(new Bishop(white ,board[7][2], this));
		board[7][3].setPiece(new Queen(white ,board[7][3], this));
		board[7][4].setPiece(new King(white ,board[7][4], this));
		board[7][5].setPiece(new Bishop(white ,board[7][5], this));
		board[7][6].setPiece(new Knight(white ,board[7][6], this));
		board[7][7].setPiece(new Rook(white ,board[7][7], this));
	}
	
	public Piece getPieceAt(int row, int column)
	{
		return getPieceAt(new Coordinate(row, column));
	}
	
	public Piece getPieceAt(Coordinate coordinate)
	{
		return getSquareAt(coordinate).getPiece();
	}
	
	public Square getSquareAt(int row, int column)
	{
		return getSquareAt(new Coordinate(row, column));
	}
	
	public Square getSquareAt(Coordinate coordinate)
	{
		return coordinate.isInsideBoard() ? board[coordinate.getRow()][coordinate.getColumn()] : null;
	}

	public void printChessboard()
	{
		System.out.println("");
		for (int i = 0; i < 8; i++)
			System.out.print(i);

		System.out.println("");
		for(int r = 0; r < 8; r++)
		{
			for(int c = 0; c < 8; c++)
			{	
				Coordinate coordinate = new Coordinate(r, c);
				char s = getSquareAt(coordinate).isEmpty() ? ' ' : getPieceAt(coordinate).getSign();
				System.out.print(s);
			}
			System.out.println(r);
		}
		System.out.println("");
	}
	
	public void doMove(Move move)
	{
		Piece piece = move.getFromPiece();
		
		if(move.isCastlingMove())
			doCastle(move);
		else
		{
			move.getToSquare().setPiece(move.getFromPiece());
			move.getFromSquare().removePiece();
			move.getToSquare().getPiece().setSquare(move.getToSquare());
		}
		
		piece.setMoved(true);
	}
	
	void doCastle(Move move)
	{
		Coordinate from = move.getFromCoordinate();
		Coordinate to = move.getToCoordinate();
		
		Piece king = getPieceAt(from);
		king.moveTo(to);

		if(king.isWhite())
			doWhiteCastling(to);
		else
			doBlackCastling(to);
	}

	private void doBlackCastling(Coordinate to)
	{
		if(to.equals(new Coordinate(0, 2)))
		{
			Piece rook = getPieceAt(0, 0);
			rook.moveTo(new Coordinate(0, 3));				
		}
		else
		{
			Piece rook = getPieceAt(0, 7);
			rook.moveTo(new Coordinate(0, 5));				
		}
	}

	private void doWhiteCastling(Coordinate to)
	{
		if(to.equals(7, 2))
		{
			Piece rook = getPieceAt(7, 0);
			rook.moveTo(new Coordinate(7, 3));
		}
		else
		{
			Piece rook = getPieceAt(7, 7);
			rook.moveTo(new Coordinate(7, 5));
		}
	}
	
	public Piece getKing(boolean white)
	{
		PieceIterator it = new PieceIterator(this);
		while(it.hasNext())
		{
			Piece piece = it.next();
			if(piece.isKing() && piece.isColor(white))
				return piece;
		}
		return null;
	}
}