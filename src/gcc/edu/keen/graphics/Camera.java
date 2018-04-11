package gcc.edu.keen.graphics;

import org.joml.Vector2f;

import edu.gcc.keen.util.GameObject;

public class Camera
{
	private Vector2f position;
	private GameObject boundObject;

	public Camera(GameObject bindObject)
	{
		this.boundObject = bindObject;
	}

	public void bindObject(GameObject object)
	{
		this.boundObject = object;
	}
}
