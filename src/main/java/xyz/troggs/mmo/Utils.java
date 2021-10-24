package xyz.troggs.mmo;

import org.bukkit.ChatColor;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class Utils {

    public String chat(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public String invisibleText(String message){
        StringBuilder hidden = new StringBuilder();
        for(char c : message.toCharArray()){
            hidden.append(ChatColor.COLOR_CHAR + "").append(c);
        }
        return hidden.toString().replace("§", "");
    }
}
