package edu.gcc.keen.graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import edu.gcc.keen.util.BufferUtils;

/**
 * Textures creates openGL textures and stores the resulting id integers to be
 * accessed later
 * 
 * @author DONMOYERLR17
 *
 */
public class Textures
{
	private static Map<String, Integer> textureMap = new HashMap<>();

	private Textures()
	{
		throw new UnsupportedOperationException("Static Access Class");
	}

	/**
	 * Get the texture id associated with the given texture name, or if an id for
	 * that name does not exist, create a new texture;
	 * 
	 * @param name
	 *            of the texture
	 * @return id of the texture
	 */
	public static int getTexture(String name)
	{
		if (!textureMap.containsKey(name))
			textureMap.put(name, loadTextureFromString(name));

		return textureMap.get(name);
	}

	/**
	 * Creates a new OpenGL texture and return the resultant id.
	 * 
	 * @param path
	 *            the filename of the texture without file extension
	 * @return the OpenGL integer of the created texture
	 */
	private static int loadTextureFromString(String path)
	{
		int[] pixels = null;

		int width = 0;
		int height = 0;

		try
		{
			BufferedImage image = ImageIO.read(new FileInputStream("res/" + path + ".png"));

			width = image.getWidth();
			height = image.getHeight();

			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		int[] data = new int[width * height];

		for (int i = 0; i < width * height; i++)
		{
			int a = (pixels[i] & 0xff000000) >> 24;
			int r = (pixels[i] & 0xff0000) >> 16;
			int g = (pixels[i] & 0xff00) >> 8;
			int b = pixels[i] & 0xff;

			data[i] = a << 24 | b << 16 | g << 8 | r;
		}

		int tex = GL11.glGenTextures();
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, BufferUtils.createIntBuffer(data));
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		return tex;
	}

	/**
	 * Deletes all stored textures
	 */
	public static void cleanup()
	{
		for (Integer texture : textureMap.values())
		{
			GL11.glDeleteTextures(texture);
		}
	}
}
