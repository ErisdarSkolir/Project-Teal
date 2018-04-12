package edu.gcc.keen.util;

import org.joml.Vector2f;

import gcc.edu.keen.graphics.Texture;

public abstract class GameObject
{
	private Vector2f position;
	private Texture texture;
	private Area area;

	public GameObject(Vector2f position)
	{
		this.position = new Vector2f(position);
	}

	public abstract void tick();

	/**
	 * Called when another gameObject collides with this entity
	 * 
	 * @param object
	 */
	public abstract void onCollide(GameObject object);

	public Vector2f getPosition()
	{
		return new Vector2f(position);
	}

	public void setArea(Area area)
	{
		this.area = area;
	}
}
