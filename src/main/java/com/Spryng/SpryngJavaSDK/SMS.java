package com.Spryng.SpryngJavaSDK;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class SMS implements Constants, APIResponses
{

    /**
     * Instance of the main class to receive auth informaion.
     */
    Spryng api;

    RequestHandler http;

    /**
     * SMS Class constructor
     *
     * @param api
     */
    public SMS(Spryng api)
    {
        this.api = api;
        this.http = new RequestHandler(api, API_SEND_URI);
    }

    /**
     * Sends an SMS message.
     *
     * @param message
     * @return
     * @throws SpryngException
     */
    public SpryngResponse send(Message message) throws SpryngException
    {
        if (!message.isValid())
        {
            throw new SpryngException("The message you provided is invalid. Please refer to the API documentation");
        }

        List<NameValuePair> queryParameters = new ArrayList<NameValuePair>();

        // Add auth information to query string
        String secretPasswordKey = (this.api.isSecretIsAPIKey()) ? "secret" : "password";
        queryParameters.add(new BasicNameValuePair("username", this.api.getUsername()));
        queryParameters.add(new BasicNameValuePair(secretPasswordKey, this.api.getSecret()));
        queryParameters.add(new BasicNameValuePair("sender", this.api.getSender()));

        queryParameters.add(new BasicNameValuePair("destination", message.getDestination()));
        queryParameters.add(new BasicNameValuePair("body", message.getBody()));
        queryParameters.add(new BasicNameValuePair("route", message.getRoute()));

        if (message.getService() != null)
        {
            queryParameters.add(new BasicNameValuePair("service", message.getService()));
        }

        if (message.isAllowLong())
        {
            queryParameters.add(new BasicNameValuePair("allowlong", "1"));
        }
        else
        {
            queryParameters.add(new BasicNameValuePair("allowlong", "0"));
        }

        if (message.isRawEncoding()) {
        		queryParameters.add(new BasicNameValuePair("rawencoding", "1"));
        }
        
        if (message.getReference() != null)
        {
            queryParameters.add(new BasicNameValuePair("reference", message.getReference()));
        }

        this.http.setQueryParameters(queryParameters);

        String response = this.http.send();
        Integer intResponse = Integer.parseInt(response);

        return this.getSpryngResponse(intResponse);
    }

    private SpryngResponse getSpryngResponse(int response)
    {
        SpryngResponse spryngResponse;
        switch (response)
        {
            case RESPONSE_OK:
                spryngResponse = SpryngResponse.OK;
                break;
            case RESPONSE_MISSING_PARAMETER:
                spryngResponse = SpryngResponse.MISSING_PARAMETER;
                break;
            case RESPONSE_USERNAME_TOO_LONG:
                spryngResponse = SpryngResponse.USERNAME_TOO_LONG;
                break;
            case RESPONSE_USERNAME_TOO_SHORT:
                spryngResponse = SpryngResponse.USERNAME_TOO_SHORT;
                break;
            case RESPONSE_PASSWORD_TOO_LONG:
                spryngResponse = SpryngResponse.PASSWORD_TOO_LONG;
                break;
            case RESPONSE_PASSWORD_TOO_SHORT:
                spryngResponse = SpryngResponse.PASSWORD_TOO_SHORT;
                break;
            case RESPONSE_DESTINATION_TOO_LONG:
                spryngResponse = SpryngResponse.DESTINATION_TOO_LONG;
                break;
            case RESPONSE_DESTINATION_TOO_SHORT:
                spryngResponse = SpryngResponse.DESTINATION_TOO_SHORT;
                break;
            case RESPONSE_SENDER_TOO_LONG:
                spryngResponse = SpryngResponse.SENDER_TOO_LONG;
                break;
            case RESPONSE_SENDER_TOO_SHORT:
                spryngResponse = SpryngResponse.SENDER_TOO_SHORT;
                break;
            case RESPONSE_CONTENT_TOO_LONG:
                spryngResponse = SpryngResponse.CONTENT_TOO_LONG;
                break;
            case RESPONSE_CONTENT_TOO_SHORT:
                spryngResponse = SpryngResponse.CONTENT_TOO_SHORT;
                break;
            case RESPONSE_SECURITY_ERROR:
                spryngResponse = SpryngResponse.SECURITY_ERROR;
                break;
            case RESPONSE_UNKNOWN_ROUTE:
                spryngResponse = SpryngResponse.UNKNOWN_ROUTE;
                break;
            case RESPONSE_ROUTE_ACCESS_VIOLATION:
                spryngResponse = SpryngResponse.ROUTE_ACCESS_VIOLATION;
                break;
            case RESPONSE_INSUFFICIENT_CREDITS:
                spryngResponse = SpryngResponse.INSUFFICIENT_CREDITS;
                break;
            case RESPONSE_TECHNICAL_ERROR:
                spryngResponse = SpryngResponse.TECHNICAL_ERROR;
                break;
            default:
                spryngResponse = SpryngResponse.SDK_ERROR;
                break;
        }

        return spryngResponse;
    }
}