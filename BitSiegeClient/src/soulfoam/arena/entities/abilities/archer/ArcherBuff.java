package soulfoam.arena.entities.abilities.archer;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.archer.ArcherBuffInfo;

public class ArcherBuff extends Ability {

	private int glowTimer = 0;
	private boolean addGlowTime = true;
	private int glowSpeed = 15;

	public ArcherBuff(int gameID, Entity player) {

		abilityID = AbilityInfo.ARCHERBUFF;

		this.gameID = gameID;
		this.player = player;

		coolDown = ArcherBuffInfo.COOLDOWN;
		destroyTimer = ArcherBuffInfo.DESTROY_TIMER;

		width = ArcherBuffInfo.WIDTH;
		height = ArcherBuffInfo.HEIGHT;
		hitBoxWidth = ArcherBuffInfo.HITBOX_WIDTH;
		hitBoxHeight = ArcherBuffInfo.HITBOX_HEIGHT;

		skillIcon = Res.ARCHER_RESOURCE.ARCHER_HOTBAR_ICONS[3];
		soundEffect = Res.ARCHER_RESOURCE.SFX_ARCHER[4];

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

	public void render(Graphics g) {
		Color buffColor = new Color(255, 255, 0, glowTimer);
		if (!player.isStunned()) {
			player.getAnimation().getCurrentActionAnimation().drawFlash(player.getRenderX(), player.getRenderY(),
					player.getWidth(), player.getHeight(), buffColor);
		}
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

}
