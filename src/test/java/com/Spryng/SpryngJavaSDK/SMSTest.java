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

public class SMSTest extends SpryngTest
{
    public SMSTest() throws SpryngException
    {
    }

    @Test
    public void testAThing() throws SpryngException
    {
        Message message = new Message();

        message.setDestination(this.destination);
        message.setBody("Hi, your package has been delivered.");
        message.setRoute("business");
        message.setAllowLong(false);
        message.setReference("SMS_0123456789");

        assertTrue(message.isValid());
        this.spryng.SMS.send(message);
        assertEquals(true, true);
    }
    
    @Test
    public void testSending() throws SpryngException, ClientProtocolException, IOException, URISyntaxException
    {
    		
    		CloseableHttpClient mockHttpClient = mock(CloseableHttpClient.class);
        CloseableHttpResponse mockHttpResponse = mock(CloseableHttpResponse.class);
        StatusLine mockStatusLine = mock(StatusLine.class);
        HttpEntity mockEntity = mock(HttpEntity.class);
    
        //and:
        when(mockStatusLine.getStatusCode()).thenReturn(200);
        when(mockHttpResponse.getStatusLine()).thenReturn(mockStatusLine);
        when(mockHttpClient.execute(Mockito.any(HttpGet.class))).thenReturn(mockHttpResponse);
        when(mockHttpResponse.getEntity()).thenReturn(mockEntity);
        when(mockEntity.getContent()).thenReturn(new ByteArrayInputStream(Integer.toString(APIResponses.RESPONSE_OK).getBytes()));
        
        Spryng mockSpryng = new Spryng("test", "test", "1234567", false, new URI("http://localhost"));
        
        Message message = new Message();

        message.setDestination("123456789");
        message.setBody("Hi, your package has been delivered.");
        message.setRoute("business");
        message.setRawEncoding(true);
        message.setAllowLong(true);
        message.setReference("SMS_0123456789");
        mockSpryng.SMS.http.http = mockHttpClient;
        
        SpryngResponse spryngResponse = mockSpryng.SMS.send(message);
    
        ArgumentCaptor<HttpGet> argument = ArgumentCaptor.forClass(HttpGet.class);
        verify(mockHttpClient).execute(argument.capture()); 
        HttpGet httpGet = argument.getValue();
        String requestUri = httpGet.getURI().toString();
       	
        assertTrue("Excecute should call the send.php endpoint", requestUri.startsWith("http://localhost/send.php"));
        assertTrue("rawencoding should be set to 1", requestUri.contains("rawencoding=1"));      	
        assertEquals(SpryngResponse.OK, spryngResponse);
    }
}
