package gcc.edu.keen.gamestates;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gcc.edu.keen.KeenMain;
import gcc.edu.keen.graphics.Button;
import gcc.edu.keen.graphics.ButtonListener;
import gcc.edu.keen.graphics.MasterRenderer;

public class MainMenu extends GameState
{
	private List<Button> buttons = new ArrayList<>();

	private Random random = new Random();
	// private Level level = new Level("level" + (random.nextInt(5) + 1));

	public MainMenu()
	{
		super();
		// TODO initialize buttons

		new ButtonListener()
		{
			@Override
			public void onClick()
			{
				KeenMain.setState(new Level("level1"));
			}
		};
	}

	@Override
	public void tick()
	{
		super.tick();

		// level.tick();

		for (Button button : buttons)
		{
			button.tick();
		}

		// TODO Auto-generated method stub
	}

	@Override
	public void render(MasterRenderer renderer)
	{
		// level.render(renderer);
		// TODO Auto-generated method stub
	}
}
