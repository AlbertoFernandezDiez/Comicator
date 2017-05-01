package com.alberto.comicator;

import org.apache.log4j.Logger;

import com.alberto.comicator.actions.data.ConvertToPDFAction;
import com.alberto.comicator.actions.data.DataLoadAndPreparationAction;
import com.alberto.comicator.actions.init.InitializeAction;
import com.alberto.comicator.exceptions.FatalException;
import com.alberto.comicator.exceptions.PropertiesException;
import com.alberto.comicator.interfaces.Action;
import com.alberto.comicator.util.PropertiesUtil;

public class Main {

	private static final Logger LOGGER = Logger.getLogger(PropertiesUtil.class);

	public static void main(String[] args) {
		try {
			Action action = new InitializeAction();
			action.run();

			if (!action.isOk()) {
				throw new FatalException(FatalException.MESSAGE_FATAL_ERROR);
			}
				
			PropertiesUtil propsUtil = null;
			try {
				propsUtil = PropertiesUtil.getInstance();
			} catch (PropertiesException e) {
				LOGGER.error("Unable to load properties file", e);
				throw new FatalException(FatalException.MESSAGE_FATAL_ERROR, e);
			}
			
			
			DataLoadAndPreparationAction dataLoadAction = new DataLoadAndPreparationAction();
			
			dataLoadAction.setAutoSplitDisabled(propsUtil.isAutoSplitDisabled());
			dataLoadAction.setCollection(propsUtil.isCollection());
			dataLoadAction.setToGrayScale(propsUtil.isToGrayScale());
			dataLoadAction.setRightToLeft(propsUtil.isRightToLeft());
			dataLoadAction.setMarginBottom(propsUtil.getMarginBottom());
			dataLoadAction.setMarginTop(propsUtil.getMarginTop());
			dataLoadAction.setMarginLeft(propsUtil.getMarginLeft());
			dataLoadAction.setMarginRight(propsUtil.getMarginRight());
			
			dataLoadAction.run();
			
			ConvertToPDFAction convertAction = new ConvertToPDFAction();
			convertAction.setCollection(propsUtil.isCollection());
			convertAction.run();
			
			if (convertAction.isOk()) {
				LOGGER.info("Execution finished succesfully");
			}
			
		} catch (FatalException e) {
			LOGGER.error(e);
		}
	}
}
