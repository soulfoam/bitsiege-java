package soulfoam.arena.entities.gameshop;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.gameinfo.GameInfo;

import soulfoam.arenashared.main.gameinfo.ShopInfo;
import soulfoam.arenashared.main.opcode.OPCode;

public class GameShop {

	private ArrayList<FloatingText> floatingTexts = new ArrayList<FloatingText>();

	private StatUpgradeUI statUpgradeUI;
	private StatUI stateUI;
	private PlayerChallengerUI playerChallengerUI;

	private float x, y;

	public GameShop() {
		x = 85;
		y = 42;

		stateUI = new StatUI(85, 43);
		statUpgradeUI = new StatUpgradeUI(135, 43);
		playerChallengerUI = new PlayerChallengerUI(88, 16);
	}

	public void update(GameContainer gc, int delta) {

		stateUI.update(delta, gc);
		statUpgradeUI.update(delta, gc);
		playerChallengerUI.update(delta, gc);

		for (int i = 0; i < getFloatingTexts().size(); i++) {
			getFloatingTexts().get(i).update(gc);
			if (getFloatingTexts().get(i).getRemoveTimer() > 0) {
				getFloatingTexts().get(i).setRemoveTimer(getFloatingTexts().get(i).getRemoveTimer() - delta);
			}
			if (getFloatingTexts().get(i).getRemoveTimer() <= 0) {
				getFloatingTexts().remove(getFloatingTexts().get(i));
			}
		}
		


		//getFloatingTexts().clear();
		

	}

	public void render(Graphics g, GameContainer gc) {

		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(0, 0, GameInfo.RES_WIDTH, GameInfo.RES_HEIGHT);

		g.setColor(new Color(0, 0, 0, 180));
		g.fillRect(x, y - 25, 150, 23);
		g.setColor(Color.white);
		g.drawRect(x, y - 25, 150, 23);

		float centerOfYAD = Res.bitFont.getWidth("Buy your items and upgrades!");

		Res.bitFont.drawString(GameInfo.RES_WIDTH / 2 - centerOfYAD + 1, y - 22, "Buy your items and upgrades!",
				Color.yellow);

		stateUI.render(g, gc);
		statUpgradeUI.render(g, gc);
		playerChallengerUI.render(g, gc);

		g.setColor(new Color(0, 0, 0, 180));
		g.fillRect(x, y + 80, 150, 18);
		g.setColor(Color.white);
		g.drawRect(x, y + 80, 150, 18);


		for (int i = 0; i < getFloatingTexts().size(); i++) {
			getFloatingTexts().get(i).render(g);
		}
	}

	public boolean isMaxed(Entity player, int stat) {
		if (player != null) {
			if (stat == 0) {
				if (EntityInfo.getAttackSpeed(player.getAbilityBasicAttackCDTime(),
						player.getAttackSpeedBuffAmount()) >= EntityInfo.ATTACKSPEED_CAP) {
					return true;
				}
			}
			if (stat == 1) {
				if (player.getMoveSpeed() >= EntityInfo.MOVESPEED_CAP) {
					return true;
				}
			}
		}
		return false;
	}

	public Color canBuyColor(int price) {

		if (Game.getGame().getPlayer().getGold() >= price) {
			return new Color(0, 255, 0);
		} else {
			return new Color(255, 0, 0);
		}

	}

	public int goldFromStats(Entity player) {
		int totalGold = 0;

		totalGold += player.getHealthBuffAmount() / 20 * ShopInfo.HP_PRICE;
		// System.out.println(totalGold + " after hp");
		totalGold += player.getPowerBuffAmount() / 2 * ShopInfo.POWER_PRICE;
		// System.out.println(totalGold + " after dmg");
		totalGold += player.getAttackSpeedBuffAmount() * ShopInfo.ATTACKSPEED_PRICE;
		// System.out.println(totalGold + " after attakck speed");
		totalGold += player.getMoveSpeedBuffAmount() / 0.001f * ShopInfo.MOVESPEED_PRICE;
		// System.out.println(totalGold + " after movespeed");
		return totalGold;
	}

	public boolean buyItem(int itemID) {
		int itemPrice = 0;
		if (itemID == 0) {
			itemPrice = ShopInfo.HP_PRICE;
		}
		if (itemID == 1) {
			itemPrice = ShopInfo.POWER_PRICE;
		}
		if (itemID == 2) {
			itemPrice = ShopInfo.ATTACKSPEED_PRICE;
		}
		if (itemID == 3) {
			itemPrice = ShopInfo.MOVESPEED_PRICE;
		}
		if (Game.getGame().getPlayer().getGold() >= itemPrice) {
			Game.getGame().getClient().sendReliableData(OPCode.OP_BUYITEM + itemID);
			return true;
		}
		return false;
	}

	public ArrayList<FloatingText> getFloatingTexts() {
		return floatingTexts;
	}

	public void setFloatingTexts(ArrayList<FloatingText> floatingTexts) {
		this.floatingTexts = floatingTexts;
	}

}
