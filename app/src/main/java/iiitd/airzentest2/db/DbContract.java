package iiitd.airzentest2.db;

import android.provider.BaseColumns;

/**
 * Created by Abhishek on 14-12-2015.
 */
public class DbContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "AirPure.db";
    private static final String INT_TYPE = " INTEGER";
    private static final String STRING_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String PRIMARY_KEY = " PRIMARY KEY";

    public DbContract() {
        //Empty constructor to prevent object creation.
    }

    public static abstract class DefectPreferences implements BaseColumns {
        public static final String TABLE_NAME = "Defects";
        public static final String DEFECT = "abcde";

        public static final String[] FULL_PROJECTION={
                TABLE_NAME,
                DEFECT
        };

        public static final String CREATE_TABLE=
                "CREATE TABLE " + TABLE_NAME
                        + " ("
                        + DEFECT + STRING_TYPE + PRIMARY_KEY
                        + " );";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Aqi implements BaseColumns{
        public static final String TABLE_NAME = "CurrentAQI";
        public static final String AQI="aqi";
        public static final String NITROGEN_DIOXIDE="nitrogenDioxide";
        public static final String OZONE="ozone";
        public static final String PM25="pm25";
        public static final String PM10="pm10";
        public static final String CARBON_MONOXIDE="carbonMonoxide";

        public static final String[] FULL_PROJECTION={
                TABLE_NAME,
                AQI,
                NITROGEN_DIOXIDE,
                OZONE,
                PM25,
                PM10,
                CARBON_MONOXIDE
        };
        public static final String CREATE_TABLE=
                "CREATE TABLE " + TABLE_NAME
                        + " ("
                        + AQI + INT_TYPE + PRIMARY_KEY + COMMA_SEP
                        + NITROGEN_DIOXIDE + INT_TYPE + COMMA_SEP
                        + OZONE + INT_TYPE + COMMA_SEP
                        + PM25 + INT_TYPE + COMMA_SEP
                        + PM10 + INT_TYPE + COMMA_SEP
                        + CARBON_MONOXIDE + INT_TYPE
                        + " );";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class Past24Hours implements BaseColumns{
        public static final String TABLE_NAME="past24Hours";
        public static final String HOUR_NUMBER="hourNumber";
        public static final String AQI="aqi";
        public static final String NITROGEN_DIOXIDE="nitrogenDioxide";
        public static final String OZONE="ozone";
        public static final String PM25="pm25";
        public static final String PM10="pm10";
        public static final String CARBON_MONOXIDE="carbonMonoxide";
        public static final String[] FULL_PROJECTION={
                TABLE_NAME,
                HOUR_NUMBER,
                AQI,
                NITROGEN_DIOXIDE,
                OZONE,
                PM25,
                PM10,
                CARBON_MONOXIDE
        };
        public static final String CREATE_TABLE=
                "CREATE TABLE " + TABLE_NAME
                        + " ("
                        + HOUR_NUMBER + INT_TYPE + PRIMARY_KEY + COMMA_SEP
                        + AQI + INT_TYPE + COMMA_SEP
                        + NITROGEN_DIOXIDE + INT_TYPE + COMMA_SEP
                        + OZONE + INT_TYPE + COMMA_SEP
                        + PM25 + INT_TYPE + COMMA_SEP
                        + PM10 + INT_TYPE + COMMA_SEP
                        + CARBON_MONOXIDE + INT_TYPE
                        + " );";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class PastWeek implements BaseColumns{

        public static final String TABLE_NAME="pastWeek";
        public static final String DAY_NUMBER="dayNumber";
        public static final String AQI="aqi";
        public static final String NITROGEN_DIOXIDE="nitrogenDioxide";
        public static final String OZONE="ozone";
        public static final String PM25="pm25";
        public static final String PM10="pm10";
        public static final String CARBON_MONOXIDE="carbonMonoxide";
        public static final String[] FULL_PROJECTION={
                TABLE_NAME,
                DAY_NUMBER,
                AQI,
                NITROGEN_DIOXIDE,
                OZONE,
                PM25,
                PM10,
                CARBON_MONOXIDE
        };
        public static final String CREATE_TABLE=
                "CREATE TABLE " + TABLE_NAME
                        + " ("
                        + DAY_NUMBER + INT_TYPE + PRIMARY_KEY + COMMA_SEP
                        + AQI + INT_TYPE + COMMA_SEP
                        + NITROGEN_DIOXIDE + INT_TYPE + COMMA_SEP
                        + OZONE + INT_TYPE + COMMA_SEP
                        + PM25 + INT_TYPE + COMMA_SEP
                        + PM10 + INT_TYPE + COMMA_SEP
                        + CARBON_MONOXIDE + INT_TYPE
                        + " );";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class PastMonth implements BaseColumns{
        public static final String TABLE_NAME="pastMonth";
        public static final String DAY_NUMBER="dayNumber";
        public static final String AQI="aqi";
        public static final String NITROGEN_DIOXIDE="nitrogenDioxide";
        public static final String OZONE="ozone";
        public static final String PM25="pm25";
        public static final String PM10="pm10";
        public static final String CARBON_MONOXIDE="carbonMonoxide";
        public static final String[] FULL_PROJECTION={
                TABLE_NAME,
                DAY_NUMBER,
                AQI,
                NITROGEN_DIOXIDE,
                OZONE,
                PM25,
                PM10,
                CARBON_MONOXIDE
        };
        public static final String CREATE_TABLE=
                "CREATE TABLE " + TABLE_NAME
                        + " ("
                        + DAY_NUMBER + INT_TYPE + PRIMARY_KEY + COMMA_SEP
                        + AQI + INT_TYPE + COMMA_SEP
                        + NITROGEN_DIOXIDE + INT_TYPE + COMMA_SEP
                        + OZONE + INT_TYPE + COMMA_SEP
                        + PM25 + INT_TYPE + COMMA_SEP
                        + PM10 + INT_TYPE + COMMA_SEP
                        + CARBON_MONOXIDE + INT_TYPE
                        + " );";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static abstract class PastYear implements BaseColumns{
        public static final String TABLE_NAME="pastYear";
        public static final String MONTH_NUMBER="monthNumber";
        public static final String AQI="aqi";
        public static final String NITROGEN_DIOXIDE="nitrogenDioxide";
        public static final String OZONE="ozone";
        public static final String PM25="pm25";
        public static final String PM10="pm10";
        public static final String CARBON_MONOXIDE="carbonMonoxide";
        public static final String[] FULL_PROJECTION={
                TABLE_NAME,
                MONTH_NUMBER,
                AQI,
                NITROGEN_DIOXIDE,
                OZONE,
                PM25,
                PM10,
                CARBON_MONOXIDE
        };
        public static final String CREATE_TABLE=
                "CREATE TABLE " + TABLE_NAME
                        + " ("
                        + MONTH_NUMBER + INT_TYPE + PRIMARY_KEY + COMMA_SEP
                        + AQI + INT_TYPE + COMMA_SEP
                        + NITROGEN_DIOXIDE + INT_TYPE + COMMA_SEP
                        + OZONE + INT_TYPE + COMMA_SEP
                        + PM25 + INT_TYPE + COMMA_SEP
                        + PM10 + INT_TYPE + COMMA_SEP
                        + CARBON_MONOXIDE + INT_TYPE
                        + " );";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
