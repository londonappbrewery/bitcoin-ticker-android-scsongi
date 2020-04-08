package com.londonappbrewery.bitcointicker;

import org.json.JSONException;
import org.json.JSONObject;

public class ConvertingDataModel {


    //Member Variables
    private int mBitcoinToUSD;
    private int mBitcoinToEUR;
    private int mBitcoinToGBP;


    protected static ConvertingDataModel fromJson (JSONObject jsonObject) {

        try {
            ConvertingDataModel conversionRate = new ConvertingDataModel();

            conversionRate.mBitcoinToEUR = jsonObject.getJSONObject("EUR").getInt("15m");
            conversionRate.mBitcoinToUSD = jsonObject.getJSONObject("USD").getInt("15m");
            conversionRate.mBitcoinToGBP = jsonObject.getJSONObject("GBP").getInt("15m");

            return conversionRate;


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    public int getBitcoinToUSD() {
        return mBitcoinToUSD;
    }

    public int getBitcoinToEUR() {
        return mBitcoinToEUR;
    }

    public int getBitcoinToGBP() {
        return mBitcoinToGBP;
    }
}
