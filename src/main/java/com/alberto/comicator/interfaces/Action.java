package com.alberto.comicator.interfaces;

public interface Action {

	/**
	 * The code to be executed
	 */
	public void run();

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
