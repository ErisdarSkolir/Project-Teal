package edu.gcc.keen.animations;

public enum KeenAnimation implements Animation
{
	STATIONARY_LEFT(0),
	STATIONARY_RIGHT(1),
	WALK_LEFT(2, 3, 4, 5),
	WALK_RIGHT(6, 7, 8, 9),
	JUMP_LEFT(11, 12, 13),
	JUMP_RIGHT(14, 15, 16),
	POGO_RIGHT(32, 43),
	POGO_LEFT(10, 21),
	LOOK_UP(17),
	LOOK_DOWN(18),
	WAITING(22, 23, 24, 25),
	OPEN_BOOK(26, 27, 28, 29, 30),
	WAIT_WITH_BOOK(33, 31),
	CLOSE_BOOK(34, 35),
	SHOOT_LEFT(36),
	SHOOT_LEFT_JUMP(37),
	SHOOT_DOWN(38),
	SHOOT_UP_JUMP(39),
	SHOOT_UP(40),
	SHOOT_RIGHT(41),
	SHOOT_RIGHT_JUMP(42),
	STATIONARY_POLE_LEFT(44),
	CLIMB_POLE_LEFT(45, 46),
	STATIONARY_POLE_RIGHT(47),
	CLIMB_POLE_RIGHT(48, 49),
	SLIDE_POLE(50, 51, 52, 53),
	DIE(54, 65),
	SHOOT_LEFT_POLE(55),
	SHOOT_DOWN_LEFT_POLE(56),
	SHOOT_UP_LEFT_POLE(57),
	SHOOT_UP_RIGHT_POLE(58),
	SHOOT_DOWN_RIGHT_POLE(59),
	SHOOT_RIGHT_POLE(60),
	WALK_THRU_DOOR(61, 62, 63, 64, 20),
	CLIMB_LEDGE_LEFT(69, 68, 67, 66),
	HANG_LEDGE_LEFT(70),
	CLIMB_LEDGE_RIGHT(71),
	HANG_LEDGE_RIGHT(72, 73, 74, 75);

	private int[] animation;

	KeenAnimation(int... animation)
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
