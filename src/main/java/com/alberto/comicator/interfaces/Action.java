package com.alberto.comicator.interfaces;

import com.alberto.comicator.exceptions.FatalException;

public interface Action {

	/**
	 * The code to be executed
	 * @throws FatalException 
	 */
	public void run() throws FatalException;

	/**
	 * Gives feedback of the execution result of {@link #run()}
	 * 
	 * @return
	 *         <ul>
	 *         <li><b>true:</b> execution successful</li>
	 *         <li><b>false:</b> error while executing</li>
	 *         </ul>
	 */
	public boolean isOk();
}
