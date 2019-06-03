package soulfoam.arena.entities.abilities.warlock;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockTowerAttackInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockTowerInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class WarlockTower extends Ability {

	public WarlockTower(int gameID, float x, float y, Entity player) {

		abilityID = AbilityInfo.WARLOCKTOWER;

		this.gameID = gameID;
		this.x = x;
		this.y = y;

		this.player = player;

		coolDown = WarlockTowerInfo.COOLDOWN;
		destroyTimer = WarlockTowerInfo.DESTROY_TIMER;

		width = WarlockTowerInfo.WIDTH;
		height = WarlockTowerInfo.HEIGHT;
		hitBoxWidth = WarlockTowerInfo.HITBOX_WIDTH;
		hitBoxHeight = WarlockTowerInfo.HITBOX_HEIGHT;

		skillIcon = Res.WARLOCK_RESOURCE.WARLOCK_HOTBAR_ICONS[0];
		soundEffect = Res.WARLOCK_RESOURCE.SFX_WARLOCK[1];
		animSpeed = 55;

		animation = new Animation(player.getSkin().getWarlockSkin().getTower(), animSpeed);

	}

	public void update(int delta) {

		if (loopSound) {
			if (!soundEffect.playing()) {
				Game.getGame().getSoundSystem().playSound(soundEffect, soundEffectVolume);
			}
		}

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			soundEffect.stop();
			removeThis();
		}

	}

	public float getRenderX() {
		return Res.roundForRendering(x);
	}

	public float getRenderY() {
		return Res.roundForRendering(y);
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public float getTotalDamage() {
		float dmg = WarlockTowerAttackInfo.DAMAGE * 4
				* (WarlockTowerInfo.DESTROY_TIMER / WarlockTowerInfo.ATTACK_TIMER);
		return EntityInfo.getOverallPower(dmg, Game.getGame().getPlayer().getPowerBuffAmount());
	}

	public void playSoundEffect() {
		loopSound = true;
	}

}
