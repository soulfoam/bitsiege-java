package soulfoam.bitsiegemainserver.main.command;


public abstract class Command {

	public abstract boolean execute();
	public abstract void undo();


}
