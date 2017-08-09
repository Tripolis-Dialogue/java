package com.Spryng.SpryngJavaSDK;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Credits implements Constants, APIResponses
{
    protected Spryng api;

    protected RequestHandler http;

    public Credits(Spryng api)
    {
        this.api = api;
        this.http = new RequestHandler(api, API_BALANCE_URI);
    }

    public float check() throws SpryngException
    {
        List<NameValuePair> queryParameters = new ArrayList<NameValuePair>();
        // Add auth information to query string
        String secretPasswordKey = (this.api.isSecretIsAPIKey()) ? "secret" : "password";
        queryParameters.add(new BasicNameValuePair("username", this.api.getUsername()));
        queryParameters.add(new BasicNameValuePair(secretPasswordKey, this.api.getSecret()));
        queryParameters.add(new BasicNameValuePair("sender", this.api.getSender()));
        this.http.setQueryParameters(queryParameters);

        return (float) Float.valueOf(this.http.send());
    }
}