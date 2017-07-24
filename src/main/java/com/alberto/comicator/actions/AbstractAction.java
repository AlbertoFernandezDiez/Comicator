package com.alberto.comicator.actions;

import com.alberto.comicator.exceptions.FatalException;
import com.alberto.comicator.interfaces.Action;

public abstract class AbstractAction implements Action {

	protected boolean ok = true;
	
	protected AbstractAction(){}
	
	public abstract void run() throws FatalException;

	public boolean isOk() {
		return this.ok;
	}

}
