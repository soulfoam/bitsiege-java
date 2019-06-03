package soulfoam.arena.entities.abilities.waterqueen;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.waterqueen.WaterQueenBuffInfo;

public class WaterQueenBuff extends Ability {

	private int glowTimer = 0;
	private boolean addGlowTime = true;
	private int glowSpeed = 5;

	public WaterQueenBuff(int gameID, Entity player) {

		abilityID = AbilityInfo.WATERQUEENWATERBUFF;

		this.gameID = gameID;

		this.player = player;

		coolDown = WaterQueenBuffInfo.COOLDOWN;
		destroyTime = WaterQueenBuffInfo.DESTROY_TIMER;
		destroyTimer = destroyTime;

		width = WaterQueenBuffInfo.WIDTH;
		height = WaterQueenBuffInfo.HEIGHT;
		hitBoxWidth = WaterQueenBuffInfo.HITBOX_WIDTH;
		hitBoxHeight = WaterQueenBuffInfo.HITBOX_HEIGHT;

		skillIcon = Res.WATERQUEEN_RESOURCE.WATERQUEEN_HOTBAR_ICONS[3];
		soundEffect = Res.WATERQUEEN_RESOURCE.SFX_WATERQUEEN[4];

		x = player.getX();
		y = player.getY() + 1;

	}

	public void update(int delta) {

		x = player.getX();
		y = player.getY() + 1;

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}

		if (addGlowTime) {
			glowTimer += glowSpeed;
			if (glowTimer >= 255) {
				addGlowTime = false;
			}
		} else {
			glowTimer -= glowSpeed;
			if (glowTimer <= 40) {
				addGlowTime = true;
			}
		}

	}

	public float getTotalDamage() {
		return player.getBaseHealth() / 2;
	}

	public void render(Graphics g) {

		Color buffColor = new Color(0, 255, 0, glowTimer);

		if (player != null && !player.isStunned()) {
			player.getAnimation().getCurrentActionAnimation().drawFlash(player.getRenderX(), player.getRenderY(),
					player.getWidth(), player.getHeight(), buffColor);
		}
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

}
