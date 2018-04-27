package edu.gcc.keen.item;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Texture;

public class Marshmallow extends Item
{
	private int animationTick;

	public Marshmallow(Vector3f position)
	{
		super(Texture.getTexture("tilesheet"), 18, 165, 62, position, new Vector2f(1.0f, 1.0f));
		this.pointValue = 200;
	}

	@Override
	public void tick()
	{
		animationTick++;

		if (animationTick > 40)
		{
			if (index != 1032)
				index = 1032;
			else
				index = 1033;

			animationTick = 0;
		}
	}
}
