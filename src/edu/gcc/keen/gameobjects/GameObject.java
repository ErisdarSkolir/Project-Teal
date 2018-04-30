package edu.gcc.keen.gameobjects;

import java.util.LinkedList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.animations.Animation;
import edu.gcc.keen.util.Area;

/**
 * The base class for all in-game objects
 *
 */
public abstract class GameObject
{
	protected ObjectType objectType;
	protected Animation currentAnimation;

	protected Vector3f position;
	protected Vector2f scale;
	private Vector2f aabbOffset = new Vector2f(0.0f, 0.0f);
	protected List<Area> areas = new LinkedList<>();

	private int texture;
	private int columns;
	private int rows;
	protected int animationIndex;
	protected int index;
	protected int animationTick;

	private boolean shouldDestroy;
	protected boolean updateArea;
	protected boolean collidable;

	/**
	 * Constructor
	 * 
	 * @param texture
	 * @param columns
	 * @param rows
	 * @param index
	 * @param position
	 * @param scale
	 * @param type
	 */
	public GameObject(int texture, int columns, int rows, int index, Vector3f position, Vector2f scale, ObjectType type)
	{
		this.texture = texture;
		this.position = new Vector3f(position);
		this.scale = scale;
		this.columns = columns;
		this.rows = rows;
		this.index = index;
		this.objectType = type;
	}

	/**
	 * Update this object
	 */
	public abstract void tick();

	/**
	 * Add the given area to the list of areas that contain this object. Should only
	 * be called if the area does indeed contain this object
	 * 
	 * @param area
	 * @return true if the area was added, false otherwise
	 */
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

	/**
	 * Remove specified area from the area list
	 * 
	 * @param area
	 */
	public void removeArea(Area area)
	{
		this.updateArea = true;
		this.areas.remove(area);
	}

	/**
	 * Remove this object from all areas and set the should destroy flag to true so
	 * this object will be destroyed next level tick
	 */
	public void destroy()
	{
		shouldDestroy = true;

		for (Area area : areas)
		{
			area.removeObject(this);
		}
	}

	/**
	 * Get the texture offset required for texture atlases
	 * 
	 * @return the offset positions in a vector2f object
	 */
	public Vector2f getTextureOffset()
	{
		return new Vector2f((float) (index % columns) / columns, (float) Math.floorDiv(index, columns) / rows);
	}

	// Additional getters and setters from private and protected variables

	public Vector3f getPosition()
	{
		return new Vector3f(position.x, position.y, position.z);
	}

	public int getNumAreas()
	{
		return this.areas.size();
	}

	public int getTexture()
	{
		return texture;
	}

	public Vector2f getScale()
	{
		return new Vector2f(scale.x, scale.y);
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

	public void setIndex(int index)
	{
		this.index = index;
	}

	public int getIndex()
	{
		return index;
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

	public boolean isCollidable()
	{
		return this.collidable;
	}

	public ObjectType getType()
	{
		return this.objectType;
	}

	public Animation getCurrentAnimation()
	{
		return currentAnimation;
	}

	public void setCurrentAnimation(Animation animation)
	{
		this.currentAnimation = animation;
	}

	public void setAnimationIndex(int index)
	{
		this.animationIndex = index;
	}

	public int getAnimationIndex()
	{
		return animationIndex;
	}

	public int getAnimationTick()
	{
		return animationTick;
	}

	public void setAnimationTick(int tick)
	{
		this.animationTick = tick;
	}
}
