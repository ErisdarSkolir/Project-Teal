package edu.gcc.keen.graphics;

import org.joml.Vector2f;

import edu.gcc.keen.util.GameObject;

/**
 * The 2d camera
 * 
 * @author DONMOYERLR17
 *
 */
public class Camera
{
	private Vector2f position = new Vector2f();
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
		// TODO calculate camera position
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
}
