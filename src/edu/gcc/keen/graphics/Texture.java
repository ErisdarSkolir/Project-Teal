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
 * Class representing an OpenGL texture which are stored as integer IDs
 * Textures can also be atlases, in which case the number of rows and columns
 * need to be specified
 * 
 * @author DONMOYERLR17
 *
 */
public class Texture
{
	private static Map<String, Integer> textures = new HashMap<>();

	public static void init(String... textureNames)
	{
		for (String name : textureNames)
		{
			textures.put(name, loadTextureFromString(name));
		}
	}

	/**
	 * Creates a new OpenGL texture. If a texture with the same filename has already
	 * been created, it will return the already generated integer instead.
	 * 
	 * @param path
	 *            the filename of the texture without file extension
	 * @return the OpenGL integer of the created texture
	 */
	private static int loadTextureFromString(String path)
	{
		if (textures.containsKey(path))
			return textures.get(path);

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
		for (Integer texture : textures.values())
		{
			GL11.glDeleteTextures(texture);
		}
	}

	public static int getTexture(String name)
	{
		return textures.get(name);
	}
}
