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
import soulfoam.arenashared.main.abilityinfo.shaman.ShamanShieldTotemInfo;

public class ShamanShieldTotem extends Ability {

	private boolean addedTotem;

	public float shieldTime = ShamanShieldTotemInfo.SHIELD_TIMER;

	public ShamanShieldTotem(int gameID, float x, float y, Entity player) {

		abilityID = AbilityInfo.SHAMANSHIELDTOTEM;
		renderLayer = Ability.RENDER_GROUND;
		tag = AbilityInfo.AOE;

		this.gameID = gameID;
		this.x = x;
		this.y = y;

		this.player = player;

		coolDown = ShamanShieldTotemInfo.COOLDOWN;
		destroyTimer = ShamanShieldTotemInfo.DESTROY_TIMER;

		width = ShamanShieldTotemInfo.WIDTH;
		height = ShamanShieldTotemInfo.HEIGHT;
		hitBoxWidth = ShamanShieldTotemInfo.HITBOX_WIDTH;
		hitBoxHeight = ShamanShieldTotemInfo.HITBOX_HEIGHT;

		skillIcon = Res.SHAMAN_RESOURCE.SHAMAN_HOTBAR_ICONS[1];
		soundEffect = Res.SHAMAN_RESOURCE.SFX_SHAMAN[1];

	}

	public void initForGame() {
		if (Game.getGame().getPlayer().getTeam() == player.getTeam()) {
			animation = new Animation(Res.SHAMAN_RESOURCE.SHIELD_TOTEM_RADIUS_ALLY, animSpeed);
		} else {
			animation = new Animation(Res.SHAMAN_RESOURCE.SHIELD_TOTEM_RADIUS_ENEMY, animSpeed);
		}
	}

	public void update(int delta) {

		if (!addedTotem) {
			ShieldTotem st = new ShieldTotem(x, y, player);
			st.setGameID(Res.rand.nextInt());
			Game.getGame().getClientFunctions().getCommandHandler().addCommand(new SpawnAbilityCommand(st));
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
		return Res.roundForRendering(x - 66);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 36);
	}

	public void render(Graphics g) {
		animation.draw(getRenderX(), getRenderY(), width, height, new Color(255, 255, 255, 100));
	}

	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

}

class ShieldTotem extends Ability {

	public float particleTime = 0.25f * 1000;
	public float particleTimer = particleTime;

	public ShieldTotem(float x, float y, Entity player) {
		tag = AbilityInfo.FX;

		this.x = x - 8;
		this.y = y - 16;

		width = 16;
		height = 16;
		hitBoxWidth = 16;
		hitBoxHeight = 14;
		damage = 6;
		coolDown = 100;

		destroyTimer = ShamanShieldTotemInfo.DESTROY_TIMER;
		this.player = player;

		animation = new Animation(player.getSkin().getShamanSkin().getShieldTotem(), 100);

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
