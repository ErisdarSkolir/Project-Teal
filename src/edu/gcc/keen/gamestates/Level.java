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
import edu.gcc.keen.tiles.GameObjectCreator;
import edu.gcc.keen.tiles.Tile;
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
	private List<Tile> backgroundTiles = new ArrayList<>();
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

		camera.bindObject(keen);

		areas.add(new Area(new Vector4f(0.0f, 2.0f, 10.0f, 2.0f)));

		for (Entity entity : entities)
		{
			areas.get(0).addObject(entity);
		}

		for (Item item : items)
		{
			areas.get(0).addObject(item);
		}

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

		for (GameObject object : entities)
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
		renderer.render(keen, entities, tiles, backgroundTiles, items, camera);
	}

	/**
	 * Load a level from a file
	 * 
	 * @param filename
	 */
	private void loadFromFile(String levelName)
	{
		try (Scanner foreground = new Scanner(new File("res/levels/" + levelName + "_foreground.csv"));
				Scanner background = new Scanner(new File("res/levels/" + levelName + "_background.csv"));
				Scanner infoplane = new Scanner(new File("res/levels/" + levelName + "_infoplane.csv")))
		{
			foreground.useDelimiter(",");
			background.useDelimiter(",");
			infoplane.useDelimiter(",");

			for (int row = 0; foreground.hasNextLine(); row++)
			{
				for (int column = 0; foreground.hasNextInt(); column++)
				{
					int foregroundID = foreground.nextInt();
					int backgroundID = background.nextInt();
					int infoplaneID = infoplane.nextInt();

					if (foregroundID != -1)
					{
						Tile tile = GameObjectCreator.createTileWithData(foregroundID, new Vector2f(2.0f * column, -(2.0f * row)));

						if (tile != null)
							tiles.add(tile);
					}

					if (backgroundID != -1)
						backgroundTiles.add(new Tile(backgroundID, 18, 84, new Vector3f(2.0f * column, -(2.0f * row), -0.99f)));

					if (infoplaneID != -1 && infoplaneID != 2)
					{
						Entity entity = GameObjectCreator.createEnemy(infoplaneID, new Vector2f(column * 2.0f, -(row * 2.0f) + 2.0f));

						if (entity != null)
							entities.add(entity);
						else
						{
							Item item = GameObjectCreator.createItem(infoplaneID, new Vector2f(column * 2.0f, -(row * 2.0f) + 2.0f));

							if (item != null)
								items.add(item);
						}
					}
					else if (infoplaneID == 2)
					{
						keen = new Keen(new Vector3f(column * 2.0f, -(row * 2.0f) + 2.0f, 0.0f));
					}
				}

				foreground.nextLine();
				background.nextLine();
				infoplane.nextLine();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
