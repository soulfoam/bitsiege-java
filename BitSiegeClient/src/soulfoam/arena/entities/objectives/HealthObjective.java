package soulfoam.arena.entities.objectives;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.command.client.SpawnAbilityCommand;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.world.Tile;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.objectiveinfo.ObjectiveInfo;

public class HealthObjective extends Objective {

	private float respawnTime;
	private float respawnTimer;

	private boolean animatedOnce;

	public HealthObjective(float x, float y, byte objectiveTeam) {

		this.objectiveTeam = objectiveTeam;

		this.x = x;
		this.y = y;
		width = Tile.TILE_SIZE * 2;
		height = Tile.TILE_SIZE * 2;
		hitBoxWidth = 4;
		hitBoxHeight = 4;

		health = 600;
		baseHealth = 600;

		animation = new Animation(Res.OBJECTIVE_RESOURCE.HEALTH_ORB, 80);

		if (objectiveTeam == ObjectiveInfo.HEALTHPICKUP_OBJECTIVE) {
			respawnTime = 25 * 1000;
			respawnTimer = respawnTime;
			width = Tile.TILE_SIZE * 2;
			height = Tile.TILE_SIZE * 2;
		}
		if (objectiveTeam == ObjectiveInfo.HEALTHPICKUPSMALL_OBJECTIVE) {
			respawnTime = 20 * 1000;
			respawnTimer = respawnTime;
		}
		if (objectiveTeam == ObjectiveInfo.HEALTHPICKUPPLAYER_OBJECTIVE) {
			respawnTime = 20 * 1000;
			respawnTimer = respawnTime;
		}

	}

	public void update(int delta) {

		if (objectiveTeam == ObjectiveInfo.HEALTHPICKUPPLAYER_OBJECTIVE) {
			if (respawnTimer > 0) {
				respawnTimer -= delta;
			}
			if (respawnTimer <= 0) {
				Game.getGame().getObjectives().remove(this);
			}
		}

		if (beingHeld) {
			if (!animatedOnce && objectiveHolderID != -1) {
				if (Game.getGame().getPlayerObject(objectiveHolderID) != null) {
					HealAnimation ha = new HealAnimation(Game.getGame().getPlayerObject(objectiveHolderID));
					ha.setGameID(Res.rand.nextInt());
					Game.getGame().getClientFunctions().getCommandHandler().addCommand(new SpawnAbilityCommand(ha));
					animatedOnce = true;
				}
			}
		}
		if (!beingHeld) {
			animatedOnce = false;
		}

	}

	public void render(Graphics g) {

		if (!beingHeld) {
			animation.draw(Res.roundForRendering(x - 8), Res.roundForRendering(y - 8), width, height);
			g.setColor(Color.red);
		}
	}

	public void render(Graphics g, float x, float y) {

		animation.draw(Res.roundForRendering(x), Res.roundForRendering(y), width, height);
	}

	public void playAnimation() {
		if (!animatedOnce && objectiveHolderID != -1) {
			if (Game.getGame().getPlayerObject(objectiveHolderID) != null) {
				HealAnimation ha = new HealAnimation(Game.getGame().getPlayerObject(objectiveHolderID));
				ha.setGameID(Res.rand.nextInt());
				Game.getGame().getClientFunctions().getCommandHandler().addCommand(new SpawnAbilityCommand(ha));
				animatedOnce = true;
			}
		}
	}

	public Ellipse getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void takeDamage(float damage, Entity attacker) {

	}

	public void removeObjectiveClient() {
		Game.getGame().getObjectives().remove(this);
	}

	public float sortByY() {
		return y + height;
	}
}

class HealAnimation extends Ability {

	public HealAnimation(Entity player) {
		tag = AbilityInfo.FX;

		width = 32;
		height = 32;
		hitBoxWidth = 24;
		hitBoxHeight = 24;
		renderLayer = Ability.RENDER_FRONT;

		destroyTimer = 1.5f * 1000;
		this.player = player;

		animation = new Animation(Res.OBJECTIVE_RESOURCE.HEALTH_ORB_HEAL, 40);
		soundEffect = Res.SFX_EFFECTS[0];

		x = player.getX() - 8;
		y = player.getY() - 4;

	}

	public void update(int delta) {

		x = player.getX() - 8;
		y = player.getY() - 4;

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

	public void render(Graphics g) {
		animation.draw(getRenderX(), getRenderY(), width, height);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void playSoundEffect() {
		if (!playedSoundOnce && player.isLocalPlayer()) {
			Game.getGame().getSoundSystem().playSound(soundEffect, soundEffectVolume);
			playedSoundOnce = true;
		}
	}

}
