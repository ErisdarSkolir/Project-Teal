package edu.gcc.keen.util;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector4f;

import edu.gcc.keen.entities.Entity;
import edu.gcc.keen.item.Item;
import edu.gcc.keen.tiles.Tile;

/**
 * This class will help with collision detection. It will contain a list of
 * tiles and entities within it, so those entities only have to check collision
 * with what is in this area
 * 
 * @author DONMOYERLR17
 *
 */
public class Area
{
	private List<Entity> entities = new ArrayList<>();
	private List<Tile> tiles = new ArrayList<>();
	private List<Item> items = new ArrayList<>();

	private Vector4f positionAndSize;

	/**
	 * Constructor
	 * 
	 * @param positionAndSize
	 */
	public Area(Vector4f positionAndSize)
	{
		this.positionAndSize = new Vector4f(positionAndSize);
	}

	/**
	 * Update this Area and remove objects if needed
	 */
	public void tick()
	{

	}

	/**
	 * Check for collisions between entites and other objects and call their
	 * collision methods
	 */
	public void checkCollision()
	{
		// TODO check for collision between the entities in this area
	}
}
