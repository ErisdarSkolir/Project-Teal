package edu.gcc.keen.item;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Texture;

public class Gum extends Item
{
	public Gum(Vector3f position)
	{
		super(new Texture("tilesheet", 18, 165, 61), position, new Vector2f(1.0f, 1.0f));
		this.pointValue = 100;
	}
}
