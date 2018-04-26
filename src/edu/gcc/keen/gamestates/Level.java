package edu.gcc.keen.gamestates;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.entities.Entity;
import edu.gcc.keen.entities.Keen;
import edu.gcc.keen.graphics.MasterRenderer;
import edu.gcc.keen.graphics.Texture;
import edu.gcc.keen.item.Item;
import edu.gcc.keen.tiles.GameObjectCreator;
import edu.gcc.keen.tiles.Tile;
import edu.gcc.keen.util.Area;
import edu.gcc.keen.util.BoundingBox;
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

	private List<GameObject> gameObjects = new ArrayList<>();
	private List<GameObject> backgroundTiles = new ArrayList<>();

	private Keen keen;

	/**
	 * Constructor
	 */
	public Level(String levelName)
	{
		super();
		gameObjects = loadFromFile(levelName);
		areas = generateAreas(gameObjects, 10.0f, 10.0f, 150, 32);

		camera.bindObject(keen);
	}

	/**
	 * Update game objects in this level
	 */
	@Override
	public void tick()
	{
		for (Iterator<GameObject> itr = gameObjects.iterator(); itr.hasNext();)
		{
			GameObject object = itr.next();

			object.tick();

			if (object.shouldDestroy())
				itr.remove();
			else if (object.shouldUpdateArea())
			{
				for (Area area : areas)
				{
					if (BoundingBox.isIntersecting(object, area))
						area.addObject(object);
				}
			}
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
		renderer.render(keen, gameObjects, backgroundTiles, camera);
	}

	/**
	 * Load a level from a file
	 * 
	 * @param filename
	 */
	private List<GameObject> loadFromFile(String levelName)
	{
		List<GameObject> tmpObjects = new ArrayList<>();

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
						{
							if (tile.isCollidable() || tile.isOneWay() || tile.isPole())
								tmpObjects.add(tile);
							else
								backgroundTiles.add(tile);
						}
					}

					if (backgroundID != -1)
						backgroundTiles.add(new Tile(Texture.getTexture("background"), backgroundID, 18, 84, new Vector3f(2.0f * column, -(2.0f * row), -0.99f)));

					if (infoplaneID != -1 && infoplaneID != 2)
					{
						Entity entity = GameObjectCreator.createEnemy(infoplaneID, new Vector2f(column * 2.0f, -(row * 2.0f) + 2.0f));

						if (entity != null)
							tmpObjects.add(entity);
						else
						{
							Item item = GameObjectCreator.createItem(infoplaneID, new Vector2f(column * 2.0f, -(row * 2.0f) + 2.0f));

							if (item != null)
								tmpObjects.add(item);
						}
					}
					else if (infoplaneID == 2)
					{
						keen = new Keen(new Vector3f(column * 2.0f, -(row * 2.0f) + 2.0f, 0.0f));
						tmpObjects.add(keen);
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

		return tmpObjects;
	}

	public List<Area> generateAreas(List<GameObject> objects, float minAreaWidth, float minAreaHeight, float levelWidthInTiles, float levelHeightInTiles)
	{
		List<Area> tmpAreas = new ArrayList<>();

		float numWidth = Math.round(levelWidthInTiles / minAreaWidth);
		float numHeight = Math.round(levelHeightInTiles / minAreaHeight);
		float areaWidth = levelWidthInTiles / numWidth;
		float areaHeight = levelHeightInTiles / numHeight;

		for (int i = 0; i < numWidth; i++)
		{
			for (int k = 0; k < numHeight; k++)
			{
				tmpAreas.add(new Area(new Vector2f(i * 2.0f * areaWidth + areaWidth, -(k * 2.0f * areaHeight) - areaHeight), new Vector2f(areaWidth, areaHeight).mul(2.0f)));
			}
		}

		for (GameObject object : objects)
		{
			for (Area area : tmpAreas)
			{
				if (BoundingBox.isIntersecting(object, area))
					area.addObject(object);
			}
		}

		return tmpAreas;
	}
}
