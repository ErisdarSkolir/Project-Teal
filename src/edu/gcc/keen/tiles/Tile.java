package edu.gcc.keen.tiles;

import java.util.List;

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

	private boolean pole;
	private boolean oneWay;
	private boolean hangable;

	public Tile(int id, Vector2f position, Vector2f scale)
	{
		super(new Texture("tilesheet", 18, 165, id), position, scale);
		this.id = id;
	}

	public Tile(int id, boolean pole, boolean oneWay, boolean collidable, boolean hangable, Vector2f position)
	{
		super(new Texture("tilesheet", 18, 165, id), position, new Vector2f(1.0f, 1.0f));
		this.aabbOffset = new Vector2f(0.001f, 0.0f);
		this.id = id;
		this.pole = pole;
		this.oneWay = oneWay;
		this.collidable = collidable;
		this.hangable = hangable;
	}

	@Override
	public void tick()
	{

	}

	@Override
	public void onCollide(List<GameObject> object)
	{

	}

	public boolean isCollidable()
	{
		return collidable;
	}
}
