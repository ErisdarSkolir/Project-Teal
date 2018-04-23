package edu.gcc.keen.entities;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import edu.gcc.keen.animations.KeenAnimation;
import edu.gcc.keen.graphics.Texture;
import edu.gcc.keen.input.Input;
import edu.gcc.keen.util.BoundingBox;
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
	private boolean wallLeft = false;
	private boolean wallRight = false;

	private int animationIndex;
	private int tick = 10;
	private int jumpTick = 0;

	private float verticalVelocity;
	private float horizontalVelocity;

	public Keen(Vector3f position)
	{
		super(new Texture("keen_spritesheet", 11, 7, 0), position, new Vector2f(2.0f, 2.5f));

		this.aabbOffset = new Vector2f(-2.5f, 0.0f);

	}

	@Override
	public void move()
	{
		if (horizontalVelocity != 0.0f || verticalVelocity != 0.0f)
		{
			position.add(0.0f, verticalVelocity, 0.0f);
			area.checkCollisionY(this);

			position.add(horizontalVelocity, 0.0f, 0.0f);
			area.checkCollisionX(this);

			area.setShouldUpdate(true);
		}

		if (Input.isKeyDown(GLFW.GLFW_KEY_R))
			position = new Vector3f(0.0f, 6f, 0.0f);

		if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT) && !wallLeft)
		{
			wallRight = false;

			horizontalVelocity = -0.5f;
			setAnimation(KeenAnimation.WALK_LEFT);
		}
		else if (Input.isKeyDown(GLFW.GLFW_KEY_RIGHT) && !wallRight)
		{
			wallLeft = false;

			horizontalVelocity = 0.5f;
			setAnimation(KeenAnimation.WALK_RIGHT);
		}
		else
		{
			horizontalVelocity = 0.0f;
			setAnimation(KeenAnimation.STATIONARY_RIGHT);
		}

		if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL))
		{
			if (onGround)
			{
				jumping = true;
				onGround = false;
				verticalVelocity = 1.0f;
			}
			else if (jumping && verticalVelocity < 1.0f)
			{
				verticalVelocity += 0.2f;
			}

			if (jumpTick > 10)
				jumping = false;

			jumpTick++;
		}
		else
			jumping = false;

		if (!jumping && verticalVelocity > -1.0f)
			verticalVelocity += -0.4f;
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
	public void onCollideX(List<GameObject> collidingObjects)
	{
		if (collidingObjects.isEmpty())
			return;

		float smallest = BoundingBox.minX(this, collidingObjects.get(0));

		for (GameObject object : collidingObjects)
		{
			float tmp = BoundingBox.minX(this, object);

			if (tmp < smallest)
				smallest = tmp;
		}

		this.position.add(smallest, 0.0f, 0.0f);

		horizontalVelocity = 0.0f;
	}

	@Override
	public void onCollideY(List<GameObject> collidingObjects)
	{
		if (collidingObjects.isEmpty())
			return;

		float smallest = BoundingBox.minY(this, collidingObjects.get(0));

		for (GameObject object : collidingObjects)
		{
			float tmp = BoundingBox.minY(this, object);

			if (tmp < smallest)
				smallest = tmp;
		}

		this.position.add(0.0f, smallest, 0.0f);

		verticalVelocity = 0.0f;
		jumpTick = 0;
		onGround = true;
	}
}
