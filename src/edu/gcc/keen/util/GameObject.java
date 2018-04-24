package edu.gcc.keen.util;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Texture;
import edu.gcc.keen.graphics.TextureAtlas;

/**
 * The base class for all in-game objects
 * 
 * @author DONMOYERLR17
 *
 */
public abstract class GameObject implements TextureAtlas
{
	protected Vector3f position;
	protected Vector2f scale = new Vector2f(1.0f, 1.0f);
	protected Vector2f aabbOffset = new Vector2f(0.0f, 0.0f);
	// protected Texture texture;
	protected Area area;

	protected boolean collidable;
	protected boolean shouldDestroy;

	/**
	 * Constructor
	 * 
	 * @param texture
	 * @param position
	 */
	public GameObject(Texture texture, Vector3f position, Vector2f scale)
	{
		this.texture = texture;
		this.position = new Vector3f(position);
		this.scale = scale;
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
	public abstract void onCollideX(List<GameObject> collidingObjects);

	public abstract void onCollideY(List<GameObject> collidingObjects);

	public Vector3f getPosition()
	{
		return new Vector3f(position);
	}

	public void setPosition(Vector3f position)
	{
		this.position = position;
	}

	public void setArea(Area area)
	{
		this.area = area;
	}

	public Texture getTexture()
	{
		return texture;
	}

	public Vector2f getScale()
	{
		return new Vector2f(scale);
	}

	public boolean canCollide()
	{
		return collidable;
	}

	public boolean shouldDestroy()
	{
		return shouldDestroy;
	}

	public void destroy()
	{
		shouldDestroy = true;
	}
}
