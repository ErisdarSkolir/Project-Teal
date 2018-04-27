package edu.gcc.keen.item;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Texture;

public class Sugar extends Item
{
	private int animationTick;

	public Sugar(Vector3f position)
	{
		super(Texture.getTexture("tilesheet"), 18, 165, 66, position, new Vector2f(1.0f, 1.0f));
		this.pointValue = 5000;
	}

	@Override
	public void tick()
	{
		animationTick++;

		if (animationTick > 40)
		{
			if (index != 1042)
				index = 1042;
			else
				index = 1043;

			animationTick = 0;
		}
	}
}
