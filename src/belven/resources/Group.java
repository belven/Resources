package belven.resources;

import java.util.List;

import org.bukkit.entity.Player;

public class Group {
	private List<Player> players;
	private String name;
	private boolean isPvP;

	public Group(List<Player> players, String Name) {
		this.setPlayers(players);
		this.setName(Name);
	}

	public Group(List<Player> players, String Name, boolean pvp) {
		this(players, Name);
		this.isPvP = pvp;
	}

	public List<Player> getPlayers() {
		return players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPvP() {
		return isPvP;
	}

	public void setPvP(boolean isPvP) {
		this.isPvP = isPvP;
	}
}
