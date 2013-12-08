package Utilities;

public class Coordinate 
{
	private int row, column;

	public Coordinate(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public Coordinate(Coordinate coordinate)
	{
		row = coordinate.row;
		column = coordinate.column;
	}

	public Coordinate clone()
	{
		return new Coordinate(this);
	}

	public int getRow() 
	{
		return row;
	}

	public void setRow(int row) 
	{
		this.row = row;
	}

	public int getColumn() 
	{
		return column;
	}

	public void setColumn(int column) 
	{
		this.column = column;
	}
	
	//burde kunne override equals functionen
	public boolean equals(Coordinate coordinate)
	{
		return getRow() == coordinate.getRow() && getColumn() == coordinate.getColumn();
	}
	
	public boolean equals(int row, int column)
	{
		return equals(new Coordinate(row, column));
	}
	
	public String toString()
	{
		return "(" + getRow() + ", " + getColumn() + ")";
	}
	
	public boolean isInsideBoard()
	{
		return row >= 0 && column >= 0 && row <= 7 && column <= 7;
	}
}