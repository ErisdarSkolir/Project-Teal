package edu.gcc.keen.entities;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import edu.gcc.keen.animations.KeenAnimation;
import edu.gcc.keen.gameobjects.GameObject;
import edu.gcc.keen.gameobjects.Item;
import edu.gcc.keen.gameobjects.ObjectType;
import edu.gcc.keen.gameobjects.Tile;
import edu.gcc.keen.gamestates.Level;
import edu.gcc.keen.graphics.Textures;
import edu.gcc.keen.input.Input;
import edu.gcc.keen.interactable.KeyStoneHolder;
import edu.gcc.keen.util.Area;
import edu.gcc.keen.util.BoundingBox;
import edu.gcc.keen.util.VectorPool;

/**
 * This class represents the character that the player controls
 * 
 * @author DONMOYERLR17
 *
 */
public class Keen extends Entity
{
	private KeenAnimation currentAnimation = KeenAnimation.STATIONARY_LEFT;

	private static float LEFT_SPEED = -0.4f;
	private static float RIGHT_SPEED = 0.4f;
	private static float INITIAL_JUMP_SPEED = 0.7f;
	private static float JUMP_VELOCITY = 0.09f;
	private static float GRAVITY = -0.15f;

	private boolean jumping = false;
	private boolean hanging = false;
	private boolean onGround = false;
	private boolean onPole = false;
	private boolean[] keystones = new boolean[4];
	private boolean direction = true;

	private int animationIndex;
	private int tick = 10;
	private int jumpTick = 0;
	private int shootCooldown = 10;

	private int ammo;
	private int score;
	private int vitalin;

	public Keen(Vector3f position)
	{
		super(Textures.getTexture("keen_spritesheet"), 11, 7, 0, position, new Vector2f(2.0f, 2.5f));

		this.setAabbOffset(new Vector2f(-2.5f, -1.0f));
		this.objectType = ObjectType.KEEN;
	}

	@Override
	public void move()
	{
		if (horizontalVelocity != 0.0f || verticalVelocity != 0.0f)
		{
			position.add(0.0f, verticalVelocity, 0.0f);
			for (Area area : areas)
			{
				area.checkCollisionY(this);
			}

			position.add(horizontalVelocity, 0.0f, 0.0f);
			for (Area area : areas)
			{
				area.checkCollisionX(this);
			}
		}

		if (Input.isKeyDown(GLFW.GLFW_KEY_R))
			position = new Vector3f(0.0f, 6f, 0.0f);

		if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT))
		{
			direction = true;

			if (!onPole)
			{
				horizontalVelocity = LEFT_SPEED;
				tryShoot(0);
				setAnimation(KeenAnimation.WALK_LEFT);
			}
			else
			{
				setAnimation(KeenAnimation.STATIONARY_POLE_LEFT);
			}
		}
		else if (Input.isKeyDown(GLFW.GLFW_KEY_RIGHT))
		{
			direction = false;

			if (!onPole)
			{
				horizontalVelocity = RIGHT_SPEED;
				tryShoot(2);
				setAnimation(KeenAnimation.WALK_RIGHT);
			}
			else
			{
				setAnimation(KeenAnimation.STATIONARY_POLE_RIGHT);
			}
		}
		else if (Input.isKeyDown(GLFW.GLFW_KEY_DOWN))
		{
			if (!onPole && onGround)
			{
				setAnimation(KeenAnimation.LOOK_DOWN);
			}
			else
			{
				verticalVelocity = 6.0f;
				setAnimation(KeenAnimation.SLIDE_POLE);
			}
		}
		else
		{
			horizontalVelocity = 0.0f;

			if (!onPole)
				setAnimation(direction ? KeenAnimation.STATIONARY_LEFT : KeenAnimation.STATIONARY_RIGHT);
			else
				setAnimation(direction ? KeenAnimation.STATIONARY_POLE_LEFT : KeenAnimation.STATIONARY_POLE_RIGHT);

		}

		if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL))
		{
			if (onGround || onPole)
			{
				jumping = true;
				onPole = false;
				onGround = false;
				verticalVelocity = INITIAL_JUMP_SPEED;
			}
			else if (jumping && verticalVelocity < 0.9f)
			{
				verticalVelocity += JUMP_VELOCITY;
			}

			if (jumpTick > 8)
				jumping = false;

			jumpTick++;
		}
		else
			jumping = false;

		if (!jumping && verticalVelocity > -1.0f && !onPole)
		{
			verticalVelocity += GRAVITY;
			onGround = false;
		}
		else if (onPole && Input.isKeyDown(GLFW.GLFW_KEY_UP))
			verticalVelocity = 0.2f;
		else if (onPole && Input.isKeyDown(GLFW.GLFW_KEY_DOWN))
			verticalVelocity = -0.2f;
		else if (onPole)
			verticalVelocity = 0.0f;
	}

	public void tryShoot(int direction)
	{
		if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE) && shootCooldown <= 0 && ammo > 0)
		{
			Level.addObject(new Bullet(direction, new Vector3f(position)));

			if (direction == 0 && horizontalVelocity == 0.0f && verticalVelocity == 0.0f)
				setAnimation(KeenAnimation.SHOOT_LEFT);

			shootCooldown = 10;
			ammo--;
		}
	}

	@Override
	public void tick()
	{
		move();

		if (tick > 9)
		{
			if (animationIndex >= currentAnimation.getLenth())
				animationIndex = 0;

			setIndex(currentAnimation.getAnimation()[animationIndex++]);
			tick = 0;
		}

		tick++;

		if (shootCooldown > 0)
			shootCooldown--;
	}

	public void setAnimation(KeenAnimation animation)
	{
		if (currentAnimation != animation)
		{
			currentAnimation = animation;
			animationIndex = 0;
			tick = 0;

			setIndex(currentAnimation.getAnimation()[animationIndex++]);
		}
	}

	@Override
	public void onCollideX(List<GameObject> collidingObjects)
	{
		for (GameObject object : collidingObjects)
		{
			if (object.isCollidable())
			{
				this.position.add(BoundingBox.minX(this, object), 0.0f, 0.0f);
			}
		}

		horizontalVelocity = 0.0f;
	}

	@Override
	public void onCollideY(List<GameObject> collidingObjects)
	{
		for (GameObject object : collidingObjects)
		{
			Vector3f tmpPosition = object.getPosition();

			if (object.isCollidable())
			{
				position.add(0.0f, BoundingBox.minY(this, object), 0.0f);
			}

			if (object.getType() == ObjectType.ITEM)
			{
				object.destroy();

				Item item = (Item) object;

				if (item.givesScore)
					score += item.pointValue;
				else if (item.givesAmmo)
					ammo += 5;
				else if (item.isVitalin)
					vitalin += 1;
				else if (item.isKeyStone)
					keystones[item.keyStoneColor] = true;
			}
			else if (object.getType() == ObjectType.TILE)
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
					else
						jumping = false;
				}
				else if (tile.isPole() && (Input.isKeyDown(GLFW.GLFW_KEY_UP) || Input.isKeyDown(GLFW.GLFW_KEY_DOWN)))
				{
					onPole = true;
					this.position.x = tmpPosition.x;
				}
				else if (!onPole && tile.isOneWay() && position.y > tmpPosition.y && verticalVelocity < 0f)
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
			else if (object.getType() == ObjectType.KEYSTONE_HOLDER)
			{
				KeyStoneHolder holder = (KeyStoneHolder) object;

				if (keystones[holder.getColor()])
				{
					holder.toggle();
					keystones[holder.getColor()] = false;
				}
			}

			VectorPool.recycle(tmpPosition);
		}
	}
}
