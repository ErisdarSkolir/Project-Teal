package edu.gcc.keen.gameobjects;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.animations.ItemAnimations;
import edu.gcc.keen.graphics.Texture;

public class Item extends GameObject
{
	public boolean oneUp;
	public boolean givesScore;
	public boolean givesAmmo;
	public boolean isKeyCard;
	public boolean isKeyStone;
	public boolean isVitalin;

	public int keyStoneColor;
	public int pointValue;
	public int animationIndex;
	public int tick;

	protected ItemAnimations currentAnimation;

	public Item(int id, int[] data, Vector3f position)
	{
		super(data[1] == 1 ? Texture.getTexture("tilesheet") : Texture.getTexture("items_and_particle_spritesheet"), data[9], data[10], id, position, new Vector2f(1.0f, 1.0f));

		this.oneUp = data[0] == 1;
		this.givesScore = data[1] == 1;
		this.givesAmmo = data[2] == 1;
		this.isKeyCard = data[3] == 1;
		this.isKeyStone = data[4] == 1;
		this.isVitalin = data[5] == 1;
		this.keyStoneColor = data[6];
		this.pointValue = data[7];
		this.currentAnimation = ItemAnimations.values()[data[8]];
	}

	/**
	 * Update this item
	 */
	@Override
	public void tick()
	{
		if (tick > 40)
		{
			setIndex(currentAnimation.getAnimation()[animationIndex]);
			animationIndex++;
			tick = 0;

			if (animationIndex >= currentAnimation.getLenth())
				animationIndex = 0;
		}

		tick++;
	}

	public int getPointValue()
	{
		return pointValue;
	}
}
