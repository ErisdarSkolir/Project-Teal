package edu.gcc.keen.item;

import org.joml.Vector2f;

import edu.gcc.keen.util.GameObject;
import gcc.edu.keen.graphics.Texture;

public class Item extends GameObject
{
	public Item(Vector2f position, Texture texture)
	{
		super(texture, position);
	}

	@Override
	public void tick()
	{

	}

	@Override
	public void onCollide(GameObject object)
	{

	}
}
