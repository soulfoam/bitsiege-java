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
import soulfoam.arenashared.main.abilityinfo.shaman.ShamanBuffTotemInfo;

public class ShamanBuffTotem extends Ability {

	public float damageModifier;

	private boolean addedTotem;

	public ShamanBuffTotem(int gameID, float x, float y, Entity player) {

		abilityID = AbilityInfo.SHAMANBUFFTOTEM;
		renderLayer = Ability.RENDER_GROUND;
		tag = AbilityInfo.AOE;

		this.gameID = gameID;
		this.x = x + 4;
		this.y = y + 11;

		this.player = player;

		coolDown = ShamanBuffTotemInfo.COOLDOWN;
		destroyTimer = ShamanBuffTotemInfo.DESTROY_TIMER;

		width = ShamanBuffTotemInfo.WIDTH;
		height = ShamanBuffTotemInfo.HEIGHT;
		hitBoxWidth = ShamanBuffTotemInfo.HITBOX_WIDTH;
		hitBoxHeight = ShamanBuffTotemInfo.HITBOX_HEIGHT;

		skillIcon = Res.SHAMAN_RESOURCE.SHAMAN_HOTBAR_ICONS[0];
		soundEffect = Res.SHAMAN_RESOURCE.SFX_SHAMAN[1];

		damageModifier = Res.percentOf(player.getBaseHealth(), ShamanBuffTotemInfo.DAMAGE_BUFF_AMOUNT);

	}

	public void initForGame() {
		if (Game.getGame().getPlayer().getTeam() == player.getTeam()) {
			animation = new Animation(Res.SHAMAN_RESOURCE.BUFF_TOTEM_RADIUS_ALLY, animSpeed);
		} else {
			animation = new Animation(Res.SHAMAN_RESOURCE.BUFF_TOTEM_RADIUS_ENEMY, animSpeed);
		}
	}

	public void update(int delta) {

		handleCollision(delta);

		if (!addedTotem) {
			BuffTotem bt = new BuffTotem(x, y, player);
			bt.setGameID(Res.rand.nextInt());
			Game.getGame().getClientFunctions().getCommandHandler().addCommand(new SpawnAbilityCommand(bt));
			addedTotem = true;
		}

		if (destroyTimer > 0) {
			destroyTimer -= delta;
		}

		if (destroyTimer <= 0) {
			removeThis();
		}

	}

	public void handleCollision(int delta) {

		Entity[] tempList = new Entity[Game.getGame().getPlayers().size()];
		Game.getGame().getPlayers().toArray(tempList);
		for (Entity p : tempList) {
			if (p != null && player != null) {
				if (getBounds().contains(p.getBoundsFeet())) {
					if (!p.isDead() && p.getTeam() == player.getTeam()) {
						if (!collision.getPlayersHit().contains(p) && !collision.getPlayersCycled().contains(p)) {
							collision.getPlayersHit().add(p);
						}
					}
				}
			}
		}

		Entity[] tempListPH = new Entity[collision.getPlayersHit().size()];
		collision.getPlayersHit().toArray(tempListPH);

		if (!collision.getPlayersHit().isEmpty()) {
			for (Entity p : tempListPH) {
				if (p != null && player != null) {
					if (p.getPlayerID() == player.getPlayerID()) {
						p.setPowerBuffTempAmount(p.getPowerBuffTempAmount() + damageModifier * 2);
					} else {
						p.setPowerBuffTempAmount(p.getPowerBuffTempAmount() + damageModifier);
					}

					collision.getPlayersCycled().add(p);
					collision.getPlayersHit().remove(p);
				}
			}
		}

		Entity[] tempListPC = new Entity[collision.getPlayersCycled().size()];
		collision.getPlayersCycled().toArray(tempListPC);

		if (!collision.getPlayersCycled().isEmpty()) {
			for (Entity p : tempListPC) {
				if (p != null && player != null) {
					if (!getBounds().contains(p.getBoundsFeet())) {
						if (p.getPlayerID() == player.getPlayerID()) {
							p.setPowerBuffTempAmount(p.getPowerBuffTempAmount() - damageModifier * 2);
						} else {
							p.setPowerBuffTempAmount(p.getPowerBuffTempAmount() - damageModifier);
						}
						collision.getPlayersCycled().remove(p);
						collision.getPlayersHit().remove(p);
					}
				}
			}
		}

	}

	public float getRenderX() {
		return Res.roundForRendering(x - 88);
	}

	public float getRenderY() {
		return Res.roundForRendering(y - 48);
	}

	public void render(Graphics g) {
		animation.draw(getRenderX(), getRenderY(), width, height, new Color(255, 255, 255, 100));
	}

	public Shape getBounds() {
		return new Ellipse(x, y, hitBoxWidth, hitBoxHeight);
	}

	public void removeThis() {
		Entity[] tempListPC = new Entity[collision.getPlayersCycled().size()];
		collision.getPlayersCycled().toArray(tempListPC);

		if (!collision.getPlayersCycled().isEmpty()) {
			for (Entity p : tempListPC) {
				if (p != null && player != null) {
					if (p.getPlayerID() == player.getPlayerID()) {
						p.setPowerBuffTempAmount(p.getPowerBuffTempAmount() - damageModifier * 2);
					} else {
						p.setPowerBuffTempAmount(p.getPowerBuffTempAmount() - damageModifier);
					}
				}
			}
		}

		Game.getGame().removeAbility(this);
	}

}

class BuffTotem extends Ability {

	public float particleTime = 0.25f * 1000;
	public float particleTimer = particleTime;

	public BuffTotem(float x, float y, Entity player) {
		tag = AbilityInfo.FX;

		this.x = x - 8;
		this.y = y - 16;

		width = 16;
		height = 16;
		hitBoxWidth = 16;
		hitBoxHeight = 14;

		damage = 6;
		coolDown = 100;

		destroyTimer = ShamanBuffTotemInfo.DESTROY_TIMER;
		this.player = player;

		animation = new Animation(player.getSkin().getShamanSkin().getBufftotem(), 100);

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
			Game.getGame().getParticleSystem().addTotemParticle(x + 5, y, 0);
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
