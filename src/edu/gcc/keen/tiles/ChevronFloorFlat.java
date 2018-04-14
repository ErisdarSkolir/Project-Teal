package edu.gcc.keen.tiles;

import org.joml.Vector2f;

import edu.gcc.keen.graphics.Texture;

public class ChevronFloorFlat extends Tile
{
	public ChevronFloorFlat(Vector2f position)
	{
		super(new Texture("tilesheet", new Vector2f(18.0f, 165.0f), 163), position);
	}

	@Override
	public void tick()
	{

	}
}
