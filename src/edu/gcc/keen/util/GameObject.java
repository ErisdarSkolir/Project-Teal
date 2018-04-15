package edu.gcc.keen.util;

import org.joml.Vector2f;

import edu.gcc.keen.graphics.Texture;

/**
 * The base class for all in-game objects
 * 
 * @author DONMOYERLR17
 *
 */
public abstract class GameObject
{
	private Vector2f position;
	private Texture texture;
	private Area area;

	/**
	 * Constructor
	 * 
	 * @param texture
	 * @param position
	 */
	public GameObject(Texture texture, Vector2f position)
	{
		this.texture = texture;
		this.position = new Vector2f(position);
	}

	/**
	 * Update this object
	 */
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

	public Texture getTexture()
	{
		return texture;
	}
}
