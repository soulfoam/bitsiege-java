package soulfoam.arena.entities.abilities;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.entities.abilities.archer.ArcherBuff;
import soulfoam.arena.entities.abilities.archer.ArcherExplodingArrow;
import soulfoam.arena.entities.abilities.archer.ArcherExplodingArrowSwitch;
import soulfoam.arena.entities.abilities.archer.ArcherExplosion;
import soulfoam.arena.entities.abilities.archer.ArcherFireArrow;
import soulfoam.arena.entities.abilities.archer.ArcherFireArrowSwitch;
import soulfoam.arena.entities.abilities.archer.ArcherIceArrow;
import soulfoam.arena.entities.abilities.archer.ArcherIceArrowSwitch;
import soulfoam.arena.entities.abilities.cleric.ClericBasicAttack;
import soulfoam.arena.entities.abilities.cleric.ClericBasicAttackHeal;
import soulfoam.arena.entities.abilities.cleric.ClericBlind;
import soulfoam.arena.entities.abilities.cleric.ClericHeal;
import soulfoam.arena.entities.abilities.cleric.ClericSwitch;
import soulfoam.arena.entities.abilities.cleric.ClericTeamHeal;
import soulfoam.arena.entities.abilities.illusionist.IllusionistBasicAttack;
import soulfoam.arena.entities.abilities.illusionist.IllusionistClone;
import soulfoam.arena.entities.abilities.illusionist.IllusionistCloneAttack;
import soulfoam.arena.entities.abilities.illusionist.IllusionistRemoteCloneSpawn;
import soulfoam.arena.entities.abilities.illusionist.IllusionistSpawnClone;
import soulfoam.arena.entities.abilities.illusionist.IllusionistSwapPosition;
import soulfoam.arena.entities.abilities.knight.KnightDash;
import soulfoam.arena.entities.abilities.knight.KnightInvincibleBuff;
import soulfoam.arena.entities.abilities.knight.KnightMeleeAttack;
import soulfoam.arena.entities.abilities.knight.KnightMeleeSpin;
import soulfoam.arena.entities.abilities.knight.KnightShieldThrow;
import soulfoam.arena.entities.abilities.shaman.ShamanBasicAttack;
import soulfoam.arena.entities.abilities.shaman.ShamanBuffTotem;
import soulfoam.arena.entities.abilities.shaman.ShamanConfuseTotem;
import soulfoam.arena.entities.abilities.shaman.ShamanDebuffTotem;
import soulfoam.arena.entities.abilities.shaman.ShamanShieldTotem;
import soulfoam.arena.entities.abilities.voidlord.VoidLordAOE;
import soulfoam.arena.entities.abilities.voidlord.VoidLordHole;
import soulfoam.arena.entities.abilities.voidlord.VoidLordInvisible;
import soulfoam.arena.entities.abilities.voidlord.VoidLordMeleeAttack;
import soulfoam.arena.entities.abilities.voidlord.VoidLordPull;
import soulfoam.arena.entities.abilities.warlock.WarlockBasicAttack;
import soulfoam.arena.entities.abilities.warlock.WarlockLifeDrain;
import soulfoam.arena.entities.abilities.warlock.WarlockStorm;
import soulfoam.arena.entities.abilities.warlock.WarlockStormBit;
import soulfoam.arena.entities.abilities.warlock.WarlockTeleport;
import soulfoam.arena.entities.abilities.warlock.WarlockTeleportFX;
import soulfoam.arena.entities.abilities.warlock.WarlockTower;
import soulfoam.arena.entities.abilities.warlock.WarlockTowerAttack;
import soulfoam.arena.entities.abilities.waterqueen.WaterQueenAbsorb;
import soulfoam.arena.entities.abilities.waterqueen.WaterQueenBasicAttack;
import soulfoam.arena.entities.abilities.waterqueen.WaterQueenBuff;
import soulfoam.arena.entities.abilities.waterqueen.WaterQueenWaterBall;
import soulfoam.arena.entities.abilities.waterqueen.WaterQueenWave;
import soulfoam.arenashared.main.abilityinfo.AbilityInfo;

public class AbilityBook {

