package edu.gcc.keen.graphics;

import org.joml.Vector3f;

import edu.gcc.keen.gameobjects.GameObject;
import edu.gcc.keen.util.VectorPool;

/**
 * The 2d camera
 * 
 * @author DONMOYERLR17
 *
 */
public class Camera
{
	private Vector3f position = new Vector3f();
	private GameObject boundObject;

	/**
	 * Default constructor
	 */
	public Camera()
	{

	}

	/**
	 * Updates the camera's position based on the currently bounded object
	 * 
	 * @param bindObject
	 */
	public void tick()
	{
		if (this.boundObject != null)
		{
			Vector3f tmpPosition = boundObject.getPosition();
			this.position.set(tmpPosition);
			VectorPool.recycle(tmpPosition);
		}
	}

	/**
	 * Update this camera and calculate new position
	 */
	public Camera(GameObject bindObject)
	{
		this.boundObject = bindObject;
	}

	/**
	 * Binds the camera to a gameObject to begin following that object
	 */
	public void bindObject(GameObject object)
	{
		this.boundObject = object;
	}

	public Vector3f getPosition()
	{
		return VectorPool.getVector3f(position.x, position.y, position.z);
	}
}
