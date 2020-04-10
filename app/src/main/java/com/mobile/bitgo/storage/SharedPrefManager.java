package com.mobile.bitgo.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.mobile.bitgo.models.Balance;
import com.mobile.bitgo.models.Settings;
import com.mobile.bitgo.models.User;



    public class SharedPrefManager {

        private static final String SHARED_PREF_NAME = "my_shared_preff";
        private static final String SHARED_PREF_SETTINGS = "my_shared_settings";

        private static SharedPrefManager mInstance;
        private Context mCtx;

        private SharedPrefManager(Context mCtx) {
            this.mCtx = mCtx;
        }


        public static synchronized SharedPrefManager getInstance(Context mCtx) {
            if (mInstance == null) {
                mInstance = new SharedPrefManager(mCtx);
            }
            return mInstance;
        }


        public void saveUser(User user) {

            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putInt("id", user.getId());
            editor.putBoolean("switch1", user.isSwitch1());
            editor.putString("email", user.getEmail());
            editor.putString("firstname", user.getFirstname());
            editor.putString("lastname", user.getLastname());
            editor.putString("country", user.getCountry());
            editor.putString("username", user.getUsername());
            editor.putString("token", user.getLogin_token());


            editor.apply();

        }

        public void saveSettings(Settings settings) {

            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();


            editor.putBoolean("gcode", settings.isGcode());
            editor.putBoolean("backup_phrase", settings.isBackup_phrase());
            editor.putBoolean("email_verified", settings.isEmail_verified());


            editor.apply();

        }

        public void saveBalance(Balance balance) {

            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();


            editor.putString("balance", balance.getBalance());
            editor.putString("balance_sign", balance.getBalance_sign());
            editor.putString("balance_comma_sign",balance.getBalance_comma_sign());
            editor.putString("balance_comma_symbol", balance.getBalance_comma_symbol());


            editor.apply();

        }

        public Balance getBalance() {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return new Balance(
                    sharedPreferences.getString("balance", "0.00"),
                    sharedPreferences.getString("balance_sign", "$0.00"),
                    sharedPreferences.getString("balance_comma_sign", "$0.00"),
                    sharedPreferences.getString("balance_comma_symbol", "0.00 USD")



            );
        }



        public boolean isLoggedIn() {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return sharedPreferences.getInt("id", -1) != -1;
        }

        public User getUser() {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return new User(
                    sharedPreferences.getInt("id", -1),
                    sharedPreferences.getBoolean("switch1", true),
                    sharedPreferences.getString("email", null),
                    sharedPreferences.getString("firstname", null),
                    sharedPreferences.getString("lastname", null),
                    sharedPreferences.getString("country", null),
                    sharedPreferences.getString("username", null),
                    sharedPreferences.getString("token", null)


            );
        }

        public Settings getSettings() {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            return new Settings(
                    sharedPreferences.getBoolean("gcode", true),
                    sharedPreferences.getBoolean("backup_phrase", true),
                    sharedPreferences.getBoolean("email_verified", true)



            );
        }





        public void clear() {
            SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }

    }

