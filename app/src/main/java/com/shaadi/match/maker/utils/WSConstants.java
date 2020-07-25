package com.shaadi.match.maker.utils;

/**
 * Created by ajay
 */
public class WSConstants {

    public static final String RETROFIT_WITHOUT_HEADERS = "WithoutHeaders";
    public static final String GSON_OF_RETROFIT = "retrofit_gson";
    public static final String GSON_OF_GOOGLE = "google_gson";

    public class Db {
        public static final String DATABASE_NAME = "match_maker_database";
        public static final int CURRENT_DB_VERSION = 1;
    }

    public class Preferences {
        public static final String PREF_NAME = "common_pref";
        public static final String PROPERTY_FIRST_TIME = "first_time_Login";
    }
    
    public class AppUtils{
        public static final int KEY_PERMISSION = 999;
    }


}
