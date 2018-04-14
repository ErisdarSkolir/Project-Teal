package edu.gcc.keen.gamestates;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import edu.gcc.keen.entities.Entity;
import edu.gcc.keen.graphics.MasterRenderer;
import edu.gcc.keen.item.Item;
import edu.gcc.keen.tiles.ChevronFloorFlat;
import edu.gcc.keen.tiles.Tile;
import edu.gcc.keen.util.Area;

/**
 * A level contains a list of all entites, tiles, and items. Also contains the
 * collision detect grid system
 * 
 * @author DONMOYERLR17
 *
 */
public class Level extends GameState
{
	private List<Area> areas = new ArrayList<>();

	private List<Entity> entities = new ArrayList<>();
	private List<Tile> tiles = new ArrayList<>();
	private List<Item> items = new ArrayList<>();

	/**
	 * Constructor
	 */
	public Level(String filename)
	{
		super();
		// loadFromFile(filename);

		tiles.add(new ChevronFloorFlat(new Vector2f(10.0f, 10.0f)));
	}

	/**
	 * Update game objects in this level
	 */
	@Override
	public void tick()
	{
		super.tick();

		for (Entity entity : entities)
		{
			entity.tick();
		}

		// TODO check for collisions
	}

	/**
	 * Render this level
	 */
	@Override
	public void render(MasterRenderer renderer)
	{
		renderer.render(entities, tiles, items, camera);
	}

	/**
	 * Load a level from a file
	 * 
	 * @param filename
	 */
	private void loadFromFile(String filename)
	{
		// TODO load entities, tiles, and items from file into lists
	}
}
