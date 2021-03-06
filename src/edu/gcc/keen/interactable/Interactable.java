package edu.gcc.keen.interactable;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.gameobjects.GameObject;
import edu.gcc.keen.gameobjects.ObjectType;

public abstract class Interactable extends GameObject
{
	protected Interactable boundObject;

	public Interactable(int texture, int columns, int rows, int index, Vector3f position, Vector2f scale, ObjectType type)
	{
		super(texture, columns, rows, index, position, scale, type);
	}

	public void toggle()
	{
		if (boundObject != null)
			boundObject.toggle();
	}

	public void setBoundObject(Interactable object)
	{
		this.boundObject = object;
	}
}
