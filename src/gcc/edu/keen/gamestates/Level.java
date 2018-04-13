package gcc.edu.keen.gamestates;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;

import edu.gcc.keen.item.Item;
import edu.gcc.keen.util.Area;
import gcc.edu.keen.entities.Entity;
import gcc.edu.keen.graphics.MasterRenderer;
import gcc.edu.keen.tiles.ChevronFloorFlat;
import gcc.edu.keen.tiles.Tile;

public class Level extends GameState
{
	private List<Area> areas = new ArrayList<>();

	private List<Entity> entities = new ArrayList<>();
	private List<Tile> tiles = new ArrayList<>();
	private List<Item> items = new ArrayList<>();

	public Level(String filename)
	{
		super();
		// loadFromFile(filename);

		tiles.add(new ChevronFloorFlat(new Vector2f(10.0f, 10.0f)));
	}

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

	@Override
	public void render(MasterRenderer renderer)
	{
		renderer.render(entities, tiles, items, camera);
	}

	private void loadFromFile(String filename)
	{
		// TODO load entities, tiles, and items from file into lists
	}
}
