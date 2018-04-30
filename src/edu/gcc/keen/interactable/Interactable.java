package edu.gcc.keen.interactable;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.gameobjects.GameObject;
import edu.gcc.keen.gameobjects.ObjectType;

/**
 * An interactable is a game object that does not move but can be interacted
 * with by the player. Usually an interactable has two main states that it can
 * switch between
 */
public abstract class Interactable extends GameObject
{
	protected Interactable boundObject;

	/**
	 * Consructor
	 * 
	 * @param texture
	 * @param columns
	 * @param rows
	 * @param index
	 * @param position
	 * @param scale
	 * @param type
	 */
	public Interactable(int texture, int columns, int rows, int index, Vector3f position, Vector2f scale, ObjectType type)
	{
		super(texture, columns, rows, index, position, scale, type);
	}

	/**
	 * Toggle the state of this interactable and any other bound objects
	 */
	public void toggle()
	{
		if (boundObject != null)
			boundObject.toggle();
	}

	/**
	 * Set the object that will be toggled if this interactable is toggled
	 * 
	 * @param object
	 */
	public void setBoundObject(Interactable object)
	{
		this.boundObject = object;
	}
}
