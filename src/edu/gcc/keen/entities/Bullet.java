package edu.gcc.keen.entities;

import org.joml.Vector2f;

import edu.gcc.keen.graphics.Texture;

/**
 * Represents a bullet fired by keen's blaster
 * 
 * @author DONMOYERLR17
 *
 */
public class Bullet extends Entity
{
	private final float velocity = 100;

	public Bullet(Vector2f position)
	{
		super(new Texture(""), position);
	}

	@Override
	public void move()
	{

	}

	@Override
	public void tick()
	{

	}
}
