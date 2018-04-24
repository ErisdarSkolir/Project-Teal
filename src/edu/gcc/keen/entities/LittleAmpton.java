package edu.gcc.keen.entities;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Texture;
import edu.gcc.keen.tiles.Tile;
import edu.gcc.keen.util.BoundingBox;
import edu.gcc.keen.util.GameObject;

/**
 * Little Ampton enemy
 */
public class LittleAmpton extends Entity
{
	private boolean direction;

	public LittleAmpton(Vector3f position)
	{
		super(Texture.getTexture("enemy"), 14, 10, 15, position, new Vector2f(1.625f, 1.625f));
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
				Tile tile = (Tile) object;

				if (tile.isCollidable())
				{
					this.position.add(BoundingBox.minX(this, tile), 0.0f, 0.0f);
					direction = !direction;
					break;
				}
			}
		}
	}

	@Override
	public void onCollideY(List<GameObject> collidingObjects)
	{
		if (collidingObjects.size() <= 2)
			direction = !direction;

		for (GameObject object : collidingObjects)
		{
			if (object instanceof Tile)
			{
				Tile tile = (Tile) object;

				if (tile.isCollidable())
				{
					this.position.add(0.0f, BoundingBox.minY(this, tile), 0.0f);
					break;
				}
			}
		}
	}
}
