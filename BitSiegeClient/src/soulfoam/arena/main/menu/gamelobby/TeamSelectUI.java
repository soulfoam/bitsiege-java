package soulfoam.arena.main.menu.gamelobby;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.entities.LobbyEntity;
import soulfoam.arena.main.menu.PregameLobbyManager;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.main.util.GlowTimer;

public class TeamSelectUI {

	public float x, y;
	public boolean renderMe;

	private Rectangle t0Box;
	private Rectangle t1Box;

	private GlowTimer glowTimer = new GlowTimer(40, 255, 7);

	public TeamSelectUI(float x, float y) {
		this.x = x;
		this.y = y;

		t0Box = new Rectangle(x, y, 125, 49);
		t1Box = new Rectangle(x, y + 91, 125, 49);

	}

	public void enter() {

	}

	public void update(int delta, GameContainer gc, StateBasedGame s) {
		
		glowTimer.update(delta);

	}

	public void render(Graphics g, GameContainer gc) {

		renderTeamDBox(g, gc);
		renderTeamABox(g, gc);

	}

	public void renderTeamDBox(Graphics g, GameContainer gc) {

		if (!PregameLobbyManager.getLobby().getTeamDPlayers().isEmpty()) {
			float y = 3;
			for (LobbyEntity s : PregameLobbyManager.getLobby().getTeamDPlayers()) {

				if (s != null) {
					
					if (s.isReady()) {
						Res.UI_RESOURCE.LOBBY_TEAMD_BAR_READY.draw(t0Box.getX() + 2, t0Box.getY() + y);
					} else {
						Res.UI_RESOURCE.LOBBY_TEAMD_BAR_NOTREADY.draw(t0Box.getX() + 2, t0Box.getY() + y);
					}
					
					Res.bitFont.drawString(t0Box.getX() + 22, t0Box.getY() + y + 6, s.getPlayerName(), s.getNameColor());
					
					s.getClassImage().draw(t0Box.getX() + 3, t0Box.getY() + y + 1);
					
					y += 20;
				}
			}
		}

	}

	public void renderTeamABox(Graphics g, GameContainer gc) {

		if (!PregameLobbyManager.getLobby().getTeamAPlayers().isEmpty()) {
			float y = 3;
			for (LobbyEntity s : PregameLobbyManager.getLobby().getTeamAPlayers()) {

				if (s != null) {
					
					if (s.isReady()) {
						Res.UI_RESOURCE.LOBBY_TEAMA_BAR_READY.draw(t1Box.getX() + 2, t1Box.getY() + y);
					} else {
						Res.UI_RESOURCE.LOBBY_TEAMA_BAR_NOTREADY.draw(t1Box.getX() + 2, t1Box.getY() + y);
					}
					
					Res.bitFont.drawString(t1Box.getX() + 22, t1Box.getY() + y + 6, s.getPlayerName(), s.getNameColor());
					
					s.getClassImage().draw(t1Box.getX() + 3, t1Box.getY() + y + 1);
					
					y += 20;
				}
			}
		}

	}

}
