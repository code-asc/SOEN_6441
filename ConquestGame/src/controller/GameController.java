package controller;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import beans.Continent;
import beans.Country;
import beans.EventType;
import beans.Phase;
import beans.Player;
import config.Config;
import exception.MapInvalidException;
import gui.CardExchangeView;
import gui.Observer;
import gui.PhaseView;
import gui.UI;
//import phases.Attack;
import gui.WorldDominationView;
import strategies.AggressiveStrategy;
import strategies.BenevolentStrategy;
import strategies.CheaterStrategy;
import strategies.Human;
import strategies.RandomStrategy;
import utilities.CustomMapGenerator;
import utilities.GameInit;
import utilities.GameStat;
import utilities.MapValidator;

// TODO: Auto-generated Javadoc
/**
 * The Class GameController.
 */
/*
 * @description :
 * 
 * @author
 */
public class GameController implements Serializable{
	
	/** Unique serial ID for this class, necessary for serialization and deserialization of singleton class. */
	private static final long serialVersionUID = 1L;

	/** The Constant INPUTFILE. */
	private final static String INPUTFILE = "src/resources/World.map";
	/** The controller. */
	private static GameController controller = null;
	
	/** The attacking country. */
	private  Country attackingCountry = null;
	

	/** Keeps track if this game is a saved game. */
	private boolean isSavedGame = false;

	// For single and tournament mode

	/** Check if single mode or Tournament mode */
	private static boolean tournamentFlag = false;

	/** Game Count for Tournament Mode */
	private static int gameCount = 0;
	
	/** Turn Count for Tournament Mode */
	private static int turnCount;
	
	/** Map Input files for Tournament Mode */
	private static String mapInput = null;
	

	/** Map List generated from mapInput */
	private static String[] mapList = null;
	
	/** Strategy Input for Tournament Mode */
	private static String strategyInput = null;
	
	/** Strategy List generated from strategyInput */
	private static String[] strategyList;
	
	/** Tournament Winner Records */
	private static String[][] finalWinnerList;
	private static Scanner tScan;
	private static boolean isTest = false;
	

	public Country getAttackingCountry() {
		return attackingCountry;
	}

	/**
	 * Sets the attacking country.
	 *
	 * @param attackingCountry the new attacking country
	 */
	public void setAttackingCountry(Country attackingCountry) {
		this.attackingCountry = attackingCountry;
	}

	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Sets the controller.
	 *
	 * @param controller the new controller
	 */
	//Don't use for other purpose.

	public void setController(GameController controller) {
		GameController.controller = controller;
	}

	/** The current phase Info. */
	private Phase currentPhase;

	/** The World Domination View. */
	private static WorldDominationView wdView = null;
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Sets the world domination view.
	 *
	 * @param wd the new world domination view
	 */
	//Don't use for other purpose.
	public void setWorldDominationView(WorldDominationView wd) {
		wdView = wd;
	}
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Gets the world domination view.
	 *
	 * @return the world domination view
	 */
	//Don't use for other purpose.

	public WorldDominationView getWorldDominationView() {
		return wdView;
	}

	/** The phase view. */
	private PhaseView phaseView = null;

	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Sets the phase view.
	 *
	 * @param pv the new phase view
	 */
	//Don't use for other purpose.
	public void setPhaseView(PhaseView pv) {
		phaseView = pv;
	}
		
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Gets the phase view.
	 *
	 * @return the phase view
	 */
	//Don't use for other purpose.
	public PhaseView getPhaseView() {
		return phaseView;
	}
		
		
	/** The scan. */
	transient static Scanner scan = new Scanner(System.in);
	
	/** The card view. */
	private static CardExchangeView cardView= null;
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Sets the card exchange view.
	 *
	 * @param cv the new card exchange view
	 */
	//Don't use for other purpose.
	public void setCardExchangeView(CardExchangeView cv) {
		cardView = cv;
	}
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Gets the card exchange view.
	 *
	 * @return the card exchange view
	 */
	//Don't use for other purpose.
	public CardExchangeView getCardExchangeView() {
		return cardView;
	}

	/** The country list. */
	public static List<Country> countryList = new ArrayList<Country>();
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Sets the country list.
	 *
	 * @param cl the new country list
	 */
	//Don't use for other purpose.
	public void setCountryList(List<Country> cl) {
		countryList = cl;
	}
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Gets the country list.
	 *
	 * @return the country list
	 */
	//Don't use for other purpose.
	public List<Country> getCountryList() {
		return countryList;
	}

	/** The number of players. */
//	HashMap<Player,WorldMap> countryOwnership = new HashMap<Player,WorldMap>();
	private int numberOfPlayers;
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Sets the number of players.
	 *
	 * @param n the new number of players
	 */
	//Don't use for other purpose.
	public void setNumberOfPlayers(int n) {
		numberOfPlayers = n;
	}
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Gets the number of players.
	 *
	 * @return the number of players
	 */
	//Don't use for other purpose.

	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	/** The continent list by name. */
	private HashMap<String, Continent> continentListByName = null;
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Sets the continent list by name.
	 *
	 * @param clbm the clbm
	 */
	//Don't use for other purpose.
	public void setContinentListByName(HashMap<String, Continent> clbm) {
		continentListByName = clbm;
	}
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Gets the continent list by name.
	 *
	 * @return the continent list by name
	 */
	//Don't use for other purpose.

	public HashMap<String, Continent> getContinentListByName() {
		return continentListByName;
	}

	/** The country ownership. */
	Map<Player, ArrayList<Country>> countryOwnership = null;
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Sets the country ownership.
	 *
	 * @param co the co
	 */
	//Don't use for other purpose.
	public void setCountryOwnership(Map<Player, ArrayList<Country>> co) {
		countryOwnership = co;
	}
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Gets the country ownership.
	 *
	 * @return the country ownership
	 */
	//Don't use for other purpose.

