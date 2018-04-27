package edu.gcc.keen.tiles;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.joml.Vector2f;
import org.joml.Vector3f;

import edu.gcc.keen.entities.Entity;
import edu.gcc.keen.item.Item;

public class GameObjectCreator
{
	private static Map<Integer, boolean[]> tileData = new HashMap<>();
	private static Map<Integer, Constructor> enemyData = new HashMap<>();
	private static Map<Integer, Constructor> itemData = new HashMap<>();

	public static Entity createEnemy(int id, Vector2f position)
	{
		if (enemyData.containsKey(id))
		{
			try
			{
				return (Entity) enemyData.get(id).newInstance(new Vector3f(position, 0.0f));
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		return null;
	}

	public static Item createItem(int id, Vector2f position)
	{
		try
		{
			return (Item) itemData.get(id).newInstance(new Vector3f(position, -0.5f));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public static Tile createTileWithData(int id, Vector2f position)
	{
		boolean[] data = tileData.get(id);

		if (data == null)
		{
			return null;
		}

		return new Tile(id, data[0], data[1], data[2], data[3], new Vector3f(position, data[4] ? 0.9f : -0.9f));
	}

	public static void init()
	{
		try (Scanner tileDataScanner = new Scanner(new File("res/data/tiledata.dat")); Scanner enemyDataScanner = new Scanner(new File("res/data/enemydata.dat")))
		{
			while (tileDataScanner.hasNext())
			{
				boolean[] data = new boolean[5];
				int id = tileDataScanner.nextInt();

				for (int i = 0; i < data.length; i++)
				{
					data[i] = (tileDataScanner.nextInt() == 1) ? true : false;
				}

				tileData.put(id, data);
			}

			while (enemyDataScanner.hasNextLine())
			{
				try
				{
					int id = enemyDataScanner.nextInt();
					Class<?> clazz = Class.forName(enemyDataScanner.next());

					if (clazz.getSuperclass() == Class.forName("edu.gcc.keen.entities.Entity"))
						enemyData.put(id, clazz.getConstructor(Vector3f.class));
					else if (clazz.getSuperclass() == Class.forName("edu.gcc.keen.item.Item"))
						itemData.put(id, clazz.getConstructor(Vector3f.class));
				}
				catch (NoSuchMethodException e)
				{
					e.printStackTrace();
				}
				catch (SecurityException e)
				{
					e.printStackTrace();
				}
				catch (ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
