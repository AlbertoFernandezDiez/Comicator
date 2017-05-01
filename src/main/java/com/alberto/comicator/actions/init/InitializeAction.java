package com.alberto.comicator.actions.init;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.alberto.comicator.actions.AbstractAction;
import com.alberto.comicator.constants.ActionConstants;

public class InitializeAction extends AbstractAction {

	private static final Logger LOGGER = Logger.getLogger(InitializeAction.class);

	public InitializeAction() {
	}

	@Override
	public void run() {
		LOGGER.info("Checking wether required folder structure exists");

		File properties = new File(ActionConstants.FILE_PROPERTIES);
		File folderConverted = new File(ActionConstants.FOLDER_CONVERTED);
		File folderInput = new File(ActionConstants.FOLDER_RAW_FILES);
		File folderTemp = new File(ActionConstants.FOLDER_TEMP_FILES);

		if (!properties.exists()) {
			LOGGER.warn(ActionConstants.FILE_PROPERTIES + " file does not exist, trying to create it");

			this.ok = false;

			File originalProperties = new File(
					InitializeAction.class.getClassLoader().getResource(ActionConstants.FILE_PROPERTIES).getFile());

			if (!originalProperties.exists()) {
				try {
					FileUtils.copyFile(originalProperties, properties);
					this.ok = true;
				} catch (IOException e) {
					LOGGER.error("Unable to create " + ActionConstants.FILE_PROPERTIES, e);
				}
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
