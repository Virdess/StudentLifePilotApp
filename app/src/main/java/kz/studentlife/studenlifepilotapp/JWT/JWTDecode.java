package kz.studentlife.studenlifepilotapp.JWT;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class JWTDecode {

    public static String payload;
    public static void decodeJWT(String EncodedString) throws Exception {
        String[] appSplittedStr = EncodedString.split("\\.");
        Log.d("", "Header" + getJSON(appSplittedStr[0]));
        Log.d("", "Payload" + getJSON(appSplittedStr[1]));
        payload = getJSON(appSplittedStr[1]);
    }

    public static String getJSON(String EncodedString) throws UnsupportedEncodingException{
        byte[] decodeByte = Base64.decode(EncodedString, Base64.URL_SAFE);
        return new String(decodeByte, StandardCharsets.UTF_8);
    }
}
