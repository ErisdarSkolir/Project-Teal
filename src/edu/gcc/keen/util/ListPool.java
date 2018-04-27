package edu.gcc.keen.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import edu.gcc.keen.gameobjects.GameObject;

/**
 * This class contains LinkedLists that can get created, recycled and retrived
 * to reduce the number of object creations per tick
 * 
 * @author DONMOYERLR17
 *
 */
public class ListPool
{
	private static Deque<List<GameObject>> listStack = new ArrayDeque<>();

	private ListPool()
	{
		throw new UnsupportedOperationException("Utility Class");
	}

	/**
	 * Get a list or create a new one
	 * 
	 * @return LinkedList
	 */
	public static List<GameObject> get()
	{
		try
		{
			return listStack.pop();
		}
		catch (NoSuchElementException e)
		{
			return new LinkedList<>();
		}
	}

	/**
	 * Clear the given list and store it in the list of lists
	 * 
	 * @param list
	 */
	public static void recycle(List<GameObject> list)
	{
		list.clear();
		listStack.push(list);
	}
}
