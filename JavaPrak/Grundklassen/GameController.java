import java.util.HashMap;


public class GameController {
	private GameMap map;
	private HashMap<GameListener,Player> playerlist;
	private int state;
	private Player currentPlayer;
	
	public GameMap getMap(){
		return map;
	}
	
	public Player getTurnPlayer(){
		return currentPlayer;
	}
}
