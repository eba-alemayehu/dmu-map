package dmu.et.map.tasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;

import dmu.et.map.http.Http;

public class RegisterTask extends AsyncTask<HashMap<String, Object>,Void, JSONObject> {
    Context context;
    public RegisterTask(Context context){
        this.context = context;
    }
    @Override
    protected void onPostExecute(JSONObject obj) {
        super.onPostExecute(obj);
        SharedPreferences sharedPreferences = this.context.getSharedPreferences("registeration",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        try {
            editor.putInt("id", obj.getInt("id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        editor.apply();
    }

    @Override
    protected JSONObject doInBackground(HashMap<String, Object>... params) {
        try {
            return Http.postObj("/register",params[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
