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
import soulfoam.arenashared.main.abilityinfo.shaman.ShamanDebuffTotemInfo;

public class ShamanDebuffTotem extends Ability {

	private boolean addedTotem;

	public ShamanDebuffTotem(int gameID, float x, float y, Entity player) {

		abilityID = AbilityInfo.SHAMANDEBUFFTOTEM;
		renderLayer = Ability.RENDER_GROUND;
		tag = AbilityInfo.AOE;

		this.gameID = gameID;
		this.x = x;
		this.y = y;

		this.player = player;

		coolDown = ShamanDebuffTotemInfo.COOLDOWN;
		destroyTimer = ShamanDebuffTotemInfo.DESTROY_TIMER;
		slowDuration = ShamanDebuffTotemInfo.SLOW_DURATION;
		stunDuration = ShamanDebuffTotemInfo.STUN_DURATION;

		width = ShamanDebuffTotemInfo.WIDTH;
		height = ShamanDebuffTotemInfo.HEIGHT;
		hitBoxWidth = ShamanDebuffTotemInfo.HITBOX_WIDTH;
		hitBoxHeight = ShamanDebuffTotemInfo.HITBOX_HEIGHT;

		skillIcon = Res.SHAMAN_RESOURCE.SHAMAN_HOTBAR_ICONS[2];
		soundEffect = Res.SHAMAN_RESOURCE.SFX_SHAMAN[1];

	}

	public void initForGame() {
		if (Game.getGame().getPlayer().getTeam() == player.getTeam()) {
			animation = new Animation(Res.SHAMAN_RESOURCE.DEBUFF_TOTEM_RADIUS_ALLY, animSpeed);
		} else {
			animation = new Animation(Res.SHAMAN_RESOURCE.DEBUFF_TOTEM_RADIUS_ENEMY, animSpeed);
		}
	}

	public void update(int delta) {

		if (!addedTotem) {
			DebuffTotem dbt = new DebuffTotem(x, y, player);
			dbt.setGameID(Res.rand.nextInt());
			Game.getGame().getClientFunctions().getCommandHandler().addCommand(new SpawnAbilityCommand(dbt));
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
		return Res.roundForRendering(x - 37);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 20);
	}

	public void render(Graphics g) {

		g.setColor(new Color(255, 255, 255, 50));

		if (Game.getGame().getPlayer().getTeam() != player.getTeam()) {
			g.setColor(new Color(255, 100, 100, 150));
		}

		animation.draw(getRenderX(), getRenderY(), width, height, new Color(255, 255, 255, 100));

	}

	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

}

class DebuffTotem extends Ability {

	public float particleTime = 0.25f * 1000;
	public float particleTimer = particleTime;

	public DebuffTotem(float x, float y, Entity player) {
		tag = AbilityInfo.FX;

		this.x = x - 8;
		this.y = y - 14;

		width = 16;
		height = 16;
		hitBoxWidth = 16;
		hitBoxHeight = 14;
		damage = 6;
		coolDown = 100;

		destroyTimer = ShamanDebuffTotemInfo.DESTROY_TIMER;
		this.player = player;

		animation = new Animation(player.getSkin().getShamanSkin().getDebuffTotem(), 100);

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
			Game.getGame().getParticleSystem().addTotemParticle(x + 5, y, 2);
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
