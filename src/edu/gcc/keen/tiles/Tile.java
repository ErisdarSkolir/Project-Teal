package edu.gcc.keen.tiles;

import org.joml.Vector2f;

import edu.gcc.keen.graphics.Texture;
import edu.gcc.keen.util.GameObject;

/**
 * The tile class. A tile is a non-moving collisionable or non-collisionable
 * game object.
 * 
 * @author DONMOYERLR17
 *
 */
public class Tile extends GameObject
{
	private int id;

	public Tile(int id, Vector2f position, Vector2f scale, boolean canCollide)
	{
		super(new Texture("tilesheet", 18, 165, id), position, scale);
		this.id = id;
		this.collidable = canCollide;
	}

	@Override
	public void tick()
	{

	}

	@Override
	public void onCollide(GameObject object)
	{

	}
}
