package gcc.edu.keen.tiles;

import org.joml.Vector2f;
import org.lwjgl.openvr.Texture;

/**
 * The tile class. A tile is a non-moving collisionable or non-collisionable
 * game object.
 * 
 * @author DONMOYERLR17
 *
 */
public class Tile
{
	private Vector2f position = new Vector2f();

	private Texture texture;

	public Tile(Texture texture)
	{
		this.texture = texture;
	}
}
