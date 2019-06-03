package soulfoam.arena.entities.abilities.shaman;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.Ability;
import soulfoam.arena.main.command.client.SpawnAbilityCommand;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;
import soulfoam.arenashared.main.abilityinfo.shaman.ShamanConfuseTotemInfo;

public class ShamanConfuseTotem extends Ability {

	private boolean addedTotem;

	public ShamanConfuseTotem(int gameID, float x, float y, Entity player) {

		abilityID = AbilityInfo.SHAMANCONFUSETOTEM;
		renderLayer = Ability.RENDER_GROUND;
		tag = AbilityInfo.AOE;

		this.gameID = gameID;
		this.x = x;
		this.y = y;

		this.player = player;

		coolDown = ShamanConfuseTotemInfo.COOLDOWN;
		destroyTimer = ShamanConfuseTotemInfo.DESTROY_TIMER;

		width = ShamanConfuseTotemInfo.WIDTH;
		height = ShamanConfuseTotemInfo.HEIGHT;
		hitBoxWidth = ShamanConfuseTotemInfo.HITBOX_WIDTH;
		hitBoxHeight = ShamanConfuseTotemInfo.HITBOX_HEIGHT;

		skillIcon = Res.SHAMAN_RESOURCE.SHAMAN_HOTBAR_ICONS[3];
		soundEffect = Res.SHAMAN_RESOURCE.SFX_SHAMAN[2];
	}

	public void initForGame() {
		if (Game.getGame().getPlayer().getTeam() == player.getTeam()) {
			animation = new Animation(Res.SHAMAN_RESOURCE.MEGA_TOTEM_RADIUS_ALLY, animSpeed);
		} else {
			animation = new Animation(Res.SHAMAN_RESOURCE.MEGA_TOTEM_RADIUS_ENEMY, animSpeed);
		}
	}

	public void update(int delta) {

		if (!addedTotem) {
			ConfuseTotem ct = new ConfuseTotem(x, y, player);
			ct.setGameID(Res.rand.nextInt());
			Game.getGame().getClientFunctions().getCommandHandler().addCommand(new SpawnAbilityCommand(ct));
			addedTotem = true;
		}

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}

	}

	public float getRenderX() {
		return Res.roundForRendering(x - 50);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 27);
	}

	public void render(Graphics g) {
		if (animation != null) {
			animation.draw(getRenderX(), getRenderY(), width, height, new Color(255, 255, 255, 100));
		}
	}

	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

}

class ConfuseTotem extends Ability {

	public float particleTime = 0.25f * 1000;
	public float particleTimer = particleTime;

	public ConfuseTotem(float x, float y, Entity player) {
		tag = AbilityInfo.FX;

		this.x = x - 8;
		this.y = y - 20;

		width = 16;
		height = 24;
		hitBoxWidth = 16;
		hitBoxHeight = 20;

		damage = 6;
		coolDown = 100;

		destroyTimer = ShamanConfuseTotemInfo.DESTROY_TIMER;
		this.player = player;

		animation = new Animation(player.getSkin().getShamanSkin().getConfuseTotem(), 100);

	}

	public void update(int delta) {

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}

		if (particleTimer > 0) {
			particleTimer -= delta;
		}
		if (particleTimer <= 0) {
			Game.getGame().getParticleSystem().addTotemParticle(x + 5, y, 3);
			particleTimer = particleTime;
		}

	}

	public float getRenderX() {
		return Res.roundForRendering(x);
	}

	public float getRenderY() {
		return Res.roundForRendering(y);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void playSoundEffect() {

	}

	public float sortByY() {
		return y + hitBoxHeight;
	}
}
