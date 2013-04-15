package cz.gcm.cwg.comm;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import cz.gcm.cwg.constants.Comm;
import cz.gcm.cwg.exceptions.DialogException;
import cz.gcm.cwg.exceptions.LoginException;
import cz.gcm.cwg.exceptions.data.DataFailed;

public class CwgInfo extends CwgApi {

	private static final String LOG_TAG = CwgInfo.class.getName();
	private String name;
	
	public CwgInfo(String nameIncome){
        name = nameIncome;
    }
	
	public JSONObject getResult() {

		JSONObject jsonCwgInfo = null;
		
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("name", name));
	
			String responseData = callUrl(Comm.API_URL_CWGINFO, params);
			try{
				jsonCwgInfo = new JSONObject(responseData);	
			}catch(JSONException e){
				throw new DataFailed(e.getMessage());
			}
			try{
				if(jsonCwgInfo.getInt(Comm.API_ERROR_NAME) > 0){
					return jsonCwgInfo; 
				}
			}catch(JSONException e){
				Log.d(LOG_TAG, "JSONException:"+jsonCwgInfo.toString());
			}
		} catch (LoginException e) {
			Log.w(LOG_TAG, e.getMessage());
		} catch (DialogException e) {
			Log.w(LOG_TAG, e.getMessage());
		}
		return jsonCwgInfo;
	
	}
	
	
}
