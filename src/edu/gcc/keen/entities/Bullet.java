package edu.gcc.keen.entities;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.animations.Animateable;
import edu.gcc.keen.animations.EntityAnimations;
import edu.gcc.keen.gameobjects.GameObject;
import edu.gcc.keen.gameobjects.ObjectType;
import edu.gcc.keen.graphics.Textures;
import edu.gcc.keen.util.Area;

/**
 * Represents a bullet fired by keen's blaster
 */
public class Bullet extends Entity implements Animateable
{
	private int direction;

	public Bullet(int direction, Vector3f position)
	{
		super(Textures.getTexture("items_and_particle_spritesheet"), 16, 10, 0, position, new Vector2f(1.0f, 1.0f));
		this.direction = direction;

		this.horizontalVelocity = 0.6f;
		this.verticalVelocity = 0.6f;
		this.currentAnimation = EntityAnimations.BULLET;
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
				area.checkCollision(false, this);
			}
		}
		else
		{
			for (Area area : areas)
			{
				area.checkCollision(true, this);
			}
		}
	}

	@Override
	public void tick()
	{
		move();

		if (animationTick > 4)
		{
			if (currentAnimation == EntityAnimations.BULLET_SPLASH)
			{
				destroy();
				return;
			}

			nextAnimationFrame(this);
		}

		animationTick++;
	}

	@Override
	public void onCollideX(List<GameObject> collidingObjects)
	{
		for (GameObject object : collidingObjects)
		{
			if (object.isCollidable())
			{
				setAnimation(EntityAnimations.BULLET_SPLASH, this);

				horizontalVelocity = 0.0f;
			}

			if (object.getType() == ObjectType.ENTITY && ((Entity) object).canBeStunned)
			{
				((Entity) object).stunned = true;
				setAnimation(EntityAnimations.BULLET_SPLASH, this);
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
				setAnimation(EntityAnimations.BULLET_SPLASH, this);

				verticalVelocity = 0.0f;
			}

			if (object.getType() == ObjectType.ENTITY && ((Entity) object).canBeStunned)
			{
				((Entity) object).stunned = true;
				setAnimation(EntityAnimations.BULLET_SPLASH, this);
			}
		}
	}
}
