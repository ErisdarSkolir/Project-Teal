package edu.gcc.keen.graphics;

import org.joml.Matrix4f;
import org.joml.Vector2f;

/**
 * Subclass of shader and represents the general game 2 dimensional shader
 */
public class TwoDimensionalShader extends Shader
{
	private static final String SHADER_NAME = "2DShader";

	private static final String UNIFORM_TEXTURE_RC = "textureRowsAndColumns";
	private static final String UNIFORM_TEXTURE_OFFSET = "textureOffset";
	private static final String UNIFORM_ORTHO_MATRIX = "orthographicMatrix";
	private static final String UNIFROM_TRANS_MATRIX = "transformationMatrix";
	private static final String UNIFORM_VIEW_MATRIX = "viewMatrix";

	/**
	 * Constructor
	 */
	public TwoDimensionalShader()
	{
		super(SHADER_NAME, UNIFORM_ORTHO_MATRIX, UNIFROM_TRANS_MATRIX, UNIFORM_TEXTURE_RC, UNIFORM_TEXTURE_OFFSET, UNIFORM_VIEW_MATRIX);
	}

	/**
	 * Loads the given transformation matrix to the shader
	 * 
	 * @param matrix
	 */
	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix(UNIFROM_TRANS_MATRIX, matrix);
	}

	/**
	 * Load the columns and rows for a texture atlas to the shader
	 * 
	 * @param columns
	 * @param rows
	 */
	public void loadTextureRowsAndColumns(float columns, float rows)
	{
		super.loadVector2f(UNIFORM_TEXTURE_RC, columns, rows);
	}

	/**
	 * Load the offset for the current texture into the shader
	 * 
	 * @param offset
	 */
	public void loadTextureOffset(Vector2f offset)
	{
		super.loadVector2f(UNIFORM_TEXTURE_OFFSET, offset);
	}

	/**
	 * Bind attribute arrays
	 */
	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
	}
}
