package gcc.edu.keen.graphics;

import org.joml.Matrix4f;

public class TwoDimensionalShader extends Shader
{
	public TwoDimensionalShader()
	{
		super("2DShader", "2DShader", "orthographicMatrix", "transformationMatrix");
	}

	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix("transformationMatrix", matrix);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
	}
}
