package edu.gcc.keen.item;

import org.joml.Vector2f;

import edu.gcc.keen.graphics.Texture;
import edu.gcc.keen.util.GameObject;

public class Item extends GameObject
{
	private float pointValue;

	/**
	 * Constructor
	 * 
	 * @param position
	 * @param texture
	 */
	public Item(Texture texture, Vector2f position, Vector2f scale)
	{
		super(texture, position, scale);
	}

	/**
	 * Update this item
	 */
	@Override
	public void tick()
	{

	}

	/**
	 * Render this item
	 */
	@Override
	public void onCollide(GameObject object)
	{

	}

	public float getPointValue()
	{
		return pointValue;
	}
}
