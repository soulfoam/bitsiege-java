package soulfoam.arena.main.menu.gamelobby;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.StateBasedGame;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.EntityBook;
import soulfoam.arena.entities.challengers.tooltips.ArcherToolTip;
import soulfoam.arena.entities.challengers.tooltips.ClericToolTip;
import soulfoam.arena.entities.challengers.tooltips.IllusionistToolTip;
import soulfoam.arena.entities.challengers.tooltips.KnightToolTip;
import soulfoam.arena.entities.challengers.tooltips.ShamanToolTip;
import soulfoam.arena.entities.challengers.tooltips.VoidLordToolTip;
import soulfoam.arena.entities.challengers.tooltips.WarlockToolTip;
import soulfoam.arena.entities.challengers.tooltips.WaterQueenToolTip;
import soulfoam.arena.entities.challengers.underglows.CosmeticBook;
import soulfoam.arena.main.menu.PregameLobbyManager;
import soulfoam.arena.main.resources.Res;
import soulfoam.arena.net.lobby.LobbyManager;
import soulfoam.arenashared.main.abilityinfo.archer.ArcherFireArrowInfo;
import soulfoam.arenashared.main.abilityinfo.cleric.ClericBasicAttackInfo;
import soulfoam.arenashared.main.abilityinfo.illusionist.IllusionistBasicAttackInfo;
import soulfoam.arenashared.main.abilityinfo.knight.KnightMeleeAttackInfo;
import soulfoam.arenashared.main.abilityinfo.shaman.ShamanBasicAttackInfo;
import soulfoam.arenashared.main.abilityinfo.voidlord.VoidLordMeleeAttackInfo;
import soulfoam.arenashared.main.abilityinfo.warlock.WarlockBasicAttackInfo;
import soulfoam.arenashared.main.abilityinfo.waterqueen.WaterQueenBasicAttackInfo;
import soulfoam.arenashared.main.entityinfo.ChallengerInfo;
import soulfoam.arenashared.main.entityinfo.CosmeticLibrary;
import soulfoam.arenashared.main.entityinfo.EntityInfo;
import soulfoam.arenashared.main.entityinfo.SkinInfo;
import soulfoam.arenashared.main.entityinfo.UnderglowInfo;

public class ChallengerSelectUI {

	private CopyOnWriteArrayList<ChallengerSlot> menuSlotsChallengers = new CopyOnWriteArrayList<ChallengerSlot>();
	private CopyOnWriteArrayList<SkinSlot> menuSlotsSkins = new CopyOnWriteArrayList<SkinSlot>();
	private CopyOnWriteArrayList<UnderglowSlot> menuSlotsUnderglows = new CopyOnWriteArrayList<UnderglowSlot>();

	private float x, y;

	private ChallengerSlot selectedChallengerSlot;
	private int challengerPick = -1;

