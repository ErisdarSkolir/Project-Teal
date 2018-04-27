package edu.gcc.keen.graphics;

import org.joml.Vector3f;

import edu.gcc.keen.gameobjects.GameObject;

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
		// Default constructor
	}

	/**
	 * Constructor
	 * 
	 * @param bindObject
	 */
	public void tick()
	{
		this.position = boundObject.getPosition();
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
		return position;
	}
}
