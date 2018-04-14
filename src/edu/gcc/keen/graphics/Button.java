package edu.gcc.keen.graphics;

import org.joml.Vector4f;

/**
 * A clickable and selectable gui element to be used in menus
 * 
 * @author DONMOYERLR17
 *
 */
public class Button
{
	private Vector4f position = new Vector4f();
	private ButtonListener listener;

	private boolean selected = false;
	private boolean hover = false;

	/**
	 * Constructor
	 */
	public Button(String text, Vector4f position)
	{
		this.position = new Vector4f(position);
	}

	/**
	 * Update this button and check if it is be hovered over or selected
	 */
	public void tick()
	{
		// TODO detect when mouse if hovering over button and when it is selected

		listener.onClick();
	}

	/**
	 * Set the listener to be called when this button is selected
	 * 
	 * @param listener
	 */
	public void setListener(ButtonListener listener)
	{
		this.listener = listener;
	}
}
