package edu.gcc.keen.util;

import org.joml.Vector2f;

public class BoundingBox
{
	private BoundingBox()
	{
		throw new IllegalStateException("Utility class");
	}

	public static boolean doIntersect(GameObject object1, GameObject object2)
	{
		Vector2f position1 = object1.getPosition();
		Vector2f scale1 = object1.getScale().mul(2.0f);
		Vector2f position2 = object2.getPosition();
		Vector2f scale2 = object2.getScale().mul(2.0f);

		return (Math.abs(position1.x - position2.x) * 2 < (scale1.x + scale2.x) && Math.abs(position1.y - position2.y) * 2 < (scale1.y + scale2.y));
	}

	public static Vector2f getMinimumTranslationiVector(Vector2f position1, Vector2f scale1, Vector2f position2, Vector2f scale2)
	{
		float amountX = Math.min(Math.abs((position1.x - scale1.x / 2) - (position2.x + scale2.x / 2)), Math.abs((position1.x + scale1.x / 2) - (position2.x - scale2.x / 2)));
		float amountY = Math.min(Math.abs((position1.y + scale1.y / 2) - (position2.y - scale2.y / 2)), Math.abs((position1.y - scale1.y / 2) - (position2.y + scale2.y / 2)));

		if (position1.x - position2.x <= 0)
			amountX = -amountX;
		if (position1.y - position2.y <= 0)
			amountY = -amountY;

		return new Vector2f(amountX, amountY);
	}

	public static float minX(GameObject object1, GameObject object2)
	{
		Vector2f position1 = object1.getPosition();
		Vector2f scale1 = object1.getScale().mul(2.0f);
		Vector2f position2 = object2.getPosition();
		Vector2f scale2 = object2.getScale().mul(2.0f);

		float amountX = Math.min(Math.abs((position1.x - scale1.x / 2) - (position2.x + scale2.x / 2)), Math.abs((position1.x + scale1.x / 2) - (position2.x - scale2.x / 2)));

		return position1.x - position2.x <= 0 ? -amountX : amountX;
	}

	public static float minY(GameObject object1, GameObject object2)
	{
		Vector2f position1 = object1.getPosition();
		Vector2f scale1 = object1.getScale().mul(2.0f);
		Vector2f position2 = object2.getPosition();
		Vector2f scale2 = object2.getScale().mul(2.0f);

		float amountY = Math.min(Math.abs((position1.y + scale1.y / 2f) - (position2.y - scale2.y / 2f)), Math.abs((position1.y - scale1.y / 2f) - (position2.y + scale2.y / 2f)));

		return position1.y - position2.y <= 0 ? -amountY : amountY;
	}
}
