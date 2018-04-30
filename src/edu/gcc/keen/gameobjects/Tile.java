package edu.gcc.keen.gameobjects;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.graphics.Textures;

/**
 * The tile class. A tile is a non-moving collisionable or non-collisionable
 * game object.
 */
public class Tile extends GameObject
{
	private boolean pole;
	private boolean oneWay;
	private boolean hangable;

	/**
	 * Constructor used for when there is no associated data with the tile
	 * 
	 * @param texture
	 * @param index
	 * @param textureColumns
	 * @param textureRows
	 * @param position
	 */
	public Tile(int texture, int index, int textureColumns, int textureRows, Vector3f position)
	{
		super(texture, textureColumns, textureRows, index, position, new Vector2f(1.0f, 1.0f), ObjectType.TILE);
	}

	/**
	 * Constructor for creating a tile with associated data
	 * 
	 * @param index
	 * @param data
	 * @param position
	 */
	public Tile(int index, boolean[] data, Vector3f position)
	{
		super(Textures.getTexture("tilesheet"), 18, 165, index, position, new Vector2f(1.0f, 1.0f), ObjectType.TILE);

		this.pole = data[0];
		this.oneWay = data[1];
		this.collidable = data[2];
		this.hangable = data[3];
	}

	/**
	 * Update this tile
	 */
	@Override
	public void tick()
	{
		// Tiles do not yet currently update
	}

	public boolean isOneWay()
	{
		return oneWay;
	}

	public boolean isPole()
	{
		return pole;
	}

	public boolean isHangeable()
	{
		return hangable;
	}
}
