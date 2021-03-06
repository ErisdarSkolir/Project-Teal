package edu.gcc.keen.interactable;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.animations.InteractableAnimations;
import edu.gcc.keen.gameobjects.ObjectType;
import edu.gcc.keen.graphics.Textures;

public class KeyStoneHolder extends Interactable
{
	private InteractableAnimations currentAnimation;

	private int color;

	private boolean containsKeyStone;

	public KeyStoneHolder(int color, Vector3f position)
	{
		super(Textures.getTexture("tilesheet"), 18, 165, InteractableAnimations.values()[color].getAnimation()[0], position, new Vector2f(1.0f, 1.0f), ObjectType.KEYSTONE_HOLDER);
		this.currentAnimation = InteractableAnimations.values()[color];
		this.color = color;
	}

	@Override
	public void toggle()
	{
		if (!containsKeyStone)
		{
			super.toggle();
			containsKeyStone = true;
			index = currentAnimation.getAnimation()[1];
		}
	}

	@Override
	public void tick()
	{

	}

	public boolean containsKeyStone()
	{
		return containsKeyStone;
	}

	public int getColor()
	{
		return color;
	}
}
