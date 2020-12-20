package AhmedMElhalaby_University.com.thingstodo.Unit;

import android.content.Context;

import com.google.gson.Gson;

import AhmedMElhalaby_University.com.thingstodo.Medules.User;

public class AppUserPreferences {

    public static User GetUser(Context context) {
        Gson gson = new Gson();
        String json = AppPreferences.getString(context, "userJson");
        if (json != null) {
                return gson.fromJson(json, User.class);

        }
        return null;
    }


    public static void SetUser(Context context, User user) {
        Gson gson = new Gson();
        String  user_str = gson.toJson(user);
        AppPreferences.saveString(context,   "userJson"  , user_str);
    }
}
