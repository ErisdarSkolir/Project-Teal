package edu.gcc.keen.entities;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import edu.gcc.keen.animations.Animateable;
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

/**
 * This class represents the character that the player controls
 * 
 * @author DONMOYERLR17
 *
 */
public class Keen extends Entity implements Animateable
{
	private static final float LEFT_SPEED = -0.4f;
	private static final float RIGHT_SPEED = 0.4f;
	private static final float INITIAL_JUMP_SPEED = 0.7f;
	private static final float INITIAL_POGO_SPEED = 0.7f;
	private static final float JUMP_VELOCITY = 0.09f;
	private static final float POGO_VELOCITY = 0.08f;
	private static final float GRAVITY = -0.1f;

	private boolean jumping = false;
	private boolean jumpingToo = false;
	private boolean hanging = false;
	private boolean onGround = false;
	private boolean onPole = false;
	private boolean onPogo = false;
	private boolean pogoUp = false;
	private boolean[] keystones = new boolean[4];
	private boolean direction = true;


	private int jumpTick = 0;
	private int pogoTick = 0;
	private int shootCooldown = 10;

	private int ammo;
	private int score;
	private int vitalin;

	public Keen(Vector3f position)
	{

		super(Textures.getTexture("keen_spritesheet"), 11, 7, 0, position, new Vector2f(2.0f, 2.5f));

		this.setAabbOffset(new Vector2f(-2.5f, -1.0f));
		this.objectType = ObjectType.KEEN;
		this.currentAnimation = KeenAnimation.STATIONARY_LEFT;
	}

