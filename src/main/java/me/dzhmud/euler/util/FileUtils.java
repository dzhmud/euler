package me.dzhmud.euler.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Utility methods for reading data from text files.
 *
 * @author dzhmud
 */
public final class FileUtils {

	private FileUtils() {}

	public static String getContents(String fileName) {
		return getContents(fileName, Collectors.joining("\n"));
	}

	public static <T> T getContents(String fileName, Collector<? super String, ?, T> collector) {
		try {
			Path path = Paths.get(FileUtils.class.getClassLoader().getResource(fileName).toURI());
			return Files.lines(path).collect(collector);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
