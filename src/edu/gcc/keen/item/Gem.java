package edu.gcc.keen.item;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Texture;

public class Gem extends Item
{
	private int color;

	public Gem(Vector3f position)
	{
		super(Texture.getTexture("tilesheet"), 18, 165, 61, position, new Vector2f(1.0f, 1.0f));
	}
}
