package edu.gcc.keen.tiles;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class TileCreator
{
	private static Map<Integer, boolean[]> tileData = new HashMap<>();

	public static Tile createTileWithData(int id, Vector2f position)
	{
		boolean[] data = tileData.get(id);

		if (data == null)
		{
			System.out.println("No data for " + id);
			return null;
		}

		return new Tile(id, data[0], data[1], data[2], data[3], new Vector3f(position, data[4] ? 0.9f : -0.9f));
	}

	public static void loadData(String path)
	{
		try (Scanner scanner = new Scanner(new File(path)))
		{
			while (scanner.hasNext())
			{
				boolean[] data = new boolean[5];
				int id = scanner.nextInt();

				for (int i = 0; i < data.length; i++)
				{
					data[i] = (scanner.nextInt() == 1) ? true : false;
				}

				tileData.put(id, data);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
