package edu.gcc.keen.util;

import org.joml.Vector2f;

public abstract class GameObject
{
	private Vector2f position;

	public GameObject(Vector2f position)
	{
		this.position = new Vector2f(position);
	}

	public abstract void tick();

	public Vector2f getPosition()
	{
		return new Vector2f(position);
	}
}
