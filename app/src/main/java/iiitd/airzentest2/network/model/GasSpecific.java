package iiitd.airzentest2.network.model;

import android.util.Log;

/**
 * Created by Himanshu Sagar on 10-06-2016.
 */
public class GasSpecific
{
    private String[] healthRisks;

    private String[] pastDay;

    public GasSpecific() {
    }

    public GasSpecific(String[] healthRisks, String[] pastDay, String gasType, String[] pastYear, String aqi, String[] pastWeek, String[] suggestions, String[] pastMonth) {
        this.healthRisks = healthRisks;
        this.pastDay = pastDay;
        this.gasType = gasType;
        this.pastYear = pastYear;
        this.aqi = aqi;
        this.pastWeek = pastWeek;
        this.suggestions = suggestions;

        this.pastMonth = pastMonth;
    }

    private String gasType;

    private String[] pastYear;

    private String aqi;

    private String[] pastWeek;

    private String[] suggestions;

    private String[] pastMonth;

    public String[] getHealthRisks ()
    {
        return healthRisks;
    }

    public void setHealthRisks (String[] healthRisks)
    {
        this.healthRisks = healthRisks;
    }

    public String[] getPastDay ()
    {
        return pastDay;
    }

    public void setPastDay (String[] pastDay)
    {
        this.pastDay = pastDay;
    }

    public String getGasType ()
    {
        return gasType;
    }

    public void setGasType (String gasType)
    {
        this.gasType = gasType;
    }

    public String[] getPastYear ()
    {
        return pastYear;
    }

    public void setPastYear (String[] pastYear)
    {
        this.pastYear = pastYear;
    }

    public String getAqi ()
    {
        return aqi;
    }

    public void setAqi (String aqi)
    {
        this.aqi = aqi;
    }

    public String[] getPastWeek ()
    {
        return pastWeek;
    }

    public void setPastWeek (String[] pastWeek)
    {
        this.pastWeek = pastWeek;
    }

    public String[] getSuggestions ()
    {
        return suggestions;
    }

    public void setSuggestions (String[] suggestions)
    {
        this.suggestions = suggestions;
    }

    public String[] getPastMonth ()
    {
        return pastMonth;
    }

    public void setPastMonth (String[] pastMonth)
    {
        this.pastMonth = pastMonth;
    }

    @Override
    public String toString()
    {
        //return "GSpecific [healthRisks = "+healthRisks+", pastDay = "+pastDay+", gasType = "+gasType+", pastYear = "+pastYear+", aqi = "+aqi+", pastWeek = "+pastWeek+", suggestions = "+suggestions+", pastMonth = "+pastMonth+"]";

        for (int i= 0 ; i < healthRisks.length ; i++)
        {
            Log.d("H-Risk" + Integer.toString(i) , healthRisks[i] );
        }
        return " ";

    }
}