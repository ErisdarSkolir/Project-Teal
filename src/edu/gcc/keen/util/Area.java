package edu.gcc.keen.util;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector4f;

import edu.gcc.keen.entities.Entity;

/**
 * This class will help with collision detection. It will contain a list of
 * tiles and entities within it, so those entities only have to check collision
 * with what is in this area
 * 
 * @author DONMOYERLR17
 *
 */
public class Area
{
	private List<GameObject> objects = new ArrayList<>();

	private Vector4f positionAndSize;

	private boolean shouldUpdate;

	/**
	 * Constructor
	 * 
	 * @param positionAndSize
	 */
	public Area(Vector4f positionAndSize)
	{
		this.positionAndSize = new Vector4f(positionAndSize);
	}

	/**
	 * Update this Area and remove objects if needed
	 */
	public void tick()
	{
		if (shouldUpdate)
		{
			shouldUpdate = false;

			/*
			 * for (GameObject object : objects)
			 * {
			 * for (GameObject object2 : objects)
			 * {
			 * if (object != object2 && object.canCollide() &&
			 * BoundingBox.isIntersecting(object2.getPosition(), object2.getScale(),
			 * object.getPosition(), object.getScale()))
			 * {
			 * object2.onCollide(object);
			 * object.onCollide(object2);
			 * }
			 * }
			 * }
			 */
		}
	}

	public void checkCollisionX(Entity entity)
	{
		List<GameObject> collidingObjects = new ArrayList<>();

		for (GameObject object2 : objects)
		{
			if (entity != object2 && BoundingBox.isIntersecting(entity, object2))
			{
				collidingObjects.add(object2);
			}
		}

		if (!collidingObjects.isEmpty())
			entity.onCollideX(collidingObjects);
	}

	public void checkCollisionY(Entity entity)
	{
		List<GameObject> collidingObjects = new ArrayList<>();

		for (GameObject object2 : objects)
		{
			if (entity != object2 && BoundingBox.isIntersecting(entity, object2))
			{
				collidingObjects.add(object2);
			}
		}

		if (!collidingObjects.isEmpty())
			entity.onCollideY(collidingObjects);
	}

	public void addObject(GameObject object)
	{
		object.setArea(this);
		objects.add(object);
	}

	public void removeObject(GameObject object)
	{
		objects.remove(object);
	}

	public void setShouldUpdate(boolean update)
	{
		this.shouldUpdate = update;
	}

	public boolean shouldUpdate()
	{
		return shouldUpdate;
	}
}
