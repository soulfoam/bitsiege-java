package soulfoam.arena.main.menu.gamelobby;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import soulfoam.arena.main.menu.PregameLobbyManager;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.main.util.GlowTimer;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;

public class ChallengerSlot extends BaseSlotUI {

	private boolean locked;
	private boolean localLockIn;

	private int skinPick;

	private GlowTimer glowTimer = new GlowTimer(90, 255, 6);

	public ChallengerSlot(float x, float y) {
		super(x, y);
		width = 16;
		height = 16;
	}

	public void initChallengerSlot(int slotID, Image slotImage) {
		this.slotID = slotID;
		this.slotImage = slotImage;
		empty = false;
	}

	public void update(int delta, GameContainer gc) {

		if (getBounds().contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {

				buttonDown = true;
				if (!playedSound) {
					buttonSound.play();
				}
				playedSound = true;
			} else {
				if (buttonDown) {
					buttonDown = false;
					playedSound = false;
					select();
				}
			}
			if (owned && !empty) {
				if (!playedSoundHover) {
					// buttonSoundHover.play();
					playedSoundHover = true;
				}
			}
		} else {
			playedSoundHover = false;
			buttonDown = false;
		}

		if (selected) {
			glowTimer.update(delta);
		}

	}

	public void select() {
		if (!empty) {
			PregameLobbyManager.getLobby().getChallengerSelectUI().setSelectedChallengerSlot(this);
			PregameLobbyManager.getLobby().getChallengerSelectUI().setChallengerPick(slotID);
			PregameLobbyManager.getLobby().getChallengerSelectUI().setChallenger();

			if (owned) {
				if (PregameLobbyManager.getLobby().getSelectedChallengerUI().isReady()) {
					PregameLobbyManager.getLobby().getSelectedChallengerUI().setButtonState(1);
				} else {
					PregameLobbyManager.getLobby().getSelectedChallengerUI().setButtonState(0);
				}
			} else {
				PregameLobbyManager.getLobby().getSelectedChallengerUI().setButtonState(3);
			}

			if (locked) {
				PregameLobbyManager.getLobby().getSelectedChallengerUI().setButtonState(2);
			}

			selected = true;
		}
	}

	public void render(Graphics g, GameContainer gc) {
		if (slotImage != null) {
			Color color = new Color(255, 255, 255, 255);

			if (!owned) {
				color = new Color(80, 80, 80);
			}

			if (!selected) {
				slotImage.draw(x, y, color);
			} else {
				slotImage.draw(x, y, new Color(255, 255, 255, glowTimer.getTimer()));
			}
		}
		
		Color borderColor = new Color(40, 40, 40);
		
		if (locked) {
			borderColor = Color.red;
		}

		if (localLockIn) {
			borderColor = Color.green;
		}
		
		if (!owned) {
			borderColor = new Color(20, 20, 20);
		}
		
		if (!empty) {
			Res.UI_RESOURCE.PORTRAIT_BORDER.draw(x - 1, y - 1, borderColor);
		}

		//g.drawRect(x, y, width, height);

	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isLocalLockIn() {
		return localLockIn;
	}

	public void setLocalLockIn(boolean localLockIn) {
		this.localLockIn = localLockIn;
	}

	public int getSkinPick() {
		return skinPick;
	}

	public void setSkinPick(int skinPick) {
		if (localLockIn && this.skinPick != skinPick) {
			LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_LOBBYUPDATE);
			LobbyManager.getManager().getLobbyClient().getOutput().println("S~" + skinPick);
		}
		this.skinPick = skinPick;
	}
	
	public void setSkinPickLocalLockIn() {

		LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_LOBBYUPDATE);
		LobbyManager.getManager().getLobbyClient().getOutput().println("S~" + skinPick);

	}

}
