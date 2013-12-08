package Utilities;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * A class used to store 2 dimensjonal coordinates, or any two numbers that 
 * are related.
 */
public class SetOf2dCoords implements Iterable<Coordinate>
{
	private ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
	
	@SuppressWarnings("unchecked")
	public SetOf2dCoords(SetOf2dCoords setOf2dCoords)
	{
		coordinates = (ArrayList<Coordinate>) setOf2dCoords.coordinates.clone();
	}

	public SetOf2dCoords()
	{
		// TODO Auto-generated constructor stub
	}

	public void addList(SetOf2dCoords list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			addCoordinate(list.getCoordinateAt(i));
		}
	}
	
	public SetOf2dCoords clone()
	{
		return new SetOf2dCoords(this);
	}
	
	public void addCoordinate(Coordinate coordinate)
	{
		if(!this.contains(coordinate))
			coordinates.add(coordinate);			
	}
	
	public void addCoordinate(int row, int column)
	{
		addCoordinate(new Coordinate(row, column));
	}
	
	
	
	public int size()
	{
		return coordinates.size();
	}
	
	public void clear()
	{
		coordinates = new ArrayList<Coordinate>();
	}
	
	public boolean contains(Coordinate coordinate)
	{
		for (int i = 0; i < coordinates.size(); i++)
		{
			if(coordinate.equals(coordinates.get(i)))
				return true;
		}
		return false;
	}
	
	public boolean contains(Move move)
	{
		return contains(move.getToCoordinate());
	}
	
	public boolean isEmpty()
	{
		return size() == 0;
	}
	
	@Override
	public String toString()
	{
		if(isEmpty())
			return "Empty";
		
		String s = "";
		for (int i = 0; i < size(); i++) 
		{
			s += coordinates.get(i) + "	";
		}
		return s;
	}
	
	public Coordinate getCoordinateAt(int index)
	{
		return coordinates.get(index);
	}

	@Override
	public Iterator<Coordinate> iterator() 
	{
		return coordinates.iterator();
	}
	
}