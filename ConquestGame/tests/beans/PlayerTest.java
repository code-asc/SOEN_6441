package beans;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
private Player player;
	
	private Country vn;
	private Country indi;
	private Country usa;
	private Country can;
	private Country mex;
	private Country eng;
	private Country ger;
	private Country france;
	private Country rus;
	private Country china;
	private Country congo;
	private Country ugan;
	
	private Continent america;
	private Continent europe;
	private Continent asia;
	private Continent africa;

	@Before
	public void setUp() throws Exception {
player = new Player("PlayerOne");
		
		vn = new Country("Vietnam");
		indi = new Country("India");
		usa = new Country("USA");
		can = new Country("Canada");
		mex = new Country("Mexico");
		eng = new Country("England");
		ger = new Country("Germany");
		france = new Country("France");
		rus	= new Country("Russia");
		china = new Country("China");
		congo = new Country("Congo");
		ugan = new Country("Uganda");
		
		america = new Continent("Ameria", 3);
		europe = new Continent("Europe", 2);
		asia = new Continent("Asia", 3);
		africa = new Continent("Africa", 1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		//By default the player is treated as human.
				assertTrue(player.isHuman());
				
				player.addCountry(vn.getName(), vn);
				player.addCountry(indi.getName(), indi);
				player.addCountry(usa.getName(), usa);
				player.addCountry(can.getName(), can);
				
				assertEquals(player.getPlayerCountries().size(), 4);
				assertEquals(player.getPlayerName(), "PlayerOne");
				assertNotEquals(player.getArmies(), 1);
				
				Country[] countryObjs = {rus, china, ger, ugan};
				String[] countryNames = {rus.getName(), china.getName(),
						ger.getName(), ugan.getName()};
				
				player.addCountries(countryNames, countryObjs);
				assertEquals(player.getPlayerCountries().size(), 8);
				
				Continent[] continentObjs = {america, europe, asia};
				String[] continentNames = {america.getName(), europe.getName(),
											asia.getName()};
				
				player.addContinent(africa.getName(), africa);
				assertNotEquals(player.getPlayerContinents().size(), 0);
				
				player.addContinents(continentNames, continentObjs);
				assertEquals(player.getPlayerContinents().size(), 4);
			
	}

}