package edu.gcc.keen.gameobjects;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Textures;

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

	public Tile(int index, boolean[] data, Vector3f position)
	{
		super(Textures.getTexture("tilesheet"), 18, 165, index, position, new Vector2f(1.0f, 1.0f));

		this.pole = data[0];
		this.oneWay = data[1];
		this.collidable = data[2];
		this.hangable = data[3];
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