	public Map<Player, ArrayList<Country>> getCountryOwnership() {
		return countryOwnership;
	}

//	/** The current phase. */
//	TurnPhase currentPhase = null;

	/** The ready for next phase. */
	private boolean readyForNextPhase = false;
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Sets the ready for next phase.
	 *
	 * @param rfnp the new ready for next phase
	 */
	//Don't use for other purpose.
	public void setReadyForNextPhase(boolean rfnp) {
		readyForNextPhase = rfnp;
	}
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Gets the ready for next phase.
	 *
	 * @return the ready for next phase
	 */
	//Don't use for other purpose.

	public boolean getReadyForNextPhase() {
		return readyForNextPhase;
	}

	/** The current player. */
	private Player currentPlayer;

	/** The player list. */
	private ArrayList<Player> playerList;
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Sets the player list.
	 *
	 * @param pl the new player list
	 */
	//Don't use for other purpose.

	public void setPlayerList(ArrayList<Player> pl) {
		playerList = pl;
	}

	/** The Constant MIN_ARGS. */
	private final static int MIN_ARGS = 1;

	/** The winner. */
	private Player winner = null;
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Sets the winner.
	 *
	 * @param win the new winner
	 */
	//Don't use for other purpose.

	public void setWinner(Player win) {
		winner = win;
	}

	/** The ui. */
	private static UI ui = null;
	
	/** The phase count. */
	private int phaseCount=0;
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	//Don't use for other purpose.
	public int getPhaseCount() {
		return phaseCount;
	}
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Sets the ui.
	 *
	 * @param ui the new ui
	 */
	//Don't use for other purpose.
	public void setPhaseCount(int phaseCount) {
		this.phaseCount = phaseCount;
	}

	//TODO change to private and use reflect.
	//It should be only used for game saving.
	//Don't use for other purpose.
	public void setUI(UI ui) {
		this.ui = ui;
	}
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Gets the ui.
	 *
	 * @return the ui
	 */
	//Don't use for other purpose.
	public UI getUI() {
		return ui;
	}

	/** The custom map. */
	private CustomMapGenerator customMap=null;
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Sets the custom map cenerator.
	 *
	 * @param map the new custom map cenerator
	 */
	//Don't use for other purpose.
	public void setCustomMapCenerator(CustomMapGenerator map) {
		customMap = map;
	}
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Gets the custom map generator.
	 *
	 * @return the custom map generator
	 */
	//Don't use for other purpose.

	public CustomMapGenerator getCustomMapGenerator() {
		return customMap;
	}

	/** The continent list. */
	private List<Continent> continentList = null;
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Sets the continent list.
	 *
	 * @param cl the new continent list
	 */
	//Don't use for other purpose.
	public void setContinentList(List<Continent> cl) {
		continentList = cl;
	}
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Gets the continet list.
	 *
	 * @return the continet list
	 */
	//Don't use for other purpose.
	public List<Continent> getContinetList() {
		return continentList;
	}
	
	/**  Used for game saving and loading. */
	private static GameStat gameStat = null;
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Sets the game stat.
	 *
	 * @param game the new game stat
	 */
	//Don't use for other purpose.
	public void setGameStat(GameStat game) {
		gameStat = game;
	}
	
	//TODO change to private and use reflect.
	//It should be only used for game saving.
	/**
	 * Gets the game stat.
	 *
	 * @return the game stat
	 */
	//Don't use for other purpose.
	public GameStat getGameStat() {
		return gameStat;
	}

	/**
	 * Gets the current phase.
	 *
	 * @return String, the current phase of the game.
	 */
	public Phase getCurrentPhase() {
		return currentPhase;
	}

	/**
	 * Sets the current phase.
	 *
	 * @param currentPhase The current phase name
	 */
	public void setCurrentPhase(Phase currentPhase) {
		this.currentPhase = currentPhase;
	}

	/**
	 * Instantiates a new game controller.
	 */
	private GameController() {
		countryOwnership = new HashMap();
		playerList = new ArrayList<Player>();
		finalWinnerList = new String[10][10];
		mapList = new String[5];
		customMap = CustomMapGenerator.getInstance();

	}

