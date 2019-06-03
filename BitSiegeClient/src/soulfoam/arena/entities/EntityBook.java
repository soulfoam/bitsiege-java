package soulfoam.arena.entities;

import soulfoam.arena.entities.challengers.Archer;
import soulfoam.arena.entities.challengers.Cleric;
import soulfoam.arena.entities.challengers.Illusionist;
import soulfoam.arena.entities.challengers.Knight;
import soulfoam.arena.entities.challengers.Shaman;
import soulfoam.arena.entities.challengers.VoidLord;
import soulfoam.arena.entities.challengers.Warlock;
import soulfoam.arena.entities.challengers.WaterQueen;
import soulfoam.arenashared.main.entityinfo.EntityInfo;

public class EntityBook {

	public static Entity getChallengerByID(int challengerID, float x, float y, int skinID, int underglowID) {

		if (challengerID == EntityInfo.KNIGHTCHALLENGER) {
			return new Knight(x, y, skinID, underglowID);
		}
		if (challengerID == EntityInfo.WARLOCKCHALLENGER) {
			return new Warlock(x, y, skinID, underglowID);
		}
		if (challengerID == EntityInfo.ARCHERCHALLENGER) {
			return new Archer(x, y, skinID, underglowID);
		}
		if (challengerID == EntityInfo.CLERICCHALLENGER) {
			return new Cleric(x, y, skinID, underglowID);
		}
		if (challengerID == EntityInfo.ILLUSIONISTCHALLENGER) {
			return new Illusionist(x, y, skinID, underglowID);
		}
		if (challengerID == EntityInfo.VOIDLORDCHALLENGER) {
			return new VoidLord(x, y, skinID, underglowID);
		}
		if (challengerID == EntityInfo.WATERQUEENCHALLENGER) {
			return new WaterQueen(x, y, skinID, underglowID);
		}
		if (challengerID == EntityInfo.SHAMANCHALLENGER) {
			return new Shaman(x, y, skinID, underglowID);
		}
		if (challengerID == EntityInfo.SPECTATE_PLAYER) {
			return new SpectatePlayer(x, y, skinID, underglowID);
		}
		if (challengerID == EntityInfo.KNIGHTCHALLENGERBOT) {
			Knight knight = new Knight(x, y, skinID, underglowID);
			knight.setBot(true);
			return knight;
		}
		if (challengerID == EntityInfo.WARLOCKCHALLENGERBOT) {
			Warlock warlock = new Warlock(x, y, skinID, underglowID);
			warlock.setBot(true);
			return warlock;
		}
		if (challengerID == EntityInfo.ARCHERCHALLENGERBOT) {
			Archer archer = new Archer(x, y, skinID, underglowID);
			archer.setBot(true);
			return archer;
		}
		if (challengerID == EntityInfo.CLERICCHALLENGERBOT) {
			Cleric cleric = new Cleric(x, y, skinID, underglowID);
			cleric.setBot(true);
			return cleric;
		}
		if (challengerID == EntityInfo.ILLUSIONISTCHALLENGERBOT) {
			Illusionist illusionist = new Illusionist(x, y, skinID, underglowID);
			illusionist.setBot(true);
			return illusionist;
		}
		if (challengerID == EntityInfo.VOIDLORDCHALLENGERBOT) {
			VoidLord voidLord = new VoidLord(x, y, skinID, underglowID);
			voidLord.setBot(true);
			return voidLord;
		}
		if (challengerID == EntityInfo.WATERQUEENCHALLENGERBOT) {
			WaterQueen waterQueen = new WaterQueen(x, y, skinID, underglowID);
			waterQueen.setBot(true);
			return waterQueen;
		}
		if (challengerID == EntityInfo.SHAMANCHALLENGERBOT) {
			Shaman shaman = new Shaman(x, y, skinID, underglowID);
			shaman.setBot(true);
			return shaman;
		}

		return null;
	}

}
