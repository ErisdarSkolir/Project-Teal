package edu.gcc.keen.util;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector4f;

import edu.gcc.keen.item.Item;
import gcc.edu.keen.entities.Entity;
import gcc.edu.keen.tiles.Tile;

public class Area
{
	private List<Entity> entities = new ArrayList<>();
	private List<Tile> tiles = new ArrayList<>();
	private List<Item> items = new ArrayList<>();

	private Vector4f positionAndSize;

	public Area(Vector4f positionAndSize)
	{
		this.positionAndSize = new Vector4f(positionAndSize);
	}

	public void tick()
	{

	}

	public void checkCollision()
	{
		// TODO check for collision between the entities in this area
	}
}
