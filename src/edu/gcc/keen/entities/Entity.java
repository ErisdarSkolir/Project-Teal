package edu.gcc.keen.entities;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Texture;
import edu.gcc.keen.util.GameObject;

/**
 * An entity is any game object that moves or has more complex logic.
 * 
 * @author DONMOYERLR17
 *
 */
public abstract class Entity extends GameObject
{
	boolean canCollide = false;

	protected float horizontalVelocity;
	protected float verticalVelocity;

	/**
	 * Constructor
	 */
	public Entity(Texture texture, Vector3f position, Vector2f scale)
	{
		super(texture, position, scale);
	}

	/**
	 * Default method to move this entity.
	 */
	public abstract void move();
}
