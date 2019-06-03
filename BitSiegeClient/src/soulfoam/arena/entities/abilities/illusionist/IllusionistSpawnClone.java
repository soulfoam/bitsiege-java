package soulfoam.arena.entities.abilities.illusionist;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistCloneInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistSpawnCloneInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class IllusionistSpawnClone extends Ability {

	public IllusionistSpawnClone(int gameID, Entity player) {

		abilityID = AbilityInfo.ILLUSIONISTSPAWNCLONE;

		this.gameID = gameID;

		this.player = player;

		coolDown = IllusionistSpawnCloneInfo.COOLDOWN;

		width = IllusionistSpawnCloneInfo.WIDTH;
		height = IllusionistSpawnCloneInfo.HEIGHT;
		hitBoxWidth = IllusionistSpawnCloneInfo.HITBOX_WIDTH;
		hitBoxHeight = IllusionistSpawnCloneInfo.HITBOX_HEIGHT;

		skillIcon = Res.ILLUSIONIST_RESOURCE.ILLUSIONIST_HOTBAR_ICONS[0];
		soundEffect = Res.ILLUSIONIST_RESOURCE.SFX_ILLUSIONIST[1];

		changeDirectionOnCast = false;
	}

	public void update(int delta) {

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}

	}

	public float getRenderX() {
		return Res.roundForRendering(x - 8);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 4);
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public float getTotalDamage() {
		float dmg = IllusionistCloneInfo.DAMAGE;
		return EntityInfo.getOverallPower(dmg, Game.getGame().getPlayer().getPowerBuffAmount());
	}

}
