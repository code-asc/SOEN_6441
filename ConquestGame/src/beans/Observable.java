/**
 * 
 */
package beans;

import gui.Observer;

// TODO: Auto-generated Javadoc
/**
 * interface for observable subjects to implements.
 *
 * @author vanduong
 */
public interface Observable {

	/**
	 * Attach an observer to get updates from an observable subject.
	 *
	 * @param ob the observer to be added
	 * @param event the event
	 */
	public void attach(Observer ob, int event);
	
	/**
	 * Detach an observer from subjects observer list.
	 *
	 * @param ob the observer to be removed
	 */
	public void detach(Observer ob);
	
	/**
	 * Notify changes to observers. Each observable subject has its own list of observers
	 *
	 * @param event the event
	 */
	public void notifyChanges(int event);
}