	public ChallengerSelectUI(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void enter() {
		initChallengerSlots();
		initSkinSlots();
		initUnderglowSlots();
		initPickableSlots();
	}

	public void update(int delta, GameContainer gc, StateBasedGame s) {

		for (int i = 0; i < menuSlotsChallengers.size(); i++) {
			menuSlotsChallengers.get(i).update(delta, gc);
		}

		for (int i = 0; i < menuSlotsSkins.size(); i++) {
			menuSlotsSkins.get(i).update(delta, gc);
		}

//		for (int i = 0; i < menuSlotsUnderglows.size(); i++) {
//			menuSlotsUnderglows.get(i).update(delta, gc);
//		}

		setTakenInfo();
		
	}

	public void render(Graphics g, GameContainer gc) {

		for (int i = 0; i < menuSlotsChallengers.size(); i++) {
			menuSlotsChallengers.get(i).render(g, gc);
		}

		for (int i = 0; i < menuSlotsSkins.size(); i++) {
			menuSlotsSkins.get(i).render(g, gc);
		}
		
//		for (int i = 0; i < menuSlotsUnderglows.size(); i++) {
//			menuSlotsUnderglows.get(i).render(g, gc);
//		}

	}

	public void setTakenInfo() {
		for (ChallengerSlot cs : menuSlotsChallengers) {
			if (cs.getSlotID() == challengerPick && !cs.isEmpty()) {
				cs.setSelected(true);
			} else {
				cs.setSelected(false);
			}
		}

		for (SkinSlot ss : menuSlotsSkins) {
			if (ss.getSlotID() == selectedChallengerSlot.getSkinPick() && !ss.isEmpty()) {
				ss.setSelected(true);
			} else {
				ss.setSelected(false);
			}
		}

		for (UnderglowSlot us : menuSlotsUnderglows) {
			if (us.getSlotID() == PregameLobbyManager.underglowPick && !us.isEmpty()) {
				us.setSelected(true);
			} else {
				us.setSelected(false);
			}
		}
	}

	public void setChallenger() {

		setSkinSlots();

		if (challengerPick == EntityInfo.RANDOMCHALLENGER) {
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassTitle("Random");
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassToolTip(null);
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setStartingHealth("???");
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setStartingDamage("???");
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setStartingMoveSpeed("???");
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setStartingAttackSpeed("???");
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setSkill1Image(null);
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setSkill2Image(null);
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setSkill3Image(null);
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setSkill4Image(null);
		}

		if (challengerPick == EntityInfo.KNIGHTCHALLENGER) {
			Entity knight = EntityBook.getChallengerByID(EntityInfo.KNIGHTCHALLENGER, 0, 0,
					selectedChallengerSlot.getSkinPick(), -1);
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassTitle("Knight");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill1Image(Res.KNIGHT_RESOURCE.KNIGHT_HOTBAR_ICONS[0]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill2Image(Res.KNIGHT_RESOURCE.KNIGHT_HOTBAR_ICONS[1]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill3Image(Res.KNIGHT_RESOURCE.KNIGHT_HOTBAR_ICONS[2]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill4Image(Res.KNIGHT_RESOURCE.KNIGHT_HOTBAR_ICONS[3]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingHealth(Math.round(knight.getBaseHealth()) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingDamage(Math.round(KnightMeleeAttackInfo.DAMAGE) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingMoveSpeed(Res.getMoveSpeedDisplay(knight.getBaseMoveSpeed()));
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingAttackSpeed(Res.getAttackSpeedDisplay(KnightMeleeAttackInfo.COOLDOWN) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassToolTip(new KnightToolTip(11, 97));
		}

		if (challengerPick == EntityInfo.WARLOCKCHALLENGER) {
			Entity warlock = EntityBook.getChallengerByID(EntityInfo.WARLOCKCHALLENGER, 0, 0,
					selectedChallengerSlot.getSkinPick(), -1);
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassTitle("Warlock");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill1Image(Res.WARLOCK_RESOURCE.WARLOCK_HOTBAR_ICONS[0]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill2Image(Res.WARLOCK_RESOURCE.WARLOCK_HOTBAR_ICONS[1]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill3Image(Res.WARLOCK_RESOURCE.WARLOCK_HOTBAR_ICONS[2]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill4Image(Res.WARLOCK_RESOURCE.WARLOCK_HOTBAR_ICONS[3]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingHealth(Math.round(warlock.getBaseHealth()) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingDamage(Math.round(WarlockBasicAttackInfo.DAMAGE) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingMoveSpeed(Res.getMoveSpeedDisplay(warlock.getBaseMoveSpeed()));
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingAttackSpeed(Res.getAttackSpeedDisplay(WarlockBasicAttackInfo.COOLDOWN) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassToolTip(new WarlockToolTip(11, 97));
		}

		if (challengerPick == EntityInfo.ARCHERCHALLENGER) {

			Entity archer = EntityBook.getChallengerByID(EntityInfo.ARCHERCHALLENGER, 0, 0,
					selectedChallengerSlot.getSkinPick(), -1);
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassTitle("Archer");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill1Image(Res.ARCHER_RESOURCE.ARCHER_HOTBAR_ICONS[0]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill2Image(Res.ARCHER_RESOURCE.ARCHER_HOTBAR_ICONS[1]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill3Image(Res.ARCHER_RESOURCE.ARCHER_HOTBAR_ICONS[2]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill4Image(Res.ARCHER_RESOURCE.ARCHER_HOTBAR_ICONS[3]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingHealth(Math.round(archer.getBaseHealth()) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingDamage(Math.round(ArcherFireArrowInfo.DAMAGE) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingMoveSpeed(Res.getMoveSpeedDisplay(archer.getBaseMoveSpeed()));
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingAttackSpeed(Res.getAttackSpeedDisplay(ArcherFireArrowInfo.COOLDOWN) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassToolTip(new ArcherToolTip(11, 97));
		}

		if (challengerPick == EntityInfo.CLERICCHALLENGER) {
			Entity cleric = EntityBook.getChallengerByID(EntityInfo.CLERICCHALLENGER, 0, 0,
					selectedChallengerSlot.getSkinPick(), -1);
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassTitle("Cleric");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill1Image(Res.CLERIC_RESOURCE.CLERIC_HOTBAR_ICONS[0]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill2Image(Res.CLERIC_RESOURCE.CLERIC_HOTBAR_ICONS[1]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill3Image(Res.CLERIC_RESOURCE.CLERIC_HOTBAR_ICONS[2]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill4Image(Res.CLERIC_RESOURCE.CLERIC_HOTBAR_ICONS[3]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingHealth(Math.round(cleric.getBaseHealth()) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingDamage(Math.round(ClericBasicAttackInfo.DAMAGE) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingMoveSpeed(Res.getMoveSpeedDisplay(cleric.getBaseMoveSpeed()));
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingAttackSpeed(Res.getAttackSpeedDisplay(ClericBasicAttackInfo.COOLDOWN) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassToolTip(new ClericToolTip(11, 97));
		}

		if (challengerPick == EntityInfo.ILLUSIONISTCHALLENGER) {
			Entity illusionist = EntityBook.getChallengerByID(EntityInfo.ILLUSIONISTCHALLENGER, 0, 0,
					selectedChallengerSlot.getSkinPick(), -1);
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassTitle("Illusionist");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill1Image(Res.ILLUSIONIST_RESOURCE.ILLUSIONIST_HOTBAR_ICONS[0]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill2Image(Res.ILLUSIONIST_RESOURCE.ILLUSIONIST_HOTBAR_ICONS[1]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill3Image(Res.ILLUSIONIST_RESOURCE.ILLUSIONIST_HOTBAR_ICONS[2]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill4Image(Res.ILLUSIONIST_RESOURCE.ILLUSIONIST_HOTBAR_ICONS[3]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingHealth(Math.round(illusionist.getBaseHealth()) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingDamage(Math.round(IllusionistBasicAttackInfo.DAMAGE) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingMoveSpeed(Res.getMoveSpeedDisplay(illusionist.getBaseMoveSpeed()));
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingAttackSpeed(Res.getAttackSpeedDisplay(IllusionistBasicAttackInfo.COOLDOWN) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassToolTip(new IllusionistToolTip(11, 97));
		}

		if (challengerPick == EntityInfo.VOIDLORDCHALLENGER) {
			Entity voidlord = EntityBook.getChallengerByID(EntityInfo.VOIDLORDCHALLENGER, 0, 0,
					selectedChallengerSlot.getSkinPick(), -1);
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassTitle("Void Lord");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill1Image(Res.VOIDLORD_RESOURCE.VOIDLORD_HOTBAR_ICONS[0]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill2Image(Res.VOIDLORD_RESOURCE.VOIDLORD_HOTBAR_ICONS[1]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill3Image(Res.VOIDLORD_RESOURCE.VOIDLORD_HOTBAR_ICONS[2]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill4Image(Res.VOIDLORD_RESOURCE.VOIDLORD_HOTBAR_ICONS[3]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingHealth(Math.round(voidlord.getBaseHealth()) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingDamage(Math.round(VoidLordMeleeAttackInfo.DAMAGE) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingMoveSpeed(Res.getMoveSpeedDisplay(voidlord.getBaseMoveSpeed()));
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingAttackSpeed(Res.getAttackSpeedDisplay(VoidLordMeleeAttackInfo.COOLDOWN) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassToolTip(new VoidLordToolTip(11, 97));
		}

		if (challengerPick == EntityInfo.WATERQUEENCHALLENGER) {
			Entity waterqueen = EntityBook.getChallengerByID(EntityInfo.WATERQUEENCHALLENGER, 0, 0,
					selectedChallengerSlot.getSkinPick(), -1);
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassTitle("Water Queen");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill1Image(Res.WATERQUEEN_RESOURCE.WATERQUEEN_HOTBAR_ICONS[0]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill2Image(Res.WATERQUEEN_RESOURCE.WATERQUEEN_HOTBAR_ICONS[1]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill3Image(Res.WATERQUEEN_RESOURCE.WATERQUEEN_HOTBAR_ICONS[2]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill4Image(Res.WATERQUEEN_RESOURCE.WATERQUEEN_HOTBAR_ICONS[3]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingHealth(Math.round(waterqueen.getBaseHealth()) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingDamage(Math.round(WaterQueenBasicAttackInfo.DAMAGE) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingMoveSpeed(Res.getMoveSpeedDisplay(waterqueen.getBaseMoveSpeed()));
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingAttackSpeed(Res.getAttackSpeedDisplay(WaterQueenBasicAttackInfo.COOLDOWN) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassToolTip(new WaterQueenToolTip(11, 97));
		}

		if (challengerPick == EntityInfo.SHAMANCHALLENGER) {
			Entity shaman = EntityBook.getChallengerByID(EntityInfo.SHAMANCHALLENGER, 0, 0,
					selectedChallengerSlot.getSkinPick(), -1);
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassTitle("Shaman");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill1Image(Res.SHAMAN_RESOURCE.SHAMAN_HOTBAR_ICONS[0]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill2Image(Res.SHAMAN_RESOURCE.SHAMAN_HOTBAR_ICONS[1]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill3Image(Res.SHAMAN_RESOURCE.SHAMAN_HOTBAR_ICONS[2]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setSkill4Image(Res.SHAMAN_RESOURCE.SHAMAN_HOTBAR_ICONS[3]);
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingHealth(Math.round(shaman.getBaseHealth()) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingDamage(Math.round(ShamanBasicAttackInfo.DAMAGE) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingMoveSpeed(Res.getMoveSpeedDisplay(shaman.getBaseMoveSpeed()));
			PregameLobbyManager.getLobby().getSelectedChallengerUI()
					.setStartingAttackSpeed(Res.getAttackSpeedDisplay(ShamanBasicAttackInfo.COOLDOWN) + "");
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassToolTip(new ShamanToolTip(11, 97));
		}

		setSkin();

	}

	public void setSkin() {

		if (challengerPick == EntityInfo.RANDOMCHALLENGER) {
			PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassAnimation(new Animation());
		}

		if (challengerPick == EntityInfo.KNIGHTCHALLENGER) {
			Entity knight = EntityBook.getChallengerByID(EntityInfo.KNIGHTCHALLENGER, 0, 0, selectedChallengerSlot.getSkinPick(), -1);
			if (PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getFrameCount() <= 0 
					|| PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getFrameCount() > 0 
					&& PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getImage(0) != knight.getAnimation().getWalkDown().getImage(0)) {
				PregameLobbyManager.getLobby().getSelectedChallengerUI().setClassAnimation(knight.getAnimation().getWalkDown());
			}
			return;
		}

		if (challengerPick == EntityInfo.WARLOCKCHALLENGER) {
			Entity warlock = EntityBook.getChallengerByID(EntityInfo.WARLOCKCHALLENGER, 0, 0,
					selectedChallengerSlot.getSkinPick(), -1);
			if (PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getFrameCount() <= 0
					|| PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getFrameCount() > 0
							&& PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation()
									.getImage(0) != warlock.getAnimation().getWalkDown().getImage(0)) {
				PregameLobbyManager.getLobby().getSelectedChallengerUI()
						.setClassAnimation(warlock.getAnimation().getWalkDown());
			}
			return;
		}

		if (challengerPick == EntityInfo.ARCHERCHALLENGER) {
			Entity archer = EntityBook.getChallengerByID(EntityInfo.ARCHERCHALLENGER, 0, 0,
					selectedChallengerSlot.getSkinPick(), -1);
			if (PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getFrameCount() <= 0
					|| PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getFrameCount() > 0
							&& PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation()
									.getImage(0) != archer.getAnimation().getWalkDown().getImage(0)) {
				PregameLobbyManager.getLobby().getSelectedChallengerUI()
						.setClassAnimation(archer.getAnimation().getWalkDown());
			}
			return;
		}

		if (challengerPick == EntityInfo.CLERICCHALLENGER) {
			Entity cleric = EntityBook.getChallengerByID(EntityInfo.CLERICCHALLENGER, 0, 0,
					selectedChallengerSlot.getSkinPick(), -1);
			if (PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getFrameCount() <= 0
					|| PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getFrameCount() > 0
							&& PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation()
									.getImage(0) != cleric.getAnimation().getWalkDown().getImage(0)) {
				PregameLobbyManager.getLobby().getSelectedChallengerUI()
						.setClassAnimation(cleric.getAnimation().getWalkDown());
			}
			return;
		}

		if (challengerPick == EntityInfo.ILLUSIONISTCHALLENGER) {
			Entity illusionist = EntityBook.getChallengerByID(EntityInfo.ILLUSIONISTCHALLENGER, 0, 0,
					selectedChallengerSlot.getSkinPick(), -1);
			if (PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getFrameCount() <= 0
					|| PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getFrameCount() > 0
							&& PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation()
									.getImage(0) != illusionist.getAnimation().getWalkDown().getImage(0)) {
				PregameLobbyManager.getLobby().getSelectedChallengerUI()
						.setClassAnimation(illusionist.getAnimation().getWalkDown());
			}
			return;
		}

		if (challengerPick == EntityInfo.VOIDLORDCHALLENGER) {
			Entity voidlord = EntityBook.getChallengerByID(EntityInfo.VOIDLORDCHALLENGER, 0, 0,
					selectedChallengerSlot.getSkinPick(), -1);
			if (PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getFrameCount() <= 0
					|| PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getFrameCount() > 0
							&& PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation()
									.getImage(0) != voidlord.getAnimation().getWalkDown().getImage(0)) {
				PregameLobbyManager.getLobby().getSelectedChallengerUI()
						.setClassAnimation(voidlord.getAnimation().getWalkDown());
			}
			return;
		}

		if (challengerPick == EntityInfo.WATERQUEENCHALLENGER) {
			Entity waterqueen = EntityBook.getChallengerByID(EntityInfo.WATERQUEENCHALLENGER, 0, 0,
					selectedChallengerSlot.getSkinPick(), -1);
			if (PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getFrameCount() <= 0
					|| PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getFrameCount() > 0
							&& PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation()
									.getImage(0) != waterqueen.getAnimation().getWalkDown().getImage(0)) {
				PregameLobbyManager.getLobby().getSelectedChallengerUI()
						.setClassAnimation(waterqueen.getAnimation().getWalkDown());
			}
			return;
		}

		if (challengerPick == EntityInfo.SHAMANCHALLENGER) {
			Entity shaman = EntityBook.getChallengerByID(EntityInfo.SHAMANCHALLENGER, 0, 0,
					selectedChallengerSlot.getSkinPick(), -1);
			if (PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getFrameCount() <= 0
					|| PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation().getFrameCount() > 0
							&& PregameLobbyManager.getLobby().getSelectedChallengerUI().getClassAnimation()
									.getImage(0) != shaman.getAnimation().getWalkDown().getImage(0)) {
				PregameLobbyManager.getLobby().getSelectedChallengerUI()
						.setClassAnimation(shaman.getAnimation().getWalkDown());
			}
			return;
		}
	}

	public void initChallengerSlots() {

		menuSlotsChallengers.clear();

		for (int i = 0; i < 10; i++) {
			menuSlotsChallengers.add(new ChallengerSlot(x + i * 20 + 5, y + 5));
		}

		menuSlotsChallengers.get(0).initChallengerSlot(EntityInfo.RANDOMCHALLENGER, Res.UI_RESOURCE.RANDOM_ICON);
		menuSlotsChallengers.get(0).setOwned(true);
		menuSlotsChallengers.get(0).select();

		menuSlotsChallengers.get(1).initChallengerSlot(EntityInfo.KNIGHTCHALLENGER, Res.KNIGHT_RESOURCE.PORTRAITS[0]);
		menuSlotsChallengers.get(2).initChallengerSlot(EntityInfo.WARLOCKCHALLENGER, Res.WARLOCK_RESOURCE.PORTRAITS[0]);
		menuSlotsChallengers.get(3).initChallengerSlot(EntityInfo.ARCHERCHALLENGER, Res.ARCHER_RESOURCE.PORTRAITS[0]);
		menuSlotsChallengers.get(4).initChallengerSlot(EntityInfo.CLERICCHALLENGER, Res.CLERIC_RESOURCE.PORTRAITS[0]);
		menuSlotsChallengers.get(5).initChallengerSlot(EntityInfo.ILLUSIONISTCHALLENGER, Res.ILLUSIONIST_RESOURCE.PORTRAITS[0]);
		menuSlotsChallengers.get(6).initChallengerSlot(EntityInfo.VOIDLORDCHALLENGER, Res.VOIDLORD_RESOURCE.PORTRAITS[0]);
		menuSlotsChallengers.get(7).initChallengerSlot(EntityInfo.WATERQUEENCHALLENGER, Res.WATERQUEEN_RESOURCE.PORTRAITS[0]);
		menuSlotsChallengers.get(8).initChallengerSlot(EntityInfo.SHAMANCHALLENGER, Res.SHAMAN_RESOURCE.PORTRAITS[0]);

	}

	public void initSkinSlots() {

		menuSlotsSkins.clear();

		for (int i = 0; i < 6; i++) {
			menuSlotsSkins.add(new SkinSlot(x + i * 20 + 171, y - 17));
		}

	}

	public void initUnderglowSlots() {

		menuSlotsUnderglows.clear();

		for (int i = 0; i < 4; i++) {
			menuSlotsUnderglows.add(new UnderglowSlot(x + i * 16 + 2.5f, y + 128));
		}
		for (int i = 0; i < 7; i++) {
			menuSlotsUnderglows.add(new UnderglowSlot(x + i * 16 + 2.5f, y + 140));
		}
		for (int i = 0; i < 7; i++) {
			menuSlotsUnderglows.add(new UnderglowSlot(x + i * 16 + 2.5f, y + 152));
		}
		for (int i = 0; i < 7; i++) {
			menuSlotsUnderglows.add(new UnderglowSlot(x + i * 16 + 2.5f, y + 164));
		}

		menuSlotsUnderglows.get(0).initUnderglowSlot(CosmeticLibrary.UNDERGLOW_NONE, null);
		menuSlotsUnderglows.get(0).select();

		menuSlotsUnderglows.get(1).initUnderglowSlot(CosmeticLibrary.UNDERGLOW_ORANGE,
				CosmeticBook.getUnderglowAnimation(CosmeticLibrary.UNDERGLOW_ORANGE));
		menuSlotsUnderglows.get(2).initUnderglowSlot(CosmeticLibrary.UNDERGLOW_BLUE,
				CosmeticBook.getUnderglowAnimation(CosmeticLibrary.UNDERGLOW_BLUE));
		menuSlotsUnderglows.get(3).initUnderglowSlot(CosmeticLibrary.UNDERGLOW_WHITE,
				CosmeticBook.getUnderglowAnimation(CosmeticLibrary.UNDERGLOW_WHITE));
		menuSlotsUnderglows.get(4).initUnderglowSlot(CosmeticLibrary.UNDERGLOW_PURPLE,
				CosmeticBook.getUnderglowAnimation(CosmeticLibrary.UNDERGLOW_PURPLE));
		menuSlotsUnderglows.get(5).initUnderglowSlot(CosmeticLibrary.UNDERGLOW_GREEN,
				CosmeticBook.getUnderglowAnimation(CosmeticLibrary.UNDERGLOW_GREEN));
		menuSlotsUnderglows.get(6).initUnderglowSlot(CosmeticLibrary.UNDERGLOW_RED,
				CosmeticBook.getUnderglowAnimation(CosmeticLibrary.UNDERGLOW_RED));
		menuSlotsUnderglows.get(7).initUnderglowSlot(CosmeticLibrary.UNDERGLOW_RAINBOW,
				CosmeticBook.getUnderglowAnimation(CosmeticLibrary.UNDERGLOW_RAINBOW));
		menuSlotsUnderglows.get(8).initUnderglowSlot(CosmeticLibrary.UNDERGLOW_YELLOW,
				CosmeticBook.getUnderglowAnimation(CosmeticLibrary.UNDERGLOW_YELLOW));
		menuSlotsUnderglows.get(9).initUnderglowSlot(CosmeticLibrary.UNDERGLOW_BLOOD_SPIN,
				CosmeticBook.getUnderglowAnimation(CosmeticLibrary.UNDERGLOW_BLOOD_SPIN));

	}

	public void initPickableSlots() {

		for (int i = 0; i < menuSlotsChallengers.size(); i++) {
			ChallengerSlot cs = menuSlotsChallengers.get(i);
			if (!cs.isEmpty() && cs.getSlotID() != EntityInfo.RANDOMCHALLENGER) {
				cs.setOwned(false);
			}
		}

		for (int i = 0; i < menuSlotsChallengers.size(); i++) {
			ChallengerSlot cs = menuSlotsChallengers.get(i);
			for (ChallengerInfo ci : LobbyManager.getManager().getUserAccount().getUnlockedChallengers()) {
				if (ci.getID() == cs.getSlotID()) {
					cs.setOwned(true);
				}
			}
		}

		for (int i = 0; i < menuSlotsSkins.size(); i++) {
			SkinSlot ss = menuSlotsSkins.get(i);
			if (!ss.isEmpty()) {
				ss.setOwned(false);
			}
		}

		for (int i = 0; i < menuSlotsSkins.size(); i++) {
			SkinSlot ss = menuSlotsSkins.get(i);
			for (SkinInfo si : LobbyManager.getManager().getUserAccount().getUnlockedSkins()) {
				if (si.getChallengerID() == challengerPick) {
					if (si.getID() == ss.getSlotID()) {
						ss.setOwned(true);
					}
				}
			}
		}

		for (int i = 0; i < menuSlotsUnderglows.size(); i++) {
			UnderglowSlot us = menuSlotsUnderglows.get(i);
			if (!us.isEmpty()) {
				us.setOwned(false);
			}
		}

		for (int i = 0; i < menuSlotsUnderglows.size(); i++) {
			UnderglowSlot us = menuSlotsUnderglows.get(i);
			for (UnderglowInfo ui : LobbyManager.getManager().getUserAccount().getUnlockedUnderglows()) {
				if (ui.getID() == us.getSlotID()) {
					us.setOwned(true);
				}
			}
		}

	}

	public void setSkinSlots() {

		if (challengerPick == EntityInfo.KNIGHTCHALLENGER) {
			menuSlotsSkins.get(0).initSkinSlot(CosmeticLibrary.KNIGHT_SKIN_NORMAL, Res.KNIGHT_RESOURCE.PORTRAITS[0]);
			menuSlotsSkins.get(1).initSkinSlot(CosmeticLibrary.KNIGHT_SKIN_BLOOD, Res.KNIGHT_RESOURCE.PORTRAITS[1]);
		}

		if (challengerPick == EntityInfo.WARLOCKCHALLENGER) {
			menuSlotsSkins.get(0).initSkinSlot(CosmeticLibrary.WARLOCK_SKIN_NORMAL, Res.WARLOCK_RESOURCE.PORTRAITS[0]);
			menuSlotsSkins.get(1).initSkinSlot(CosmeticLibrary.WARLOCK_SKIN_FROST, Res.WARLOCK_RESOURCE.PORTRAITS[1]);
		}

		if (challengerPick == EntityInfo.ARCHERCHALLENGER) {
			menuSlotsSkins.get(0).initSkinSlot(CosmeticLibrary.ARCHER_SKIN_NORMAL, Res.ARCHER_RESOURCE.PORTRAITS[0]);
			menuSlotsSkins.get(1).initSkinSlot(CosmeticLibrary.ARCHER_SKIN_EXPLOSIVE, Res.ARCHER_RESOURCE.PORTRAITS[1]);
		}

		if (challengerPick == EntityInfo.CLERICCHALLENGER) {
			menuSlotsSkins.get(0).initSkinSlot(CosmeticLibrary.CLERIC_SKIN_NORMAL, Res.CLERIC_RESOURCE.PORTRAITS[0]);
			menuSlotsSkins.get(1).initSkinSlot(CosmeticLibrary.CLERIC_SKIN_AMETHYST,Res.CLERIC_RESOURCE.PORTRAITS[1]);
		}

		if (challengerPick == EntityInfo.ILLUSIONISTCHALLENGER) {
			menuSlotsSkins.get(0).initSkinSlot(CosmeticLibrary.ILLUSIONIST_SKIN_NORMAL, Res.ILLUSIONIST_RESOURCE.PORTRAITS[0]);
			menuSlotsSkins.get(1).initSkinSlot(CosmeticLibrary.ILLUSIONIST_SKIN_PYRO, Res.ILLUSIONIST_RESOURCE.PORTRAITS[1]);
		}

		if (challengerPick == EntityInfo.VOIDLORDCHALLENGER) {
			menuSlotsSkins.get(0).initSkinSlot(CosmeticLibrary.VOIDLORD_SKIN_NORMAL, Res.VOIDLORD_RESOURCE.PORTRAITS[0]);
			menuSlotsSkins.get(1).initSkinSlot(CosmeticLibrary.VOIDLORD_SKIN_BLOOD, Res.VOIDLORD_RESOURCE.PORTRAITS[1]);
		}

		if (challengerPick == EntityInfo.WATERQUEENCHALLENGER) {
			menuSlotsSkins.get(0).initSkinSlot(CosmeticLibrary.WATERQUEEN_SKIN_NORMAL, Res.WATERQUEEN_RESOURCE.PORTRAITS[0]);
			menuSlotsSkins.get(1).initSkinSlot(CosmeticLibrary.WATERQUEEN_SKIN_SWAMP, Res.WATERQUEEN_RESOURCE.PORTRAITS[1]);
		}

		if (challengerPick == EntityInfo.SHAMANCHALLENGER) {
			menuSlotsSkins.get(0).initSkinSlot(CosmeticLibrary.SHAMAN_SKIN_NORMAL, Res.SHAMAN_RESOURCE.PORTRAITS[0]);
			menuSlotsSkins.get(1).initSkinSlot(CosmeticLibrary.SHAMAN_SKIN_VOID, Res.SHAMAN_RESOURCE.PORTRAITS[1]);
		}

		if (challengerPick == EntityInfo.RANDOMCHALLENGER) {
			for (SkinSlot ss : menuSlotsSkins) {
				ss.setEmpty(true);
				ss.setSlotImage(null);
				ss.setSelected(false);
				ss.setOwned(false);
			}
		}

		initPickableSlots();

	}

	public void setLockedInChallenger(int challengerID, boolean local) {
		for (ChallengerSlot cs : menuSlotsChallengers) {
			if (cs.getSlotID() == challengerID && !cs.isEmpty()) {
				if (local) {
					cs.setLocalLockIn(true);
					PregameLobbyManager.getLobby().getSelectedChallengerUI().lockIn();

					cs.select();
					setSkinSlots();

					cs.setSkinPickLocalLockIn();
					
				} else {
					cs.setLocked(true);
				}
			}
		}
	}

	public ChallengerSlot getSelectedChallengerSlot() {
		return selectedChallengerSlot;
	}

	public void setSelectedChallengerSlot(ChallengerSlot selectedChallengerSlot) {
		this.selectedChallengerSlot = selectedChallengerSlot;
	}

	public int getChallengerPick() {
		return challengerPick;
	}

	public void setChallengerPick(int challengerPick) {
		this.challengerPick = challengerPick;
	}

}
