package com.wonderfulrobot.bandcampapi.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
import android.util.Log;

import com.wonderfulrobot.bandcampapi.util.ServiceConfiguration;

public class BandcampAPI {
	
	public static final String HOST = "api.bandcamp.com";
	public static final int PORT = 80;
	public static final String REQUEST_PATH = "/";
	public static final int TRIES = 3;
	public static final String SUFFIX = "";

	private String key;
	private String secret;
	private HttpClient httpClient;
	private boolean gzip;
	
	public BandcampAPI( ServiceConfiguration config) {
		if(config == null)
			throw new NullPointerException("Config should be not Null");
		
		this.key = config.getApiKey();
		this.secret = config.getSecret();
		
		this.gzip = config.isGzip();
		
		createClient(config);
	}
	
	/**
	 * 
	 * Builds HTTPClient infrastructure for communicating with Bandcamp
	 * 
	 * @param config
	 */
	private void createClient(ServiceConfiguration config){
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = config.getConnectionTimeout();
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = config.getSocketTimeout();
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		
		//httpParameters.setParameter(CookieSpecPNames.DATE_PATTERNS, Arrays.asList("EEE, dd MMM yyyy HH:mm:ss z"));

		httpClient = new DefaultHttpClient(httpParameters);
		((AbstractHttpClient) httpClient).setHttpRequestRetryHandler(new
            		DefaultHttpRequestRetryHandler(3, false));
	}
	
	public void shutdown(){
        httpClient.getConnectionManager().shutdown();
	}
	

	public JSONObject sendRequest(String endpoint,  Map<String, Object> fields) throws IOException, JSONException  {
		return sendRequest(BandcampConstants.GET,endpoint, new Object[]{}, fields,new String[]{}, false);
	}
	
	public JSONObject sendRequest(String endpoint, Object[] idValues, Map<String, Object> fields) throws IOException, JSONException  {
		return sendRequest(BandcampConstants.GET,endpoint, idValues,fields, new String[]{},false);
	}
	
	public JSONObject sendRequest(String endpoint, Object[] idValues, Map<String, Object> fields, String[] postAppend) throws IOException, JSONException  {
		return sendRequest(BandcampConstants.GET,endpoint, idValues,fields, postAppend,false);
	}
	
	
	public JSONObject sendRequest(String method,String endpoint, Object[] idValues, Map<String, Object> fields) throws IOException, JSONException  {
		return sendRequest(method,endpoint, idValues,fields,new String[]{}, false);
	}
	
	public JSONObject sendRequest(String method,String endpoint, Object[] idValues, Map<String, Object> fields, String[] postAppend) throws IOException, JSONException  {
		return sendRequest(method,endpoint, idValues,fields,postAppend, false);
	}

	/**
	 * Performs Bandcamp API call. 
	 *
	 * @param method The API method name.
	 * @param idValues An Array of Id's (e.g. Mix Id) to substitute into a URL string
	 * @param fields A map of API parameters.
	 * @throws IOException 
	 * @throws JSONException 
	 * @throws Exception 
	 */
	public JSONObject sendRequest(String method,String endpoint, Object[] idValues, Map<String, Object> fields, String[] postEncodeAppendix, boolean isSecure) throws IOException, JSONException  {
		if (key == null || secret == null) {
			throw new NullPointerException();
		}

		if (method == null) {
			throw new NullPointerException("Method should be given");
		}
		
		String url = buildURL(endpoint, idValues, fields, postEncodeAppendix, isSecure);

		InputStream instream = null;
		try {
			HttpResponse response = null;
			
			response = executeRequest(method,url, httpClient);
			
			
			if (response != null) {
				if(response.getEntity()!= null){
					instream = response.getEntity().getContent();
					
					Header contentEncoding = response.getFirstHeader("Content-Encoding");
					if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
					    instream = new GZIPInputStream(instream);
					}
	
	
					JSONObject json = read(instream);
					
					errorCheck(json, endpoint);
	
					return json;
				}
			}
		}  catch (ParserConfigurationException e) {
			throw new RuntimeException(e);
		}  finally {
			// Closing the input stream will trigger connection release
	        if (instream != null) {
	        	try {
	        		instream.close();
				} catch (IOException e) {
					//We shouldn't handle this exception as we won't be changing state after this point
				}
	        }
		    // When HttpClient instance is no longer needed, 
		    // shut down the connection manager to ensure
		    // immediate deallocation of all system resources
	    }
		
