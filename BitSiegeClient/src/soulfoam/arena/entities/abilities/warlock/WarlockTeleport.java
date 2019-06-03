package soulfoam.arena.entities.abilities.warlock;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockTeleportInfo;

public class WarlockTeleport extends Ability {

	public WarlockTeleport(int gameID, float x, float y, Entity player) {

		abilityID = AbilityInfo.WARLOCKTELEPORT;

		this.gameID = gameID;
		this.x = x;
		this.y = y;

		this.player = player;

		coolDown = WarlockTeleportInfo.COOLDOWN;
		destroyTimer = WarlockTeleportInfo.DESTROY_TIMER;

		width = WarlockTeleportInfo.WIDTH;
		height = WarlockTeleportInfo.HEIGHT;
		hitBoxWidth = WarlockTeleportInfo.HITBOX_WIDTH;
		hitBoxHeight = WarlockTeleportInfo.HITBOX_HEIGHT;

		skillIcon = Res.WARLOCK_RESOURCE.WARLOCK_HOTBAR_ICONS[2];
		soundEffect = Res.WARLOCK_RESOURCE.SFX_WARLOCK[3];

		animation = new Animation(Res.WARLOCK_RESOURCE.TELEPORT_END, animSpeed);
		animation.setLooping(false);

	}

	public boolean canCast(float x, float y) {
		if (Game.getGame().getWorld().getTile(x, y + 4).isBlocked()
				|| Game.getGame().getWorld().getTile(x, y + 4).getTileBelow().isBlocked()) {
			return false;
		}

		return true;
	}

	public void update(int delta) {

		playSoundEffect();

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}

		x = player.getX();
		y = player.getY();

	}

	public float getRenderX() {
		return Res.roundForRendering(x - 24);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 32);
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void playSoundEffect() {
		if (!playedSoundOnce && player != null && player.isLocalPlayer()) {
			Game.getGame().getSoundSystem().playSound(soundEffect, 1);
			playedSoundOnce = true;
		}
	}

	public float sortByY() {
		return y + 12;
	}

}
