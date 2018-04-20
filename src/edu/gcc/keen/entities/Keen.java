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
	private KeenAnimation currentAnimation = KeenAnimation.STATIONARY_LEFT;

	private boolean jumping = false;
	private boolean hanging = false;
	private boolean onGround = false;

	private int animationIndex;
	private int tick = 10;

	private float verticalVelocity;
	private float horizontalVelocity;

	public Keen(Vector2f position)
	{
		super(new Texture("keen_spritesheet", 11, 7, 0), position, new Vector2f(2.0f, 2.5f));
	}

	@Override
	public void move()
	{
		if (horizontalVelocity != 0.0f || verticalVelocity != 0.0f)
		{
			position.add(horizontalVelocity, verticalVelocity);
			area.setShouldUpdate(true);

			horizontalVelocity = 0.0f;
			verticalVelocity = 0.0f;
		}

		if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT))
		{
			horizontalVelocity = -0.5f;
			setAnimation(KeenAnimation.WALK_LEFT);
		}
		else if (Input.isKeyDown(GLFW.GLFW_KEY_RIGHT))
		{
			horizontalVelocity = 0.5f;
			setAnimation(KeenAnimation.WALK_RIGHT);
		}
		else
			setAnimation(KeenAnimation.STATIONARY_RIGHT);
	}

	@Override
	public void tick()
	{
		move();

		if (tick > 10)
		{
			texture.setTextureIndex(currentAnimation.getAnimation()[animationIndex]);
			animationIndex++;
			tick = 0;

			if (animationIndex >= currentAnimation.getLenth())
				animationIndex = 0;
		}

		tick++;
	}

	public void setAnimation(KeenAnimation animation)
	{
		if (currentAnimation != animation)
		{
			currentAnimation = animation;
			animationIndex = 0;
			tick = 10;
		}
	}

	@Override
	public void onCollide(GameObject gameObject)
	{

	}
}
