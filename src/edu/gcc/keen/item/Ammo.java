package edu.gcc.keen.item;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Texture;

public class Ammo extends Item
{
	private int animationTick;

	public Ammo(Vector3f position)
	{
		super(Texture.getTexture("tilesheet"), 18, 165, 68, position, new Vector2f(1.0f, 1.0f));
	}

	@Override
	public void tick()
	{
		animationTick++;

		if (animationTick > 40)
		{
			if (index != 1114)
				index = 1114;
			else
				index = 1115;

			animationTick = 0;
		}
	}
}