	/**
	 * Gets the single instance of GameController.
	 *
	 * @return single instance of GameController
	 */
	public static GameController getInstance() {
		if (controller == null) {
			synchronized (GameController.class) {
				if (controller == null) {
					controller = new GameController();
				}
			}
		}
		return controller;
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException         Signals that an I/O exception has occurred.
	 * @throws MapInvalidException the map invalid exception
	 * @throws ClassNotFoundException the class not found exception

	 */
	/*
	 * @description :
	 * 
	 * @author
	 */
	public static void main(String[] args) throws IOException, MapInvalidException, ClassNotFoundException {
		String loadStatFromFile = Config.getProperty("loadcontroller");
		File f = new File(loadStatFromFile);
		if(f.exists() && !f.isDirectory()) { 
			System.out.println("Do you want to load a game?(Y/N)");
			Scanner in = new Scanner(System.in);
			String input = in.next().toLowerCase();
			if(input.equals("y") || input.equals("yes")){
				gameStat = GameStat.getInstance();
				//GameController controllerObj = gameStat.load();
				GameInit initObj = gameStat.load();
				controller = initObj.gc;
				controller.loadStat(controller);
				controller.createWorldDominationView();
				controller.createCardExchangeView();
				ui = new UI();
				controller.registerObserver(ui, EventType.PHASE_NOTIFY);
				controller.registerObserver(wdView, EventType.FORTIFICATION_NOTIFY);
				controller.registerObserver(cardView, EventType.CARDS_EXCHANGE_NOTIFY);
				controller.takeTurns();
			}
		}
		//start new game
		GameController controller = GameController.getInstance();
		System.out.println("Do you want to play in single mode or in tournament mode ? S/T");
		char mode = scan.next().charAt(0);
		if (mode == 'S' || mode == 's') {
			controller.modeSingle();
		}
		if (mode == 'T' || mode == 't') {
			tScan = new Scanner(System.in);
			tournamentFlag = true;
			controller.modeTournament();
		}
		
		
		
	}

	/**
	 * Register given observer with all player.
	 *
	 * @param ob    the ob
	 * @param event the event
	 */
	public void registerObserver(Observer ob, int event) {

		for (int i = 0; i < numberOfPlayers; i++) {
			GameController con = GameController.getInstance();

			controller.playerList.get(i).attach(ob, event);
		}
	}

	/**
	 * place one army on each and every country occupied by players.
	 */
	private void placeInitialArmies() {
		for (int i = 0; i < controller.playerList.size(); i++) {
			Player player = controller.getPlayer(i);
			for (int j = 0; j < player.getPlayerCountries().size(); j++) {
				Country c = player.getPlayerCountries().get(j);
				c.setNumArmies(1);
				c.setOwner(player);
				player.setNumArmiesDispatched(j + 1);
			}
		}

	}

	/**
	 * This method is responsible for setting up the strategy for individual Player 
	 * based on user input.

	 */
	private void setupStrategy() {
		Scanner selectStrategy = new Scanner(System.in);
		if (tournamentFlag == false) {
			System.out.println("1. Aggressive 2. Human 3. Benevolent 4. Cheater 5. Random");
			for (int i = 0; i < controller.playerList.size(); i++) {
				Player player = playerList.get(i);
				currentPlayer = player;
				System.out.println("Assign a strategy for" + " " + currentPlayer.getPlayerName() + " " + ":");
				String choice = selectStrategy.nextLine();

				// Select strategies
				if (choice.equalsIgnoreCase("Aggressive")) {
					player.setStrategyType("Aggressive");
					player.setStrategy(new AggressiveStrategy(currentPlayer));
				} else if (choice.equalsIgnoreCase("Human")) {
					player.setStrategyType("Human");
					player.setStrategy(new Human(currentPlayer));
				} else if (choice.equalsIgnoreCase("Benevolent")) {
					player.setStrategyType("Benevolent");
					player.setStrategy(new BenevolentStrategy(currentPlayer));
				} else if (choice.equalsIgnoreCase("Cheater")) {
					player.setStrategyType("Cheater");
					player.setStrategy(new CheaterStrategy(currentPlayer));
				} else if (choice.equalsIgnoreCase("Random")) {
					player.setStrategyType("Random");
					player.setStrategy(new RandomStrategy(currentPlayer));
				} else {
					System.out.println("Invalid input. Select a valid strategy");
				}
				// player.setStrategy(new BenevolentStrategy(currentPlayer));
			}
		} else {
			System.out.println("Setting up the strategies for players...");
			for (int i = 0; i < strategyList.length; i++) {
				Player player = playerList.get(i);
				currentPlayer = player;
					if (strategyList[i].equalsIgnoreCase("Aggressive")) {
						player.setStrategyType("Aggressive");
						player.setStrategy(new AggressiveStrategy(currentPlayer));
					} else if (strategyList[i].equalsIgnoreCase("Benevolent")) {
						player.setStrategyType("Benevolent");
						player.setStrategy(new BenevolentStrategy(currentPlayer));
					} else if (strategyList[i].equalsIgnoreCase("Cheater")) {
						player.setStrategyType("Cheater");
						player.setStrategy(new CheaterStrategy(currentPlayer));
					} else if (strategyList[i].equalsIgnoreCase("Random")) {
						player.setStrategyType("Random");
						player.setStrategy(new RandomStrategy(currentPlayer));
					}
					
					//System.out.println("player"+i +"    "+player.getStrategyType());
			}
		}
	}
		
	

	/**
	 * player places their armies on their territories in setup phase.
	 */

	private void placeArmiesForSetup() {
		// each player take turns to place their armies
		if (tournamentFlag == false) {
			for (int i = 0; i < controller.playerList.size(); i++) {
				Player player = playerList.get(i);
				currentPlayer = player;
				// player.setStrategy(new AggressiveStrategy(currentPlayer));
				// player.setStrategy(new BenevolentStrategy(currentPlayer));
				player.getStrategy().placeArmiesForSetup();
			}
		} else {
			int nOPForTournament = strategyList.length;
			for (int i = 0; i < nOPForTournament; i++) {
				Player player = playerList.get(i);
				currentPlayer = player;
				// player.setStrategyType("Benevolent");
				// player.setStrategy(new AggressiveStrategy(currentPlayer));
				// player.setStrategy(new BenevolentStrategy(currentPlayer));
				player.getStrategy().placeArmiesForSetup();
			}
		}
	}

	/**
	 * create and register a WorldDomination interface as observer to player.
	 */
	public void createWorldDominationView() {
		wdView = WorldDominationView.getInstance();
	}

	/**
	 * create and register a CardExchange interface as observer to player.
	 */

	public void createCardExchangeView() {
		cardView = new CardExchangeView();
	}

	/**
	 * create and register a PhaseView interface as observer to player.
	 *
	 * @return the player list
	 */
//	public void createPhaseView() {
//		PhaseView phaseView = new PhaseView();
//	}

	/**
	 * Gets the player list.
	 *
	 * @return the list of players
	 */
	public ArrayList<Player> getPlayerList() {
		return this.playerList;
	}

	/**
	 * Show help.
	 */
	public void showHelp() {
		// TODO
	}

	/**
	 * Adds the player.
	 *
	 * @param player the player
	 */
	public void addPlayer(Player player) {
		playerList.add(player);
		numberOfPlayers++;
	}

	/**
	 * Gets the num players.
	 *
	 * @return the num players
	 */
	public int getNumPlayers() {
		return numberOfPlayers;
	}

	/**
	 * Gets the player.
	 *
	 * @param idx the idx
	 * @return the player
	 */
	public Player getPlayer(int idx) {
		return playerList.get(idx);
	}

	/**
	 * Gets the winner.
	 *
	 * @return the winner
	 */
	public Player getWinner() {
		return winner;
	}

	/**
	 * Load map.
	 *
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws MapInvalidException the map invalid exception

	 */
	/*
	 * @description :
	 * 
	 * @author
	 */
	public void loadMap() throws ClassNotFoundException, IOException, MapInvalidException {
		customMap = CustomMapGenerator.getInstance();
		System.out.println("----------Welcome----------");
		System.out.println(
				"Please select the following options.\n1)Load exisiting map\n2)Create map\n3)Edit existing map\n4)Load game\n");
		Scanner mapOption = new Scanner(System.in);
		int selectedOption = mapOption.nextInt();
		if (selectedOption == 1) {
			try {
				customMap.LoadMap();
			} catch (IOException | MapInvalidException e) {
				ui.handleExceptions(e.getMessage());
				System.exit(1);
			}
		} else if (selectedOption == 2) {

			try {
				customMap.createCustomMap();
			} catch (IOException | MapInvalidException e) {
				ui.handleExceptions(e.getMessage());
				System.exit(1);
			}
		} else if (selectedOption == 3) {
			try {
				customMap.editExistingMap();
			} catch (IOException | MapInvalidException e) {
				ui.handleExceptions(e.getMessage());
				System.exit(1);
			}
		} else if (selectedOption == 4) {
			//try {
				


//			} catch (ClassNotFoundException e) {
//				
//				System.out.println("Problem while loading game");
//				//System.out.println(e.getMessage());
//				//System.exit(1);
//				//e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				System.out.println("Problem while loading game");
//				//System.out.println(e.getMessage());
//				//System.exit(1);
//				//e.printStackTrace();
//			} catch (MapInvalidException e) {
//				// TODO Auto-generated catch block
//				System.out.println("Problem while loading game");
//				//System.out.println(e.getMessage());
//				//System.exit(1);
//				//e.printStackTrace();
//			}
//			
		} else {
			System.out.println("Enter appropiate choice");
			System.exit(1);
		}
		countryList.addAll(customMap.countryDefault);
	}


/**
 * Load stat.
 *
 * @param controllerObj the controller obj
 */
	private void loadStat(GameController controllerObj) {
		this.setSavedGame(true);
		this.setController(controllerObj);
		this.setCustomMapCenerator(controllerObj.getCustomMapGenerator());
		this.setPlayerList(controllerObj.getPlayerList());
		this.setCurrentPlayer(controllerObj.getCurrentPlayer());
		//if saved phase is at the end of fortification, next player gets to play
		if(controllerObj.getCurrentPhase() == Phase.FORTIFICATION) {
			List<Player> playerList = this.getPlayerList();
			int nextPlayer = (playerList.indexOf(this.getCurrentPlayer()) + 1) % playerList.size();
			this.setCurrentPlayer(playerList.get(nextPlayer));
		}
		this.setCurrentPhase(Phase.getPhase(controllerObj.getCurrentPhase().getValue() + 1));
		
		this.setPhaseView(controllerObj.getPhaseView());
		this.setCountryList(controllerObj.getCountryList());
		this.setNumberOfPlayers(controllerObj.getNumberOfPlayers());
		this.setContinentListByName(controllerObj.getContinentListByName());
		this.setCountryOwnership(controllerObj.getCountryOwnership());
		this.setReadyForNextPhase(controllerObj.getReadyForNextPhase());
		this.setWinner(controllerObj.getWinner());
		this.setContinentList(controllerObj.getContinetList());
		this.setGameStat(controllerObj.getGameStat());
		

		CustomMapGenerator customMapObj = controllerObj.getCustomMapGenerator();
		customMap = customMapObj;
		customMap.setCustomMap(customMapObj.getCustomMap());
		customMap.setContinents(customMapObj.getContinents());
		customMap.setCountries(customMapObj.getCountries());
		customMap.setRemoveContinents(customMapObj.getRemoveContinents());
		customMap.setRemoveCountries(customMapObj.getRemoveCountries());
		customMap.setRemoveAdjacentCountries(customMapObj.getRemoveAdjacentCountries());
		customMap.setAdjMap(customMapObj.getAdjMap());
		customMap.setCountryDefault(customMapObj.getCountryDefault());
		customMap.setContinentmap(customMapObj.getContinentmap());
		customMap.setCountryMap(customMapObj.getCountryMap());
		customMap.setAdjCountryMap(customMapObj.getAdjCountryMap());
		customMap.setEditMap(customMapObj.getEditMap());
		customMap.setMapController(customMapObj.getMapController());
		controller = this;
		controller = GameController.getInstance();
		
		for(Player player : playerList) {
			if(player.getStrategy() instanceof Human) {
				player.setStrategy(new Human(player));
			} else if (player.getStrategy() instanceof AggressiveStrategy) {
				player.setStrategy(new AggressiveStrategy(player));
			} else if (player.getStrategy() instanceof BenevolentStrategy) {
				player.setStrategy(new BenevolentStrategy(player));
			} else if(player.getStrategy() instanceof CheaterStrategy) {
				player.setStrategy(new CheaterStrategy(player));
			} else if(player.getStrategy() instanceof RandomStrategy) {
				player.setStrategy(new RandomStrategy(player));
			}
		}
		
		this.setWorldDominationView(controllerObj.getWorldDominationView());
		this.setCardExchangeView(controllerObj.getCardExchangeView());
		this.setUI(controllerObj.getUI());


	}

//	/**
//	 * Sets the phase.
//	 *
//	 * @param turnPhase the new phase
//	 */
//	/*
//	 * @description :
//	 * @author
//	 */
//	public void setPhase(TurnPhase turnPhase) {
//		currentPhase = turnPhase;
//	}

//	/**
// * Take turns.
// *
// * @throws MapInvalidException the map invalid exception
//	 * @throws IOException 
// */
//	public void takeTurns() throws MapInvalidException, IOException {
//		int i = 0;
//		//System.out.println("Provide number of turns you wish to play");
//		//phaseCount= scan.nextInt();
//		while (winner == null) {
//			i = i % playerList.size();
//			currentPlayer = playerList.get(i);
//			System.out.println("==============" + currentPlayer.getPlayerName() + "'S TURN==================");
//			System.out.println("Initial Number of Armies: " + currentPlayer.getArmies());
//			takePhases();
//			i++;
//			//phaseCount--;
//		}
//		/*if(winner== null) {
//			System.out.println("GAME DRAW !!");
//		}
//		else {
//			System.out.println(winner.getPlayerName()+" "+"WONS THE GAME !!");
//		}*/
//	}
	
	/**
 * Take turns.
 *
 * @throws MapInvalidException the map invalid exception
 * @throws IOException Signals that an I/O exception has occurred.
 */
public void takeTurns() throws MapInvalidException, IOException {

		int i = 0;
		if (isSavedGame) {
			i = playerList.indexOf(this.currentPlayer);
		}
		while (winner == null) {
			i = i % playerList.size();
			currentPlayer = playerList.get(i);
			System.out.println("==============" + currentPlayer.getPlayerName() + "'S TURN==================");
			System.out.println("Initial Number of Armies: " + currentPlayer.getArmies());
			System.out.println("Countries occupied by Player: " + currentPlayer.getPlayerCountries().size());
			// if player does not have any country,all of its phases are skipped.
			if (currentPlayer.getPlayerCountries().size() > 0) {
				takePhases();
			}
			i++;

		}
	}
	
	/**
	 * Exchange cards.
	 */
	private void exchangeCards() {
		cardView.getCardProgress();
		if (currentPlayer.getCardsAcquired().size() >= 3 && getCurrentPlayer().getStrategyType().equals("Human")) {
			System.out.println("Do you want to exchange your cards for army reinforcement ? Y/N");
			char exchangeChoice = scan.next().charAt(0);
			if (exchangeChoice == 'Y' || exchangeChoice == 'y') {
				currentPlayer.exchangeCards();
				currentPlayer.notifyChanges(EventType.CARDS_EXCHANGE_NOTIFY);
			}
			if (exchangeChoice == 'N' || exchangeChoice == 'n') {
				if (currentPlayer.getCardsAcquired().size() >= 5) {
					System.out.println("Since you have 5 or more cards, you have to exchange one set of your cards");
					currentPlayer.exchangeCards();
					currentPlayer.notifyChanges(EventType.CARDS_EXCHANGE_NOTIFY);
				}
			}
		} else if (currentPlayer.getCardsAcquired().size() >= 5) {
			currentPlayer.exchangeCards();
		}
	}
	
	/**
	 * Take phases.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void takePhases() throws IOException {
		Phase curPhase = Phase.REENFORCEMENT;
		if(isSavedGame) {//this is a saved game, not a new game
			curPhase = this.getCurrentPhase();
			this.isSavedGame = false;
		}
		switch(curPhase.getValue()) {

		case 0:

			exchangeCards();
			reEnforce();
		case 1:
			boolean isAnyCountryInvaded = false;
			while (true) {

				try {
					this.setCurrentPhase(Phase.ATTACK);
					// ask user if wants to make an attack and check if user is able to attack (at
					// least 2 armies in one country)
					if (isWar() && canAttack()) {

						attack();
					}
					if(currentPlayer.getStrategyType().toLowerCase().equals("human")) {
						Scanner userOpinion = new Scanner(System.in);
						System.out.println("Do you want to save progress?");
						if(userOpinion.next().toLowerCase().equals("y") ||
								userOpinion.nextLine().toLowerCase().equals("yes")) {
							
							GameStat progress = GameStat.getInstance();
							progress.save();
							System.out.println("saved....");
						
						}
						
						else {
							System.out.println("Alright....Proceed....");
						}
					}
					
					isAnyCountryInvaded = currentPlayer.isAnyCountryInvaded();
					if (isAnyCountryInvaded == true && winner==null) {
						currentPlayer.addCards();
					}

					break;
				} catch (IllegalArgumentException e) {
					ui.handleExceptions(e.getMessage());
				}
			}
		case 2:
			while (true) {
				try {
					this.setCurrentPhase(Phase.FORTIFICATION);
					if (canFortify() && winner==null) {

						fortify();
					} else {
						ui.showDialog("The player is not qualified for fortification");
					}
					break;
				} catch (IllegalArgumentException e) {
					ui.handleExceptions(e.getMessage());
				}
			}
			break;
		}
	}
//	/**
//	 * Take phases.
//	 * 
//	 * @throws MapInvalidException
//	 * @throws IOException 
//	 */
//	public void takePhases() throws MapInvalidException, IOException {	
//		
//		exchangeCards();
//		reEnforce();
//		boolean	isAnyCountryInvaded =false;
//		while (true) {
//			
//			try {
//				// ask user if wants to make an attack and check if user is able to attack (at
//				// least 2 armies in one country)
//				if (isWar() && canAttack()) {
//					attack();
//				}
//				isAnyCountryInvaded = currentPlayer.isAnyCountryInvaded();
//				if (isAnyCountryInvaded == true) {
//					currentPlayer.addCards();
//				}
//				if(canFortify()) {
//					fortify();
//				} else {
//					ui.showDialog("The player is not qualified for fortification");
//				}
//				
//				break;
//			} catch (IllegalArgumentException e) {
//				ui.handleExceptions(e.getMessage());
//			}
//		}
//	}

	/**
 * Fortify.
 *
 * @throws IllegalArgumentException the illegal argument exception
 * @throws IOException Signals that an I/O exception has occurred.
 */

	private void fortify() throws IllegalArgumentException, IOException {
		currentPlayer.fortify();
		currentPlayer.notifyChanges(EventType.PHASE_NOTIFY);

	}

	/**
	 * Attack.
	 *
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IOException Signals that an I/O exception has occurred.

	 */
	private void attack() throws IllegalArgumentException, IOException {
		do {
			currentPlayer.attack();
			// check if current player has won the game
			if (currentPlayer.getPlayerCountries().size() == MapValidator.countriesList.size()) {
				winner = currentPlayer;
				ui.showDialog(currentPlayer.getPlayerName() + " HAS CONQUER THE WORLD!!");
				ui.showDialog("THE END!!!");
				if (!tournamentFlag) {
					System.exit(0);
				}else {
					break;
				}
			}
			currentPlayer.notifyChanges(EventType.PHASE_NOTIFY);
		} while (canAttack() && keepWar());

	}

	/**
	 * Re enforce.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.

	 */
	private void reEnforce() throws IOException {
		setCurrentPhase(Phase.REENFORCEMENT);
		currentPlayer.reEnforce();
		currentPlayer.notifyChanges(EventType.PHASE_NOTIFY);

	}

	/**
	 * checks if player can fortify by having at least 1 country with 2 or mor armies and at least 2 adj countries.
	 *

	 * @return true if player can fortify
	 */
	private boolean canFortify() {
		// check if there's at least 1 pair of countries adj to each other and one of
		// them has 2 or more armies
		List<Country> countries = currentPlayer.getPlayerCountries();
		for (int i = 0; i < countries.size() - 1; i++) {
			Country con = countries.get(i);
			List<String> adjConNames = con.getAdjacentCountries();
			for (int j = i + 1; j < countries.size(); j++) {
				if (adjConNames.contains(countries.get(j).getName())) {
					if (con.getNumArmies() >= 2 || countries.get(j).getNumArmies() >= 2) {
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * checks if player can attack by having at least 2 armies in one of player's
	 * countries and such country is adjacent to another player's countries.
	 *
	 * @return true, player can attack
	 */
	private boolean canAttack() {
		List<Country> curPlayerCountries = currentPlayer.getPlayerCountries();

		for (Country country : curPlayerCountries) {
			if (country.getNumArmies() >= 2) {
				// check if this country is adjacent to at least a country occupied by another
				// player
				List<String> adjCountries = country.getAdjacentCountries();
				for (String countryName : adjCountries) {
//					Country con = this.getCountryByCountryName(countryName);
					Country con = customMap.getCountry(countryName);
					if (!con.getOwner().getPlayerName().equals(currentPlayer.getPlayerName())) {
						return true;
					}
				}
			}
		}
		ui.showDialog(currentPlayer.getPlayerName() + " does not qualify to start any attack");
		return false;
	}

	/**
	 * This method calls appropriate UI to ask user if he/she wants to continue
	 * killing.
	 *
	 * @return true, if user wants to
	 */
	private boolean keepWar() {
		boolean keepWar = false;
		if (getCurrentPlayer().getStrategyType().equalsIgnoreCase("Aggressive")) {
			if (getCurrentPlayer().isHasEnemy()) {
				keepWar = true;
			}
		} else if (getCurrentPlayer().getStrategyType().equalsIgnoreCase("Cheater")) {
			keepWar = false;
		} else if (getCurrentPlayer().getStrategyType().equalsIgnoreCase("Random")) {
			Random r = new Random();
			int temp = r.nextInt((50 - 0) + 1) + 0;
			if (temp != 0) {
				keepWar = true;
			} else {
				keepWar = false;
			}
		} else {
			keepWar = UI.keepWar();
		}
		return keepWar;
	}

	/**
	 * Request GUI to ask if user wants to go to war.
	 *
	 * @return true if user wants to go to war
	 */
	private boolean isWar() {
		boolean isWar = false;
		if (getCurrentPlayer().getStrategyType().equalsIgnoreCase("Aggressive")
				|| getCurrentPlayer().getStrategyType().equalsIgnoreCase("Random")
				|| getCurrentPlayer().getStrategyType().equalsIgnoreCase("Cheater")

		) {
			isWar = true;
		} else if (getCurrentPlayer().getStrategyType().equalsIgnoreCase("Benevolent")) {
			isWar = false;
		} else {
			isWar = UI.isWar();
		}
		return isWar;

	}

	/**
	 * Gets the current player.
	 *
	 * @author Van
	 * @return current player
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Sets the current player.
	 *
	 * @param player the new current player
	 */
	public void setCurrentPlayer(Player player) {
		currentPlayer = player;
	}

	/**
	 * Inits the game.
	 *
	 * @throws MapInvalidException Inits the game. @throws
	 * @throws IOException Signals that an I/O exception has occurred.

	 */
	public void initGame() throws MapInvalidException, IOException {

		// Getting Player Info

		if (tournamentFlag == false) {
			System.out.println("Please enter the number of players between 2 and 6: ");
			Scanner inputNumPlayers = new Scanner(System.in);
			int numberOfPlayers = inputNumPlayers.nextInt();
			// if user inputs number of players less than 2 or greater than 6 then exit the
			// game
			if (!(numberOfPlayers >= 2 && numberOfPlayers <= 6)) {
				System.exit(0);
			}
			int initialArmies = 0;
			switch (numberOfPlayers) {
			case 2:
				initialArmies = 40;
				break;
			case 3:
				initialArmies = 35;
				break;
			case 4:
				initialArmies = 30;
				break;
			case 5:
				initialArmies = 25;
				break;
			case 6:
				initialArmies = 20;
				break;
			}
			for (int i = 1; i <= numberOfPlayers; i++) {
				String playerName = "Player " + i;
				Player player = new Player(playerName, true, initialArmies);
				controller.addPlayer(player);
			}

			controller.registerObserver(ui, EventType.PHASE_NOTIFY);
			controller.registerObserver(wdView, EventType.FORTIFICATION_NOTIFY);
			controller.registerObserver(cardView, EventType.CARDS_EXCHANGE_NOTIFY);

//				controller.registerObserver(phaseView, EventType.PHASE_VIEW_NOTIFY);

			System.out.println("evenly distributing countries among players in random fashion...");
			controller.randomizeCountryDistribution(countryList, controller.getPlayerList());
			System.out.println("-------- Setup --------");
			setupStrategy();
			controller.placeInitialArmies();
			controller.placeArmiesForSetup();

			controller.takeTurns();
		} else {
			int numberOfPlayersForTournament = strategyList.length;
			System.out.println("Number of Players: " + numberOfPlayersForTournament);
			int initialArmies = 0;
			switch (numberOfPlayersForTournament) {
			case 2:
				initialArmies = 40;
				break;
			case 3:
				initialArmies = 35;
				break;
			case 4:
				initialArmies = 30;
				break;
			}
			numberOfPlayers = 0;
			playerList.removeAll(playerList);
			for (int i = 1; i <= numberOfPlayersForTournament; i++) {
				String playerName = "Player " + i;
				Player player = new Player(playerName, true, initialArmies);
				controller.addPlayer(player);
			}

			controller.registerObserver(ui, EventType.PHASE_NOTIFY);
			controller.registerObserver(wdView, EventType.FORTIFICATION_NOTIFY);
			controller.registerObserver(cardView, EventType.CARDS_EXCHANGE_NOTIFY);
//					controller.registerObserver(phaseView, EventType.PHASE_VIEW_NOTIFY);

			System.out.println("evenly distributing countries among players in random fashion...");
			controller.randomizeCountryDistribution(countryList, controller.getPlayerList());
			System.out.println("-------- Setup --------");
			controller.setupStrategy();
			controller.placeInitialArmies();
			controller.placeArmiesForSetup();
			// for(int k=0; k < turnCount; k++) {
			controller.takeTurnsForTournament();
			// }
		}
	}

	/**
	 * evenly distributes countries among players in a random fashion.
	 *
	 * @author vanduong
	 * @param countries the countries
	 * @param players   the players
	 */
	public void randomizeCountryDistribution(List<Country> countries, List<Player> players) {
		Random rand = new Random();
		int playerIdx = 0;

		// players take turn to add a country to their occupied_list until the
		// unoccupied country list is empty
		while (countries.size() > 0) {
			// if playerIdx >= playerList size, reset playerIdx
			playerIdx = playerIdx % players.size();
			Player player = players.get(playerIdx);
			int randIdx = rand.nextInt(countries.size());
			Country country = countries.get(randIdx);
			player.addCountry(country.getName(), country);
			playerIdx++;
			countries.remove(randIdx);
		}

	}

	/**
	 * Asks GUI to ask user to input number of armies to be distributed to each
	 * occupied countries.
	 *
	 * @param newArmies the new armies
	 * @return the map
	 */
	public Map<Country, Integer> distributeArmies(int newArmies) {
		return ui.distributeArmies(currentPlayer.getPlayerCountries(), newArmies);
	}

	/**
	 * Gets the params for fortification.
	 *
	 * @param countryFrom the country from
	 * @return a set of 3 objects: country to move armies from, country to move
	 *         armies to, and number of armies
	 */
	public int getParamsForFortification(Country countryFrom) {
		return ui.getFortificationInfo(countryFrom);

	}

	/**
	 * Select country to transfer from.
	 *
	 * @param playerCountries the player countries
	 * @return the string
	 */
	public String selectCountryToTransferFrom(List<Country> playerCountries) {
		return ui.selectCountryToTransferFrom(playerCountries);
	}

	/**
	 * Select country to transfer to.
	 *
	 * @param adjCountries the adj countries
	 * @return the string
	 */
	public String selectCountryToTransferTo(List<String> adjCountries) {
		return ui.selectCountryToTransferTo(adjCountries);
	}

	/**
	 * Call UI to get the attacked country from player.
	 *
	 * @return the attacked country
	 */
	public Country getAttackedCountry() {
		UI.showAdjCountriesAndOwner(currentPlayer);
		String countryName = UI.getAttackedCountryByName();
//		return this.getCountryByCountryName(countryName);
		return customMap.getCountry(countryName);
	}

	/**
	 * Gets the owner by country name. Null if the country has no owner
	 *
	 * @param name the name
	 * @return the owner by country name
	 */
	public Player getOwnerByCountryName(String name) {
		return customMap.getCountry(name).getOwner();
	}

	/**
	 * Calls GUI selectAttackingCountry() method and return the selected country
	 * name This method gets called inside attack phase only when there are two or
	 * more options for attacking country.
	 *
	 * @param attackingCountries the attacking countries
	 * @return the string
	 */
	public String selectAttackingCountry(List<String> attackingCountries) {
		return UI.selectAttackingCountry(attackingCountries);
	}

	/**
	 * Ask UI to get the number dice from attacker.
	 *
	 * @return the number dice
	 */
	public int getNumDiceAttacker() {
		return ui.getNumDiceAttacker();
	}

	/**
	 * Gets the num dice defender.
	 *
	 * @return the num dice defender
	 */
	public int getNumDiceDefender() {

		return ui.getNumDiceDefender();
	}

	/**
	 * Shows dialog to user.
	 *
	 * @param message the message
	 */
	public void showDialog(String message) {
		ui.showDialog(message);
	}

	/**
	 * tells UI to ask user to choose if is all out mode.
	 *
	 * @return true, if is all out mode
	 */
	public boolean isAllOutMode() {
		return ui.isAllOutMode();
	}

	/**
	 * Gets the continent by name.
	 *
	 * @param continent the continent
	 * @return the continent by name
	 */
	public Continent getContinentByName(String continent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * This method is necessary to make sure the saved and loaded controller are the same instance.
	 *
	 * @return the only instance of GameControl
	 */
	public Object readResolved() {
		return controller;
	}

	/**
	 * Checks if is saved game.
	 *
	 * @return the isSavedGame
	 */
	public boolean isSavedGame() {
		return isSavedGame;
	}

	/**
	 * Sets the saved game.
	 *
	 * @param isSavedGame the isSavedGame to set
	 */
	public void setSavedGame(boolean isSavedGame) {
		this.isSavedGame = isSavedGame;
	}

	/**
	 * @throws ClassNotFoundException invalid class called
	 * @throws IOException            invalid input
	 * @throws MapInvalidException    invalid map input
	 */
	public void modeSingle() throws ClassNotFoundException, IOException, MapInvalidException {
		controller.loadMap();
		controller.createWorldDominationView();
		controller.createCardExchangeView();
		ui = new UI();
		controller.initGame();
	}

	/**
	 * @return list of winners of tournament mode
	 * @throws ClassNotFoundException invalid class called
	 * @throws IOException            invalid input
	 * @throws MapInvalidException    invalid map input
	 */
	public String[][] modeTournament() throws ClassNotFoundException, IOException, MapInvalidException {
		// GameController controller = GameController.getInstance();
		if (!isTest) {
			System.out.println("Provide 1-5 maps you wish to play on:");
			System.out.println("*********MAP MENU*********");
			System.out.println("1. XSmallMap 2. SmallMap 3. MediumMap 4. LargeMap 5. XLargeMap");
			mapInput = tScan.nextLine();
			System.out.println("Provide 2-4 player's strategies :");
			System.out.println("*********STRATEGY MENU************");
			System.out.println("1. Aggressive 2. Benevolent 3. Cheater 4. Random");
			strategyInput = tScan.nextLine();
			System.out.println("Provide number of games on each map you want to play on. Range from 1-5 games: ");
			gameCount = tScan.nextInt();
			if(gameCount<1 || gameCount>5 ) {
				System.out.println("ERROR!! Provide number of games ranging from 1-5 !");
			}
			System.out.println("Provide number of turns in each game you want to play for. Range from 10-50 turns: ");
			turnCount = tScan.nextInt();
			if(turnCount<10 || turnCount>50) {
				System.out.println("ERROR!! Provide number of turns ranging from 10-50 !");
			}
		}
		if (mapInput.contains(",")) {
			mapList = mapInput.split(",");
		}
		else {
			mapList= mapInput.split(" ");
		}
		if(mapList.length<1 || mapList.length>5) {
			System.out.println("ERROR!! Select maps between 1 to 5 only");
		}
		for (int i = 0; i < mapList.length; i++) {
			try {
				// controller.loadMapForTournament(mapList[i]);
				for (int j = 0; j < gameCount; j++) {
					System.out.println("Map Loaded for the game:" + (j+1) + " is " + mapList[i]);
					controller.loadMapForTournament(mapList[i]);
					strategyList = new String[5];
					if (strategyInput.contains(",")) {
						strategyList = strategyInput.split(",");
					}
					controller.createWorldDominationView();
					controller.createCardExchangeView();
					ui = new UI();
					controller.initGame();
					System.out.println("Game" + " " + (j + 1) + " " + "has ended. Going for next game...");
					if (winner != null) {
						finalWinnerList[i][j] = controller.getWinner().getStrategyType();
						winner=null;
						controller = GameController.getInstance();
					} else {
						finalWinnerList[i][j] = "DRAW";
						controller = GameController.getInstance();
					}
				}
				System.out.println("All games of Map" + " " + (i + 1) + " " + "are completed. Going for next map...");
			} catch (IOException | MapInvalidException e) {
				ui.handleExceptions(e.getMessage());
				System.exit(1);
			}
		}
		System.out.println("**************TOURNAMENT GAME RESULTS**************");
		for (int i = 0; i < mapList.length; i++) {
			System.out.println("FOR MAP" + " " + (i + 1) + " " + ":");
			for (int j = 0; j < gameCount; j++) {
				System.out.println("Winner of Game" + " " + (j + 1) + " " + "is :" + finalWinnerList[i][j]);
			}
			System.out.println("******************************************");
		}
		return finalWinnerList;
	}

	public static boolean isTest() {
		return isTest;
	}

	public static void setTest(boolean isTest) {
		GameController.isTest = isTest;
	}

	public static String getMapInput() {
		return mapInput;
	}

	public static void setMapInput(String mapInput) {
		GameController.mapInput = mapInput;
	}

	public static String getStrategyInput() {
		return strategyInput;
	}

	public static void setStrategyInput(String strategyInput) {
		GameController.strategyInput = strategyInput;
	}

	public static boolean isTournamentFlag() {
		return tournamentFlag;
	}

	public static int getGameCount() {
		return gameCount;
	}

	public static void setGameCount(int gameCount) {
		GameController.gameCount = gameCount;
	}

	public static void setTournamentFlag(boolean tournamentFlag) {
		GameController.tournamentFlag = tournamentFlag;
	}

	public static int getTurnCount() {
		return turnCount;
	}

	public static void setTurnCount(int turnCount) {
		GameController.turnCount = turnCount;
	}

	/**
	 * @param mapName name of Map passed for Map Load
	 * @throws IOException         invalid input
	 * @throws MapInvalidException invalid map input
	 */
	public void loadMapForTournament(String mapName) throws IOException, MapInvalidException {
		customMap.reset();
		customMap = CustomMapGenerator.getInstance();
		customMap.LoadMap(mapName);
		countryList.removeAll(countryList);
		countryList.addAll(customMap.countryDefault);
	}

	/**
	 * @throws IOException         invalid input
	 * @throws MapInvalidException invalid Map Input
	 */
	public void takeTurnsForTournament() throws IOException, MapInvalidException {
		int i = 0;
		int counter = 0;
		while (counter < turnCount) {
			if(winner!=null) {
				break;
			}
			counter++;
			i = i % playerList.size();
			currentPlayer = playerList.get(i);
			System.out.println("==============" + currentPlayer.getPlayerName() + "'S TURN==================");
			System.out.println("Turn Number" + counter);
			System.out.println("Initial Number of Armies: " + currentPlayer.getArmies());
			System.out.println("Countries occupied by Player: " + currentPlayer.getPlayerCountries().size());
			// if player does not have any country,all of its phases are skipped.
			if (currentPlayer.getPlayerCountries().size() > 0) {
				takePhases();
			}
			i++;
		}

	}

}