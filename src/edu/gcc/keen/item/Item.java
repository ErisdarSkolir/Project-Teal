package edu.gcc.keen.item;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

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
	public Item(Vector3f position)
	{
		super(new Texture("tilesheet"), position, new Vector2f(1.0f, 1.0f));
	}

	/**
	 * Update this item
	 */
	@Override
	public void tick()
	{

	}

	public float getPointValue()
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
