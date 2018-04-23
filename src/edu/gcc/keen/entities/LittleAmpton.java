package edu.gcc.keen.entities;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Texture;
import edu.gcc.keen.util.GameObject;

/**
 * Little Ampton enemy
 */
public class LittleAmpton extends Entity
{
	public LittleAmpton(Vector3f position)
	{
		super(new Texture("enemy", 14, 10, 15), position, new Vector2f(1.0f, 1.0f));
	}

	@Override
	public void move()
	{

	}

	@Override
	public void tick()
	{

	}

	@Override
	public void onCollideX(List<GameObject> collidingObjects)
	{

	}

	@Override
	public void onCollideY(List<GameObject> collidingObjects)
	{

	}
}
