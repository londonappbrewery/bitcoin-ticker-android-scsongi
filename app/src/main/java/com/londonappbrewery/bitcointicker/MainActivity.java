package com.londonappbrewery.bitcointicker;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://blockchain.info/ticker";

    // Member Variables:
    TextView mPriceTextView;
    private String mSelectedCurrency;
    private int mConversionRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);



        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                // An item was selected. You can retrieve the selected item using
                Log.d("Bitcoin", "Selected item is: BTC" + parent.getItemAtPosition(pos));

                mSelectedCurrency = parent.getItemAtPosition(pos).toString();
                Log.d("Bitcoin", mSelectedCurrency);
                letsDoSomeNetworking();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
                Log.d("Bitcoin", "User did not select anything");
            }
        });

    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking() {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d("Bitcoin", "JSON: " + response.toString());

                ConvertingDataModel convertingBitcoin = ConvertingDataModel.fromJson(response);

                if (mSelectedCurrency.equals("USD")){

                    mConversionRate = convertingBitcoin.getBitcoinToUSD();
                    Log.d("Bitcoin", "BTCUSD Rate is " + convertingBitcoin.getBitcoinToUSD());

                } else if (mSelectedCurrency.equals("EUR")) {

                    mConversionRate = convertingBitcoin.getBitcoinToEUR();
                    Log.d("Bitcoin", "BTCEUR Rate is: " + convertingBitcoin.getBitcoinToEUR());

                } else if (mSelectedCurrency.equals("GBP")){

                    mConversionRate = convertingBitcoin.getBitcoinToGBP();
                    Log.d("Bitcoin", "BTCGBP Rate is: " + convertingBitcoin.getBitcoinToGBP());

                } else {
                    Log.d("Bitcoin", "Something went wrong");
                }

                mPriceTextView.setText("Rate is: " + mConversionRate);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.d("Bitcoin", "Request fail! Status code: " + statusCode);
                Log.d("Bitcoin", "Fail response: " + response);
                Log.e("ERROR", e.toString());
            }
        });
    }


}
