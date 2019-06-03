package soulfoam.arena.entities.abilities.archer;

import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.archer.ArcherFireArrowInfo;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class ArcherFireArrowSwitch extends Ability {

	public ArcherFireArrowSwitch(int gameID, Entity player) {

		abilityID = AbilityInfo.ARCHERFIREARROWSWITCH;

		this.gameID = gameID;
		this.player = player;
		coolDown = ArcherFireArrowInfo.COOLDOWN;

		skillIcon = Res.ARCHER_RESOURCE.ARCHER_HOTBAR_ICONS[1];

	}

	public void update(int delta) {
		removeThis();
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public float getTotalDamage() {
		float dmg = ArcherFireArrowInfo.DAMAGE;
		return EntityInfo.getOverallPower(dmg, Game.getGame().getPlayer().getPowerBuffAmount());
	}

}
