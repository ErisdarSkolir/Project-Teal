package edu.gcc.keen.entities;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.gameobjects.GameObject;
import edu.gcc.keen.gameobjects.ObjectType;

/**
 * An entity is any game object that moves or has more complex logic.
 */
public abstract class Entity extends GameObject
{
	protected float horizontalVelocity;
	protected float verticalVelocity;

	protected boolean canKill;
	protected boolean canCollideWithKeen;
	protected boolean stunned;
	protected boolean canBeStunned;

	/**
	 * Constructor
	 */
	public Entity(int texture, int columns, int rows, int index, Vector3f position, Vector2f scale)
	{
		super(texture, columns, rows, index, position, scale, ObjectType.ENTITY);
	}

	/**
	 * Default method to move this entity.
	 */
	public abstract void move();

	/**
	 * Called when another gameObject collides with this entity on the X axis
	 * 
	 * @param object
	 */
	public abstract void onCollideX(List<GameObject> collidingObjects);

	/**
	 * Called when another gameObject collides with this entity on the Y axis
	 * 
	 * @param object
	 */
	public abstract void onCollideY(List<GameObject> collidingObjects);
}