	public static Ability getAbilityByID(int abilityID, int ghID, float x, float y, float dx, float dy, Entity player) {

		if (player == null) {
			return null;
		}

		Ability a = null;

		if (abilityID == AbilityInfo.WARLOCKBASICATTACK) {
			a = new WarlockBasicAttack(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.WARLOCKTOWER) {
			a = new WarlockTower(ghID, x, y, player);
		}
		if (abilityID == AbilityInfo.WARLOCKLIFEDRAIN) {
			a = new WarlockLifeDrain(ghID, x, y, player);
		}
		if (abilityID == AbilityInfo.WARLOCKSTORMBIT) {
			a = new WarlockStormBit(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.WARLOCKSTORM) {
			a = new WarlockStorm(ghID, x, y, player);
		}
		if (abilityID == AbilityInfo.WARLOCKTELEPORTFX) {
			a = new WarlockTeleportFX(ghID, x, y, player);
		}
		if (abilityID == AbilityInfo.WARLOCKTOWERATTACK) {
			a = new WarlockTowerAttack(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.WARLOCKTELEPORT) {
			a = new WarlockTeleport(ghID, x, y, player);
		}
		if (abilityID == AbilityInfo.KNIGHTMELEEATTACK) {
			a = new KnightMeleeAttack(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.KNIGHTSHIELDTHROW) {
			a = new KnightShieldThrow(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.KNIGHTINVINCIBLEBUFF) {
			a = new KnightInvincibleBuff(ghID, player);
		}
		if (abilityID == AbilityInfo.KNIGHTMELEESPIN) {
			a = new KnightMeleeSpin(ghID, player);
		}
		if (abilityID == AbilityInfo.KNIGHTDASH) {
			a = new KnightDash(ghID, player);
		}
		if (abilityID == AbilityInfo.ARCHERICEARROWSWITCH) {
			a = new ArcherIceArrowSwitch(ghID, player);
		}
		if (abilityID == AbilityInfo.ARCHERICEARROW) {
			a = new ArcherIceArrow(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.ARCHERFIREARROWSWITCH) {
			a = new ArcherFireArrowSwitch(ghID, player);
		}
		if (abilityID == AbilityInfo.ARCHERFIREARROW) {
			a = new ArcherFireArrow(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.ARCHEREXPLODINGARROWSWITCH) {
			a = new ArcherExplodingArrowSwitch(ghID, player);
		}
		if (abilityID == AbilityInfo.ARCHEREXPLODINGARROW) {
			a = new ArcherExplodingArrow(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.ARCHEREXPLOSION) {
			a = new ArcherExplosion(ghID, x, y, player);
		}
		if (abilityID == AbilityInfo.ARCHERBUFF) {
			a = new ArcherBuff(ghID, player);
		}
		if (abilityID == AbilityInfo.CLERICBASICATTACK) {
			a = new ClericBasicAttack(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.CLERICHEAL) {
			a = new ClericHeal(ghID, x, y, player);
		}
		if (abilityID == AbilityInfo.CLERICBLIND) {
			a = new ClericBlind(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.CLERICSWITCH) {
			a = new ClericSwitch(ghID, player);
		}
		if (abilityID == AbilityInfo.CLERICBASICATTACKHEAL) {
			a = new ClericBasicAttackHeal(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.CLERICTEAMHEAL) {
			a = new ClericTeamHeal(ghID, player);
		}
		if (abilityID == AbilityInfo.ILLUSIONISTBASICATTACK) {
			a = new IllusionistBasicAttack(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.ILLUSIONISTSPAWNCLONE) {
			a = new IllusionistSpawnClone(ghID, player);
		}
		if (abilityID == AbilityInfo.ILLUSIONISTCLONEATTACK) {
			a = new IllusionistCloneAttack(ghID, x, y, player);
		}
		if (abilityID == AbilityInfo.ILLUSIONISTSWAPPOSITION) {
			a = new IllusionistSwapPosition(ghID, x, y, player);
		}
		if (abilityID == AbilityInfo.ILLUSIONISTREMOTECLONESPAWN) {
			a = new IllusionistRemoteCloneSpawn(ghID, player);
		}
		if (abilityID == AbilityInfo.ILLUSIONISTCLONE) {
			a = new IllusionistClone(ghID, x, y, player);
		}
		if (abilityID == AbilityInfo.ILLUSIONISTULTCLONE) {
			a = new IllusionistClone(ghID, x, y, player);
		}
		if (abilityID == AbilityInfo.VOIDLORDMELEEATTACK) {
			a = new VoidLordMeleeAttack(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.VOIDLORDPULL) {
			a = new VoidLordPull(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.VOIDLORDINVISIBLE) {
			a = new VoidLordInvisible(ghID, player);
		}
		if (abilityID == AbilityInfo.VOIDLORDAOE) {
			a = new VoidLordAOE(ghID, player);
		}
		if (abilityID == AbilityInfo.VOIDLORDHOLE) {
			a = new VoidLordHole(ghID, x, y, player);
		}
		if (abilityID == AbilityInfo.WATERQUEENBASICATTACK) {
			a = new WaterQueenBasicAttack(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.WATERQUEENWATERBALL) {
			a = new WaterQueenWaterBall(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.WATERQUEENWAVE) {
			a = new WaterQueenWave(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.WATERQUEENABSORB) {
			a = new WaterQueenAbsorb(ghID, player);
		}
		if (abilityID == AbilityInfo.WATERQUEENWATERBUFF) {
			a = new WaterQueenBuff(ghID, player);
		}
		if (abilityID == AbilityInfo.SHAMANBUFFTOTEM) {
			a = new ShamanBuffTotem(ghID, x, y, player);
		}
		if (abilityID == AbilityInfo.SHAMANBASICATTACK) {
			a = new ShamanBasicAttack(ghID, x, y, dx, dy, player);
		}
		if (abilityID == AbilityInfo.SHAMANSHIELDTOTEM) {
			a = new ShamanShieldTotem(ghID, x, y, player);
		}
		if (abilityID == AbilityInfo.SHAMANDEBUFFTOTEM) {
			a = new ShamanDebuffTotem(ghID, x, y, player);
		}
		if (abilityID == AbilityInfo.SHAMANCONFUSETOTEM) {
			a = new ShamanConfuseTotem(ghID, x, y, player);
		}

		if (a != null) {
			a.calculateRotation();
		}

		return a;
	}

}
