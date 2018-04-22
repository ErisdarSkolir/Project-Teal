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

		System.out.println(scale1);

		// System.out.println(position1 + " " + position2 + " " + (left1Y - scale1.y) +
		// " " + left2Y);

		return !(left1X + scale1.x < left2X || left1X > left2X + scale2.x || top1Y - scale1.y > top2Y || top1Y < top2Y - scale2.y);
	}

	public static boolean doIntersect(Vector2f position1, Vector2f scale1, Vector2f position2, Vector2f scale2)
	{
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

		// System.out.println(amountX + " " + amountY);

		return new Vector2f(amountX, amountY);
	}

	public static float minX(Vector2f position1, Vector2f scale1, Vector2f position2, Vector2f scale2)
	{
		float amountX = Math.min(Math.abs((position1.x - scale1.x / 2) - (position2.x + scale2.x / 2)), Math.abs((position1.x + scale1.x / 2) - (position2.x - scale2.x / 2)));

		if (BoundingBox.doIntersect(position1, scale1, position2, scale2))
			return position1.x - position2.x <= 0 ? -amountX : amountX;
		else
			return 0.0f;
	}

	public static float minY(Vector2f position1, Vector2f scale1, Vector2f position2, Vector2f scale2)
	{
		float amountY = Math.min(Math.abs((position1.y + scale1.y / 2f) - (position2.y - scale2.y / 2f)), Math.abs((position1.y - scale1.y / 2f) - (position2.y + scale2.y / 2f)));

		return position1.y - position2.y <= 0 ? -amountY : amountY;
	}
}
