package edu.gcc.keen.tiles;

public class TileMetaData
{
	private boolean pole;
	private boolean oneWay;
	private boolean collidable;
	private boolean hangable;

	public TileMetaData(boolean collidable, boolean pole, boolean oneWay, boolean hangable)
	{
		this.pole = pole;
		this.collidable = collidable;
		this.oneWay = oneWay;
		this.hangable = hangable;
	}

	public boolean isPole()
	{
		return pole;
	}

	public boolean isOneWay()
	{
		return oneWay;
	}

	public boolean isCollidable()
	{
		return collidable;
	}

	public boolean isHangable()
	{
		return hangable;
	}
}
