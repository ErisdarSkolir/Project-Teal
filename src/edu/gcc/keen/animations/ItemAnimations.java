package edu.gcc.keen.animations;

public enum ItemAnimations
{
	RED_KEYSTONE(6, 7),
	YELLOW_KEYSTONE(8, 9),
	BLUE_KEYSTONE(10, 11),
	GREEN_KEYSTONE(12, 13),
	GUM(1030, 1031),
	MARSHMALLOW(1032, 1033),
	CHOCOLATE_MILK(1034, 1035),
	PIXIE_STICK(1036, 1037),
	CEREAL(1040, 1041),
	SUGAR(1042, 1043),
	KEG_O_VITALIN(32, 33),
	AMMO(16, 17),
	KEYCARD(0),
	VITALIN(19, 20, 21, 22);

	private int[] animation;

	ItemAnimations(int... animation)
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
