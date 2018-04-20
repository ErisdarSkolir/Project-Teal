package edu.gcc.keen.animations;

public enum KeenAnimation
{
	STATIONARY_LEFT(0),
	STATIONARY_RIGHT(1),
	WALK_LEFT(2, 3, 4, 5),
	WALK_RIGHT(6, 7, 8, 9),
	JUMP_LEFT(11, 12, 13);

	private int[] animation;

	KeenAnimation(int... animation)
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
