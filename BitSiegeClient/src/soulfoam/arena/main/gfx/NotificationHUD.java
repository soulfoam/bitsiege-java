package soulfoam.arena.main.gfx;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.gameinfo.GameInfo;


public class NotificationHUD extends BaseHUDUI {

	private ArrayList<MessageBox> messageList = new ArrayList<MessageBox>();
	private ArrayList<MessageBox> textMessageList = new ArrayList<MessageBox>();

	private float logX, logY;
	private float logScale;

	private boolean pickedUpText;

	private float textX, textY;
	private float textScale;

	private float popUpLogX;
	private float popUpLogY;

	private float popUpTextX;
	private float popUpTextY;

	private int logOpacity, textOpacity;

	private Rectangle logRect;
	private Rectangle textRect;

	private HUDButton scaleUpLogsButton;
	private HUDButton scaleDownLogsButton;
	private HUDButton logsOpacityPlusButton;
	private HUDButton logsOpacityMinusButton;
	private HUDButton removeLogsButton;

	private HUDButton scaleUpTextButton;
	private HUDButton scaleDownTextButton;
	private HUDButton textOpacityPlusButton;
	private HUDButton textOpacityMinusButton;

	private boolean showPopUpMenuText;

	private boolean removeLogs;

	private int spacing;

	public NotificationHUD(float x, float y, float scale, int logOpacity, boolean removeLogs, float textX, float textY,
			float textScale, int textOpacity) {
		setLogX(x);
		setLogY(y);
		setTextX(textX);
		setTextY(textY);
		this.textScale = textScale;
		setLogScale(scale);
		setLogOpacity(logOpacity);
		setTextOpacity(textOpacity);
		setRemoveLogs(removeLogs);

		logRect = new Rectangle(x, y, scale * 2, 204);
		textRect = new Rectangle(textX, textY, GameInfo.RES_WIDTH, textScale * 4);

		scaleUpLogsButton = new HUDButton("+", x, y, 10, 5);
		scaleDownLogsButton = new HUDButton("-", x, y, 10, 5);
		removeLogsButton = new HUDButton("", x, y, 22, 8);
		logsOpacityPlusButton = new HUDButton("+", x, y, 10, 5);
		logsOpacityMinusButton = new HUDButton("-", x, y, 10, 5);

		scaleUpTextButton = new HUDButton("+", x, y, 10, 5);
		scaleDownTextButton = new HUDButton("-", x, y, 10, 5);
		textOpacityPlusButton = new HUDButton("+", x, y, 10, 5);
		textOpacityMinusButton = new HUDButton("-", x, y, 10, 5);
	}

