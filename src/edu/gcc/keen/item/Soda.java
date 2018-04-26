package edu.gcc.keen.item;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Texture;

public class Soda extends Item
{
	public int animationTick;

	public Soda(Vector3f position)
	{
		super(Texture.getTexture("tilesheet"), 18, 165, 63, position, new Vector2f(1.0f, 1.0f));
		this.pointValue = 500;
	}

	@Override
	public void tick()
	{
		animationTick++;

		if (animationTick > 40)
		{
			if (index != 1034)
				index = 1034;
			else
				index = 1035;

			animationTick = 0;
		}
	}
}
