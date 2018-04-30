package edu.gcc.keen.animations;

import edu.gcc.keen.gameobjects.GameObject;

/**
 * This interface contains default implementation of methods used to animate
 * objects
 */
public interface Animateable
{
	/**
	 * Set the texture index of the given object to the next frame
	 * 
	 * @param object
	 */
	public default void nextAnimationFrame(GameObject object)
	{
		Animation animation = object.getCurrentAnimation();

		if (object.getAnimationIndex() >= animation.getLenth())
			object.setAnimationIndex(0);

		object.setIndex(animation.getAnimation()[object.getAnimationIndex()]);
		object.setAnimationIndex(object.getAnimationIndex() + 1);
		object.setAnimationTick(0);
	}

	/**
	 * Set the current animation of the given object to the given animation
	 * 
	 * @param animation
	 * @param object
	 */
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
