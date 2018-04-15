package edu.gcc.keen.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * This class provides utility functions to create arrays into buffers needed
 * for OpenGL
 * 
 * @author DONMOYERLR17
 *
 */
public class BufferUtils
{
	private BufferUtils()
	{

	}

	public static ByteBuffer createByteBuffer(byte[] array)
	{
		ByteBuffer result = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
		result.put(array).flip();

		return result;
	}

	public static FloatBuffer createFloatBuffer(float[] array)
	{
		FloatBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
		result.put(array).flip();

		return result;
	}

	public static IntBuffer createIntBuffer(int[] array)
	{
		IntBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		result.put(array).flip();

		return result;
	}
}