	public void update(GameContainer gc, int delta) {

		runTimer(delta);

		if (pickedUp) {
			pickedUpText = false;
		}
		if (getMessageList().size() > 17) {
			getMessageList().remove(0);
		}

		for (int i = 0; i < getMessageList().size(); i++) {
			if (getMessageList().get(i) != null) {
				getMessageList().get(i).update(delta);
			}
		}

		if (!getTextMessageList().isEmpty() && getTextMessageList().get(0) != null) {
			getTextMessageList().get(0).update(delta);
			if (getTextMessageList().get(0).needsRemoved) {
				getTextMessageList().remove(0);
			}
		}

		if (Game.getGame().getHUDDisplay().isEditingHUD()) {
			if (pickedUp) {
				setLogX(gc.getInput().getMouseX() - logRect.getWidth() / 2);
				setLogY(gc.getInput().getMouseY() - logRect.getHeight() / 2);

				if (getLogX() + getLogScale() * 4 >= GameInfo.RES_WIDTH) {
					setLogX(GameInfo.RES_WIDTH - getLogScale() * 4);
				}

				if (getLogY() <= 0 - spacing - getLogScale() - 4) {
					setLogY(0 - spacing - getLogScale() - 4);
				}

				if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					setDown();
				}

			} else {
				if (logRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
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

			if (pickedUpText) {
				setTextX(gc.getInput().getMouseX() - textRect.getWidth() / 2);
				setTextY(gc.getInput().getMouseY() - textRect.getHeight() / 2);

				if (getTextX() <= 0) {
					setTextX(0);
				}
				if (getTextY() <= 0) {
					setTextY(0);
				}
				if (getTextY() + textScale * 8 >= GameInfo.RES_HEIGHT) {
					setTextY(GameInfo.RES_HEIGHT - textScale * 8);
				}

				if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					popUpTimeoutTimer = popUpTimeoutTime;
					pickedUpText = false;
					showPopUpMenuText = false;
				}

			} else {
				if (textRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
					if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
						if (canLeftClick()) {
							showPopUpMenuText = !showPopUpMenuText;
						}
					}
					if (gc.getInput().isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
						pickedUpText = true;
						showPopUpMenuText = true;
					}
				}
			}

			if (scaleUpLogsButton.isClicked()) {
				setLogScale(getLogScale() + 1);
			}
			if (scaleDownLogsButton.isClicked()) {
				setLogScale(getLogScale() - 1);
				if (getLogScale() <= 0) {
					setLogScale(1);
				}
			}

			if (logsOpacityPlusButton.isClicked()) {
				setLogOpacity(getLogOpacity() + 20);
				if (getLogOpacity() >= 255) {
					setLogOpacity(255);
				}
			}
			if (logsOpacityMinusButton.isClicked()) {
				setLogOpacity(getLogOpacity() - 20);
				if (getLogOpacity() <= 0) {
					setLogOpacity(0);
				}
			}

			if (removeLogsButton.isClicked()) {
				setRemoveLogs(!isRemoveLogs());
			}

			if (scaleUpTextButton.isClicked()) {
				textScale += 0.25;
			}
			if (scaleDownTextButton.isClicked()) {
				textScale -= 0.25;
				if (textScale <= 0) {
					textScale = 0.25f;
				}
			}
			if (textOpacityPlusButton.isClicked()) {
				setTextOpacity(getTextOpacity() + 20);
				if (getTextOpacity() >= 255) {
					setTextOpacity(255);
				}
			}
			if (textOpacityMinusButton.isClicked()) {
				setTextOpacity(getTextOpacity() - 20);
				if (getTextOpacity() <= 0) {
					setTextOpacity(0);
				}
			}

			if (showPopUpMenu) {
				scaleUpLogsButton.update(gc);
				scaleDownLogsButton.update(gc);
				removeLogsButton.update(gc);
				logsOpacityPlusButton.update(gc);
				logsOpacityMinusButton.update(gc);
			}

			if (showPopUpMenuText) {
				scaleUpTextButton.update(gc);
				scaleDownTextButton.update(gc);
				textOpacityPlusButton.update(gc);
				textOpacityMinusButton.update(gc);
			}

			setLog();
			setButtons();
			setText();
		} else {
			pickedUp = false;
			pickedUpText = false;
		}

	}

	public void setLog() {
		logRect.setX(getLogX());
		logRect.setY(getLogY() + getLogScale());
		logRect.setWidth(getLogScale() * 4);
		logRect.setHeight(GameInfo.RES_HEIGHT);
	}

	public void setText() {
		textRect.setX(getTextX());
		textRect.setY(getTextY());
		textRect.setWidth(GameInfo.RES_WIDTH / 2);
		textRect.setHeight(textScale + textScale * 8);
	}

	public void setButtons() {
		scaleUpLogsButton.setX(popUpLogX + 2);
		scaleUpLogsButton.setY(popUpLogY + 2);

		scaleDownLogsButton.setX(popUpLogX + 14);
		scaleDownLogsButton.setY(popUpLogY + 2);

		removeLogsButton.setX(popUpLogX + 2);
		removeLogsButton.setY(popUpLogY + 16);

		logsOpacityPlusButton.setX(popUpLogX + 2);
		logsOpacityPlusButton.setY(popUpLogY + 8);

		logsOpacityMinusButton.setX(popUpLogX + 14);
		logsOpacityMinusButton.setY(popUpLogY + 8);

		scaleUpTextButton.setX(popUpTextX + 2);
		scaleUpTextButton.setY(popUpTextY + 2);

		scaleDownTextButton.setX(popUpTextX + 14);
		scaleDownTextButton.setY(popUpTextY + 2);

		textOpacityPlusButton.setX(popUpTextX + 2);
		textOpacityPlusButton.setY(popUpTextY + 8);

		textOpacityMinusButton.setX(popUpTextX + 14);
		textOpacityMinusButton.setY(popUpTextY + 8);

		if (isRemoveLogs()) {
			removeLogsButton.setButtonColor(new Color(0, 255, 0));
		} else {
			removeLogsButton.setButtonColor(new Color(255, 0, 0));
		}

	}

	public void render(Graphics g) {

		for (int i = 0; i < getMessageList().size(); i++) {
			spacing += getLogScale() + 4;
			getMessageList().get(i).renderLogItem(g, getLogX(), getLogY() + spacing, getLogScale(), getLogOpacity());
		}

		spacing = 0;

		if (!getTextMessageList().isEmpty()) {
			getTextMessageList().get(0).renderTextItem(g, getTextX(), getTextY(), textScale, getTextOpacity());
		}

		if (Game.getGame().getHUDDisplay().isEditingHUD()) {
			if (!pickedUp) {
				g.setColor(Color.red);
				if (showPopUpMenu) {
					g.setColor(Color.yellow);
				}
				g.draw(logRect);
			} else {
				g.setColor(Color.green);
				g.draw(logRect);
			}

			if (!pickedUpText) {
				g.setColor(Color.red);
				if (showPopUpMenuText) {
					g.setColor(Color.yellow);
				}
				g.draw(textRect);
			} else {
				g.setColor(Color.green);
				g.draw(textRect);
			}

			popUpLogX = getLogX();
			popUpLogY = getLogY() - 15;

			if (popUpLogX >= GameInfo.RES_WIDTH - 100) {
				popUpLogX = GameInfo.RES_WIDTH - 100;
			}
			if (popUpLogX <= 0) {
				popUpLogX = 0;
			}
			if (popUpLogY <= 0) {
				popUpLogY = 0;
			}

			popUpTextX = getTextX();
			popUpTextY = getTextY() - 20;

			if (popUpTextX >= 220) {
				popUpTextX = 220;
			}
			if (popUpTextX <= 0) {
				popUpTextX = 0;
			}
			if (popUpTextY <= 0) {
				popUpTextY = 0;
			}

			g.setColor(new Color(0, 0, 0, 180));
			if (showPopUpMenu) {
				g.fillRect(popUpLogX, popUpLogY, 100, 26);
			}
			if (showPopUpMenuText) {
				g.fillRect(popUpTextX, popUpTextY, 78, 15);
			}

			if (showPopUpMenu) {
				scaleUpLogsButton.render(g);
				scaleDownLogsButton.render(g);
				removeLogsButton.render(g);
				logsOpacityPlusButton.render(g);
				logsOpacityMinusButton.render(g);
				Res.bitFont.drawString(Res.roundForRendering(popUpLogX + 26), Res.roundForRendering(popUpLogY + 2),
						"Scale: " + getLogScale());
				Res.bitFont.drawString(Res.roundForRendering(popUpLogX + 26), Res.roundForRendering(popUpLogY + 8),
						"Opacity: " + getLogOpacity());

				Res.bitFont.drawString(Res.roundForRendering(popUpLogX + 26), Res.roundForRendering(popUpLogY + 14),
						"Remove logs after");
				Res.bitFont.drawString(Res.roundForRendering(popUpLogX + 26), Res.roundForRendering(popUpLogY + 20),
						"every round?:" + isRemoveLogs());
			}

			if (showPopUpMenuText) {
				scaleUpTextButton.render(g);
				scaleDownTextButton.render(g);
				textOpacityPlusButton.render(g);
				textOpacityMinusButton.render(g);
				Res.bitFont.drawString(Res.roundForRendering(popUpTextX + 26), Res.roundForRendering(popUpTextY + 2),
						"Scale: " + textScale);
				Res.bitFont.drawString(Res.roundForRendering(popUpTextX + 26), Res.roundForRendering(popUpTextY + 8),
						"Opacity: " + getTextOpacity());
			}

		}

	}

	public MessageBox getNotification(int id) {
		for (MessageBox m : getMessageList()) {
			if (m != null) {
				if (m.id == id) {
					return m;
				}
			}
		}

		return null;
	}

	public boolean isRemoveLogs() {
		return removeLogs;
	}

	public void setRemoveLogs(boolean removeLogs) {
		this.removeLogs = removeLogs;
	}

	public int getLogOpacity() {
		return logOpacity;
	}

	public void setLogOpacity(int logOpacity) {
		this.logOpacity = logOpacity;
	}

	public float getLogScale() {
		return logScale;
	}

	public void setLogScale(float logScale) {
		this.logScale = logScale;
	}

	public float getLogX() {
		return logX;
	}

	public void setLogX(float logX) {
		this.logX = logX;
	}

	public float getLogY() {
		return logY;
	}

	public void setLogY(float logY) {
		this.logY = logY;
	}

	public float getTextX() {
		return textX;
	}

	public void setTextX(float textX) {
		this.textX = textX;
	}

	public float getTextY() {
		return textY;
	}

	public void setTextY(float textY) {
		this.textY = textY;
	}

	public int getTextOpacity() {
		return textOpacity;
	}

	public void setTextOpacity(int textOpacity) {
		this.textOpacity = textOpacity;
	}

	public ArrayList<MessageBox> getTextMessageList() {
		return textMessageList;
	}

	public void setTextMessageList(ArrayList<MessageBox> textMessageList) {
		this.textMessageList = textMessageList;
	}

	public ArrayList<MessageBox> getMessageList() {
		return messageList;
	}

	public void setMessageList(ArrayList<MessageBox> messageList) {
		this.messageList = messageList;
	}

}
