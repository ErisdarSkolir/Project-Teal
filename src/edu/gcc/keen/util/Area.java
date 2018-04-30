package edu.gcc.keen.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.joml.Vector2f;

import edu.gcc.keen.entities.Entity;
import edu.gcc.keen.gameobjects.GameObject;

/**
 * This class will help with collision detection. It will contain a list of
 * tiles and entities within it, so those entities only have to check collision
 * with what is in this area
 */
public class Area
{
	private List<GameObject> objects = new LinkedList<>();

	private Vector2f position;
	private Vector2f scale;

	private boolean shouldUpdate;

	/**
	 * Constructor
	 * 
	 * @param positionAndSize
	 */
	public Area(Vector2f position, Vector2f scale)
	{
		this.position = position;
		this.scale = scale;
	}

	/**
	 * Update this Area and remove objects if needed
	 */
	public void tick()
	{
		if (!shouldUpdate)
			return;

		shouldUpdate = false;

		for (Iterator<GameObject> itr = objects.iterator(); itr.hasNext();)
		{
			GameObject object = itr.next();

			if (!BoundingBox.contains(object, this))
			{
				object.setShouldUpdateArea(true);

				if (!BoundingBox.isIntersecting(object, this))
				{
					itr.remove();
					object.removeArea(this);
				}
			}
		}
	}

	/**
	 * Check for collision between the given entity and the rest of the objects in
	 * this area. Calls the entity's collision methods if there is a collision on
	 * the given axis
	 * 
	 * @param axis
	 * @param entity
	 */
	public void checkCollision(boolean axis, Entity entity)
	{
		shouldUpdate = true;

		ArrayList<GameObject> collidingObjects = new ArrayList<>();
		collidingObjects.ensureCapacity(objects.size());

		for (GameObject object2 : objects)
		{
			if (entity != object2 && BoundingBox.isIntersecting(entity, object2))
				collidingObjects.add(object2);
		}

		if (!collidingObjects.isEmpty())
		{
			if (axis)
				entity.onCollideX(collidingObjects);
			else
				entity.onCollideY(collidingObjects);
		}
	}

	/**
	 * Add an object to this area
	 * 
	 * @param object
	 */
	public void addObject(GameObject object)
	{
		if (object.addArea(this))
			objects.add(object);
	}

	/**
	 * Remove an object from this area
	 * 
	 * @param object
	 */
	public void removeObject(GameObject object)
	{
		objects.remove(object);
	}

	// Getters and Setters

	public void setShouldUpdate(boolean update)
	{
		this.shouldUpdate = update;
	}

	public boolean shouldUpdate()
	{
		return shouldUpdate;
	}

	public Vector2f getPosition()
	{
		return new Vector2f(position.x, position.y);
	}

	public Vector2f getScale()
	{
		return new Vector2f(scale.x, scale.y);
	}
}
