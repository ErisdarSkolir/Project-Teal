package edu.gcc.keen.tiles;

import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

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

	public Tile(int id, int textureColumns, int textureRows, Vector3f position)
	{
		super(new Texture("tilesheet", textureColumns, textureRows, id), position, new Vector2f(1.0f, 1.0f));
		this.id = id;
	}

	public Tile(int id, boolean pole, boolean oneWay, boolean collidable, boolean hangable, Vector3f position)
	{
		super(new Texture("tilesheet", 18, 165, id), position, new Vector2f(1.0f, 1.0f));
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
	public void onCollideX(List<GameObject> collidingObjects)
	{

	}

	@Override
	public void onCollideY(List<GameObject> collidingObjects)
	{

	}

	public boolean isCollidable()
	{
		return collidable;
	}

	public boolean isOneWay()
	{
		return oneWay;
	}

	public boolean isPole()
	{
		return pole;
	}
}
