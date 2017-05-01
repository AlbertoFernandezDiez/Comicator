package com.alberto.comicator.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.alberto.comicator.actions.init.InitializeAction;
import com.alberto.comicator.exceptions.PropertiesException;

public class PropertiesUtil {

	private Properties prop = null;

	private static final String PROPERTIES_FILE_NAME = "comicator.properties";

	private static PropertiesUtil THIS = null;

	// Properties
	private static final String KEY_IS_COLLECTION = "is-collection";
	private static final String KEY_IS_RIGHT_TO_LEFT = "is-right-to-left";
	private static final String KEY_AUTO_SPLIT_DISABLED = "auto-split-disabled";
	private static final String KEY_QUIT_MARGIN_TOP = "quit-margin-top";
	private static final String KEY_QUIT_MARGIN_LEFT = "quit-margin-left";
	private static final String KEY_QUIT_MARGIN_RIGHT = "quit-margin-right";
	private static final String KEY_QUIT_MARGIN_BOTTOM = "quit-margin-bottom";
	private static final String KEY_TO_GRAY_SCALE = "to-gray-scale";

	private PropertiesUtil() throws PropertiesException {
		try {
			InputStream is = new FileInputStream(PROPERTIES_FILE_NAME);

			prop = new Properties();

			prop.load(is);
		} catch (IOException e) {
			throw new PropertiesException("Unable to load the properties file", e);
		}
	}

	public static PropertiesUtil getInstance() throws PropertiesException {
		if (THIS == null) {
			THIS = new PropertiesUtil();
		}

		return THIS;
	}

	private String getValue(String key) {

		return prop.getProperty(key);

	}

	public boolean isCollection() {
		String value = getValue(KEY_IS_COLLECTION);

		return Boolean.valueOf(value);
	}

	public boolean isRightToLeft() {
		String value = getValue(KEY_IS_RIGHT_TO_LEFT);

		return Boolean.valueOf(value);
	}

	public boolean isAutoSplitDisabled() {
		String value = getValue(KEY_AUTO_SPLIT_DISABLED);

		return Boolean.valueOf(value);
	}

	public boolean isToGrayScale() {
		String value = getValue(KEY_TO_GRAY_SCALE);

		return Boolean.valueOf(value);
	}

	public int getMarginTop() {
		String value = getValue(KEY_QUIT_MARGIN_TOP);

		return Integer.valueOf(value);
	}

	public int getMarginLeft() {
		String value = getValue(KEY_QUIT_MARGIN_LEFT);

		return Integer.valueOf(value);
	}

	public int getMarginRight() {
		String value = getValue(KEY_QUIT_MARGIN_RIGHT);

		return Integer.valueOf(value);
	}

	public int getMarginBottom() {
		String value = getValue(KEY_QUIT_MARGIN_BOTTOM);

		return Integer.valueOf(value);
	}
}
