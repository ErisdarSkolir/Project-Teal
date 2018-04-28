package edu.gcc.keen.entities;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.gameobjects.GameObject;
import edu.gcc.keen.gameobjects.Tile;
import edu.gcc.keen.graphics.Textures;
import edu.gcc.keen.util.Area;
import edu.gcc.keen.util.BoundingBox;

/**
 * Little Ampton enemy
 */
public class LittleAmpton extends Entity
{
	private boolean direction;

	public LittleAmpton(Vector3f position)
	{
		super(Textures.getTexture("enemy"), 14, 10, 15, position, new Vector2f(1.625f, 1.625f));
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
			horizontalVelocity = 0.2f;
		else
			horizontalVelocity = -0.2f;

		verticalVelocity = -0.2f;
	}

	@Override
	public void tick()
	{
		move();
	}

	@Override
	public void onCollideX(List<GameObject> collidingObjects)
	{
		for (GameObject object : collidingObjects)
		{
			if (object instanceof Tile)
			{
				this.position.add(BoundingBox.minX(this, object), 0.0f, 0.0f);
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
			if (object instanceof Tile)
			{
				this.position.add(0.0f, BoundingBox.minY(this, object), 0.0f);
				break;
			}
		}
	}
}
