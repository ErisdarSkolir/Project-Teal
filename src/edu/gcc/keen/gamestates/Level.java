package edu.gcc.keen.gamestates;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.joml.Vector2f;
import org.joml.Vector4f;

import edu.gcc.keen.entities.Entity;
import edu.gcc.keen.entities.Keen;
import edu.gcc.keen.graphics.MasterRenderer;
import edu.gcc.keen.item.Item;
import edu.gcc.keen.tiles.Tile;
import edu.gcc.keen.tiles.TileCreator;
import edu.gcc.keen.util.Area;
import edu.gcc.keen.util.GameObject;

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

	private List<GameObject> gameObjects = new ArrayList<>();

	private Keen keen;

	/**
	 * Constructor
	 */
	public Level(String levelName)
	{
		super();
		tiles.addAll(loadFromFile(levelName));

		keen = new Keen(new Vector2f(0.0f, 6f));

		camera.bindObject(keen);

		areas.add(new Area(new Vector4f(0.0f, 2.0f, 10.0f, 2.0f)));

		for (Tile tile : tiles)
		{
			areas.get(0).addObject(tile);
		}

		areas.get(0).addObject(keen);
	}

	/**
	 * Update game objects in this level
	 */
	@Override
	public void tick()
	{
		keen.tick();

		for (GameObject object : gameObjects)
		{
			object.tick();

			if (object.shouldDestroy())
				gameObjects.remove(object);
		}

		for (Area area : areas)
		{
			area.tick();
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
	private List<Tile> loadFromFile(String levelName)
	{
		List<Tile> tmpList = new ArrayList<>();

		try (Scanner scanner = new Scanner(new File("res/levels/" + levelName + "_foreground.csv")))
		{
			scanner.useDelimiter(",|\n");

			for (int row = 0; scanner.hasNextLine(); row++)
			{
				for (int column = 0; scanner.hasNextInt(); column++)
				{
					int id = scanner.nextInt();

					if (id != -1)
						tmpList.add(TileCreator.createTile(id, new Vector2f(2.0f * column, -(2.0f * row))));
				}

				scanner.nextLine();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return tmpList;
	}
}
