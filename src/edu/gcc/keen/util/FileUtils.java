package edu.gcc.keen.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils
{
	private FileUtils()
	{
		// Private constructor
	}

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
			e.printStackTrace();
		}

		return stringBuilder.toString();
	}
}