package edu.gcc.keen.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * This class provides utility functions for file IO
 * 
 * @author DONMOYERLR17
 *
 */
public class FileUtils
{
	private static final Logger LOGGER = Logger.getLogger("Logger");

	private FileUtils()
	{
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Take in a filename and return that file as a string
	 * 
	 * @param file
	 * @return
	 */
	public static String loadAsString(String file)
	{
		StringBuilder stringBuilder = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(new FileReader(file));)
		{
			String buffer = "";
			while ((buffer = reader.readLine()) != null)
			{
				stringBuilder.append(buffer).append("\n");
			}
		}
		catch (IOException e)
		{
			LOGGER.log(java.util.logging.Level.SEVERE, e.getMessage());
		}

		return stringBuilder.toString();
	}
}
