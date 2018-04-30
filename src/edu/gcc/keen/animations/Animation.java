package edu.gcc.keen.animations;

/**
 * Inteface describing the common methods a animation enumeration need to
 * implement
 *
 */
public interface Animation
{
	/**
	 * Returns the list of indexes in the animation
	 * 
	 * @return an int array of indexes
	 */
	public int[] getAnimation();

	/**
	 * Return the number of indexes in the animation
	 * 
	 * @return the number of indexes
	 */
	public int getLenth();
}
