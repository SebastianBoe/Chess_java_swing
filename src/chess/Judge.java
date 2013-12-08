package chess;

import Pieces.*;
import UI.Audio;
import Utilities.PieceIterator;
import Utilities.SquareIterator;
import Utilities.Move;
import Utilities.Coordinate;
import Utilities.SetOf2dCoords;

public class Judge
{
	private Square selectedSquare = null;
	private boolean whiteTurn = true, aKillHasBeenMade = false;
	private Audio audio = new Audio();
	private Board board;
	
	public Judge(Board board)
	{
		this.board = board;
	}
	
	public Judge(Judge judge)
	{
		Coordinate selected = judge.selectedSquare.getCoordinate();
		whiteTurn = judge.whiteTurn;
		aKillHasBeenMade = judge.aKillHasBeenMade;
		audio = judge.audio;
		board = judge.board.clone();
		selectedSquare = judge.board.getSquareAt(selected);
	}
	
	public Judge clone()
	{
		return new Judge(this);
	}
	
	public boolean SquareClicked(Square clickedSquare)
	{
		Move move = new Move(selectedSquare, clickedSquare);
		Piece clickedPiece = clickedSquare.getPiece();
		
		if(selectedSquare == null)
		{
			if(clickedSquare.hasAPiece())
			{
				if(clickedPiece.isColor(whiteTurn))
					setSelectedSquare(clickedSquare);
				else audio.invalidMove();
			}
			else audio.invalidMove();
		}
		else if(selectedSquare != null)
		{
			if(clickedSquare.hasAPiece())
			{
				if(clickedPiece.isColor(whiteTurn))
					setSelectedSquare(clickedSquare);
				else
					return tryToKillPiece(move);
			}
			else if(clickedSquare.isEmpty())
				return tryToMovePiece(move);
		}
		audio.updateMusic(false);
		return false;
	}
	
	private boolean tryToKillPiece(Move move)
	{
		Piece piece = move.getToPiece();
		if(executeOrder(move))
		{
			if(!aKillHasBeenMade)
				audio.firstBlood();
			aKillHasBeenMade = true;
			audio.attack(piece);
			return true;
		}
		return false;
	}
	
	private boolean tryToMovePiece(Move move)
	{
		if(executeOrder(move))
		{
			audio.move(move.getToPiece());
			return true;
		}
		return false;
	}
	
	private boolean executeOrder(Move move) 
	{
		if(isValidMove(move))
		{
			board.doMove(move);
			Piece piece = move.getToPiece();
			checkAndExecutePawnUpgrade(piece);
			piece.setMoved(true);
			
			updateThreats();
			
			if(kingIsInMate(!whiteTurn))
			{
				audio.mate();
				audio.updateMusic(true);
			}
			
			if(kingIsInChessmate(!whiteTurn))
				audio.chessMate();
			whiteTurn = !whiteTurn;
			selectedSquare = null;
			return true;
		}
		audio.invalidMove();
		return false;	
	}

	private void checkAndExecutePawnUpgrade(Piece piece) {
		if(piece.isPawn())
		{
			Square square = piece.getSquare();
			if(square.getRow() == 0)
			{
				square.setPiece(new Queen(true, square));
				updateThreats();
				audio.PawnUpgrade();
			}
			else if(square.getRow() == 7)
			{
				square.setPiece(new Queen(false, square));
				updateThreats();
				audio.PawnUpgrade();
			}
		}
	}

	public Square getSelectedSquare() 
	{
		return selectedSquare;
	}
	
	public void setSelectedSquare(Square selectedSquare) 
	{
		this.selectedSquare = selectedSquare;
		audio.selected(selectedSquare.getPiece());
	}
	
	public boolean isWhiteTurn() 
	{
		return whiteTurn;
	}
	
	public void setWhiteTurn(boolean whiteTurn) 
	{
		this.whiteTurn = whiteTurn;
	}
	
	public void updateThreats()
	{
		clearThreats();
		addThreats();
	}

	private void addThreats() 
	{
		PieceIterator it = new PieceIterator(board);
		while(it.hasNext())
			{
			Piece piece = it.next();
			Square threateningSquare = piece.getSquare();
			SetOf2dCoords threateningMoves = piece.getSetOfPossibleMoves();
			for (int index = 0; index < threateningMoves.size(); index++)
				board.getSquareAt(threateningMoves.getCoordinateAt(index)).addThreat(threateningSquare.getCoordinate());
			}
	}
	
	public boolean isValidMove(Move move)
	{
		Square squareMovingFrom = move.getFromSquare();
		Piece pieceToBeMoved = move.getFromPiece();
		
		if(move.isInsideBoard())
			if(squareMovingFrom.hasAPiece())
			{
				if(pieceToBeMoved.getSetOfPossibleMoves().contains(move))
				{
					if(!simulatedMovePutsYourselfIntoMate(move))
						return true;
				}
				else if(isALegalCastling(move))
				{
					if(!simulatedCastlingPutsYourselfIntoMate2(move))
						return true;
				}
			}
		return false;
	}
	
