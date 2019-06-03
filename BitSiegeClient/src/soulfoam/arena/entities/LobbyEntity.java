package soulfoam.arena.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import soulfoam.arena.main.resources.Res;

public class LobbyEntity {

	private int id;
	private String playerName;
	private int playerChallenger;
	private byte playerTeam;
	private boolean ready;
	private Color color;

	private Image classImage;

	public LobbyEntity(int playerID, String playerName, int r, int g, int b, byte playerTeam, int playerChallenger,
			boolean isReady) {
		id = playerID;
		this.playerName = playerName;
		this.playerTeam = playerTeam;
		this.playerChallenger = playerChallenger;
		ready = isReady;
		color = new Color(r, g, b);

		setClassImage();
	}

	public void setClassImage() {
		Entity c = EntityBook.getChallengerByID(playerChallenger, 0, 0, 0, -1);
		if (c != null) {
			classImage = c.animation.getDefaultPortrait();
		} else {
			classImage = Res.UI_RESOURCE.RANDOM_ICON;
		}
	}

	public void lockIn(int challengerID) {
		ready = true;
		playerChallenger = challengerID;
		setClassImage();
	}

	public boolean isReady() {
		return ready;
	}

	public String getPlayerName() {
		return playerName;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public byte getPlayerTeam() {
		return playerTeam;
	}

	public int getPlayerChallenger() {
		return playerChallenger;
	}

	public Image getClassImage() {
		return classImage;
	}

	public Color getNameColor() {
		return color;
	}

}