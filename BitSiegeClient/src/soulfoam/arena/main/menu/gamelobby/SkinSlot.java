package soulfoam.arena.main.menu.gamelobby;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import soulfoam.arena.main.menu.PregameLobbyManager;
import soulfoam.arena.main.resources.Res;

public class SkinSlot extends BaseSlotUI {

	public SkinSlot(float x, float y) {
		super(x, y);
		width = 16;
		height = 16;
	}

	public void initSkinSlot(int slotID, Image slotImage) {
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
	}

	public void select() {
		if (owned && !empty) {
			PregameLobbyManager.getLobby().getChallengerSelectUI().getSelectedChallengerSlot().setSkinPick(slotID);
			PregameLobbyManager.getLobby().getChallengerSelectUI().setSkin();
			selected = true;
		}

	}

	public void render(Graphics g, GameContainer gc) {
		if (slotImage != null) {
			if (owned){
				slotImage.draw(x, y);
			}
			else{
				slotImage.draw(x, y, new Color(80, 80, 80));
			}
		}

		Color borderColor = new Color(40, 40, 40);
		
		if (selected) {
			borderColor = Color.green;
		}

		if (!owned) {
			borderColor = new Color(20, 20, 20);
		}
		
		if (!empty) {
			Res.UI_RESOURCE.PORTRAIT_BORDER.draw(x - 1, y - 1, borderColor);
		}
		
	}

}
