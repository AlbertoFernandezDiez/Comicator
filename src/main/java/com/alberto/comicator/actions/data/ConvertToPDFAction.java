package com.alberto.comicator.actions.data;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.alberto.comicator.actions.AbstractAction;
import com.alberto.comicator.constants.ActionConstants;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class ConvertToPDFAction extends AbstractAction {

	Logger LOGGER = Logger.getLogger(ConvertToPDFAction.class);

	private boolean collection = false;
	private static final String PDF_NAME_FORMAT = "%s.pdf";

	@Override
	public void run() {
		try {
			File tempDir = new File(ActionConstants.FOLDER_TEMP_FILES);
			File outputDir = new File(ActionConstants.FOLDER_CONVERTED);

			LinkedList<Image> pages = new LinkedList<Image>();
			File[] directories = tempDir.listFiles();

			for (File dir : directories) {
				if (dir.isDirectory()) {

					File[] files = dir.listFiles();

					Image itextImage = null;
					for (File file : files) {
						itextImage = Image.getInstance(file.toURI().toURL());
						pages.addLast(itextImage);
					}

					Document document = new Document();
					// The pdf file is created in the
					// same location of the initial
					// directory
					PdfWriter writer = PdfWriter.getInstance(document,
							new FileOutputStream(new File(outputDir, String.format(PDF_NAME_FORMAT, dir.getName()))));
					Iterator<Image> it = pages.iterator();
					Image img = null;

					document.open();

					document.addTitle(dir.getName());

					while (it.hasNext()) {
						img = it.next();

						document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
						img.setAbsolutePosition(0, 0);
						// img.setAlignment(Chunk.ANCHOR);
						document.newPage();
						document.add(img);
					}
					it.remove();
					document.close();
					img = null;
					writer.close();
					pages.clear();
				}
			}

		} catch (Exception e) {
			this.ok = false;
		}
	}

	public boolean isCollection() {
		return collection;
	}

	public void setCollection(boolean collection) {
		this.collection = collection;
	}

}
