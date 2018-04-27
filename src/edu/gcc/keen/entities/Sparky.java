package edu.gcc.keen.entities;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Texture;
import edu.gcc.keen.util.GameObject;

/**
 * Sparky enemy
 * 
 * @author DONMOYERLR17
 *
 */
public class Sparky extends Entity
{
	public Sparky(Vector3f position)
	{
		super(Texture.getTexture("enemy"), 14, 10, 0, position, new Vector2f(1.625f, 1.625f));
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
