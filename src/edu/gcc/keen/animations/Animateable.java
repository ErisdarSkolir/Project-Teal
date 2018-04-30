package edu.gcc.keen.animations;

import edu.gcc.keen.gameobjects.GameObject;

public interface Animateable
{
	public default void nextAnimationFrame(GameObject object)
	{
		Animation animation = object.getCurrentAnimation();

		if (object.getAnimationIndex() >= animation.getLenth())
			object.setAnimationIndex(0);

		object.setIndex(animation.getAnimation()[object.getAnimationIndex()]);
		object.setAnimationIndex(object.getAnimationIndex() + 1);
		object.setAnimationTick(0);
	}

	public default void setAnimation(Animation animation, GameObject object)
	{
		if (!object.getCurrentAnimation().equals(animation))
		{
			object.setCurrentAnimation(animation);
			object.setAnimationIndex(1);
			object.setAnimationTick(0);
			object.setIndex(animation.getAnimation()[0]);
		}
	}
}
