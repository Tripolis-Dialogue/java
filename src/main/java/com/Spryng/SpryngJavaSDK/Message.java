package com.Spryng.SpryngJavaSDK;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a SMS message.
 */
public class Message implements Constants
{
    /**
     * The destination phone number.
     */
    protected String destination;

    /**
     * The body of the SMS message.
     */
    protected String body;

    /**
     * The service parameter, used for filtering.
     */
    protected String service;

    /**
     * Parameter to specify the route to be used.
     */
    protected String route;

    /**
     * Indicates whether it's OK to send multiple messages.
     */
    protected boolean allowLong = false;

    /**
     * The Reference parameter, used to identify a message when receiving a delivery report.
     */
    protected String reference;
    
    /**
     * Enable rawEncoding to send UTF-8 strings directly to API without converting first.
     */
    protected boolean rawEncoding = false;

    /**
     * Instantiates a new Message.
     *
     * @param destination the destination
     * @param body        the body
     * @param service     the service
     * @param route       the route
     * @param allowLong   to allow long
     * @param rawEncoding to enable raw encoding
     * @param reference   the reference
     */
    public Message(String destination, String body, String service, String route, boolean allowLong, boolean rawEncoding, String reference)
    {
        this.destination = destination;
        this.body = body;
        this.service = service;
        this.route = route;
        this.allowLong = allowLong;
        this.rawEncoding = rawEncoding;
        this.reference = reference;
    }
    
    /**
     * Instantiates a new Message.
     * 
     * @param the destination
     * @param the body
     * @param the service
     * @param the route
     * @param to allowLong
     * @param the reference
     */
    public Message(String destination, String body, String service, String route, boolean allowLong, String reference)
    {
       this(destination, body, service, route, allowLong, false, reference);
    }

    /**
     * Empty constructor
     */
    public Message()
    {

    }

    /**
     * Checks if the supplied data is valid.
     *
     * @return the boolean
     */
    public boolean isValid()
    {
        if (this.getDestination() == null)
        {
            return false;
        }
        else
        {
            Pattern p = Pattern.compile("^[1-9]{1}[0-9]{3,14}$");
            Matcher m = p.matcher(this.getDestination());

            if (!m.find())
            {
                return false;
            }
        }

        if (this.getBody() == null || (this.isAllowLong() && this.getBody().length() > MAX_BODY_LENGTH_WITH_ALLOW_LONG) ||
                (!this.isAllowLong() && this.getBody().length() > DEFAULT_MAX_BODY_LENGTH))
        {
            return false;
        }

        if (this.getRoute() == null)
        {
            return false;
        }

        if (this.getReference() != null)
        {
            if (this.getReference().length() < 1 || this.getReference().length() > REFERENCE_MAX_LENGTH)
            {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets destination.
     *
     * @return the destination
     */
    public String getDestination()
    {
        return destination;
    }

    /**
     * Sets destination.
     *
     * @param destination the destination
     */
    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    /**
     * Gets body.
     *
     * @return the body
     */
    public String getBody()
    {
        return body;
    }

    /**
     * Sets body.
     *
     * @param body the body
     */
    public void setBody(String body)
    {
        this.body = body;
    }

    /**
     * Gets service.
     *
     * @return the service
     */
    public String getService()
    {
        return service;
    }

    /**
     * Sets service.
     *
     * @param service the service
     */
    public void setService(String service)
    {
        this.service = service;
    }

    /**
     * Gets route.
     *
     * @return the route
     */
    public String getRoute()
    {
        return route;
    }

    /**
     * Sets route.
     *
     * @param route the route
     */
    public void setRoute(String route)
    {
        this.route = route;
    }

    /**
     * Is allow long boolean.
     *
     * @return the boolean
     */
    public boolean isAllowLong()
    {
        return allowLong;
    }

    /**
     * Sets allow long.
     *
     * @param allowLong the allow long
     */
    public void setAllowLong(boolean allowLong)
    {
        this.allowLong = allowLong;
    }

    /**
     * Gets reference.
     *
     * @return the reference
     */
    public String getReference()
    {
        return reference;
    }

    /**
     * Sets reference.
     *
     * @param reference the reference
     */
    public void setReference(String reference)
    {
        this.reference = reference;
    }

    /**
     * Gets rawEncoding setting
     * 
     * @return
     */
	public boolean isRawEncoding() {
		return rawEncoding;
	}

	/**
	 * Sets if rawEncoding should be used
	 * 
	 * @param rawEncoding
	 */
	public void setRawEncoding(boolean rawEncoding) {
		this.rawEncoding = rawEncoding;
	}
    
}
