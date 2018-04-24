package edu.gcc.keen.item;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Texture;
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
	public Item(Texture texture, Vector3f position, Vector2f scale)
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

	public int getPointValue()
	{
		return pointValue;
	}

	@Override
	public void onCollideX(List<GameObject> collidingObjects)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onCollideY(List<GameObject> collidingObjects)
	{
		// TODO Auto-generated method stub

	}
}
