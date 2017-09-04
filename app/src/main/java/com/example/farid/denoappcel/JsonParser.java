package com.example.farid.denoappcel;

/**
 * Created by Farid on 04/09/2017.
 */

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class JsonParser {

    public static JSONObject LerJsonUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try  {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = Ler(rd);
            return new JSONObject(jsonText);
        } finally {
            is.close();
        }
    }

    private static String Ler(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}
