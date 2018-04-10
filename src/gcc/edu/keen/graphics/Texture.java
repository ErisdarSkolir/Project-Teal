package gcc.edu.keen.graphics;

/**
 * Class represting an opengl texture which are stored as integer IDs
 * Textures can also be atlases, in which case the number of rows and columns
 * need to be defined
 * 
 * @author DONMOYERLR17
 *
 */
public class Texture
{
	private int ID;
	private int numberOfRows;
	private int numberOfColumns;

	public Texture(int ID, int numberOfRows)
	{
		this.ID = ID;
		this.numberOfRows = numberOfRows;
	}

	public Texture(int ID)
	{
		this.ID = ID;
	}

	public int getID()
	{
		return ID;
	}

	public int getNumberOfRows()
	{
		return numberOfRows;
	}
}
