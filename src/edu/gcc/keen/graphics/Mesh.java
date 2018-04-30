package edu.gcc.keen.graphics;

/**
 * A small class representing vertexes required for rendering
 */
public class Mesh
{
	private int vaoID;
	private int vertexCount;

	/**
	 * Constructor
	 * 
	 * @param vaoID
	 * @param vertexCount
	 */
	public Mesh(int vaoID, int vertexCount)
	{
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}

	// Getters

	public int getVaoID()
	{
		return vaoID;
	}

	public int getVertexCount()
	{
		return vertexCount;
	}
}
