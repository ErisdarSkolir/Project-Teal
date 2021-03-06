package edu.gcc.keen.util;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.gameobjects.GameObject;

public class BoundingBox
{
	private BoundingBox()
	{
		throw new IllegalStateException("Utility class");
	}

	public static boolean isIntersecting(GameObject object, Area area)
	{
		Vector3f position1 = object.getPosition();
		Vector2f scale1 = object.getScale().mul(2.0f).add(object.getAabbOffset());
		Vector2f position2 = area.getPosition();
		Vector2f scale2 = area.getScale();

		return (Math.abs(position1.x - position2.x) * 2 < (scale1.x + scale2.x) && Math.abs(position1.y - position2.y) * 2 < (scale1.y + scale2.y));
	}

	public static boolean contains(GameObject object, Area area)
	{
		Vector3f position2 = object.getPosition();
		Vector2f scale2 = object.getScale().mul(2.0f).add(object.getAabbOffset());
		Vector2f position1 = area.getPosition();
		Vector2f scale1 = area.getScale();

		return (Math.abs(position1.x - position2.x) * 2 < (scale1.x - scale2.x) && Math.abs(position1.y - position2.y) * 2 < (scale1.y - scale2.y));
	}

	public static boolean isIntersecting(GameObject object1, GameObject object2)
	{
		Vector3f position1 = object1.getPosition();
		Vector2f scale1 = object1.getScale().mul(2.0f).add(object1.getAabbOffset());
		Vector3f position2 = object2.getPosition();
		Vector2f scale2 = object2.getScale().mul(2.0f).add(object2.getAabbOffset());

		return (Math.abs(position1.x - position2.x) * 2 < (scale1.x + scale2.x) && Math.abs(position1.y - position2.y) * 2 < (scale1.y + scale2.y));
	}

	public static float minX(GameObject object1, GameObject object2)
	{
		Vector3f position1 = object1.getPosition();
		Vector2f scale1 = object1.getScale().mul(2.0f).add(object1.getAabbOffset());
		Vector3f position2 = object2.getPosition();
		Vector2f scale2 = object2.getScale().mul(2.0f).add(object2.getAabbOffset());

		float amountX = Math.min(Math.abs((position1.x - scale1.x / 2) - (position2.x + scale2.x / 2)), Math.abs((position1.x + scale1.x / 2) - (position2.x - scale2.x / 2)));

		return position1.x - position2.x <= 0 ? -amountX : amountX;
	}

	public static float minY(GameObject object1, GameObject object2)
	{
		Vector3f position1 = object1.getPosition();
		Vector2f scale1 = object1.getScale().mul(2.0f).add(object1.getAabbOffset());
		Vector3f position2 = object2.getPosition();
		Vector2f scale2 = object2.getScale().mul(2.0f).add(object2.getAabbOffset());

		float amountY = Math.min(Math.abs((position1.y + scale1.y / 2f) - (position2.y - scale2.y / 2f)), Math.abs((position1.y - scale1.y / 2f) - (position2.y + scale2.y / 2f)));

		return position1.y - position2.y <= 0 ? -amountY : amountY;
	}
}
