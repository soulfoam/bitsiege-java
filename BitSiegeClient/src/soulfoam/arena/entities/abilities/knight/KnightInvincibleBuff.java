package soulfoam.arena.entities.abilities.knight;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.knight.KnightInvincibleBuffInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class KnightInvincibleBuff extends Ability {

	public KnightInvincibleBuff(int gameID, Entity player) {

		abilityID = AbilityInfo.KNIGHTINVINCIBLEBUFF;

		this.gameID = gameID;

		this.player = player;

		coolDown = KnightInvincibleBuffInfo.COOLDOWN;
		destroyTimer = KnightInvincibleBuffInfo.DESTROY_TIMER;

		width = KnightInvincibleBuffInfo.WIDTH;
		height = KnightInvincibleBuffInfo.HEIGHT;
		hitBoxWidth = KnightInvincibleBuffInfo.HITBOX_WIDTH;
		hitBoxHeight = KnightInvincibleBuffInfo.HITBOX_HEIGHT;

		skillIcon = Res.KNIGHT_RESOURCE.KNIGHT_HOTBAR_ICONS[3];
		soundEffect = Res.KNIGHT_RESOURCE.SFX_KNIGHT[4];

		x = player.getX() - 4;
		y = player.getY() - 7;

	}

	public void update(int delta) {

		player.setCanBeDamaged(false);

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}

		x = player.getX() - 4;
		y = player.getY() - 7;

	}

	public void render(Graphics g) {

		if (player.getCurrentAction() == EntityInfo.IDLE) {
			player.getAnimation().getIdleAnimation().draw(player.getRenderX() - 12, player.getRenderY() - 11, width,
					height, new Color(50, 50, 50, 120));
		} else if (player.getCurrentAction() == EntityInfo.WALKING) {
			player.getAnimation().getWalkAnimation().draw(player.getRenderX() - 12, player.getRenderY() - 11, width,
					height, new Color(50, 50, 50, 120));
		} else if (player.getCurrentAction() == EntityInfo.SPINNING) {
			player.getAnimation().getSpinAnimation().draw(player.getRenderX() - 12, player.getRenderY() - 11, width,
					height, new Color(50, 50, 50, 120));
		}

	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void removeThis() {
		player.setCanBeDamaged(true);
		Game.getGame().removeAbility(this);
	}

}
