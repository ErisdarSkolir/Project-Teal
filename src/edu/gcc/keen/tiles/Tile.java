package edu.gcc.keen.tiles;

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
public abstract class Tile extends GameObject
{
	private boolean pole;
	private boolean oneWay;
	private boolean collidable;
	private boolean hangable;

	public Tile(int index, int textureColumns, int textureRows, Vector3f position)
	{
		super(Texture.getTexture("tilesheet"), textureColumns, textureRows, index, position, new Vector2f(1.0f, 1.0f));
	}

	public Tile(int index, boolean pole, boolean oneWay, boolean collidable, boolean hangable, Vector3f position)
	{
		super(Texture.getTexture("tilesheet"), 18, 165, index, position, new Vector2f(1.0f, 1.0f));
		this.pole = pole;
		this.oneWay = oneWay;
		this.collidable = collidable;
		this.hangable = hangable;
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
