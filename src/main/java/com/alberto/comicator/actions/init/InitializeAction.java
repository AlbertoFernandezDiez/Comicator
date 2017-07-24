package com.alberto.comicator.actions.init;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.alberto.comicator.actions.AbstractAction;
import com.alberto.comicator.constants.ActionConstants;
import com.alberto.comicator.exceptions.FatalException;
import com.alberto.comicator.exceptions.PropertiesException;

public class InitializeAction extends AbstractAction {

	private static final Logger LOGGER = Logger.getLogger(InitializeAction.class);

	public InitializeAction() {
	}

	@Override
	public void run() throws FatalException {
		LOGGER.info("Checking wether required folder structure exists");

		File properties = new File(ActionConstants.FILE_PROPERTIES);
		File folderConverted = new File(ActionConstants.FOLDER_CONVERTED);
		File folderInput = new File(ActionConstants.FOLDER_RAW_FILES);
		File folderTemp = new File(ActionConstants.FOLDER_TEMP_FILES);

		LOGGER.debug("Properties file path: \t" + properties.getAbsolutePath());
		
		if (!properties.exists()) {
			LOGGER.warn(ActionConstants.FILE_PROPERTIES + " file does not exist, trying to create it");
			this.ok = false;

			InputStream originalProperties = InitializeAction.class.getClassLoader().getResourceAsStream(ActionConstants.FILE_PROPERTIES);

					
			try {
				LOGGER.info("Trying to create the default options file");
				Files.copy(originalProperties, properties.toPath(),StandardCopyOption.REPLACE_EXISTING);
				LOGGER.info("File successfully created");
			} catch (IOException e) {
				throw new FatalException("Unable to create the default properties file", e);
				
			}
			

	
		} else {
			LOGGER.info("Using existing properties file:\t" + ActionConstants.FILE_PROPERTIES);

		}

		if (!folderConverted.exists()) {
			LOGGER.warn(ActionConstants.FOLDER_CONVERTED + " folder does not exist, trying to create it");
			folderConverted.mkdirs();
		} else {
			LOGGER.info("Using existing folder:\t" + ActionConstants.FOLDER_CONVERTED);
		}

		if (!folderInput.exists()) {
			LOGGER.warn(ActionConstants.FOLDER_RAW_FILES + " folder does not exist, trying to create it");
			folderInput.mkdirs();
		} else {
			LOGGER.info("Using existing folder:\t" + ActionConstants.FOLDER_RAW_FILES);
		}

		if (!folderTemp.exists()) {
			LOGGER.warn(ActionConstants.FOLDER_TEMP_FILES + " folder does not exist, trying to create it");
			folderTemp.mkdirs();
		} else {
			LOGGER.info("Using existing folder:\t" + ActionConstants.FOLDER_TEMP_FILES);
		}

	}

}
