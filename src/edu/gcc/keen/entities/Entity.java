package edu.gcc.keen.entities;

import org.joml.Vector2f;

import edu.gcc.keen.graphics.Texture;
import edu.gcc.keen.util.GameObject;

/**
 * An entity is any game object that moves or has more complex logic.
 * 
 * @author DONMOYERLR17
 *
 */
public class Entity extends GameObject
{
	boolean canCollide = false;

	/**
	 * Constructor
	 */
	public Entity(Texture texture, Vector2f position)
	{
		super(texture, position);
	}

	/**
	 * Default update method.
	 */
	@Override
	public void tick()
	{

	}

	/**
	 * Default method to move this entity.
	 */
	public void move()
	{

	}

	@Override
	public void onCollide(GameObject object)
	{

	}
}
