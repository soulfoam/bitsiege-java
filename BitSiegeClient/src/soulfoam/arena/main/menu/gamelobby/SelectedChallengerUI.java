package soulfoam.arena.main.menu.gamelobby;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.entities.challengers.tooltips.ChallengerToolTip;
import soulfoam.arena.main.menu.MenuButton;
import soulfoam.arena.main.menu.PregameLobbyManager;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.main.util.GlowTimer;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.lobbyopcode.LobbyOPCode;

public class SelectedChallengerUI {

	public float x, y;
	public float width = 80, height = 196;

	public boolean renderMe;

	private String classTitle = "";
	private Animation classAnimation = new Animation();
	private Animation underglowAnimation = new Animation();
	private Image skill1Image;
	private Image skill2Image;
	private Image skill3Image;
	private Image skill4Image;

	private Rectangle readyUpButton;
	private int buttonState = 0;
	
	private String startingHealth = "";
	private String startingDamage = "";
	private String startingMoveSpeed = "";
	private String startingAttackSpeed = "";

	private Rectangle skillBarRect;

	private Rectangle hpRect;
	private Rectangle aaRect;
	private Rectangle atkspdRect;
	private Rectangle spdRect;

	private GlowTimer glowTimer = new GlowTimer(40, 255, 7);

	private byte team;
	private boolean ready;

	private ChallengerToolTip ctt;

	public SelectedChallengerUI(float x, float y) {
		this.x = x;
		this.y = y;

		skillBarRect = new Rectangle(x + 2, y + 91, 76, 18);

		readyUpButton = new Rectangle(x + 6, y + height - 13, 69, 12);

		hpRect = new Rectangle(x + 2, y + 114, 76, 14);
		spdRect = new Rectangle(x + 2, y + 131, 76, 14);
		aaRect = new Rectangle(x + 2, y + 148, 76, 14);
		atkspdRect = new Rectangle(x + 2, y + 164, 76, 14);
		
		enter();
	}

	public void enter() {
		setUnderglowAnimation(null);
		setClassAnimation(new Animation());
		setClassTitle("Random");
		setStartingHealth("???");
		setStartingDamage("???");
		setStartingMoveSpeed("???");
		setStartingAttackSpeed("???");
		setClassToolTip(null);
		setSkill1Image(null);
		setSkill2Image(null);
		setSkill3Image(null);
		setSkill4Image(null);
		ready = false;
		buttonState = 0;
	}

