package edu.gcc.keen.graphics;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import edu.gcc.keen.util.FileUtils;

/**
 * A class representing an OpenGL shader. Some details will have to be provided
 * by implementation since this object is abstract
 * 
 * @author DONMOYERLR17
 *
 */
public abstract class Shader
{
	private FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
	private Map<String, Integer> uniforms = new HashMap<>();

	private int id;

	private boolean enabled = false;

	/**
	 * Constructor. Generates a vertex and fragment shader from the given file names
	 * and stores the shader uniforms in a HashMap
	 * 
	 * @param vertexPath
	 * @param fragementPath
	 * @param uniformNames
	 */
	protected Shader(String vertexPath, String fragementPath, String... uniformNames)
	{
		id = createShader(vertexPath, fragementPath);

		bindAttributes();
		GL20.glLinkProgram(id);
		GL20.glValidateProgram(id);
		getUniformLocations(uniformNames);
	}

	/**
	 * Bind attribute arrays
	 */
	protected abstract void bindAttributes();

	/**
	 * Find the integer ids for all given uniforms in this shader and store them in
	 * a map
	 * 
	 * @param uniformNames
	 */
	protected void getUniformLocations(String... uniformNames)
	{
		for (String name : uniformNames)
		{
			uniforms.put(name, GL20.glGetUniformLocation(id, name));
		}
	}

	/**
	 * Bind current shader attributes
	 * 
	 * @param attribute
	 * @param variableName
	 */
	protected void bindAttribute(int attribute, String variableName)
	{
		GL20.glBindAttribLocation(id, attribute, variableName);
	}

	/**
	 * Create a vertex and fragment shader program from the given file paths and
	 * return the program id
	 * 
	 * @param vertexPath
	 * @param fragmentPath
	 * @return the OpenGL program id
	 */
	private int createShader(String vertexPath, String fragmentPath)
	{
		int program = GL20.glCreateProgram();
		int vertID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
		int fragID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);

		String vertString = FileUtils.loadAsString("res/shaders/" + vertexPath + ".vert");
		String fragString = FileUtils.loadAsString("res/shaders/" + fragmentPath + ".frag");

		GL20.glShaderSource(vertID, vertString);
		GL20.glCompileShader(vertID);
		if (GL20.glGetShaderi(vertID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			System.err.println(GL20.glGetShaderInfoLog(vertID));
		}
		GL20.glAttachShader(program, vertID);
		GL20.glDeleteShader(vertID);

		GL20.glShaderSource(fragID, fragString);
		GL20.glCompileShader(fragID);
		if (GL20.glGetShaderi(fragID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			System.err.println(GL20.glGetShaderInfoLog(fragID));
		}
		GL20.glAttachShader(program, fragID);
		GL20.glDeleteShader(fragID);

		return program;
	}

	/**
	 * Load a Matrix4f to the given uniform
	 * 
	 * @param location
	 * @param matrix
	 */
	protected void loadMatrix(String location, Matrix4f matrix)
	{
		GL20.glUniformMatrix4fv(uniforms.get(location), false, matrix.get(buffer));
	}

	/**
	 * Load a single float value to the given uniform
	 * 
	 * @param location
	 * @param value
	 */
	protected void loadFloat(String location, float value)
	{
		GL20.glUniform1f(uniforms.get(location), value);
	}

	/**
	 * Load a Vector2f to the given uniform location
	 * 
	 * @param location
	 * @param vector
	 */
	protected void loadVector2f(String location, Vector2f vector)
	{
		GL20.glUniform2f(uniforms.get(location), vector.x, vector.y);
	}

	protected void loadVector2f(String location, float var1, float var2)
	{
		GL20.glUniform2f(uniforms.get(location), var1, var2);
	}

	/**
	 * Enable this shader
	 */
	public void enable()
	{
		enabled = true;
		GL20.glUseProgram(id);
	}

	/**
	 * Disable this shader
	 */
	public void disable()
	{
		enabled = false;
		GL20.glUseProgram(id);
	}

	/**
	 * Delete this shader to free up the resources OpenGL acquired
	 */
	public void cleanup()
	{
		if (enabled)
			disable();

		GL20.glDeleteProgram(id);
	}
}
