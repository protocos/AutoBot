package me.protocos.bot.exception;

public class InvalidCommandException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3278569962674744205L;
	private String command;
	private int lineNumber;

	public InvalidCommandException(String command, int lineNumber)
	{
		this.command = command;
		this.lineNumber = lineNumber;
	}

	@Override
	public String getMessage()
	{
		return "'" + command + "' is not a valid command on line " + lineNumber;
	}
}