	public void update(int delta, GameContainer gc, StateBasedGame s) {

		glowTimer.update(delta);

		if (gc.getInput().isMouseButtonDown(Input.MOUSE_LEFT_BUTTON) && !ready){
			if (readyUpButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())){
				LobbyManager.getManager().getLobbyClient().getOutput().println(LobbyOPCode.OP_LOBBYUPDATE);
				LobbyManager.getManager().getLobbyClient().getOutput().println("L~" + PregameLobbyManager.getLobby().getChallengerSelectUI().getSelectedChallengerSlot().getSlotID());
			}
		}

	}

	public void render(Graphics g, GameContainer gc) {

		Res.bitFont.drawString(x - Res.getTextCenter(getClassTitle()) + width / 2, y + 4, getClassTitle());

		if (getUnderglowAnimation() != null) {
			getUnderglowAnimation().draw(x + 18, y + 35, 24, 16, new Color(255, 255, 255, glowTimer.getTimer()));
		}

		if (getClassAnimation() != null) {
			getClassAnimation().draw(x + 9, y + 19, 64, 64);
		}

		if (getSkill1Image() != null && getSkill2Image() != null && getSkill3Image() != null && getSkill4Image() != null) {
			getSkill1Image().draw(x + 5, y + 92, 16, 16); 
			getSkill2Image().draw(x + 23, y + 92, 16, 16);
			getSkill3Image().draw(x + 41, y + 92, 16, 16);
			getSkill4Image().draw(x + 59, y + 92, 16, 16);
		}

		g.setColor(new Color(0, 0, 0, 100));
		
		Res.bitFont.drawString(x - Res.getTextCenter(getStartingHealth()) + ((width + 18) / 2 - 3), y + 118, getStartingHealth(), Color.green);
		Res.bitFont.drawString(x - Res.getTextCenter(getStartingMoveSpeed()) + ((width + 18) / 2 - 3), y + 135, getStartingMoveSpeed(), Color.green);
		Res.bitFont.drawString(x - Res.getTextCenter(getStartingDamage()) + ((width + 18) / 2 - 3), y + 152, getStartingDamage(), Color.green);
		Res.bitFont.drawString(x - Res.getTextCenter(getStartingAttackSpeed()) + ((width + 18) / 2 - 3), y + 169, getStartingAttackSpeed(), Color.green);
		

		if (hpRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			g.setColor(new Color(0, 0, 0));
			g.fillRect(hpRect.getX(), hpRect.getY(), 76, 14);
			Res.bitFont.drawString(hpRect.getX() + 20, hpRect.getY() + 4, "Health");
		}
		
		if (spdRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			g.setColor(new Color(0, 0, 0));
			g.fillRect(spdRect.getX(), spdRect.getY(), 76, 14);
			Res.bitFont.drawString(spdRect.getX() + 8, spdRect.getY() + 4, "Move Speed");
		}

		if (aaRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			g.setColor(new Color(0, 0, 0));
			g.fillRect(aaRect.getX(), aaRect.getY() - 2, 76, 17);
			Res.bitFont.drawString(aaRect.getX() + 5, aaRect.getY(), "Auto Attack");
			Res.bitFont.drawString(aaRect.getX() + 20, aaRect.getY() + 8, "Damage");
		}

		if (atkspdRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			g.setColor(new Color(0, 0, 0));
			g.fillRect(atkspdRect.getX(), atkspdRect.getY() - 2, 76, 17);
			Res.bitFont.drawString(atkspdRect.getX() + 5, atkspdRect.getY(), "Auto Attack");
			Res.bitFont.drawString(atkspdRect.getX() + 24, atkspdRect.getY() + 8, "Speed");
		}
		
		if (buttonState == 0){
			Res.UI_RESOURCE.LOBBY_UI_BUTTON_LOCKIN.draw(readyUpButton.getX(), readyUpButton.getY());
		}
		if (buttonState == 1){
			Res.UI_RESOURCE.LOBBY_UI_BUTTON_LOCKEDIN.draw(readyUpButton.getX(), readyUpButton.getY());
		}
		if (buttonState == 2){
			Res.UI_RESOURCE.LOBBY_UI_BUTTON_TAKEN.draw(readyUpButton.getX(), readyUpButton.getY());
		}
		if (buttonState == 3){
			Res.UI_RESOURCE.LOBBY_UI_BUTTON_NOTOWNED.draw(readyUpButton.getX(), readyUpButton.getY());
		}
		
		if (readyUpButton.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY()) && buttonState == 0) {
			Res.UI_RESOURCE.LOBBY_UI_BUTTON_SELECT.draw(readyUpButton.getX(), readyUpButton.getY());
		}
		
		if (skillBarRect.contains(gc.getInput().getMouseX(), gc.getInput().getMouseY())) {
			if (getClassToolTip() != null) {
				getClassToolTip().render(g);
			}
		}

	}

	public void lockIn() {
		ready = true;
		buttonState = 1;
	}

	public Animation getClassAnimation() {
		return classAnimation;
	}

	public void setClassAnimation(Animation classAnimation) {
		this.classAnimation = classAnimation;
	}

	public String getClassTitle() {
		return classTitle;
	}

	public void setClassTitle(String classTitle) {
		this.classTitle = classTitle;
	}

	public Image getSkill1Image() {
		return skill1Image;
	}

	public void setSkill1Image(Image skill1Image) {
		this.skill1Image = skill1Image;
	}

	public Image getSkill2Image() {
		return skill2Image;
	}

	public void setSkill2Image(Image skill2Image) {
		this.skill2Image = skill2Image;
	}

	public Image getSkill3Image() {
		return skill3Image;
	}

	public void setSkill3Image(Image skill3Image) {
		this.skill3Image = skill3Image;
	}

	public Image getSkill4Image() {
		return skill4Image;
	}

	public void setSkill4Image(Image skill4Image) {
		this.skill4Image = skill4Image;
	}

	public String getStartingHealth() {
		return startingHealth;
	}

	public void setStartingHealth(String startingHealth) {
		this.startingHealth = startingHealth;
	}

	public String getStartingDamage() {
		return startingDamage;
	}

	public void setStartingDamage(String startingDamage) {
		this.startingDamage = startingDamage;
	}

	public String getStartingMoveSpeed() {
		return startingMoveSpeed;
	}

	public void setStartingMoveSpeed(String startingMoveSpeed) {
		this.startingMoveSpeed = startingMoveSpeed;
	}

	public String getStartingAttackSpeed() {
		return startingAttackSpeed;
	}

	public void setStartingAttackSpeed(String startingAttackSpeed) {
		this.startingAttackSpeed = startingAttackSpeed;
	}

	public ChallengerToolTip getClassToolTip() {
		return ctt;
	}

	public void setClassToolTip(ChallengerToolTip ctt) {
		this.ctt = ctt;
	}

	public Animation getUnderglowAnimation() {
		return underglowAnimation;
	}

	public void setUnderglowAnimation(Animation underglowAnimation) {
		this.underglowAnimation = underglowAnimation;
	}

	public byte getTeam() {
		return team;
	}

	public void setTeam(byte team) {
		this.team = team;
	}

	public boolean isReady() {
		return ready;
	}

	public void setReady(boolean ready) {
		this.ready = ready;
	}
	
	public int getButtonState() {
		return buttonState;
	}

	public void setButtonState(int buttonState) {
		this.buttonState = buttonState;
	}
}
