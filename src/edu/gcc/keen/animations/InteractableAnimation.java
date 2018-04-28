package edu.gcc.keen.animations;

public enum InteractableAnimation
{
	KEYSTONE_HOLDER_RED(1196, 1214),
	KEYSTONE_HOLDER_YELLOW(1197, 1215),
	KEYSTONE_HOLDER_BLUE(1198, 1216),
	KEYSTONE_HOLDER_GREEN(1199, 1217),
	KEYSTONE_DOOR_TOP(1314, 1315, 1316, 1317),
	KEYSTONE_DOOR_MIDDLE(1332, 1333, 1334, 1335),
	KEYSTONE_DOOR_BOTTOM(1350, 1351, 1352, 1353);

	private int[] animation;

	InteractableAnimation(int... animation)
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
