package edu.gcc.keen.graphics;

import org.joml.Vector4f;

public class Button
{
	private Vector4f position = new Vector4f();
	private ButtonListener listener;

	private boolean selected = false;
	private boolean hover = false;

	public Button(String text, Vector4f position)
	{
		this.position = new Vector4f(position);
	}

	public void tick()
	{
		// TODO detect when mouse if hovering over button and when it is selected

		listener.onClick();
	}

	public void setListener(ButtonListener listener)
	{
		this.listener = listener;
	}
}
