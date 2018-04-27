package edu.gcc.keen.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class VectorPool
{
	private static Deque<Vector2f> vector2fList = new ArrayDeque<>();
	private static Deque<Vector3f> vector3fList = new ArrayDeque<>();
	private static Deque<Matrix4f> matrix4fList = new ArrayDeque<>();

	public static Vector2f getVector2f(float x, float y)
	{
		try
		{
			return vector2fList.pop().set(x, y);
		}
		catch (NoSuchElementException e)
		{
			return new Vector2f(x, y);
		}
	}

	public static Vector3f getVector3f(float x, float y, float z)
	{
		try
		{
			return vector3fList.pop().set(x, y, z);
		}
		catch (NoSuchElementException e)
		{
			return new Vector3f(x, y, z);
		}
	}

	public static Matrix4f getMatrix4f()
	{
		try
		{
			return matrix4fList.pop();
		}
		catch (NoSuchElementException e)
		{
			return new Matrix4f().identity();
		}
	}

	public static void recycle(Vector3f vector)
	{
		vector3fList.add(vector);
	}

	public static void recycle(Vector2f vector)
	{
		vector2fList.add(vector);
	}

	public static void recycle(Matrix4f matrix)
	{
		matrix4fList.add(matrix.identity());
	}
}
