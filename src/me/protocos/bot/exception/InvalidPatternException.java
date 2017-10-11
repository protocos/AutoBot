package me.protocos.bot.exception;

public class InvalidPatternException extends RuntimeException
{
	private String command;
	private int lineNumber;

	public InvalidPatternException(String command, int lineNumber)
	{
		this.command = command;
		this.lineNumber = lineNumber;
	}

	@Override
	public String getMessage()
	{
		return "'" + command + "' is not a valid pattern on line " + lineNumber;
	}
}
