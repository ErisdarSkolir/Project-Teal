package edu.gcc.keen.interactable;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.animations.Animateable;
import edu.gcc.keen.animations.InteractableAnimations;
import edu.gcc.keen.gameobjects.ObjectType;
import edu.gcc.keen.graphics.Textures;

/**
 * Represents an interactable object that switches between opens when toggled
 */
public class KeyStoneDoor extends Interactable implements Animateable
{
	private boolean animate;

	public KeyStoneDoor(InteractableAnimations animation, Vector3f position)
	{
		super(Textures.getTexture("tilesheet"), 18, 165, animation.getAnimation()[0], position, new Vector2f(1.0f, 1.0f), ObjectType.INTERACTABLE);
		this.objectType = ObjectType.INTERACTABLE;
		this.currentAnimation = animation;
		this.collidable = true;
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
			if (animationIndex >= currentAnimation.getLenth())
			{
				animate = false;

				if (currentAnimation == InteractableAnimations.KEYSTONE_DOOR_MIDDLE || currentAnimation == InteractableAnimations.KEYSTONE_DOOR_TOP)
					collidable = false;
			}
			if (animationTick > 4)
				nextAnimationFrame(this);

			animationTick++;
		}
	}
}
