package gcc.edu.keen.gamestates;

import java.util.ArrayList;
import java.util.List;

import edu.gcc.keen.item.Item;
import gcc.edu.keen.entities.Entity;
import gcc.edu.keen.graphics.MasterRenderer;
import gcc.edu.keen.tiles.Tile;

public class Level extends GameState
{
	private List<Entity> entities = new ArrayList<>();
	private List<Tile> tiles = new ArrayList<>();
	private List<Item> items = new ArrayList<>();

	public Level(String filename)
	{

	}

	@Override
	public void tick()
	{

	}

	@Override
	public void render(MasterRenderer renderer)
	{
		renderer.render(entities, tiles, items);
	}
}
