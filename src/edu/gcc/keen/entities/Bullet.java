package edu.gcc.keen.entities;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.animations.EntityAnimations;
import edu.gcc.keen.gameobjects.GameObject;
import edu.gcc.keen.graphics.Textures;
import edu.gcc.keen.util.Area;

/**
 * Represents a bullet fired by keen's blaster
 * 
 * @author DONMOYERLR17
 *
 */
public class Bullet extends Entity
{
	private EntityAnimations currentAnimation = EntityAnimations.BULLET;

	private int animationIndex;
	private int tick;
	private int direction;

	public Bullet(int direction, Vector3f position)
	{
		super(Textures.getTexture("items_and_particle_spritesheet"), 16, 10, 0, position, new Vector2f(1.0f, 1.0f));
		this.direction = direction;

		this.horizontalVelocity = 0.6f;
		this.verticalVelocity = 0.6f;
	}

	@Override
	public void move()
	{
		if (direction == 1)
			position.add(0.0f, verticalVelocity, 0.0f);
		else if (direction == 3)
			position.add(0.0f, -verticalVelocity, 0.0f);
		else if (direction == 0)
			position.add(-horizontalVelocity, 0.0f, 0.0f);
		else if (direction == 2)
			position.add(horizontalVelocity, 0.0f, 0.0f);

		if (direction == 1 || direction == 3)
		{
			for (Area area : areas)
			{
				area.checkCollisionY(this);
			}
		}
		else
		{
			for (Area area : areas)
			{
				area.checkCollisionX(this);
			}
		}
	}

	@Override
	public void tick()
	{
		move();

		if (tick > 4)
		{
			if (currentAnimation == EntityAnimations.BULLET_SPLASH)
			{
				destroy();
				return;
			}

			setIndex(currentAnimation.getAnimation()[animationIndex++]);
			tick = 0;

			if (animationIndex >= currentAnimation.getLenth())
				animationIndex = 0;
		}

		tick++;
	}

	public void setAnimation(EntityAnimations animation)
	{
		if (currentAnimation != animation)
		{
			currentAnimation = animation;
			animationIndex = 0;
			tick = 0;

			setIndex(currentAnimation.getAnimation()[animationIndex]);
		}
	}

	@Override
	public void onCollideX(List<GameObject> collidingObjects)
	{
		for (GameObject object : collidingObjects)
		{
			if (object.isCollidable())
			{
				setAnimation(EntityAnimations.BULLET_SPLASH);

				horizontalVelocity = 0.0f;
			}
		}
	}

	@Override
	public void onCollideY(List<GameObject> collidingObjects)
	{
		for (GameObject object : collidingObjects)
		{
			if (object.isCollidable())
			{
				setAnimation(EntityAnimations.BULLET_SPLASH);

				verticalVelocity = 0.0f;
			}
		}
	}
}
