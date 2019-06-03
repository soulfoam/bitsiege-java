package soulfoam.arena.main.gfx;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.gui.TextField;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.misc.ChatMessage;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.gameinfo.GameInfo;
import soulfoam.arenashared.main.opcode.OPCode;

public class ChatHUD extends BaseHUDUI {

	private ArrayList<ChatMessage> chatList = new ArrayList<ChatMessage>();

	private Rectangle chatRect;

	private HUDButton scaleSizeWidthPlusButton;
	private HUDButton scaleSizeWidthMinusButton;
	private HUDButton scaleSizeHeightPlusButton;
	private HUDButton scaleSizeHeightMinusButton;
	private HUDButton scaleTextSizePlusButton;
	private HUDButton scaleTextSizeMinusButton;
	private HUDButton opacityPlusButton;
	private HUDButton opacityMinusButton;

	private TextField chatTextField;

	private boolean chatting;

	public ChatHUD(float x, float y, float width, float height, float textScale, int opacity, GameContainer gc) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.textScale = textScale;
		this.opacity = opacity;

		chatRect = new Rectangle(x, y, 15, 10);

		scaleSizeWidthPlusButton = new HUDButton("+", x, y, 10, 5);
		scaleSizeWidthMinusButton = new HUDButton("-", x, y, 10, 5);

		scaleSizeHeightPlusButton = new HUDButton("+", x, y, 10, 5);
		scaleSizeHeightMinusButton = new HUDButton("-", x, y, 10, 5);

		scaleTextSizePlusButton = new HUDButton("+", x, y, 10, 5);
		scaleTextSizeMinusButton = new HUDButton("-", x, y, 10, 5);

		opacityPlusButton = new HUDButton("+", x, y, 10, 5);
		opacityMinusButton = new HUDButton("-", x, y, 10, 5);

