package edu.gcc.keen.entities;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import edu.gcc.keen.graphics.Texture;
import edu.gcc.keen.input.Input;

/**
 * This class represents the character that the player controls
 * 
 * @author DONMOYERLR17
 *
 */
public class Keen extends Entity
{
	private boolean jumping = false;
	private boolean hanging = false;

	private int[] animation = { 2, 3, 4, 5 };
	private int animationIndex = 0;
	private int tick = 0;

	public Keen(Vector2f position)
	{
		super(new Texture("keen_spritesheet", new Vector2f(11.0f, 7.0f), 0), position);
		scale = new Vector2f(1.0f, 1.25f);
	}

	@Override
	public void move()
	{
		if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT))
			position.add(-0.5f, 0.0f);
		else if (Input.isKeyDown(GLFW.GLFW_KEY_RIGHT))
			position.add(0.5f, 0.0f);

		if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE))
		{

		}

	}

	@Override
	public void tick()
	{
		move();

		if (tick > 1)
		{
			getTexture().setTextureIndex(animation[animationIndex]);
			animationIndex++;
			tick = 0;

			if (animationIndex >= animation.length)
				animationIndex = 0;
		}

		tick++;
	}
}
