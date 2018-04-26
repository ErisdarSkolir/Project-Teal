package edu.gcc.keen.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.joml.Vector2f;

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
		if (shouldUpdate)
		{
			shouldUpdate = false;

			for (Iterator<GameObject> itr = objects.iterator(); itr.hasNext();)
			{
				GameObject object = itr.next();

				if (object.shouldDestroy)
					itr.remove();

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
	}

	public void checkCollisionX(Entity entity)
	{
		this.setShouldUpdate(true);

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
		this.setShouldUpdate(true);

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
		if (!objects.contains(object))
		{
			object.addArea(this);
			objects.add(object);
		}
	}

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
		return position;
	}

	public Vector2f getScale()
	{
		return scale;
	}
}
