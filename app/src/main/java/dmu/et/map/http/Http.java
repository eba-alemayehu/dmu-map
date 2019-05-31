package dmu.et.map.http;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class Http {
    private static final String ROOT_URL = "http://test";

    public static JSONArray getArray(String url, HashMap<String, Object> params) throws IOException, URISyntaxException, JSONException {
        return new JSONArray(getString(get(url, params)));
    }

    public static JSONObject getObj(String url, HashMap<String, Object> params) throws IOException, URISyntaxException, JSONException {
        return new JSONObject(getString(get(url, params)));
    }

    public static JSONArray postArray(String url, HashMap<String, Object> params) throws IOException, URISyntaxException, JSONException {
        return new JSONArray(getString(post(url, params)));
    }

    public static JSONObject postObj(String url, HashMap<String, Object> params) throws IOException, URISyntaxException, JSONException {
        return new JSONObject(getString(post(url, params)));
    }

    public static HttpResponse get(String url, HashMap<String, Object> params) throws IOException, URISyntaxException {
        HttpGet request = new HttpGet(uri(url, params));
        return HttpClientBuilder.create().build().execute(request);
    }

    public static HttpResponse post(String url, HashMap<String, Object> params) throws IOException, URISyntaxException {
        HttpPost request = new HttpPost(uri(url, params));
        return HttpClientBuilder.create().build().execute(request);
    }

    public static String getString(HttpResponse response) throws IOException, JSONException {
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));

        StringBuffer result = new StringBuffer();
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();
    }

    public static URI uri(String url, HashMap<String, Object> params) throws IOException, URISyntaxException {
        URIBuilder builder = new URIBuilder(ROOT_URL + url);
        for (Map.Entry<String, Object> param : params.entrySet()) {
            builder.setParameter(param.getKey(), param.getValue().toString());
        }
        return builder.build();
    }
}
