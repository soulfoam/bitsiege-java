package soulfoam.arena.main.menu;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.gameinfo.GameInfo;

public class UpdateUI {

	public float x, y;
	public float width, height;

	public MenuButton downloadUpdate;

	public MenuButton exitButton;

	public boolean renderMe;
	public boolean clickedDownload;

	public UpdateUI(float x, float y) {
		this.x = x;
		this.y = y;

		width = 200;
		height = 150;
		downloadUpdate = new MenuButton("Download Update", 20, 20, 12, 10);
		exitButton = new MenuButton("X", 20, 20, 12, 10);
		handleSetButtons();
	}

	public void update(GameContainer gc, StateBasedGame s, int delta) {
		x = GameInfo.RES_WIDTH / 2 - width / 2;
		y = 15;

		downloadUpdate.update(gc);

		exitButton.update(gc);

		if (downloadUpdate.isClicked()) {
			Res.openURL("bitsiege.com/download.php");
			clickedDownload = true;
		}

		if (exitButton.isClicked()) {
			gc.exit();
		}

		handleSetButtons();
	}

	public void render(Graphics g, GameContainer gc) {

		if (renderMe) {
			renderWindow(g);

			downloadUpdate.render(g, 2, gc);

			exitButton.render(g, 1, gc);

		}
	}

	public void renderWindow(Graphics g) {
		g.setColor(new Color(0, 0, 0, 220));
		g.fillRect(x, y, width, height);

		g.setColor(new Color(255, 255, 255, 40));
		g.fillRect(x, y + 134, width, 12);

		g.setColor(new Color(8, 103, 170, 140));
		g.fillRect(x, y, width, 10);

		g.fillRect(x, y + 32, width, 20);
		g.fillRect(x, y + 84, width, 14);

		g.setColor(new Color(53, 146, 212));
		g.drawRect(x, y, width, height);
		g.drawRect(x, y, width, 10);
		g.drawRect(x, y + 32, width, 20);
		g.drawRect(x, y + 84, width, 14);

		String twtvtext = "Click to see live development of Bit Siege!";
		float twtvtextcenter = Res.bitFont.getWidth(twtvtext);
		Res.bitFont.drawString(x + width / 2 - twtvtextcenter + 8, y + 138, twtvtext, Color.white);

		String headerText = "Updates Found!";
		float center = Res.bitFont.getWidth(headerText);

		Res.bitFont.drawString(x + width / 2 - center, y + 3, headerText, Color.green);

		String vertext = "Your Version: " + GameInfo.GAME_VERSION;
		String vertextnew = "New Version: " + MainMenuManager.getMainMenu().getLoginUI().serverVer;
		Color vertTextColor = Color.red;
		if (!clickedDownload) {
			vertext = "Your Version: " + GameInfo.GAME_VERSION;
			vertextnew = "New Version: " + MainMenuManager.getMainMenu().getLoginUI().serverVer;
		} else {
			vertext = "Opening webpage...";
			vertextnew = "www.bitsiege.com/download.php";
			vertTextColor = Color.white;
		}
		float vertextnewcenter = Res.bitFont.getWidth(vertextnew);
		float vertextcenter = Res.bitFont.getWidth(vertext);
		Res.bitFont.drawString(x + width / 2 - vertextcenter, y + 35, vertext, vertTextColor);
		Res.bitFont.drawString(x + width / 2 - vertextnewcenter, y + 45, vertextnew, Color.green);

	}

	public void handleSetButtons() {

		float centerForButtons = x + width / 2 - 40;

		downloadUpdate.setX(centerForButtons);
		downloadUpdate.setY(y + 86);
		downloadUpdate.setWidth(80);
		downloadUpdate.setHeight(10);

		downloadUpdate.getButton().setX(centerForButtons);
		downloadUpdate.getButton().setY(y + 86);
		downloadUpdate.getButton().setWidth(80);
		downloadUpdate.getButton().setHeight(10);

		exitButton.setX(x + 2);
		exitButton.setY(y + 1);
		exitButton.setWidth(32);
		exitButton.setHeight(8);
		exitButton.setText("X");
		exitButton.getButton().setX(x + 2);
		exitButton.getButton().setY(y + 1);
		exitButton.getButton().setWidth(32);
		exitButton.getButton().setHeight(8);

	}
}
