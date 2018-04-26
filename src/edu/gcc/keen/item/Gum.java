package edu.gcc.keen.item;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Texture;

public class Gum extends Item
{
	public int animationTick;

	public Gum(Vector3f position)
	{
		super(Texture.getTexture("tilesheet"), 18, 165, 61, position, new Vector2f(1.0f, 1.0f));
		this.pointValue = 100;
	}

	@Override
	public void tick()
	{
		animationTick++;

		if (animationTick > 50)
		{
			if (index != 1030)
				index = 1030;
			else
				index = 1031;

			animationTick = 0;
		}
	}
}
