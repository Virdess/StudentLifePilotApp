package kz.studentlife.studenlifepilotapp.JWT;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Context context;
    int privatemode = 0;
    private static final String PREF_NAME="JWTTOKEN";
    private static final String isLoggedIn = "ISLOGIN";
    private static final String KeyName = "key-name";
    private static final String Name = "name";

    public TokenManager (Context context){
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, privatemode);
        editor = preferences.edit();
    }

    public void CreateLoginSession(String keyname, String username){
        //editor.putString(keyname, keyname);
        editor.putString(keyname, username);

        editor.commit();
    }
}
