package edu.gcc.keen.util;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class BoundingBox
{
	private BoundingBox()
	{
		throw new IllegalStateException("Utility class");
	}

	public static boolean isIntersecting(GameObject object1, GameObject object2)
	{
		Vector3f position1 = object1.getPosition();
		Vector2f scale1 = object1.getScale().mul(2.0f).add(object1.aabbOffset);
		Vector3f position2 = object2.getPosition();
		Vector2f scale2 = object2.getScale().mul(2.0f).add(object2.aabbOffset);

		return (Math.abs(position1.x - position2.x) * 2 < (scale1.x + scale2.x) && Math.abs(position1.y - position2.y) * 2 < (scale1.y + scale2.y));
	}

	public static float minX(GameObject object1, GameObject object2)
	{
		Vector3f position1 = object1.getPosition();
		Vector2f scale1 = object1.getScale().mul(2.0f).add(object1.aabbOffset);
		Vector3f position2 = object2.getPosition();
		Vector2f scale2 = object2.getScale().mul(2.0f).add(object2.aabbOffset);

		float amountX = Math.min(Math.abs((position1.x - scale1.x / 2) - (position2.x + scale2.x / 2)), Math.abs((position1.x + scale1.x / 2) - (position2.x - scale2.x / 2)));

		return position1.x - position2.x <= 0 ? -amountX : amountX;
	}

	public static float minY(GameObject object1, GameObject object2)
	{
		Vector3f position1 = object1.getPosition();
		Vector2f scale1 = object1.getScale().mul(2.0f).add(object1.aabbOffset);
		Vector3f position2 = object2.getPosition();
		Vector2f scale2 = object2.getScale().mul(2.0f).add(object2.aabbOffset);

		float amountY = Math.min(Math.abs((position1.y + scale1.y / 2f) - (position2.y - scale2.y / 2f)), Math.abs((position1.y - scale1.y / 2f) - (position2.y + scale2.y / 2f)));

		return position1.y - position2.y <= 0 ? -amountY : amountY;
	}

	public static Vector3f resolveY(GameObject object, List<GameObject> collidingObjects)
	{
		if (collidingObjects.isEmpty())
			return new Vector3f();

		float smallest = BoundingBox.minY(object, collidingObjects.get(0));

		for (GameObject secondObject : collidingObjects)
		{
			float tmp = BoundingBox.minY(object, secondObject);

			if (tmp < smallest)
				smallest = tmp;
		}

		return new Vector3f(0.0f, smallest, 0.0f);
	}

	public static Vector3f resolveX(GameObject object, List<GameObject> collidingObjects)
	{
		if (collidingObjects.isEmpty())
			return new Vector3f();

		float smallest = BoundingBox.minX(object, collidingObjects.get(0));

		for (GameObject secondObject : collidingObjects)
		{
			float tmp = BoundingBox.minX(object, secondObject);

			if (tmp < smallest)
				smallest = tmp;
		}

		return new Vector3f(smallest, 0.0f, 0.0f);
	}
}
