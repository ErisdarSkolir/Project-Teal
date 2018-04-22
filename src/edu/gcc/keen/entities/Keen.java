package edu.gcc.keen.entities;

import java.util.List;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import edu.gcc.keen.animations.KeenAnimation;
import edu.gcc.keen.graphics.Texture;
import edu.gcc.keen.input.Input;
import edu.gcc.keen.tiles.Tile;
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

	private int animationIndex;
	private int tick = 10;
	private int jumpTick = 0;

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
			area.checkCollision(this);
			area.setShouldUpdate(true);
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
		float smallestY = Float.MIN_VALUE;

		for (GameObject gameObject : collidingObjects)
		{
			if (gameObject instanceof Tile)
			{
				Tile tile = (Tile) gameObject;

				if (tile.isCollidable())
				{
					if (smallestY < BoundingBox.minY(position, new Vector2f(2.0f, 6.0f), tile.getPosition(), tile.getScale()))
						smallestY = BoundingBox.minY(position, new Vector2f(2.0f, 6.0f), tile.getPosition(), tile.getScale());

					onGround = true;
					jumpTick = 0;

				}
			}
		}

		this.position.add(0.0f, smallestY);

		List<GameObject> xColliding = area.stillColliding(this);

		if (!xColliding.isEmpty())
		{
			float smallestX = Float.MIN_VALUE;

			for (GameObject gameObject : collidingObjects)
			{
				if (gameObject instanceof Tile)
				{
					Tile tile = (Tile) gameObject;

					if (tile.isCollidable())
					{
						if (smallestX < BoundingBox.minX(position, new Vector2f(2.0f, 6.0f), tile.getPosition(), tile.getScale()))
							smallestX = BoundingBox.minX(position, new Vector2f(2.0f, 6.0f), tile.getPosition(), tile.getScale());
					}
				}
			}

			this.position.add(smallestX, 0.0f);
		}
	}
}
