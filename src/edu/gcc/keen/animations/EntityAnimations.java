package edu.gcc.keen.animations;

/**
 * Animations associated with entities besides the player
 */
public enum EntityAnimations implements Animation
{
	BULLET(0, 1, 2, 3, 4),
	BULLET_SPLASH(5),
	AMPTON_WALK_LEFT(18, 19, 20, 21),
	AMPTON_WALK_RIGHT(25, 24, 23, 22),
	AMPTON_ON_POLE(15),
	AMPTON_STUNNED(17),
	SPARKY_WALK_LEFT(0, 1, 2, 3),
	SPARKY_WALK_RIGHT(10, 9, 8, 7);

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
