package edu.gcc.keen.graphics;

import org.joml.Vector2f;

import edu.gcc.keen.util.GameObject;

public class Camera
{
	private Vector2f position = new Vector2f();
	private GameObject boundObject;

	public Camera()
	{
		// Default constructor
	}

	public void tick()
	{
		// TODO calculate camera position
	}

	public Camera(GameObject bindObject)
	{
		this.boundObject = bindObject;
	}

	public void bindObject(GameObject object)
	{
		this.boundObject = object;
	}
}
