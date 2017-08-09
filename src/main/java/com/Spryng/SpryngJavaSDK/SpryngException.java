package com.Spryng.SpryngJavaSDK;

public class SpryngException extends Exception
{

	private static final long serialVersionUID = -4391751877206752732L;
	
	protected String field;

    public SpryngException(String message)
    {
        super(message);
    }
}
