package edu.gcc.keen.gameobjects;

import java.util.LinkedList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.util.Area;
import edu.gcc.keen.util.VectorPool;

/**
 * The base class for all in-game objects
 * 
 * @author DONMOYERLR17
 *
 */
public abstract class GameObject
{
	protected Vector3f position;
	protected Vector2f scale;
	private Vector2f aabbOffset = new Vector2f(0.0f, 0.0f);
	protected List<Area> areas = new LinkedList<>();

	private int texture;
	protected int index;
	private int columns;
	private int rows;

	private boolean shouldDestroy;
	protected boolean updateArea;

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
		return VectorPool.getVector3f(position.x, position.y, position.z);
	}

	public boolean addArea(Area area)
	{
		if (!areas.contains(area))
		{
			this.updateArea = false;
			this.areas.add(area);

			return true;
		}

		return false;
	}

	public void removeArea(Area area)
	{
		this.updateArea = true;
		this.areas.remove(area);
	}

	public int numAreas()
	{
		return this.areas.size();
	}

	public int getTexture()
	{
		return texture;
	}

	public Vector2f getScale()
	{
		return VectorPool.getVector2f(scale.x, scale.y);
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
		return this.shouldDestroy;
	}

	public void destroy()
	{
		shouldDestroy = true;

		for (Area area : areas)
		{
			area.removeObject(this);
		}
	}

	protected void setIndex(int index)
	{
		this.index = index;
	}

	public boolean shouldUpdateArea()
	{
		return updateArea;
	}

	public void setShouldUpdateArea(boolean shouldUpdateArea)
	{
		this.updateArea = shouldUpdateArea;
	}

	public Vector2f getAabbOffset()
	{
		return aabbOffset;
	}

	public void setAabbOffset(Vector2f aabbOffset)
	{
		this.aabbOffset = aabbOffset;
	}
}