package edu.gcc.keen.entities;

import java.util.List;

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
 * Little Ampton enemy
 */
public class LittleAmpton extends Entity implements Animateable
{
	private boolean direction;
	private boolean onPole;

	private int poleCooldown = 100;

	public LittleAmpton(Vector3f position)
	{
		super(Textures.getTexture("enemy"), 14, 10, 15, position, new Vector2f(1.625f, 1.625f));
		this.setAabbOffset(new Vector2f(0.5f, 0.5f));
		this.currentAnimation = EntityAnimations.AMPTON_WALK_LEFT;
		this.canKill = false;
		this.canCollideWithKeen = true;
		this.canBeStunned = true;
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

		if (!stunned)
		{
			if (direction && !onPole)
			{
				horizontalVelocity = -0.2f;
				setAnimation(EntityAnimations.AMPTON_WALK_LEFT, this);
			}
			else if (!onPole)
			{
				horizontalVelocity = 0.2f;
				setAnimation(EntityAnimations.AMPTON_WALK_RIGHT, this);
			}

			if (onPole && direction)
			{
				verticalVelocity = 0.4f;
				setAnimation(EntityAnimations.AMPTON_ON_POLE, this);
			}
			else if (onPole)
			{
				verticalVelocity = -0.4f;
				setAnimation(EntityAnimations.AMPTON_ON_POLE, this);
			}
			else
			{
				verticalVelocity = -0.4f;
			}
		}
		else if (verticalVelocity > 0.0f)
			verticalVelocity += -0.2f;
	}

	@Override
	public void tick()
	{
		move();

		if (stunned && canBeStunned)
		{
			setAnimation(EntityAnimations.AMPTON_STUNNED, this);
			verticalVelocity = 0.6f;
			horizontalVelocity = 0.0f;
			canBeStunned = false;
		}

		if (animationTick > 9)
			nextAnimationFrame(this);

		animationTick++;
		if (poleCooldown < 100)
			poleCooldown++;
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
				if (onPole && poleCooldown >= 50 && verticalVelocity < 0.0f && (((Tile) object).isOneWay() || object.isCollidable()))
				{
					onPole = false;
					canKill = false;
					position.add(0.0f, BoundingBox.minY(this, object), 0.0f);
					poleCooldown = 0;
					verticalVelocity = 0.0f;
				}
				else if (onPole && (((Tile) object).isCollidable() || (object.getIndex() == 193 && object.getPosition().y <= position.y)))
				{
					direction = !direction;

					if (object.isCollidable())
						position.add(0.0f, BoundingBox.minY(this, object), 0.0f);
				}

				else if (!onPole && poleCooldown >= 100 && ((Tile) object).isPole())
				{
					onPole = true;
					canKill = true;
					poleCooldown = 0;
					horizontalVelocity = 0.0f;
					position.x = object.getPosition().x;
				}
				else if (!onPole && (object.isCollidable() || ((Tile) object).isOneWay()))
				{
					position.add(0.0f, BoundingBox.minY(this, object), 0.0f);
				}
			}
		}
	}
}
