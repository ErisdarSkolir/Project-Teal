package edu.gcc.keen.item;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Texture;

public class PixieStick extends Item
{
	private int animationTick;

	public PixieStick(Vector3f position)
	{
		super(Texture.getTexture("tilesheet"), 18, 165, 1036, position, new Vector2f(1.0f, 1.0f));
		this.pointValue = 1000;
	}

	@Override
	public void tick()
	{
		animationTick++;

		if (animationTick > 40)
		{
			if (index != 1036)
				index = 1036;
			else
				index = 1037;

			animationTick = 0;
		}
	}
}
