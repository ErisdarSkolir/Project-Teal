package gcc.edu.keen.entities;

import org.joml.Vector2f;

import gcc.edu.keen.graphics.Texture;

/**
 * An entity is any game object that moves or has more complex logic.
 * 
 * @author DONMOYERLR17
 *
 */
public class Entity
{
	private Vector2f position = new Vector2f();
	private Texture texture;

	/**
	 * Default constructor.
	 */
	public Entity(Texture texture)
	{
		this.texture = texture;
	}

	/**
	 * Default update method.
	 */
	public void tick()
	{

	}

	/**
	 * Default method to move this entity.
	 */
	public void move(float x, float y)
	{
		position.add(x, y);
	}

	public Vector2f getPosition()
	{
		return position;
	}
}
