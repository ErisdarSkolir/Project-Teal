package edu.gcc.keen.util;

import org.joml.Vector2f;
import org.joml.Vector3f;

/**
 * The base class for all in-game objects
 * 
 * @author DONMOYERLR17
 *
 */
public abstract class GameObject
{
	protected Vector3f position;
	protected Vector2f scale = new Vector2f(1.0f, 1.0f);
	protected Vector2f aabbOffset = new Vector2f(0.0f, 0.0f);
	protected Area area;

	private int texture;
	private int index;
	private int columns;
	private int rows;

	protected boolean shouldDestroy;

	/**
	 * Constructor
	 * 
	 * @param texture
	 * @param position
	 */
	public GameObject(int texture, int columns, int rows, int index, Vector3f position, Vector2f scale)
	{
		this.texture = texture;
		this.position = new Vector3f(position);
		this.scale = scale;
		this.columns = columns;
		this.rows = rows;
		this.index = index;
	}

	/**
	 * Update this object
	 */
	public abstract void tick();

	/**
	 * Get the texture offset required for texture atlases
	 * 
	 * @return the offset positions in a vector2f object
	 */
	public Vector2f getTextureOffset()
	{
		return new Vector2f((float) (index % columns) / columns, (float) (index / columns) / rows);
	}

	public Vector3f getPosition()
	{
		return new Vector3f(position);
	}

	public void setArea(Area area)
	{
		this.area = area;
	}

	public int getTexture()
	{
		return texture;
	}

	public Vector2f getScale()
	{
		return new Vector2f(scale);
	}

	public int getColumns()
	{
		return columns;
	}

	public int getRows()
	{
		return rows;
	}

	public boolean shouldDestroy()
	{
		return shouldDestroy;
	}

	public void destroy()
	{
		shouldDestroy = true;
	}

	protected void setIndex(int index)
	{
		this.index = index;
	}
}
