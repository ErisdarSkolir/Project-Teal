package edu.gcc.keen.entities;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
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

	public Keen(Vector2f position)
	{
		super(new Texture("keen_spritesheet", 11, 7, 0), position, new Vector2f(2.0f, 2.5f));

		this.aabbOffset = new Vector2f(-2.0f, 0.0f);
	}

	@Override
	public void move()
	{
		if (horizontalVelocity != 0.0f || verticalVelocity != 0.0f)
		{
			position.add(horizontalVelocity, verticalVelocity);
			area.checkCollision(this);
			area.setShouldUpdate(true);
		}

		if (Input.isKeyDown(GLFW.GLFW_KEY_R))
			position = new Vector2f(0.0f, 6f);

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
			else if (jumping)
			{
				verticalVelocity = 1.0f;
			}

			if (jumpTick > 5)
				jumping = false;

			jumpTick++;
		}
		else
			jumping = false;

		if (!onGround && !jumping && verticalVelocity > -1.0f)
			verticalVelocity += -0.2f;
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
	public void onCollide(List<GameObject> collidingObjects)
	{
		float sumY = 0f;
		float sumX = 0f;

		List<Float> correctionsY = new ArrayList<>();
		List<Float> correctionsX = new ArrayList<>();

		for (GameObject gameObject : collidingObjects)
		{
			if (gameObject.canCollide())
			{
				float correctionY = BoundingBox.minY(this, gameObject);
				float correctionX = BoundingBox.minX(this, gameObject);

				correctionsY.add(correctionY);
				correctionsX.add(correctionX);

				if (correctionY < 0)
					sumY += -1;
				else if (correctionY > 0)
					sumY += 1;

				if (correctionX < 0)
					sumX += -1;
				else if (correctionX > 0)
					sumX += 1;
			}
		}

		float smallestY = getSmallest(correctionsY);
		float smallestX = getSmallest(correctionsX);

		// System.out.println(sumX + " " + sumY);

		if (Math.abs(sumY) > Math.abs(sumX))
		{
			this.position.add(0.0f, smallestY);

			if (area.stillColliding(this, collidingObjects))
				this.position.add(smallestX, 0.0f);

			verticalVelocity = 0.0f;

			onGround = true;
			jumpTick = 0;
		}
		else if (Math.abs(sumX) > Math.abs(sumY))
		{
			this.position.add(smallestX, 0.0f);

			if (smallestX > 0)
				wallLeft = true;
			else if (smallestX < 0)
				wallRight = true;

			if (area.stillColliding(this, collidingObjects))
				this.position.add(0.0f, smallestY);

			horizontalVelocity = 0.0f;
		}
	}

	public float getSmallest(List<Float> numbers)
	{
		if (numbers.isEmpty())
			return 0f;

		float smallest = numbers.get(0);

		for (Float number : numbers)
		{
			if (number < smallest)
				smallest = number;
		}

		return smallest;
	}
}
