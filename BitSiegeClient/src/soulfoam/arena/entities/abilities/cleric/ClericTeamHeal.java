package soulfoam.arena.entities.abilities.cleric;

import org.newdawn.slick.Animation;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.command.client.SpawnAbilityCommand;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.world.Tile;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericTeamHealInfo;

public class ClericTeamHeal extends Ability {

	public ClericTeamHeal(int gameID, Entity player) {

		abilityID = AbilityInfo.CLERICTEAMHEAL;

		this.gameID = gameID;

		this.player = player;

		coolDown = ClericTeamHealInfo.COOLDOWN;
		damage = ClericTeamHealInfo.DAMAGE;

		skillIcon = Res.CLERIC_RESOURCE.CLERIC_HOTBAR_ICONS[3];
		soundEffect = Res.CLERIC_RESOURCE.SFX_CLERIC[6];
	}

	public void update(int delta) {

		playSoundEffect();

		Entity[] tempList = new Entity[Game.getGame().getPlayers().size()];
		Game.getGame().getPlayers().toArray(tempList);
		for (Entity p : tempList) {
			if (p != null) {
				if (p.getTeam() == player.getTeam() && !collision.getPlayersHit().contains(p)) {
					HealAnimation ha = new HealAnimation(p);
					ha.setGameID(Res.rand.nextInt());
					Game.getGame().getClientFunctions().getCommandHandler().addCommand(new SpawnAbilityCommand(ha));
					collision.getPlayersHit().add(p);
				}
			}
		}

		removeThis();

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}
	}

	public float getRenderX() {
		return Res.roundForRendering(x - 4);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 4);
	}

	public Shape getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void playSoundEffect() {
		if (!playedSoundOnce && Game.getGame().getPlayer().getTeam() == player.getTeam()) {
			Game.getGame().getSoundSystem().playSound(soundEffect, 1);
			playedSoundOnce = true;
		}
	}

	class HealAnimation extends Ability {

		public HealAnimation(Entity player) {
			width = 16;
			height = 32;
			hitBoxWidth = 16;
			hitBoxHeight = 41;

			renderLayer = Ability.RENDER_SKY;

			destroyTimer = 1.5f * 1000;
			this.player = player;

			animSpeed = 40;
			animation = new Animation(Res.CLERIC_RESOURCE.ULT_HEAL, animSpeed);

		}

		public void update(int delta) {

			x = player.getX() - 4;
			y = player.getY() - Tile.TILE_SIZE * 3;

			if (destroyTimer > 0) {
				destroyTimer -= delta;
			}

			if (destroyTimer <= 0) {
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

		public void playSoundEffect() {

		}
	}
}
