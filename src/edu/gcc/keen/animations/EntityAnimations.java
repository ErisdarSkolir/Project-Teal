package edu.gcc.keen.animations;

public enum EntityAnimations
{
	BULLET(0, 1, 2, 3, 4),
	BULLET_SPLASH(5);

	private int[] animation;

	EntityAnimations(int... animation)
	{
		this.animation = animation;
	}

	public int[] getAnimation()
	{
		return animation;
	}

	public int getLenth()
	{
		return animation.length;
	}
}
