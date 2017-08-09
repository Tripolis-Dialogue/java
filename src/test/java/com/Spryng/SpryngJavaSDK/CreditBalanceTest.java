package com.Spryng.SpryngJavaSDK;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class CreditBalanceTest extends SpryngTest
{
    public CreditBalanceTest() throws SpryngException
    {

    }

    @Test
    public void testInvalidCredentialsReturnNegativeOne() throws SpryngException
    {
        this.spryng.setUsername("admin");
        this.spryng.setSecret("");

        assertEquals(this.spryng.credits.check(), (float) -1.0);

        this.spryng.setUsername(this.username);
        this.spryng.setSecret(this.secret);
    }

    @Test
    public void testCreditCheckReturnsValidFloat() throws SpryngException
    {
        float credits = this.spryng.credits.check();
        assertNotNull(credits);
    }
    
    @Test
    public void testSending() throws SpryngException, ClientProtocolException, IOException, URISyntaxException
    {
    		
    		CloseableHttpClient mockHttpClient = mock(CloseableHttpClient.class);
        CloseableHttpResponse mockHttpResponse = mock(CloseableHttpResponse.class);
        StatusLine mockStatusLine = mock(StatusLine.class);
        HttpEntity mockEntity = mock(HttpEntity.class);
    
        when(mockStatusLine.getStatusCode()).thenReturn(200);
        when(mockHttpResponse.getStatusLine()).thenReturn(mockStatusLine);
        when(mockHttpClient.execute(Mockito.any(HttpGet.class))).thenReturn(mockHttpResponse);
        when(mockHttpResponse.getEntity()).thenReturn(mockEntity);
        when(mockEntity.getContent()).thenReturn(new ByteArrayInputStream("20".getBytes()));
        
        Spryng mockSpryng = new Spryng("test", "test", "1234567", false, new URI("http://localhost"));
        mockSpryng.credits.http.http = mockHttpClient;
        
        float credits = mockSpryng.credits.check();
    
        ArgumentCaptor<HttpGet> argument = ArgumentCaptor.forClass(HttpGet.class);
        verify(mockHttpClient).execute(argument.capture()); 
        HttpGet httpGet = argument.getValue();
        String requestUri = httpGet.getURI().toString();
       	
        assertTrue("Excecute should call the send.php endpoint", requestUri.startsWith("http://localhost/check.php"));     	
        assertEquals((float)20.0, credits);
    }
}