	@Override
	public void move()
	{
		if (horizontalVelocity != 0.0f || verticalVelocity != 0.0f)
		{
			position.add(0.0f, verticalVelocity, 0.0f);
			for (Area area : areas)
			{
				area.checkCollision(false, this);
			}

			position.add(horizontalVelocity, 0.0f, 0.0f);
			for (Area area : areas)
			{
				area.checkCollision(true, this);
			}
		}

		if (Input.isKeyDown(GLFW.GLFW_KEY_R))
			position = new Vector3f(0.0f, 6f, 0.0f);

		if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT))
		{
			direction = true;

			if (!onPole && !onPogo)
			{
				if(onGround)
					setAnimation(KeenAnimation.WALK_LEFT, this);
				horizontalVelocity = LEFT_SPEED;
				tryShoot(0);

			}
			else if (onPogo)
			{
				horizontalVelocity = LEFT_SPEED + .1f;
				tryShoot(0);
				setAnimation(KeenAnimation.POGO_LEFT, this);
			}
		}
		else if (Input.isKeyDown(GLFW.GLFW_KEY_RIGHT))
		{
			direction = false;

			if (!onPole && !onPogo)
			{
				horizontalVelocity = RIGHT_SPEED;
				tryShoot(2);
				setAnimation(KeenAnimation.WALK_RIGHT, this);
			}
			else if (onPogo)
			{
				horizontalVelocity = RIGHT_SPEED - .1f;
				tryShoot(0);
				setAnimation(KeenAnimation.POGO_RIGHT, this);
			}
		}
		else if (Input.isKeyDown(GLFW.GLFW_KEY_DOWN))
		{
			if (!onPole && onGround)
			{
				setAnimation(KeenAnimation.LOOK_DOWN, this);
				tryShoot(3);
			}
			else if (onPole)
			{
				setAnimation(KeenAnimation.SLIDE_POLE, this);
				tryShoot(3);
			}
		}
		else if (Input.isKeyDown(GLFW.GLFW_KEY_UP))
		{
			if  (!onPole && onGround)
			{
				setAnimation(KeenAnimation.LOOK_UP, this);
				tryShoot(1);
			}
			else if (onPole)
			{
				setAnimation(direction ? KeenAnimation.CLIMB_POLE_LEFT : KeenAnimation.CLIMB_POLE_RIGHT, this);
				tryShoot(1);
			}
		}
		else if (!jumping && onGround)
		{
			horizontalVelocity = 0.0f;

			if (!onPole)
				setAnimation(direction ? KeenAnimation.STATIONARY_LEFT : KeenAnimation.STATIONARY_RIGHT, this);
			else
				setAnimation(direction ? KeenAnimation.STATIONARY_POLE_LEFT : KeenAnimation.STATIONARY_POLE_RIGHT, this);
		}

		if (Input.isKeyDown(GLFW.GLFW_KEY_LEFT_CONTROL))
		{
			if ((onGround || onPole) && !onPogo)
			{
				jumping = true;
				onPole = false;
				onGround = false;
				verticalVelocity = INITIAL_JUMP_SPEED;
			}
			else if (jumping && verticalVelocity < 0.9f && !onPogo)
			{
				verticalVelocity += JUMP_VELOCITY;
			}
			else if (onPogo)
			{
				jumpingToo = true;
			}

			if (jumpTick > 6)
				jumping = false;

			if (direction)
				setAnimation(KeenAnimation.JUMP_LEFT, this);
			else
				setAnimation(KeenAnimation.JUMP_RIGHT, this);

			jumpTick++;
		}
		else
		{
			jumping = false;
			jumpingToo = false;
		}

		if (Input.isKeyDownOnce(GLFW.GLFW_KEY_LEFT_ALT)) 
		{
			onPogo = !onPogo;

			if(!onPogo)
				pogoUp = false;
		}

		if (onPogo)
		{
			if (onGround || onPole)
			{
				pogoUp = true;
				onPole = false;
				onGround = false;
				verticalVelocity = INITIAL_POGO_SPEED;
			}
			else if (pogoUp && verticalVelocity < 1.0f)
			{
				verticalVelocity += POGO_VELOCITY;
			}

			if (jumpingToo) {
				if (pogoTick > 15)
					pogoUp = false;
			}
			else {
				if (pogoTick > 5)
					pogoUp = false;
			}

			if (direction)
				setAnimation(KeenAnimation.POGO_LEFT, this);
			else
				setAnimation(KeenAnimation.POGO_RIGHT, this);

			pogoTick++;
		}

		if (!jumping && !pogoUp && !onPole && verticalVelocity > -1.0f)
		{
			verticalVelocity += GRAVITY;
			onGround = false;
		}
		else if (onPole && Input.isKeyDown(GLFW.GLFW_KEY_UP))
			verticalVelocity = 0.2f;
		else if (onPole && Input.isKeyDown(GLFW.GLFW_KEY_DOWN))
			verticalVelocity = -0.3f;
		else if (onPole)
			verticalVelocity = 0.0f;
		
		if (direction)
			tryShoot(0);
		if (!direction)
			tryShoot(2);

	}

	public void tryShoot(int direction)
	{
		if (Input.isKeyDown(GLFW.GLFW_KEY_SPACE) && shootCooldown <= 0 && ammo > 0)
		{

			Level.addObject(new Bullet(direction, new Vector3f(position)));

			if (direction == 0 && horizontalVelocity == 0.0f && verticalVelocity == 0.0f)
				setAnimation(KeenAnimation.SHOOT_LEFT, this);

			shootCooldown = 10;
			ammo--;
		}
	}

	@Override
	public void tick()
	{
		move();

		if (animationTick > (onGround ? 15 : 9))
			nextAnimationFrame(this);

		animationTick++;

		if (shootCooldown > 0)
			shootCooldown--;
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
						pogoTick = 0;
						onGround = true;
					}
					else
					{
						jumping = false;
						pogoUp = false;
					}
				}
				else if (tile.isPole() && (Input.isKeyDown(GLFW.GLFW_KEY_UP) || Input.isKeyDown(GLFW.GLFW_KEY_DOWN)))
				{
					onPole = true;
					this.position.x = object.getPosition().x;
				}
				else if (!onPole && tile.isOneWay() && position.y > object.getPosition().y && verticalVelocity < 0f)
				{
					this.position.add(0.0f, BoundingBox.minY(this, tile), 0.0f);

					onPole = false;

					if (verticalVelocity < 0.0f)
					{
						verticalVelocity = 0.0f;
						jumpTick = 0;
						pogoTick = 0;
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
		}
	}
}
