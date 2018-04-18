package edu.gcc.keen.util;

import org.joml.Vector2f;

public class BoundingBox
{
	public static boolean isIntersecting(Vector2f position1, Vector2f scale1, Vector2f position2, Vector2f scale2)
	{
		float left1X = position1.x - (scale1.x / 2);
		float left1Y = position1.y - (scale1.y / 2);
		float left2X = position2.x - (scale2.x / 2);
		float left2Y = position2.y - (scale2.y / 2);

		return !(left1X + scale1.x < left2X || left1X > left2X + scale2.x || left1Y - scale1.y > left2Y || left1Y < left2Y - scale2.y);
	}

	public Vector2f getIntersectingAmount(BoundingBox boundingBox)
	{
		return null;
	}
}
