package com.Spryng.SpryngJavaSDK;

import junit.framework.TestCase;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

public class SpryngTest extends TestCase
{
    public final String username = "";

    public final String secret = "";

    public final boolean secretIsApiKey = false;

    public final String destination = "";

    public final String senderName = "Spryng BV";

    public Spryng spryng;

    public SpryngTest() throws SpryngException
    {
        this.spryng = new Spryng(this.username, this.secret, this.senderName, this.secretIsApiKey);
    }

    @Test
    public void testConstructorGivesProperInstance()
    {
        assertEquals(this.spryng.getClass(), Spryng.class);
    }
    
    @Test
    public void testConstructorWithCustomBasePath() throws URISyntaxException
    {
		try {
			Spryng spryng = new Spryng(this.username, this.secret, this.senderName, this.secretIsApiKey, new URI("http://api.mydomain.com/httpapi"));
			
			assertEquals("Should add trailing slash to custom base path","http://api.mydomain.com/httpapi/", spryng.getBasePath().toString());
			
			spryng = new Spryng(this.username, this.secret, this.senderName, this.secretIsApiKey, new URI("https://api2.mydomain.com/httpapi/"));
			assertEquals("Should not add trailing slash to custom base path that ends with slash","https://api2.mydomain.com/httpapi/", spryng.getBasePath().toString());
			
			spryng = new Spryng(this.username, this.secret, this.senderName, this.secretIsApiKey);
			assertEquals("Should set default base path when no custom path URI is set","https://api.spryngsms.com/api/", spryng.getBasePath().toString());
			
		} catch (SpryngException e) {
			fail("should not throw exception");
		}
    }
}