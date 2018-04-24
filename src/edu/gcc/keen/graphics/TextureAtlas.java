package edu.gcc.keen.graphics;

import org.joml.Vector2f;

public interface TextureAtlas
{
	/**
	 * Get the texture offset required for texture atlases
	 * 
	 * @return the offset positions in a vector2f object
	 */
	public default Vector2f getTextureOffset(int index, int columns, int rows)
	{
		return new Vector2f((float) (index % columns) / columns, (float) (index / columns) / rows);
	}
}
