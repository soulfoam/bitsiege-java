package soulfoam.arena.main.command.client;

import soulfoam.arena.entities.Entity;
import soulfoam.arena.main.command.Command;
import soulfoam.arena.main.game.Game;

public class UpdateEntityMiscCommand extends Command {

	private float oldBaseHealth;
	private float oldBaseShield;
	private float oldMoveSpeed;
	private int oldPlayerKills;
	private int oldPlayerDeaths;
	private int oldPlayerCaptureScore;
	private int oldPlayerAssists;
	private float oldAttackSpeedBuffAmount;
	private float oldPowerBuffAmount;
	private float oldMoveSpeedBuffAmount;
	private int oldGold;
	private byte oldPlayerTeam;
	private int oldA1cd;
	private int oldA2cd;
	private int oldA3cd;
	private int oldA4cd;
	private int oldPing;
	private float oldAbadcd;

	private float baseHealth;
	private float baseShield;
	private float moveSpeed;
	private int playerKills;
	private int playerDeaths;
	private int playerCaptureScore;
	private int playerAssists;
	private float attackSpeedBuffAmount;
	private float powerBuffAmount;
	private float moveSpeedBuffAmount;
	private int gold;
	private byte playerTeam;
	private int a1cd;
	private int a2cd;
	private int a3cd;
	private int a4cd;
	private float abacd;
	private int ping;

	public UpdateEntityMiscCommand(int playerID, float baseHealth, float baseShield, float moveSpeed,
			int playerKills, int playerDeaths, int playerCaptureScore, int playerAssists, float attackSpeedBuffAmount,
			float powerBuffAmount, float moveSpeedBuffAmount, int gold, byte playerTeam, int a1cd, int a2cd, int a3cd,
			int a4cd, int ping, float abacd) {

		super(Command.COMMAND_PACKET_UPDATE_ENTITY, playerID);

		this.baseHealth = baseHealth;
		this.baseShield = baseShield;
		this.moveSpeed = moveSpeed;
		this.playerKills = playerKills;
		this.playerDeaths = playerDeaths;
		this.playerCaptureScore = playerCaptureScore;
		this.playerAssists = playerAssists;
		this.attackSpeedBuffAmount = attackSpeedBuffAmount;
		this.powerBuffAmount = powerBuffAmount;
		this.moveSpeedBuffAmount = moveSpeedBuffAmount;
		this.gold = gold;
		this.playerTeam = playerTeam;
		this.a1cd = a1cd;
		this.a2cd = a2cd;
		this.a3cd = a3cd;
		this.a4cd = a4cd;
		this.ping = ping;
		this.abacd = abacd;

	}

	public boolean execute(int delta) {

		player = Game.getGame().getPlayerObject(playerID);
		
		if (player == null){
			Game.getGame().getClientFunctions().syncGame();
			return true;
		}
		
		setOldValues();
		
		storedDelta = delta;

		player.setBaseHealth(baseHealth);
		player.setBaseShield(baseShield);
		player.setMoveSpeed(moveSpeed);
		player.setKills(playerKills);
		player.setDeaths(playerDeaths);
		player.setPoints(playerCaptureScore);
		player.setAssists(playerAssists);
		player.setAttackSpeedBuffAmount(attackSpeedBuffAmount);
		player.setPowerBuffAmount(powerBuffAmount);
		player.setMoveSpeedBuffAmount(moveSpeedBuffAmount);
		player.setGold(gold);
		player.setTeam(playerTeam);
		player.setAbility1CDTimer(a1cd);
		player.setAbility2CDTimer(a2cd);
		player.setAbility3CDTimer(a3cd);
		player.setAbility4CDTimer(a4cd);
		player.setPing(ping);
		player.setAbilityBasicAttackCDTime(abacd);
		
		return true;
	}
	
	private void setOldValues(){
		oldBaseHealth = player.getBaseHealth();
		oldBaseShield = player.getBaseShield();
		oldMoveSpeed = player.getMoveSpeed();
		oldPlayerKills = player.getKills();
		oldPlayerDeaths = player.getDeaths();
		oldPlayerCaptureScore = player.getPoints();
		oldPlayerAssists = player.getAssists();
		oldAttackSpeedBuffAmount = player.getAttackSpeedBuffAmount();
		oldPowerBuffAmount = player.getPowerBuffAmount();
		oldMoveSpeedBuffAmount = player.getMoveSpeedBuffAmount();
		oldGold = player.getGold();
		oldPlayerTeam = player.getTeam();
		oldA1cd = Math.round(player.getAbility1CDTimer());
		oldA2cd = Math.round(player.getAbility2CDTimer());
		oldA3cd = Math.round(player.getAbility3CDTimer());
		oldA4cd = Math.round(player.getAbility4CDTimer());
		oldPing = player.getPing();
		oldAbadcd = player.getAbilityBasicAttackCDTime();
	}
	
	public void undo() {
		player.setBaseHealth(oldBaseHealth);
		player.setBaseShield(oldBaseShield);
		player.setMoveSpeed(oldMoveSpeed);
		player.setKills(oldPlayerKills);
		player.setDeaths(oldPlayerDeaths);
		player.setPoints(oldPlayerCaptureScore);
		player.setAssists(oldPlayerAssists);
		player.setAttackSpeedBuffAmount(oldAttackSpeedBuffAmount);
		player.setPowerBuffAmount(oldPowerBuffAmount);
		player.setMoveSpeedBuffAmount(oldMoveSpeedBuffAmount);
		player.setGold(oldGold);
		player.setTeam(oldPlayerTeam);
		player.setAbility1CDTimer(oldA1cd);
		player.setAbility2CDTimer(oldA2cd);
		player.setAbility3CDTimer(oldA3cd);
		player.setAbility4CDTimer(oldA4cd);
		player.setPing(oldPing);
		player.setAbilityBasicAttackCDTime(oldAbadcd);
	}

}
