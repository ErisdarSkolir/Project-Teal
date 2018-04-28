package edu.gcc.keen.gameobjects;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.animations.Animateable;
import edu.gcc.keen.animations.ItemAnimations;
import edu.gcc.keen.graphics.Textures;

public class Item extends GameObject implements Animateable
{
	public boolean oneUp;
	public boolean givesScore;
	public boolean givesAmmo;
	public boolean isKeyCard;
	public boolean isKeyStone;
	public boolean isVitalin;

	public int keyStoneColor;
	public int pointValue;

	public Item(int id, int[] data, Vector3f position)
	{
		super(data[1] == 1 ? Textures.getTexture("tilesheet") : Textures.getTexture("items_and_particle_spritesheet"), data[9], data[10], id, position, new Vector2f(1.0f, 1.0f), ObjectType.ITEM);

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
		if (animationTick > 40)
			nextAnimationFrame(this);

		animationTick++;
	}

	public int getPointValue()
	{
		return pointValue;
	}
}
