package edu.gcc.keen.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;

/**
 * Subclass of shader and represents the general game 2 dimensional shader
 */
public class TwoDimensionalShader extends Shader
{
	/**
	 * Constructor
	 */
	public TwoDimensionalShader()
	{
		super("2DShader", "2DShader", "orthographicMatrix", "transformationMatrix", "textureRowsAndColumns", "textureOffset");
	}

	/**
	 * Loads the given transformation matrix to the shader
	 * 
	 * @param matrix
	 */
	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix("transformationMatrix", matrix);
	}

	/**
	 * Load texture atlas information to the shader
	 * 
	 * @param rowsAndColumns
	 * @param offset
	 */
	public void loadTextureAtlasInformation(Vector2f rowsAndColumns, Vector2f offset)
	{
		super.loadVector2f("textureRowsAndColumns", rowsAndColumns);
		super.loadVector2f("textureOffset", offset);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
	}
}
