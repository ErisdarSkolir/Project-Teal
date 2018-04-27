package edu.gcc.keen.gameobjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.entities.Entity;

/**
 * A factory class for creating gameObjects based on configuration from the
 * given data files.
 * 
 * @author DONMOYERLR17
 *
 */
public class GameObjectCreator
{
	private static final Logger LOGGER = Logger.getLogger("Logger");

	private static final String DATA_PATH = "res/data/";

	private static Map<Integer, boolean[]> tileData;
	private static Map<Integer, int[]> itemData;
	private static Map<Integer, Constructor<?>> enemyData;

	private GameObjectCreator()
	{
		throw new UnsupportedOperationException("Static Factory Class");
	}

	/**
	 * Load the data configuration files
	 */
	public static void init()
	{
		tileData = loadTileData("tiledata");
		itemData = loadItemData("itemdata");
		enemyData = loadEntityData("enemydata");
	}

	/**
	 * Create an infoplane object which could be either an entity or an item
	 * 
	 * @param id
	 * @param position
	 * @return the entity or item corresponding to the given id or null if there is
	 *         no object for the id
	 */
	public static GameObject createInfoplaneObject(int id, Vector2f position)
	{
		if (enemyData.containsKey(id))
			return createEnemy(id, position);
		else if (itemData.containsKey(id))
			return createItem(id, position);
		else if (id != -1)
			LOGGER.log(java.util.logging.Level.INFO, "No infoplane data for {0}", id);

		return null;
	}

	/**
	 * Create an entity with the given id and position
	 * 
	 * @param id
	 * @param position
	 * @return an entity that corresponds to the given id or null if there is
	 *         no data for the id
	 */
	public static Entity createEnemy(int id, Vector2f position)
	{
		if (id != -1 && enemyData.containsKey(id))
		{
			try
			{
				return (Entity) enemyData.get(id).newInstance(new Vector3f(position, 0.0f));
			}
			catch (Exception e)
			{
				LOGGER.log(java.util.logging.Level.SEVERE, e.getMessage());
			}
		}
		else if (id != -1)
			LOGGER.log(java.util.logging.Level.INFO, "No enemy data for {0}", id);

		return null;
	}

	/**
	 * Create an item with the given id and position
	 * 
	 * @param id
	 * @param position
	 * @return an item that corresponds to the given id or null if there is no data
	 *         for the id
	 */
	public static Item createItem(int id, Vector2f position)
	{
		if (id != -1 && itemData.containsKey(id))
			return new Item(id, itemData.get(id), new Vector3f(position, 0.0f));
		else if (id != -1)
			LOGGER.log(java.util.logging.Level.INFO, "No item data for {0}", id);

		return null;
	}

	/**
	 * Create a tile with the given id and position
	 * 
	 * @param id
	 * @param position
	 * @return a tile that corresponds to the given id or null if there is no data
	 *         for the id
	 */
	public static Tile createTile(int id, Vector2f position)
	{
		if (id != -1 && tileData.containsKey(id))
		{
			boolean[] data = tileData.get(id);
			return new Tile(id, data, new Vector3f(position, data[4] ? 0.9f : -0.9f));
		}
		else if (id != -1)
			LOGGER.log(java.util.logging.Level.INFO, "No tile data for {0}", id);

		return null;
	}

	/**
	 * Load tile data from the give file
	 * 
	 * @param filename
	 * @return a map of ids and tile data
	 */
	private static Map<Integer, boolean[]> loadTileData(String filename)
	{
		Map<Integer, boolean[]> tmpTileData = new HashMap<>();

		try (Scanner tileDataScanner = new Scanner(new File(DATA_PATH + filename + ".dat")))
		{
			while (tileDataScanner.hasNext())
			{
				boolean[] data = new boolean[5];
				int id = tileDataScanner.nextInt();

				for (int i = 0; i < data.length; i++)
				{
					data[i] = (tileDataScanner.nextInt() == 1);
				}

				tmpTileData.put(id, data);
			}
		}
		catch (FileNotFoundException e)
		{
			LOGGER.log(java.util.logging.Level.SEVERE, e.getMessage());
		}

		return tmpTileData;
	}

	/**
	 * Load item data from the give file
	 * 
	 * @param filename
	 * @return a map of ids and item data
	 */
	private static Map<Integer, int[]> loadItemData(String filename)
	{
		Map<Integer, int[]> tmpItemData = new HashMap<>();

		try (Scanner itemDataScanner = new Scanner(new File(DATA_PATH + filename + ".dat")))
		{
			while (itemDataScanner.hasNextLine())
			{
				int[] data = new int[11];
				int id = itemDataScanner.nextInt();

				for (int i = 0; i < data.length; i++)
				{
					data[i] = itemDataScanner.nextInt();
				}

				tmpItemData.put(id, data);
			}
		}
		catch (FileNotFoundException e)
		{
			LOGGER.log(java.util.logging.Level.SEVERE, e.getMessage());
		}

		return tmpItemData;
	}

	/**
	 * Load entity data from the give file
	 * 
	 * @param filename
	 * @return a map of ids and entity constructor
	 */
	private static Map<Integer, Constructor<?>> loadEntityData(String filename)
	{
		Map<Integer, Constructor<?>> tmpEntityData = new HashMap<>();

		try (Scanner enemyDataScanner = new Scanner(new File(DATA_PATH + filename + ".dat")))
		{
			while (enemyDataScanner.hasNextLine())
			{
				int id = enemyDataScanner.nextInt();
				Class<?> clazz = Class.forName(enemyDataScanner.next());

				if (clazz.getSuperclass() == Class.forName("edu.gcc.keen.entities.Entity"))
					tmpEntityData.put(id, clazz.getConstructor(Vector3f.class));
			}
		}
		catch (Exception e)
		{
			LOGGER.log(java.util.logging.Level.SEVERE, e.getMessage());
		}

		return tmpEntityData;
	}
}