		chatTextField = new TextField(gc, Res.bitFont, x, y + height - 7, width, height);
		chatTextField.setBackgroundColor(new Color(0, 0, 0, opacity + 100));
	}

	public void update(GameContainer gc, int delta) {

		runTimer(delta);

		if (Game.getGame().getHUDDisplay().getChatHUD().chatting) {
			if (gc.getInput().isKeyDown(Input.KEY_LCONTROL) && gc.getInput().isKeyDown(Input.KEY_ENTER)) {
				chatTextField.setText("/all ");
				chatTextField.setCursorPos(5);
			}
			if (!chatTextField.hasFocus()) {
				chatTextField.setFocus(true);
			}
		} else {
			chatTextField.setFocus(false);
		}

		float chatDisplay = 0;
		for (int i = 0; i < chatList.size(); i++) {
			chatDisplay += textScale * 8 + 2;

			if (chatDisplay >= height - 4) {
				chatList.remove(0);
			}
		}

		if (Game.getGame().getHUDDisplay().isEditingHUD()) {
			if (pickedUp) {

				x = gc.getInput().getMouseX() - width / 2;
				y = gc.getInput().getMouseY() - height / 2;

				if (x <= 0) {
					x = 0;
				}
				if (y <= 0) {
					y = 0;
				}
				if (x + width >= GameInfo.RES_WIDTH) {
					x = GameInfo.RES_WIDTH - width;
				}
				if (y + height >= GameInfo.RES_HEIGHT) {
					y = GameInfo.RES_HEIGHT - height;
				}

				if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					setDown();
				}

			} else {
				if (chatRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
					if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
						if (canLeftClick()) {
							showPopUpMenu = !showPopUpMenu;
						}
					}
					if (gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
						Game.getGame().getHUDDisplay().attemptToPickUpHUD(this);
						showPopUpMenu = true;
					}
				}
			}

			if (showPopUpMenu) {
				scaleSizeWidthPlusButton.update(gc);
				scaleSizeWidthMinusButton.update(gc);
				scaleSizeHeightPlusButton.update(gc);
				scaleSizeHeightMinusButton.update(gc);
				scaleTextSizePlusButton.update(gc);
				scaleTextSizeMinusButton.update(gc);
				opacityPlusButton.update(gc);
				opacityMinusButton.update(gc);

				if (scaleSizeWidthPlusButton.isClicked()) {
					width += 2.0f;
				}
				if (scaleSizeWidthMinusButton.isClicked()) {
					width -= 2.0f;
					if (width <= 0) {
						width = 10;
					}
				}

				if (scaleSizeHeightPlusButton.isClicked()) {
					height += 2.0f;
				}
				if (scaleSizeHeightMinusButton.isClicked()) {
					height -= 2.0f;
					if (height <= 0) {
						height = 10;
					}
				}

				if (scaleTextSizePlusButton.isClicked()) {
					textScale += 0.25f;
				}
				if (scaleTextSizeMinusButton.isClicked()) {
					textScale -= 0.25f;
					if (textScale <= 0.25f) {
						textScale = 0.25f;
					}
				}

				if (opacityPlusButton.isClicked()) {
					if (opacity == 1) {
						opacity = 20;
					} else {
						opacity += 20;
					}
					if (opacity >= 255) {
						opacity = 255;
					}
				}
				if (opacityMinusButton.isClicked()) {
					opacity -= 20;
					if (opacity <= 0) {
						opacity = 1;
					}
				}
			}
		} else {
			pickedUp = false;
		}

		setButtons();
		setChatRect();
	}

	public void render(GameContainer gc, Graphics g) {

		int chatDisplay = 0;

		g.setColor(new Color(0, 0, 0, opacity));
		g.fillRect(x, y, width, height);
		g.setColor(Color.white);

		ChatMessage[] tempChatList = new ChatMessage[chatList.size()];
		chatList.toArray(tempChatList);

		for (ChatMessage cm : tempChatList) {
			chatDisplay += textScale * 8 + 2;
			cm.x = x + 2;
			cm.y = y + chatDisplay - textScale * 8;
			cm.render(g, textScale, opacity);
		}

		if (Game.getGame().getHUDDisplay().getChatHUD().chatting) {
			chatTextField.render(gc, g);
		}

		if (Game.getGame().getHUDDisplay().isEditingHUD()) {
			if (!pickedUp) {
				g.setColor(Color.red);
				if (showPopUpMenu) {
					g.setColor(Color.yellow);
				}
				g.draw(chatRect);
			} else {
				g.setColor(Color.green);
				g.draw(chatRect);
			}

			if (showPopUpMenu) {

				popUpX = x;
				popUpY = y - 30;

				if (popUpX >= GameInfo.RES_WIDTH - 110) {
					popUpX = GameInfo.RES_WIDTH - 110;
				}
				if (popUpY <= 0) {
					popUpY = 0;
				}

				g.setColor(new Color(0, 0, 0, 180));
				g.fillRect(popUpX, popUpY, 110, 27);
				Res.bitFont.drawString(popUpX + 26, popUpY + 2, "Width: " + width);
				Res.bitFont.drawString(popUpX + 26, popUpY + 8, "Height: " + height);
				Res.bitFont.drawString(popUpX + 26, popUpY + 14, "Text Scale: " + textScale);
				int opacityDisplay = opacity;
				if (opacity <= 1) {
					opacityDisplay = 0;
				}
				Res.bitFont.drawString(popUpX + 26, popUpY + 20, "Opacity: " + opacityDisplay);

				scaleSizeWidthPlusButton.render(g);
				scaleSizeWidthMinusButton.render(g);
				scaleSizeHeightPlusButton.render(g);
				scaleSizeHeightMinusButton.render(g);
				scaleTextSizePlusButton.render(g);
				scaleTextSizeMinusButton.render(g);
				opacityPlusButton.render(g);
				opacityMinusButton.render(g);
			}
		}
	}

	public void setButtons() {

		scaleSizeWidthPlusButton.setX(popUpX + 2);
		scaleSizeWidthPlusButton.setY(popUpY + 2);

		scaleSizeWidthMinusButton.setX(popUpX + 14);
		scaleSizeWidthMinusButton.setY(popUpY + 2);

		scaleSizeHeightPlusButton.setX(popUpX + 2);
		scaleSizeHeightPlusButton.setY(popUpY + 8);

		scaleSizeHeightMinusButton.setX(popUpX + 14);
		scaleSizeHeightMinusButton.setY(popUpY + 8);

		scaleTextSizePlusButton.setX(popUpX + 2);
		scaleTextSizePlusButton.setY(popUpY + 14);

		scaleTextSizeMinusButton.setX(popUpX + 14);
		scaleTextSizeMinusButton.setY(popUpY + 14);

		opacityPlusButton.setX(popUpX + 2);
		opacityPlusButton.setY(popUpY + 20);

		opacityMinusButton.setX(popUpX + 14);
		opacityMinusButton.setY(popUpY + 20);

	}

	private void setChatRect() {

		chatRect.setX(x);
		chatRect.setY(y);
		chatRect.setWidth(width);
		chatRect.setHeight(height);

		chatTextField.setLocation(x, y + height - 7);
		chatTextField.setSize(width, 7);
		chatTextField.setBackgroundColor(new Color(0, 0, 0, opacity + 100));

	}

	public void sendChatMessage() {

		if (Game.getGame().getClient() != null && !chatTextField.getText().trim().isEmpty()) {
			if (chatTextField.getText().toLowerCase().startsWith("/all")
					&& chatTextField.getText().trim().length() <= 4) {
				chatTextField.setText("");
				return;
			}

			Game.getGame().getClient().sendReliableData(OPCode.OP_CHATMESSAGE + chatTextField.getText().trim());
			chatTextField.setText("");

		}
	}

	public ArrayList<ChatMessage> getChatList() {
		return chatList;
	}

	public boolean isChatting() {
		return chatting;
	}

	public TextField getChatTextField() {
		return chatTextField;
	}

	public void setChatting(boolean chatting) {
		this.chatting = chatting;
	}

}
