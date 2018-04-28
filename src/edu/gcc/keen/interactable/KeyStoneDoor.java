package edu.gcc.keen.interactable;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.animations.InteractableAnimation;
import edu.gcc.keen.gameobjects.ObjectType;
import edu.gcc.keen.graphics.Textures;

public class KeyStoneDoor extends Interactable
{
	private int animationIndex = 0;
	private int animationTick = 10;

	private boolean animate;

	private InteractableAnimation animation;

	public KeyStoneDoor(InteractableAnimation animation, Vector3f position)
	{
		super(Textures.getTexture("tilesheet"), 18, 165, animation.getAnimation()[0], position, new Vector2f(1.0f, 1.0f), ObjectType.INTERACTABLE);
		this.objectType = ObjectType.INTERACTABLE;
		this.animation = animation;
		collidable = true;
	}

	@Override
	public void toggle()
	{
		super.toggle();
		animate = true;
	}

	@Override
	public void tick()
	{
		if (animate)
		{
			if (animationIndex > animation.getLenth() - 1)
			{
				animate = false;

				if (animation == InteractableAnimation.KEYSTONE_DOOR_MIDDLE || animation == InteractableAnimation.KEYSTONE_DOOR_TOP)
					collidable = false;
			}
			if (animationTick > 4)
			{
				index = animation.getAnimation()[animationIndex++];
				animationTick = 0;
			}

			animationTick++;
		}
	}
}
