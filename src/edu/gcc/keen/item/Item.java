package edu.gcc.keen.item;

import org.joml.Vector2f;

import edu.gcc.keen.graphics.Texture;
import edu.gcc.keen.util.GameObject;

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
