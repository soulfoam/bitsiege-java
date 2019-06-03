package soulfoam.arena.entities.gameshop;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.gfx.HUDButton;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.gameinfo.ShopInfo;

public class StatUpgradeUI {

	private HUDButton buyHPButton;
	private HUDButton buyPowerButton;
	private HUDButton buyAttackSpeedButton;
	private HUDButton buyMoveSpeedButton;

	private Rectangle hpRect;
	private Rectangle powerRect;
	private Rectangle atkspdRect;
	private Rectangle spdRect;

	private float buyButtonTime = 0.15f * 1000;
	private float buyButtonTimer = 0;

	private float x, y;

	public StatUpgradeUI(float x, float y) {
		this.x = x;
		this.y = y;

		buyHPButton = new HUDButton("Buy", 158, 33, 20, 7);
		buyPowerButton = new HUDButton("Buy", 158, 83, 20, 7);
		buyAttackSpeedButton = new HUDButton("Buy", 158, 133, 20, 7);
		buyMoveSpeedButton = new HUDButton("Buy", 158, 183, 20, 7);

		hpRect = new Rectangle(0, 0, 0, 0);
		powerRect = new Rectangle(0, 0, 0, 0);
		atkspdRect = new Rectangle(0, 0, 0, 0);
		spdRect = new Rectangle(0, 0, 0, 0);
	}

	public void update(int delta, GameContainer gc) {
		buyHPButton.update(gc);
		buyPowerButton.update(gc);
		buyAttackSpeedButton.update(gc);
		buyMoveSpeedButton.update(gc);

		if (buyHPButton.isClicked()) {
			if (Game.getGame().getGameShop().buyItem(0) && canClickBuyButton()) {
				Game.getGame().getGameShop().getFloatingTexts().add(new FloatingText(35, 160, 0));
			}
		}

		if (buyPowerButton.isClicked() && canClickBuyButton()) {
			if (Game.getGame().getGameShop().buyItem(1)) {
				Game.getGame().getGameShop().getFloatingTexts().add(new FloatingText(35, 160, 1));
			}
		}

		if (buyAttackSpeedButton.isClicked()
				&& !Game.getGame().getGameShop().isMaxed(Game.getGame().getPlayer(), 0)
				&& canClickBuyButton()) {
			if (Game.getGame().getGameShop().buyItem(2)) {
				Game.getGame().getGameShop().getFloatingTexts().add(new FloatingText(35, 160, 2));
			}
		}

		if (buyMoveSpeedButton.isClicked()
				&& !Game.getGame().getGameShop().isMaxed(Game.getGame().getPlayer(), 1)
				&& canClickBuyButton()) {
			if (Game.getGame().getGameShop().buyItem(3)) {
				Game.getGame().getGameShop().getFloatingTexts().add(new FloatingText(35, 160, 3));
			}
		}

		if (buyButtonTimer > 0) {
			buyButtonTimer -= delta;
		}

		if (buyButtonTimer <= 0) {
			buyButtonTimer = 0;
		}

		setButtons();
		setTipRects();
	}

