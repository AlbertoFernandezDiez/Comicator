package com.alberto.comicator.actions.data;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.log4j.Logger;

import com.alberto.comicator.actions.AbstractAction;
import com.alberto.comicator.constants.ActionConstants;
import com.alberto.exceptions.ImageManagerException;
import com.alberto.manipulator.ImageManipulator;

public class DataLoadAndPreparationAction extends AbstractAction {

	Logger LOGGER = Logger.getLogger(DataLoadAndPreparationAction.class);

	private static final String IMAGE_NAME_FORMAT = "image%04d";
	private static final String[] VALID_MIMETYPES = { "jpg", "png" };

	private boolean collection = false;
	private boolean rightToLeft = false;
	private boolean autoSplitDisabled = false;
	private boolean toGrayScale = false;
	private int marginBottom = 0;
	private int marginTop = 0;
	private int marginLeft = 0;
	private int marginRight = 0;

	@Override
	public void run() {

		try {
			String fileExtension = null;
			File tempFolder = new File(ActionConstants.FOLDER_TEMP_FILES);
			File rawFolder = new File(ActionConstants.FOLDER_RAW_FILES);

			File destinationFolder = null;

			ImageManipulator manipulator = ImageManipulator.getMyManipulator();

			FileUtils.cleanDirectory(tempFolder);

			List<File> files = null;

			if (isCollection()) {
				files = Arrays.asList(rawFolder.listFiles());
				for (File dir : files) {
					if (dir.isDirectory()) {
						destinationFolder = new File(tempFolder, dir.getName());
						destinationFolder.mkdir();
						loadImages(manipulator,
								new LinkedList<File>(
										FileUtils.listFiles(dir, TrueFileFilter.TRUE, TrueFileFilter.INSTANCE)),
								destinationFolder);
					}
				}

			} else {
				files = Arrays.asList(rawFolder.listFiles());
				if (files.size() == 1 && files.get(0).isDirectory()) {
					File parentFolder = files.get(0);
					destinationFolder = new File(tempFolder, parentFolder.getName());
					destinationFolder.mkdir();
					files = new LinkedList<File>(
							FileUtils.listFiles(parentFolder, TrueFileFilter.TRUE, TrueFileFilter.INSTANCE));
					loadImages(manipulator, files, destinationFolder);
				} else {
					this.ok = false;
				}
			}

		} catch (Exception e) {
			this.ok = false;
			LOGGER.error("An error ocurred", e);
		}

	}

	private void loadImages(ImageManipulator manipulator, List<File> files, File destinationFolder)
			throws ImageManagerException {
		BufferedImage originalImage = null;
		int currentIndex = 0;
		for (File file : files) {
			LOGGER.debug("FILE:\t" + file.getAbsolutePath());

			currentIndex = processImage(manipulator, currentIndex, file, destinationFolder);

		}
	}

	private int processImage(ImageManipulator manipulator, int currentIndex, File file, File destinationFolder)
			throws ImageManagerException {
		String fileExtension;
		BufferedImage originalImage;
		if (FilenameUtils.isExtension(file.getName(), VALID_MIMETYPES)) {

			originalImage = manipulator.openImageFromFile(file);

			fileExtension = "." + FilenameUtils.getExtension(file.getName());

			if (isToGrayScale()) {
				if (!manipulator.toGrayScale(originalImage)) {
					this.ok = false;
				}
			}

			originalImage = manipulator.quitBorder(originalImage, getMarginTop(), getMarginBottom(), getMarginLeft(),
					getMarginRight());

			if (!isAutoSplitDisabled()) {

				if (originalImage.getWidth() > originalImage.getHeight()) {

					BufferedImage[] splittedImages = manipulator.splitImage(originalImage, false);

					if (isRightToLeft()) {
						manipulator.saveImage(splittedImages[1], new File(destinationFolder,
								String.format(IMAGE_NAME_FORMAT, ++currentIndex) + fileExtension));
						manipulator.saveImage(splittedImages[0], new File(destinationFolder,
								String.format(IMAGE_NAME_FORMAT, ++currentIndex) + fileExtension));
					} else {
						manipulator.saveImage(splittedImages[0], new File(destinationFolder,
								String.format(IMAGE_NAME_FORMAT, ++currentIndex) + fileExtension));
						manipulator.saveImage(splittedImages[1], new File(destinationFolder,
								String.format(IMAGE_NAME_FORMAT, ++currentIndex) + fileExtension));
					}
				} else {
					manipulator.saveImage(originalImage, new File(destinationFolder,
							String.format(IMAGE_NAME_FORMAT, ++currentIndex) + fileExtension));
				}

			} else {

			}
		}
		return currentIndex;
	}

	public boolean isCollection() {
		return collection;
	}

	public void setCollection(boolean collection) {
		this.collection = collection;
	}

	public boolean isRightToLeft() {
		return rightToLeft;
	}

	public void setRightToLeft(boolean rightToLeft) {
		this.rightToLeft = rightToLeft;
	}

	public boolean isAutoSplitDisabled() {
		return autoSplitDisabled;
	}

	public void setAutoSplitDisabled(boolean autoSplitDisabled) {
		this.autoSplitDisabled = autoSplitDisabled;
	}

	public boolean isToGrayScale() {
		return toGrayScale;
	}

	public void setToGrayScale(boolean toGrayScale) {
		this.toGrayScale = toGrayScale;
	}

	public int getMarginBottom() {
		return marginBottom;
	}

	public void setMarginBottom(int marginBottom) {
		this.marginBottom = marginBottom;
	}

	public int getMarginTop() {
		return marginTop;
	}

	public void setMarginTop(int marginTop) {
		this.marginTop = marginTop;
	}

	public int getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(int marginLeft) {
		this.marginLeft = marginLeft;
	}

	public int getMarginRight() {
		return marginRight;
	}

	public void setMarginRight(int marginRight) {
		this.marginRight = marginRight;
	}

}
