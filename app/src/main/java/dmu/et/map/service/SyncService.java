package dmu.et.map.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import androidx.annotation.Nullable;
import dmu.et.map.http.Http;
import dmu.et.map.model.Location;

public class SyncService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public SyncService() {
        super("Sync");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i("syncing ", "=====>>><<<<======");
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("registeration", Context.MODE_PRIVATE);
        int id = sharedPreferences.getInt("id", -1);
        if(id != -1){
            HashMap<String, Object> params = new HashMap<>();
            params.put("id", id);
            while(true){
                try {
                    JSONArray data = Http.getArray("/sync", params);
                    for(int i=0; i<data.length(); i++){
                        HashMap<String,Object> raw = new HashMap<>();
                        raw.put(Location.LATITUDE, data.getJSONObject(i).getDouble("latitude"));
                        raw.put(Location.LONGTUDE, data.getJSONObject(i).getDouble("longtiude"));
                        raw.put(Location.LEVEL, data.getJSONObject(i).getInt("level"));
                        raw.put(Location.NAME, data.getJSONObject(i).getString("name"));
                        raw.put(Location.ICON, 1);

                        new Location(this).insert(raw);
                    }


                    Thread.sleep(1000);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