	public void render(Graphics g, GameContainer gc) {

		g.setColor(new Color(28, 61, 113));
		g.fillRect(x, y, 100, 51);

		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(x, y + 3, 100, 9);
		g.fillRect(x, y + 15, 100, 9);
		g.fillRect(x, y + 27, 100, 9);
		g.fillRect(x, y + 39, 100, 9);

		g.setColor(Color.white);
		g.drawRect(x, y, 100, 51);


		Res.bitFont.drawString(x + 14, y + 5, ShopInfo.HP_PRICE + "",
				Game.getGame().getGameShop().canBuyColor(ShopInfo.HP_PRICE));

		Res.bitFont.drawString(x + 44, y + 5, "+" + Math.round(ShopInfo.HP_ADD_VALUE), Color.green);


		Res.bitFont.drawString(x + 14, y + 17, ShopInfo.POWER_PRICE + "",
				Game.getGame().getGameShop().canBuyColor(ShopInfo.POWER_PRICE));
	
		Res.bitFont.drawString(x + 44, y + 17, "+" + Math.round(ShopInfo.POWER_ADD_VALUE) + "%", Color.green);


		Res.bitFont.drawString(x + 14, y + 29, ShopInfo.ATTACKSPEED_PRICE + "",
				Game.getGame().getGameShop().canBuyColor(ShopInfo.ATTACKSPEED_PRICE));

		Res.bitFont.drawString(x + 44, y + 29, "+" + Math.round(ShopInfo.ATTACKSPEED_ADD_VALUE) + "%", Color.green);


		Res.bitFont.drawString(x + 14, y + 41, ShopInfo.MOVESPEED_PRICE + "",
				Game.getGame().getGameShop().canBuyColor(ShopInfo.MOVESPEED_PRICE));

		Res.bitFont.drawString(x + 44, y + 41, "+" + Res.getMoveSpeedDisplay(ShopInfo.MOVESPEED_ADD_VALUE), Color.green);

		buyHPButton.renderScaleSelf(g, 0.5f);
		buyPowerButton.renderScaleSelf(g, 0.5f);
		buyAttackSpeedButton.renderScaleSelf(g, 0.5f);
		buyMoveSpeedButton.renderScaleSelf(g, 0.5f);

		if (hpRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			renderToolTip(g, 0);
		}
		if (powerRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			renderToolTip(g, 1);
		}
		if (atkspdRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			renderToolTip(g, 2);
		}
		if (spdRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			renderToolTip(g, 3);
		}
	}

	public void renderToolTip(Graphics g, int toolTip) {

		g.setColor(new Color(28, 61, 113));
		g.fillRect(x, y + 53, 100, 22);
		g.setColor(Color.white);
		g.drawRect(x, y + 53, 100, 22);

		if (toolTip == 0) {
			Res.bitFont.drawString(x + 2, y + 55, "Health is a great way");
			Res.bitFont.drawString(x + 2, y + 61, "to build defensively.");
		}
		if (toolTip == 1) {
			Res.bitFont.drawString(x + 2, y + 55, "Power gives abilities");
			Res.bitFont.drawString(x + 2, y + 61, "stronger damage and");
			Res.bitFont.drawString(x + 2, y + 67, "stronger heals.");
		}
		if (toolTip == 2) {
			Res.bitFont.drawString(x + 2, y + 55, "Attack Speed is");
			Res.bitFont.drawString(x + 2, y + 61, "great for classes who");
			Res.bitFont.drawString(x + 2, y + 67, "focus on auto attacking.");
		}
		if (toolTip == 3) {
			Res.bitFont.drawString(x + 2, y + 55, "Move Speed is great");
			Res.bitFont.drawString(x + 2, y + 61, "for becoming faster to");
			Res.bitFont.drawString(x + 2, y + 67, "dodge attacks easier.");
		}
	}

	public void setButtons() {
		buyHPButton.setX(x + 75);
		buyHPButton.setY(y + 4);

		buyPowerButton.setX(x + 75);
		buyPowerButton.setY(y + 16);

		buyAttackSpeedButton.setX(x + 75);
		buyAttackSpeedButton.setY(y + 28);

		buyMoveSpeedButton.setX(x + 75);
		buyMoveSpeedButton.setY(y + 40);

		if (Game.getGame().getGameShop().isMaxed(Game.getGame().getPlayer(), 0)) {
			buyAttackSpeedButton.setText("Max");
		} else {
			buyAttackSpeedButton.setText("Buy");
		}
		if (Game.getGame().getGameShop().isMaxed(Game.getGame().getPlayer(), 1)) {
			buyMoveSpeedButton.setText("Max");
		} else {
			buyMoveSpeedButton.setText("Buy");
		}

	}

	public void setTipRects() {

		hpRect.setX(x);
		hpRect.setY(y + 3);
		hpRect.setWidth(100);
		hpRect.setHeight(9);

		powerRect.setX(x);
		powerRect.setY(y + 15);
		powerRect.setWidth(100);
		powerRect.setHeight(9);

		atkspdRect.setX(x);
		atkspdRect.setY(y + 27);
		atkspdRect.setWidth(100);
		atkspdRect.setHeight(9);

		spdRect.setX(x);
		spdRect.setY(y + 39);
		spdRect.setWidth(100);
		spdRect.setHeight(9);

	}

	public boolean canClickBuyButton() {
		if (buyButtonTimer <= 0) {
			buyButtonTimer = buyButtonTime;
			return true;
		}
		return false;
	}
}
