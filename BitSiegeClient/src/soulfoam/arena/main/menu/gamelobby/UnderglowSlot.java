package soulfoam.arena.main.menu.gamelobby;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import soulfoam.arena.entities.challengers.underglows.CosmeticBook;
import soulfoam.arena.main.menu.PregameLobbyManager;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;

public class UnderglowSlot extends BaseSlotUI {

	public Animation slotAnimation;

	public UnderglowSlot(float x, float y) {
		super(x, y);
		width = 14;
		height = 10;
	}

	public void initUnderglowSlot(int slotID, Animation slotAnimation) {
		this.slotID = slotID;
		this.slotAnimation = slotAnimation;
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
	}

	public void select() {
		if (owned && !empty) {
			if (PregameLobbyManager.underglowPick != slotID) {
				LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_LOBBYUPDATE);
				LobbyManager.getManager().getLobbyClient().getOutput().println("U~" + slotID);
			}
			PregameLobbyManager.underglowPick = slotID;
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setUnderglowAnimation(CosmeticBook.getUnderglowAnimation(PregameLobbyManager.underglowPick));
			selected = true;
		}

	}

	public void render(Graphics g, GameContainer gc) {
		if (slotAnimation != null) {
			slotAnimation.draw(x - 1, y - 1, 16, 12);
		}

		if (empty) {
			g.setColor(new Color(100, 100, 100, 150));
		} else {
			g.setColor(new Color(255, 255, 255, 150));
		}

		if (selected) {
			g.setColor(Color.green);
		}

		if (!owned) {
			g.setColor(Color.red);
		}

		g.drawRect(x, y, width, height);
	}

}
