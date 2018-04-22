package edu.gcc.keen.util;

import org.joml.Vector2f;

public class BoundingBox
{
	private BoundingBox()
	{
		throw new IllegalStateException("Utility class");
	}

	public static boolean isIntersecting(Vector2f position1, Vector2f scale1, Vector2f position2, Vector2f scale2)
	{
		float left1X = position1.x - (scale1.x / 2f);
		float top1Y = position1.y + (scale1.y / 2f);
		float left2X = position2.x - (scale2.x / 2f);
		float top2Y = position2.y + (scale2.y / 2f);

		// System.out.println(position1 + " " + position2 + " " + (left1Y - scale1.y) +
		// " " + left2Y);

		return !(left1X + scale1.x < left2X || left1X > left2X + scale2.x || top1Y - scale1.y > top2Y || top1Y < top2Y - scale2.y);
	}

	public static boolean doIntersect(GameObject object1, GameObject object2)
	{
		return (Math.abs(object1.getPosition().x - object2.getPosition().x) * 2 < (object1.getScale().mul(2.0f).x + object2.getScale().mul(2.0f).x)
				&& Math.abs(object1.getPosition().y - object2.getPosition().y) * 2 < (object1.getScale().mul(2.0f).y + object2.getScale().mul(2.0f).y));
	}

	public static Vector2f getMinimumTranslationiVector(Vector2f position1, Vector2f scale1, Vector2f position2, Vector2f scale2)
	{
		float amountX = Math.min(Math.abs((position1.x - scale1.x / 2) - (position2.x + scale2.x / 2)), Math.abs((position1.x + scale1.x / 2) - (position2.x - scale2.x / 2)));
		float amountY = Math.min(Math.abs((position1.y + scale1.y / 2) - (position2.y - scale2.y / 2)), Math.abs((position1.y - scale1.y / 2) - (position2.y + scale2.y / 2)));

		if (position1.x - position2.x <= 0)
			amountX = -amountX;
		if (position1.y - position2.y <= 0)
			amountY = -amountY;

		// System.out.println(amountX + " " + amountY);

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
