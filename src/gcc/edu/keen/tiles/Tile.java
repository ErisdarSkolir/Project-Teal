package gcc.edu.keen.tiles;

import org.joml.Vector2f;

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
	public Tile(Vector2f position)
	{
		super(position);
	}

	@Override
	public void tick()
	{

	}

	@Override
	public void onCollide(GameObject object)
	{

	}
}
