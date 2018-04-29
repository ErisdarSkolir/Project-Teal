package edu.gcc.keen.entities;

import java.util.List;
import java.util.Random;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.animations.Animateable;
import edu.gcc.keen.animations.EntityAnimations;
import edu.gcc.keen.gameobjects.GameObject;
import edu.gcc.keen.gameobjects.ObjectType;
import edu.gcc.keen.gameobjects.Tile;
import edu.gcc.keen.graphics.Textures;
import edu.gcc.keen.util.Area;
import edu.gcc.keen.util.BoundingBox;

/**
 * Sparky enemy
 * 
 * @author DONMOYERLR17
 *
 */
public class Sparky extends Entity implements Animateable
{
	private Random random = new Random();

	private boolean direction;
	private boolean onPole;

	public Sparky(Vector3f position)
	{
		super(Textures.getTexture("enemy"), 14, 10, 0, position, new Vector2f(1.625f, 1.625f));
		this.canKill = true;
		this.currentAnimation = EntityAnimations.SPARKY_WALK_LEFT;
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

		if (direction)
		{
			horizontalVelocity = -0.2f;
			setAnimation(EntityAnimations.SPARKY_WALK_LEFT, this);
		}
		else if (!direction)
		{
			horizontalVelocity = 0.2f;
			setAnimation(EntityAnimations.SPARKY_WALK_RIGHT, this);
		}
	}

	@Override
	public void tick()
	{
		move();

		if (animationTick > 9)
			nextAnimationFrame(this);

		animationTick++;
	}

	@Override
	public void onCollideX(List<GameObject> collidingObjects)
	{
		for (GameObject object : collidingObjects)
		{
				if (object.isCollidable())
				{
					position.add(BoundingBox.minX(this, object), 0.0f, 0.0f);
					direction = !direction;
					break;
				}
		}
	}

	@Override
	public void onCollideY(List<GameObject> collidingObjects)
	{
		for (GameObject object : collidingObjects)
		{
			if (object.getType().equals(ObjectType.TILE))
			{
				if (object.isCollidable() || ((Tile) object).isOneWay())
				{
					position.add(0.0f, BoundingBox.minY(this, object), 0.0f);
					break;
				}
			}
		}
	}
}