	private boolean simulatedCastlingPutsYourselfIntoMate2(Move move) 
	{
		Boolean whiteKing = move.getFromPiece().isWhite();
		
		Judge judge = this.clone();
		judge.board.doCastle(move);
		
		return judge.kingIsInMate(whiteKing);
	}

	
	private boolean isALegalCastling(Move move) 
	{
		Piece piece = move.getFromPiece();
		Coordinate toCoordinate = move.getToCoordinate();
		if(piece.isKing() && piece.isSafeFromAttack() && !piece.isMoved())
		{
			if(whiteTurn)
			{
				if(whiteCanCastleWest(toCoordinate))
					return true;
				else if(whiteCanCastleEast(toCoordinate))
					return true;
			}
			else 
			{
				if(!whiteTurn)
				{
					if(blackCanCastleWest(toCoordinate))
						return true;
					else if(blackCanCastleEast(toCoordinate))
						return true;
				}
			}
		}
		return false;
	}

	private boolean blackCanCastleEast(Coordinate toCoordinate) 
	{
		Piece rook = board.getSquareAt(0, 7).getPiece();
		
		return toCoordinate.equals(new Coordinate(0, 6)) && !rook.isMoved()
		&& rook.isRook()
		&& !rook.isWhite()
		&& board.getSquareAt(0, 6).isEmpty()
		&& !isUnderThreatByColor(true, board.getSquareAt(0, 6))
		&& board.getSquareAt(0, 5).isEmpty()
		&& !isUnderThreatByColor(true, board.getSquareAt(0, 5));
	}
	
	private boolean isUnderThreatByColor(boolean white, Square square)
	{
		SetOf2dCoords coords = square.getSetOfThreats();
		for (int i = 0; i < coords.size(); i++)
		{
			Piece piece = board.getPieceAt(coords.getCoordinateAt(i));
			if(piece != null && piece.isColor(white))
				return true;
		}
		return false;
	}
	
	private boolean blackCanCastleWest(Coordinate toCoordinate) 
	{
		Piece rook = board.getSquareAt(0, 0).getPiece();
		
		return toCoordinate.equals(new Coordinate(0, 2)) && !rook.isMoved()
		&& rook.isRook()
		&& !rook.isWhite()
		&& board.getSquareAt(0, 3).isEmpty()
		&& board.getSquareAt(0, 2).isEmpty()
		&& board.getSquareAt(0, 1).isEmpty()
		&& !isUnderThreatByColor(true, board.getSquareAt(0, 2))
		&& !isUnderThreatByColor(true, board.getSquareAt(0, 3));
	}

	private boolean whiteCanCastleEast(Coordinate toCoordinate) {
		Piece rook = board.getSquareAt(7, 7).getPiece();
		
		return toCoordinate.equals(new Coordinate(7, 6)) && !rook.isMoved()
				&& rook.isRook()
				&& rook.isWhite()
				&& board.getSquareAt(7, 6).isEmpty()
				&& board.getSquareAt(7, 5).isEmpty()
				&& !isUnderThreatByColor(false, board.getSquareAt(7, 6))
				&& !isUnderThreatByColor(false, board.getSquareAt(7, 5));
	}

	private boolean whiteCanCastleWest(Coordinate toCoordinate) 
	{
		Piece rook = board.getSquareAt(7, 0).getPiece();
		
		return toCoordinate.equals(new Coordinate(7, 2)) && !rook.isMoved()
				&& rook.isRook()
				&& rook.isWhite()
				&& board.getSquareAt(7, 3).isEmpty()
				&& board.getSquareAt(7, 2).isEmpty()
				&& board.getSquareAt(7, 1).isEmpty()
				&& !isUnderThreatByColor(false, board.getSquareAt(7, 2))
				&& !isUnderThreatByColor(false, board.getSquareAt(7, 3));
	}

	public boolean simulatedMovePutsYourselfIntoMate(Move move)
	{
		Piece toPiece = move.getToPiece();
		Piece fromPiece = move.getFromPiece();
		Square toSquare = move.getToSquare();
		
		board.doMove(move);
		
		boolean kingIsInMate = kingIsInMate(fromPiece.isWhite());
		
		fromPiece.moveTo(move.getFromCoordinate());
		toSquare.setPiece(toPiece);
		
		return kingIsInMate;
	}
	
	private void clearThreats()
	{
		SquareIterator it = new SquareIterator(board);
		while(it.hasNext())
		{
			it.next().clearThreats();
		}
	}
	
	public boolean kingIsInChessmate(boolean white)
	{
		if(kingIsInMate(white))
		{
			PieceIterator it = new PieceIterator(board);
			while(it.hasNext())
			{
				Piece piece = it.next();
				Coordinate fromCoordinate = piece.getSquare().getCoordinate();
				SetOf2dCoords moves = piece.getSetOfPossibleMoves();
				if(piece.isColor(white) && !moves.isEmpty())
				{
					for (Coordinate toCoordinate : moves)
					{
						Move move = new Move(board.getSquareAt(fromCoordinate), board.getSquareAt(toCoordinate));
						if(isValidMove(move))
							return false;
					}
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean kingIsInMate(boolean white)
	{
		return !board.getKing(white).isSafeFromAttack();
	}

	public Audio getAudio() 
	{
		return audio;
	}
}