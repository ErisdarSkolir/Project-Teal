package edu.gcc.keen.gameobjects;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Texture;

/**
 * The tile class. A tile is a non-moving collisionable or non-collisionable
 * game object.
 * 
 * @author DONMOYERLR17
 *
 */
public class Tile extends GameObject
{
	private boolean pole;
	private boolean oneWay;
	private boolean collidable;
	private boolean hangable;

	public Tile(int texture, int index, int textureColumns, int textureRows, Vector3f position)
	{
		super(texture, textureColumns, textureRows, index, position, new Vector2f(1.0f, 1.0f));
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

	@Override
	public void tick()
	{
		// TODO Auto-generated method stub

	}
}
