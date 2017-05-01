package com.alberto.comicator.actions.data;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.LinkedList;

import com.alberto.comicator.actions.AbstractAction;
import com.alberto.comicator.constants.ActionConstants;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class ConvertToPDFAction extends AbstractAction {

	private boolean collection = false;
	private static final String PDF_NAME_FORMAT = "%s.pdf";
	
	@Override
	public void run() {
	try {
		File tempDir = new File(ActionConstants.FOLDER_TEMP_FILES);
		File outputDir = new File(ActionConstants.FOLDER_CONVERTED);
		
		if (isCollection()) {
			
		} else {
			LinkedList<Image> pages = new LinkedList<Image>();
			File[] files = tempDir.listFiles();
			Image itextImage = null;
			for (File file : files) {
				itextImage = Image.getInstance(file.toURI().toURL());
				pages.addLast(itextImage);
			}
			
			Document document = new Document();
			//The pdf file is created in the
			//same location of the initial 
			//directory
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(new File(outputDir, String.format(PDF_NAME_FORMAT, "Prueba"))));
			Iterator<Image> it = pages.iterator();
			Image img = null;

			document.open();

			document.addTitle("Prueba");

			while (it.hasNext())
			{
				img = it.next();

				document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
				img.setAbsolutePosition(0, 0);
				//img.setAlignment(Chunk.ANCHOR);
				document.newPage();
				document.add(img);
			}
			it.remove();
			document.close();
			img = null;
			writer.close();
			pages.clear();
			
		}
		
	
		
		

	}catch (Exception e) {
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
