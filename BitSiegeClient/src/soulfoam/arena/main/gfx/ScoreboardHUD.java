package soulfoam.arena.main.gfx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.game.Game;
import soulfoam.arena.main.resources.Res;

public class ScoreboardHUD extends BaseHUDUI {

	private ArrayList<Entity> scoreboardPlayers = new ArrayList<Entity>();

	private Rectangle kdaSection;
	private Rectangle pointsSection;
	private Rectangle pingSection;

	public ScoreboardHUD(float x, float y) {
		this.x = x;
		this.y = y;
		kdaSection = new Rectangle(x + 152, y + 6, 49, 7);
		pointsSection = new Rectangle(x + 205, y + 6, 31, 7);
		pingSection = new Rectangle(x + 247, y + 6, 20, 7);
	}

	public void update(GameContainer gc) {

	}

	public void renderPlayers(Graphics g) {
		Color opaq = new Color(255, 255, 255, 200);
		int teamDDisplay = 0;
		int teamADisplay = 0;

		scoreboardPlayers = Game.getGame().getPlayers();

		Collections.sort(scoreboardPlayers, new Comparator<Entity>() {

			public int compare(Entity player2, Entity player1) {
				Integer p1K = player1.getKills() * 2 + player1.getAssists();
				Integer p2K = player2.getKills() * 2 + player2.getAssists();

				int pK = p1K.compareTo(p2K);

				if (pK != 0) {
					return pK;
				} else {
					Integer p1D = player1.getDeaths();
					Integer p2D = player2.getDeaths();
					return p2D.compareTo(p1D);
				}

			}
		});

		for (Entity p : scoreboardPlayers) {

			if (p.getTeam() == Res.TEAM_D) {

				teamDDisplay += 21;
				Res.UI_RESOURCE.SCOREBOARD_BAR_BLUE.draw(x, y + teamDDisplay, opaq);

				p.getAnimation().getPortrait().draw(x + 1, y + teamDDisplay + 1);
				Res.bitFont.drawString(x + 20, y + teamDDisplay + 7, p.getUsername(), p.getUsernameColor());
				Res.bitFont.drawString(x + 141, y + teamDDisplay + 7, p.getLevel() + "", Color.white);
				String kdaString = p.getKills() + "/" + p.getDeaths() + "/" + p.getAssists();
				Res.bitFont.drawString(kdaSection.getX() - Res.getTextCenter(kdaString) + kdaSection.getWidth() / 2, y + teamDDisplay + 7, kdaString, Color.white);
				Res.bitFont.drawString(pointsSection.getX() - Res.getTextCenter(p.getPoints() + "") + pointsSection.getWidth() / 2, y + teamDDisplay + 7, p.getPoints() + "", Color.white);
				Res.bitFont.drawString(pingSection.getX() - Res.getTextCenter(p.getPing() + "") + pingSection.getWidth() / 2, y + teamDDisplay + 7, p.getPing() + "", Res.getPingColor(p.getPing()));
				
				if (p.isDead()) {
					g.setColor(new Color(0, 0, 0, 180));
					g.fillRect(x + 1, y + teamDDisplay + 1, 16, 16);
					Res.bitFont.drawString(x + 1 - Res.getTextCenter((int)p.getRespawnTimer() + "") + 15 / 2, y + teamDDisplay + 7, (int)p.getRespawnTimer() + "", Color.red);
				}
				
			} 
			else if (p.getTeam() == Res.TEAM_A) {
				int teamAMod = 108;
				
				teamADisplay += 21;
				Res.UI_RESOURCE.SCOREBOARD_BAR_ORANGE.draw(x, y + teamADisplay + teamAMod, opaq);

				p.getAnimation().getPortrait().draw(x + 1, y + teamADisplay + 1 + teamAMod);
				Res.bitFont.drawString(x + 20, y + teamADisplay + 7 + teamAMod, p.getUsername(), p.getUsernameColor());
				Res.bitFont.drawString(x + 141, y + teamADisplay + 7 + teamAMod, p.getLevel() + "", Color.white);
				String kdaString = p.getKills() + "/" + p.getDeaths() + "/" + p.getAssists();
				Res.bitFont.drawString(kdaSection.getX() - Res.getTextCenter(kdaString) + kdaSection.getWidth() / 2, y + teamADisplay + 7 + teamAMod, kdaString, Color.white);
				Res.bitFont.drawString(pointsSection.getX() - Res.getTextCenter(p.getPoints() + "") + pointsSection.getWidth() / 2, y + teamADisplay + 7 + teamAMod, p.getPoints() + "", Color.white);
				Res.bitFont.drawString(pingSection.getX() - Res.getTextCenter(p.getPing() + "") + pingSection.getWidth() / 2, y + teamADisplay + 7 + teamAMod, p.getPing() + "", Res.getPingColor(p.getPing()));
				
				if (p.isDead()) {
					g.setColor(new Color(0, 0, 0, 180));
					g.fillRect(x + 1, y + teamADisplay + 1 + teamAMod, 16, 16);
					Res.bitFont.drawString(x + 1 - Res.getTextCenter((int)p.getRespawnTimer() + "") + 15 / 2, y + teamADisplay + 7 + teamAMod, (int)p.getRespawnTimer() + "", Color.red);
				}

			}
		}
	}

	public void render(Graphics g, GameContainer gc) {

		Res.UI_RESOURCE.SCOREBOARD.draw(0, -8, new Color(255, 255, 255, 200));

		renderPlayers(g);

	}

}
