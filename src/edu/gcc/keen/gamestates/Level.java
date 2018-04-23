package edu.gcc.keen.gamestates;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.joml.Vector2f;
import org.joml.Vector3f;
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
	private List<Tile> background = new ArrayList<>();
	private List<Item> items = new ArrayList<>();

	private List<GameObject> gameObjects = new ArrayList<>();

	private Keen keen;

	/**
	 * Constructor
	 */
	public Level(String levelName)
	{
		super();
		loadFromFile(levelName);

		keen = new Keen(new Vector3f(0.0f, 6f, 0.0f));

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
		renderer.render(keen, entities, tiles, background, items, camera);
	}

	/**
	 * Load a level from a file
	 * 
	 * @param filename
	 */
	private void loadFromFile(String levelName)
	{
		try (Scanner scanner = new Scanner(new File("res/levels/" + levelName + "_foreground.csv")))
		{
			scanner.useDelimiter(",|\n");

			for (int row = 0; scanner.hasNextLine(); row++)
			{
				for (int column = 0; scanner.hasNextInt(); column++)
				{
					int id = scanner.nextInt();

					if (id != -1)
					{
						Tile tile = TileCreator.createTileWithData(id, new Vector2f(2.0f * column, -(2.0f * row)));

						if (tile != null)
							tiles.add(tile);
					}
				}

				scanner.nextLine();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		try (Scanner scanner = new Scanner(new File("res/levels/" + levelName + "_background.csv")))
		{
			scanner.useDelimiter(",|\n");

			for (int row = 0; scanner.hasNextLine(); row++)
			{
				for (int column = 0; scanner.hasNextInt(); column++)
				{
					int id = scanner.nextInt();

					if (id != -1)
						background.add(new Tile(id, 18, 84, new Vector3f(2.0f * column, -(2.0f * row), -0.99f)));
				}

				scanner.nextLine();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
