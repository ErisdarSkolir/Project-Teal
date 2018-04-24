package edu.gcc.keen.entities;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import edu.gcc.keen.animations.KeenAnimation;
import edu.gcc.keen.graphics.Texture;
import edu.gcc.keen.input.Input;
import edu.gcc.keen.item.Item;
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
	private boolean onPole = false;
	private boolean wallLeft = false;
	private boolean wallRight = false;

	private int animationIndex;
	private int tick = 10;
	private int jumpTick = 0;

	public Keen(Vector3f position)
	{
		super(Texture.getTexture("keen_spritesheet"), 11, 7, 0, position, new Vector2f(2.0f, 2.5f));

		this.aabbOffset = new Vector2f(-2.5f, -1.0f);

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

		if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT) && !onPole)
		{
			horizontalVelocity = -0.5f;
			setAnimation(KeenAnimation.WALK_LEFT);
		}
		else if (Input.isKeyDown(GLFW.GLFW_KEY_RIGHT) && !onPole)
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
			if (onGround || onPole)
			{
				jumping = true;
				onPole = false;
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

		if (!jumping && verticalVelocity > -1.0f && !onPole)
			verticalVelocity += -0.4f;
		else if (onPole && Input.isKeyDown(GLFW.GLFW_KEY_UP))
			verticalVelocity = 0.2f;
		else if (onPole && Input.isKeyDown(GLFW.GLFW_KEY_DOWN))
			verticalVelocity = -0.2f;
		else if (onPole)
			verticalVelocity = 0.0f;
	}

	@Override
	public void tick()
	{
		move();

		if (tick > 10)
		{
			setIndex(currentAnimation.getAnimation()[animationIndex]);
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
		for (GameObject object : collidingObjects)
		{
			if (object instanceof Tile)
			{
				Tile tile = (Tile) object;

				if (tile.isCollidable())
				{
					this.position.add(BoundingBox.minX(this, tile), 0.0f, 0.0f);
				}
			}
		}

		horizontalVelocity = 0.0f;
	}

	@Override
	public void onCollideY(List<GameObject> collidingObjects)
	{
		for (GameObject object : collidingObjects)
		{
			if (object instanceof Item)
			{
				// object.destroy();
			}
			else if (object instanceof Tile)
			{
				Tile tile = (Tile) object;

				if (!tile.isOneWay() && tile.isCollidable())
				{
					this.position.add(0.0f, BoundingBox.minY(this, tile), 0.0f);

					if (verticalVelocity < 0.0f)
					{
						verticalVelocity = 0.0f;
						jumpTick = 0;
						onGround = true;
					}
				}
				else if (tile.isPole() && (Input.isKeyDown(GLFW.GLFW_KEY_UP) || Input.isKeyDown(GLFW.GLFW_KEY_DOWN)))
				{
					onPole = true;
					this.position.x = tile.getPosition().x;
				}
				else if (!onPole && tile.isOneWay() && position.y > tile.getPosition().y && verticalVelocity < 0f)
				{
					this.position.add(0.0f, BoundingBox.minY(this, tile), 0.0f);

					onPole = false;

					if (verticalVelocity < 0.0f)
					{
						verticalVelocity = 0.0f;
						jumpTick = 0;
						onGround = true;
					}
				}
			}
		}
	}
}
