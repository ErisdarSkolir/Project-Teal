package edu.gcc.keen.entities;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import edu.gcc.keen.animations.KeenAnimation;
import edu.gcc.keen.graphics.Texture;
import edu.gcc.keen.input.Input;
import edu.gcc.keen.util.GameObject;

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

	private int animationIndex;
	private int tick = 10;
	private int jumpTick;

	private float verticalVelocity;
	private float horizontalVelocity;

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

		if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE) && position.y <= 0)
		{
			jumping = true;
			verticalVelocity = 0.5f;
			jumpTick = 0;
		}
		else if (jumping && jumpTick > 5)
		{
			jumping = false;
		}

		if (position.y > 0 && verticalVelocity > -1.5f && !jumping)
		{
			verticalVelocity -= 1.5f;
		}
		else if (position.y < 0)
		{
			this.position.y = 0;
			verticalVelocity = 0;
		}

		this.position.add(horizontalVelocity, verticalVelocity);

		if (jumping)
			jumpTick++;
	}

	@Override
	public void tick()
	{
		move();

		if (tick > 10)
		{
			getTexture().setTextureIndex(KeenAnimation.WALK_LEFT.getAnimation()[animationIndex]);
			animationIndex++;
			tick = 0;

			if (animationIndex >= KeenAnimation.WALK_LEFT.getAnimation().length)
				animationIndex = 0;
		}

		tick++;
	}

	@Override
	public void onCollide(GameObject gameObject)
	{

	}
}
