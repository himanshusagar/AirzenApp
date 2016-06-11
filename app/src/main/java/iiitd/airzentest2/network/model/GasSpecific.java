package iiitd.airzentest2.network.model;

import android.util.Log;

/**
 * Created by Himanshu Sagar on 10-06-2016.
 */
public class GasSpecific
{



    private String[] healthRisks;

    private int[] pastDay;

    public GasSpecific() {
    }


    private String gasType;

    private int[] pastYear;

    private int aqi;

    private int[] pastWeek;

    private String[] suggestions;

    private int[] pastMonth;

    public String[] getHealthRisks ()
    {
        return healthRisks;
    }

    public void setHealthRisks (String[] healthRisks)
    {
        this.healthRisks = healthRisks;
    }

    public int[] getPastDay ()
    {
        return pastDay;
    }

    public void setPastDay (int[] pastDay)
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

    public int[] getPastYear ()
    {
        return pastYear;
    }

    public void setPastYear (int[] pastYear)
    {
        this.pastYear = pastYear;
    }

    public int getAqi ()
    {
        return aqi;
    }

    public void setAqi (int aqi)
    {
        this.aqi = aqi;
    }

    public int[] getPastWeek ()
    {
        return pastWeek;
    }

    public void setPastWeek (int[] pastWeek)
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

    public int[] getPastMonth ()
    {
        return pastMonth;
    }

    public void setPastMonth (int[] pastMonth)
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