package edu.gcc.keen.tiles;

import org.joml.Vector2f;

import edu.gcc.keen.graphics.Texture;

public class ChevronFloorFlat extends Tile
{
	public ChevronFloorFlat(Vector2f position)
	{
		super(new Texture("tilesheet", new Vector2f(18.0f, 165.0f), 163), position, new Vector2f(1.0f, 1.0f), true);
	}

	@Override
	public void tick()
	{

	}
}
