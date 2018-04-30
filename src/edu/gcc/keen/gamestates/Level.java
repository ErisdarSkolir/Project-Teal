package edu.gcc.keen.gamestates;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.animations.InteractableAnimations;
import edu.gcc.keen.gameobjects.GameObject;
import edu.gcc.keen.gameobjects.GameObjectCreator;
import edu.gcc.keen.gameobjects.ObjectType;
import edu.gcc.keen.gameobjects.Tile;
import edu.gcc.keen.graphics.MasterRenderer;
import edu.gcc.keen.graphics.Textures;
import edu.gcc.keen.interactable.Interactable;
import edu.gcc.keen.interactable.KeyStoneDoor;
import edu.gcc.keen.interactable.KeyStoneHolder;
import edu.gcc.keen.util.Area;
import edu.gcc.keen.util.BoundingBox;

/**
 * A level contains a list of all entites, tiles, and items. Also contains the
 * collision detect grid system
 */
public class Level extends GameState
{
	private static final Logger LOGGER = Logger.getLogger("Logger");
	private static final String LEVEL_PATH = "res/levels/";
	private static List<GameObject> addObjects = new ArrayList<>();

	private List<Area> areas = new ArrayList<>();
	private List<GameObject> gameObjects = new ArrayList<>();
	private List<GameObject> backgroundTiles = new ArrayList<>();

	/**
	 * Constructor
	 */
	public Level(String levelName)
	{
		super();
		gameObjects = loadFromFile(levelName);
		areas = generateAreas(gameObjects, 10.0f, 10.0f, 150, 32);
	}

	/**
	 * Update game objects in this level
	 */
	@Override
	public void tick()
	{
		if (!addObjects.isEmpty())
		{
			gameObjects.addAll(addObjects);

			for (GameObject object : addObjects)
			{
				for (Area area : areas)
				{
					if (BoundingBox.isIntersecting(object, area))
						area.addObject(object);
				}
			}

			addObjects.clear();
		}

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

		super.tick();
	}

	/**
	 * Render this level
	 */
	@Override
	public void render(MasterRenderer renderer)
	{
		renderer.render(gameObjects, backgroundTiles, camera);
	}

	/**
	 * Add an object to the current level. Can be called statically
	 * 
	 * @param object
	 */
	public static void addObject(GameObject object)
	{
		addObjects.add(object);
	}

	/**
	 * Load a level from a file
	 * 
	 * @param filename
	 */
	private List<GameObject> loadFromFile(String levelName)
	{
		List<GameObject> tmpObjects = new ArrayList<>();

		Tile tmpTile;
		GameObject tmpiInfoplaneObject;

		try (Scanner foreground = new Scanner(new File(LEVEL_PATH + levelName + "_foreground.csv"));
				Scanner background = new Scanner(new File(LEVEL_PATH + levelName + "_background.csv"));
				Scanner infoplane = new Scanner(new File(LEVEL_PATH + levelName + "_infoplane.csv")))
		{
			foreground.useDelimiter(",");
			background.useDelimiter(",");
			infoplane.useDelimiter(",");

			for (int row = 0; foreground.hasNextLine(); row++)
			{
				for (int column = 0; foreground.hasNextInt(); column++)
				{
					if ((tmpTile = GameObjectCreator.createTile(foreground.nextInt(), new Vector2f(2.0f * column, -(2.0f * row)))) != null)
					{
						if (tmpTile.isCollidable() || tmpTile.isOneWay() || tmpTile.isPole())
							tmpObjects.add(tmpTile);
						else
							backgroundTiles.add(tmpTile);
					}

					if ((tmpiInfoplaneObject = GameObjectCreator.createInfoplaneObject(infoplane.nextInt(), new Vector2f(column * 2.0f, -(row * 2.0f) + 2.0f))) != null)
					{
						tmpObjects.add(tmpiInfoplaneObject);

						if (tmpiInfoplaneObject.getType() == ObjectType.KEEN)
							camera.bindObject(tmpiInfoplaneObject);
					}

					backgroundTiles.add(new Tile(Textures.getTexture("background"), background.nextInt(), 18, 84, new Vector3f(2.0f * column, -(2.0f * row), -0.99f)));
				}

				foreground.nextLine();
				background.nextLine();
				infoplane.nextLine();
			}

			while (infoplane.hasNextLine())
			{
				Interactable last = null;
				Interactable now = null;

				while (infoplane.hasNextInt())
				{
					int id = infoplane.nextInt();
					Vector3f position = new Vector3f(infoplane.nextInt() * 2.0f, -infoplane.nextInt() * 2.0f, -0.2f);

					if (id == 1196)
						now = new KeyStoneHolder(0, position);
					else if (id == 1350)
						now = new KeyStoneDoor(InteractableAnimations.KEYSTONE_DOOR_BOTTOM, position);
					else if (id == 1332)
						now = new KeyStoneDoor(InteractableAnimations.KEYSTONE_DOOR_MIDDLE, position);
					else if (id == 1314)
						now = new KeyStoneDoor(InteractableAnimations.KEYSTONE_DOOR_TOP, position);

					if (last != null)
						last.setBoundObject(now);

					last = now;

					tmpObjects.add(now);
				}
			}
		}
		catch (IOException e)
		{
			LOGGER.log(java.util.logging.Level.SEVERE, e.getMessage());
		}

		return tmpObjects;
	}

	/**
	 * Generate the spatial partitioning grid and place previously generated
	 * gameObjects inside the correct area
	 * 
	 * @param objects
	 * @param minAreaWidth
	 * @param minAreaHeight
	 * @param levelWidthInTiles
	 * @param levelHeightInTiles
	 * @return a list of areas created for the level
	 */
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
				{
					area.addObject(object);
					break;
				}
			}
		}

		return tmpAreas;
	}
}
