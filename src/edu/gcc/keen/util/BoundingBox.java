package edu.gcc.keen.util;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.gameobjects.GameObject;

/**
 * Utility class providing math required for collision detection
 */
public class BoundingBox
{
	private BoundingBox()
	{
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Return true if the given object and area are intersecting
	 * 
	 * @param object
	 * @param area
	 * @return true if the object is intersecting with the area
	 */
	public static boolean isIntersecting(GameObject object, Area area)
	{
		Vector3f position1 = object.getPosition();
		Vector2f scale1 = object.getScale().mul(2.0f).add(object.getAabbOffset());
		Vector2f position2 = area.getPosition();
		Vector2f scale2 = area.getScale();

		return (Math.abs(position1.x - position2.x) * 2 < (scale1.x + scale2.x) && Math.abs(position1.y - position2.y) * 2 < (scale1.y + scale2.y));
	}

	/**
	 * Returns true if the given object is completely contained inside the area
	 * 
	 * @param object
	 * @param area
	 * @return true if object is inside the area
	 */
	public static boolean contains(GameObject object, Area area)
	{
		Vector3f position2 = object.getPosition();
		Vector2f scale2 = object.getScale().mul(2.0f).add(object.getAabbOffset());
		Vector2f position1 = area.getPosition();
		Vector2f scale1 = area.getScale();

		return (Math.abs(position1.x - position2.x) * 2 < (scale1.x - scale2.x) && Math.abs(position1.y - position2.y) * 2 < (scale1.y - scale2.y));
	}

	/**
	 * Returns true if the objects are intersecting
	 * 
	 * @param object1
	 * @param object2
	 * @return true if objects are intersecting
	 */
	public static boolean isIntersecting(GameObject object1, GameObject object2)
	{
		Vector3f position1 = object1.getPosition();
		Vector2f scale1 = object1.getScale().mul(2.0f).add(object1.getAabbOffset());
		Vector3f position2 = object2.getPosition();
		Vector2f scale2 = object2.getScale().mul(2.0f).add(object2.getAabbOffset());

		return (Math.abs(position1.x - position2.x) * 2 < (scale1.x + scale2.x) && Math.abs(position1.y - position2.y) * 2 < (scale1.y + scale2.y));
	}

	/**
	 * Get the minimum x distance to move the first object to move out of collision
	 * 
	 * @param object1
	 * @param object2
	 * @return a float of the minimum translation to get out of collision
	 */
	public static float minX(GameObject object1, GameObject object2)
	{
		Vector3f position1 = object1.getPosition();
		Vector2f scale1 = object1.getScale().mul(2.0f).add(object1.getAabbOffset());
		Vector3f position2 = object2.getPosition();
		Vector2f scale2 = object2.getScale().mul(2.0f).add(object2.getAabbOffset());

		float amountX = Math.min(Math.abs((position1.x - scale1.x / 2) - (position2.x + scale2.x / 2)), Math.abs((position1.x + scale1.x / 2) - (position2.x - scale2.x / 2)));

		return position1.x - position2.x <= 0 ? -amountX : amountX;
	}

	/**
	 * Get the minimum y distance to move the first object to move out of collision
	 * 
	 * @param object1
	 * @param object2
	 * @return a float of the minimum translation to get out of collision
	 */
	public static float minY(GameObject object1, GameObject object2)
	{
		Vector3f position1 = object1.getPosition();
		Vector2f scale1 = object1.getScale().mul(2.0f).add(object1.getAabbOffset());
		Vector3f position2 = object2.getPosition();
		Vector2f scale2 = object2.getScale().mul(2.0f).add(object2.getAabbOffset());

		float amountY = (float) Math.min(Math.abs((position1.y + scale1.y / 2.0) - (position2.y - scale2.y / 2.0)), Math.abs((position1.y - scale1.y / 2.0) - (position2.y + scale2.y / 2.0)));

		return position1.y - position2.y <= 0 ? -amountY : amountY;
	}
}
