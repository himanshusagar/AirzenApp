package iiitd.airzentest2.json;

import java.io.BufferedReader;

/**
 * Created by Abhishek on 14-12-2015.
 */
public class DataHandler {
    BufferedReader data;

    public DataHandler(BufferedReader inputReader){

        this.data = inputReader;
    }

    public BufferedReader getData(){
        return data;
    }

    public void setData(BufferedReader newData){
        this.data = newData;
    }
}
