package edu.gcc.keen.animations;

public enum EntityAnimations implements Animation
{
	BULLET(0, 1, 2, 3, 4),
	BULLET_SPLASH(5);

	private int[] animation;

	EntityAnimations(int... animation)
	{
		this.animation = animation;
	}

	@Override
	public int[] getAnimation()
	{
		return animation;
	}

	@Override
	public int getLenth()
	{
		return animation.length;
	}
}
