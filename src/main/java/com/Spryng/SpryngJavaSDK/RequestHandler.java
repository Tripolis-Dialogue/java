package com.Spryng.SpryngJavaSDK;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Entity;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class RequestHandler implements Constants
{
    protected CloseableHttpClient http;

    protected List<NameValuePair> queryParameters;

    protected URI uri;
    
    protected String endpoint;
    

    public RequestHandler(Spryng api, String endpoint)
    {
        this.http = HttpClients.createDefault();
        this.uri = api.getBasePath().resolve(endpoint);
    }

    public String send() throws SpryngException
    {
    	 	
		try {
			
			URIBuilder builder = new URIBuilder(this.getUri());
			builder.setCharset(Charset.forName(URL_ENCODING));
	    	 	builder.addParameters(this.queryParameters);
	        HttpGet get = new HttpGet(builder.build());
			
	        CloseableHttpResponse  httpResponse = this.http.execute(get);
	        try {
	        		int httpResponseCode = httpResponse.getStatusLine().getStatusCode();
				if (httpResponseCode == HttpStatus.SC_OK)
				{
				    return this.httpResponseToString(httpResponse);
				}
				else
				{
				    throw new SpryngException("Got non-OK HTTP status for request. Code: " + httpResponseCode);
				}	
			} finally {
				httpResponse.close();
			}
	        
		} catch (IOException e) {
			throw new SpryngException("HTTP Request failed. Message: " + e.getMessage());
		} catch (URISyntaxException e) {
			throw new SpryngException("Error occurred while trying to initiate URI for request. Message: " + e.getMessage());
		}
    }

    public String httpResponseToString(HttpResponse httpResponse) throws SpryngException
    {
        String response = "";
        if (httpResponse.getEntity() != null)
        {
            try
            {
                response = EntityUtils.toString(httpResponse.getEntity(), URL_ENCODING);
            }
            catch (ParseException e)
            {
                throw new SpryngException("Could not properly parse HTTP response to a string. Additional Message: "
                    + e.getMessage());
            }
            catch (IOException e)
            {
                throw new SpryngException("Could not parse the HTTP response to a string.");
            }
        }

        return response;
    }

    public List<NameValuePair> getQueryParameters()
    {
        return queryParameters;
    }

    public void setQueryParameters(List<NameValuePair> queryParameters)
    {
        this.queryParameters = queryParameters;
    }

    public URI getUri()
    {
        return uri;
    }

    public void setUri(URI uri)
    {
        this.uri = uri;
    }

}
