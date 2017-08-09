package com.Spryng.SpryngJavaSDK;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URLEncodedUtils;

public class Spryng implements Constants
{
    protected String username;

    protected String secret;

    protected String sender;

    protected boolean secretIsAPIKey = false;
      
    private URI basePath;

    public SMS SMS;

    public Credits credits;
    
    private static final URI DEFAULT_BASE_PATH_URI; 
    
    static {
    		try {
				DEFAULT_BASE_PATH_URI = new URI(HTTP_SCHEME, API_HOST, API_PATH + URL_PATH_SEPARATOR, null);
			} catch (URISyntaxException e) {
				throw new Error(e);
			}
    }
   
    /**
     * Constructor for Spryng client with option to provide a custom API basePath (e.g. http://test.myservice.com/httpapi/)
     * @param username
     * @param secret
     * @param sender
     * @param secretIsAPIKey
     * @param basePath
     * @throws SpryngException
     */
    public Spryng(String username, String secret, String sender, boolean secretIsAPIKey, URI basePath) throws SpryngException
    {
    		if (!basePath.getPath().endsWith(URL_PATH_SEPARATOR))
    			basePath = basePath.resolve(basePath.getPath() + URL_PATH_SEPARATOR);
    		
		this.basePath = basePath;
		this.setUsername(username);
        this.setSecret(secret);
        this.setSender(sender);
        this.setSecretIsAPIKey(secretIsAPIKey);

        this.SMS = new SMS(this);
        this.credits = new Credits(this);
    }
    
    /**
     * Constructor for Spryng client.
     * @param username
     * @param secret
     * @param sender
     * @param secretIsAPIKey
     * @throws SpryngException
     */
    public Spryng(String username, String secret, String sender, boolean secretIsAPIKey) throws SpryngException
    {
        this(username, secret, sender, secretIsAPIKey, DEFAULT_BASE_PATH_URI);
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getSecret()
    {
        return secret;
    }

    public void setSecret(String secret)
    {
        this.secret = secret;
    }

    public String getSender()
    {
        return sender;
    }

    public void setSender(String sender) throws SpryngException
    {
        if (sender.length() > SENDER_MAX_LENGTH)
        {
            throw new SpryngException("The Sender ID you provided is too long. The maximum length is: " + SENDER_MAX_LENGTH);
        }
        this.sender = sender;
    }

    public boolean isSecretIsAPIKey()
    {
        return secretIsAPIKey;
    }

    public void setSecretIsAPIKey(boolean secretIsAPIKey)
    {
        this.secretIsAPIKey = secretIsAPIKey;
    }

    /**
     * Get the API basepath.
     * As default it returns https://api.spryngsms.com/api/
     * 
     * @return
     */
	public URI getBasePath() {
		return basePath;
	}
}
