/**
 * 
 */
package gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beans.Country;
import beans.Observable;
import utilities.Tuple;

/**
 * The user interface of the application. 
 * @author vanduong
 *
 */
public class UI implements Observer{

	/**
	 * @return a tuple containing: country to move armies from, country to move armies to, and number of armies
	 */
	public static Tuple getFortificationInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return true of user is ready for next phase
	 */
	public static boolean readyForNextPhase() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * display error messages
	 * @param message
	 */
	public static void handleExceptions(String message) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see gui.Observer#update(beans.Observable)
	 */
	@Override
	public void update(Observable sub) {
		//TODO display number of countries occupied
		//display number of continents occupied
		
		
	}

	/**
	 *Ask if user wants to go to war
	 * @return true if user wants to go to war
	 */
	public static boolean isWar() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Ask user to select number of armies to be distributed for each country
	 * @param list List of countries owned by current player
	 * @param i number of armies owned by this player
	 * @return list of countries and their assigned armies
	 */
	public static Map<Country, Integer> distributeArmies(List<Country> list, int numArmies) {
		while(numArmies > 0)
			for(Country c : list) {
				//TODO ask user to input number of army for each country in the list
			}
		return null;
	}


}