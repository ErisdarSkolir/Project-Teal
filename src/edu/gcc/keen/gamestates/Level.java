package edu.gcc.keen.gamestates;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import edu.gcc.keen.entities.Entity;
import edu.gcc.keen.entities.Keen;
import edu.gcc.keen.graphics.MasterRenderer;
import edu.gcc.keen.item.Item;
import edu.gcc.keen.tiles.ChevronFloorFlat;
import edu.gcc.keen.tiles.ChevronFloorFlatTop;
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

	private Keen keen;

	/**
	 * Constructor
	 */
	public Level(String filename)
	{
		super();
		// loadFromFile(filename);

		tiles.add(new ChevronFloorFlat(new Vector2f(0.0f, 0.0f)));
		tiles.add(new ChevronFloorFlat(new Vector2f(2.0f, 0.0f)));
		tiles.add(new ChevronFloorFlatTop(new Vector2f(2.0f, 2.0f)));
		tiles.add(new ChevronFloorFlatTop(new Vector2f(0.0f, 2.0f)));

		keen = new Keen(new Vector2f(0.0f, 0.0f));

		camera.bindObject(keen);
	}

	/**
	 * Update game objects in this level
	 */
	@Override
	public void tick()
	{
		keen.tick();

		for (Entity entity : entities)
		{
			entity.tick();
		}

		// TODO check for collisions
		super.tick();
	}

	/**
	 * Render this level
	 */
	@Override
	public void render(MasterRenderer renderer)
	{
		renderer.render(keen, entities, tiles, items, camera);
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