		return null;
	}
	
	/**
	 * 
	 * @param method
	 * @param url
	 * @param client
	 * @return
	 * @throws IOException
	 */
	private HttpResponse executeRequest(String method, String url, HttpClient client) throws IOException {

		HttpGet get = null;
		HttpPost post = null;
		
		try {
			HttpResponse response = null; 

			if(method.equals(BandcampConstants.GET)){
				get = new HttpGet(url);
				
				if(gzip)
					get.addHeader("Accept-Encoding", "gzip");

				
				response = client.execute(get);
			}
			else{
				post = new HttpPost(url);
				
				if(gzip)
					post.addHeader("Accept-Encoding", "gzip");
				
				response = client.execute(post);
			}
			
			return response;
		} catch (ClientProtocolException e) {
	        // In case of an unexpected exception you may want to abort
	        // the HTTP request in order to shut down the underlying
	        // connection and release it back to the connection manager.
			if(get != null)
				get.abort();
			if(post != null)
				post.abort();
	        throw new RuntimeException(e);
		}
	}
	
	/**
	 * 
	 * @param endpoint
	 * @param idValues
	 * @param fields
	 * @param postEncodeAppendix
	 * @param isSecure
	 * @return
	 */
	private String buildURL(String endpoint, Object[] idValues, Map<String, Object> fields, String[] postEncodeAppendix, boolean isSecure){
		//Add an Id's which are part of the path
		StringBuilder requestPathBuilder = new StringBuilder();
		requestPathBuilder.append(REQUEST_PATH);
		if(idValues.length > 0)
			requestPathBuilder.append(String.format(endpoint, idValues));
		else
			requestPathBuilder.append(endpoint);
		
		requestPathBuilder.append(SUFFIX);
		
		//Add The API Key
		fields.put(BandcampConstants.KEY, key);

		removeNulls(fields);
		
		//Add the Request Args
	    Uri uri = buildUri(requestPathBuilder.toString(), fields, isSecure);
	    String uriString =  uri.toString();
	    
	    //Append any subsequent args (gets around Bandcamps illegal use of commas in some aggregated requests (e.g. discography)
	    StringBuilder postAppendBuilder = new StringBuilder();
	    postAppendBuilder.append(uriString);
	    for(String postAppend : postEncodeAppendix){
	    	postAppendBuilder.append(postAppend);
	    }

		Log.i("Query", postAppendBuilder.toString());
	    return postAppendBuilder.toString();
	}
	
	/**
	 * 
	 * @param requestPath
	 * @param fields
	 * @param isSecure
	 * @return
	 */
	private Uri buildUri(String requestPath, Map<String, Object> fields, boolean isSecure){
		Uri.Builder requestUriBuilder = new Uri.Builder()
	    .authority(HOST)
	    .path(requestPath);
		
		if(isSecure)
			requestUriBuilder.scheme("https");
		else
			requestUriBuilder.scheme("http");
		
		for (String key : fields.keySet()) {
			requestUriBuilder.appendQueryParameter(key, fields.get(key).toString());
		}
		Log.i("Query", requestUriBuilder.build().toString());
		return requestUriBuilder.build();
	}
	

	private void removeNulls(Map<String, Object> fields) {
		Iterator<Object> iter = fields.values().iterator();
		while (iter.hasNext()) {
			if (iter.next() == null) {
				iter.remove();
			}
		}
	}
	
	/**
	 * 
	 * Creates an JSON Object from an InputStream
	 * 
	 * @param instream
	 * @return
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws JSONException
	 */
	private JSONObject read(InputStream instream) throws IOException, ParserConfigurationException, JSONException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
		}
		
		JSONObject json = new JSONObject(sb.toString());

		return json;
	}

	
	
	private void errorCheck(JSONObject doc, String method) {
	}

}
