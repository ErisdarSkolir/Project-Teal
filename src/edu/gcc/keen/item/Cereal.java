package edu.gcc.keen.item;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Texture;

public class Cereal extends Item
{
	private int animationTick;

	public Cereal(Vector3f position)
	{
		super(Texture.getTexture("tilesheet"), 18, 165, 65, position, new Vector2f(1.0f, 1.0f));
		this.pointValue = 2000;
	}

	@Override
	public void tick()
	{
		animationTick++;

		if (animationTick > 40)
		{
			if (index != 1040)
				index = 1040;
			else
				index = 1041;

			animationTick = 0;
		}
	}
}
