package edu.gcc.keen.item;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.util.GameObject;

public class Item extends GameObject
{
	protected int pointValue;

	/**
	 * Constructor
	 * 
	 * @param position
	 * @param texture
	 */
	public Item(int texture, int columns, int rows, int index, Vector3f position, Vector2f scale)
	{
		super(texture, columns, rows, index, position, scale);
	}

	/**
	 * Update this item
	 */
	@Override
	public void tick()
	{

	}

	public int getPointValue()
	{
		return pointValue;
	}
}
