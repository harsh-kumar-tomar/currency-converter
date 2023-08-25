package com.example.javaappversion9;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    double inrValue = 0;
    double dollarValue = 0;
    double euroValue = 0;
    double yenValue = 0;

    int flag = 0 ;
    EditText input ;
    TextView dollar , euro , yen , loading ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intialize();

        loading.setVisibility(View.INVISIBLE);





    }

    private void intialize() {

        input = findViewById(R.id.input);
        dollar = findViewById(R.id.dollar);
        euro = findViewById(R.id.euro);
        yen = findViewById(R.id.yen);
        loading = findViewById(R.id.loading);
        flag = 0 ;

    }

    public void calculate (View view )
    {

        loading.setVisibility(View.VISIBLE);
        input.setEnabled(false);


        if (flag==0) {

            flag = 1 ;
            Toast.makeText(this , "fetching data from server" , Toast.LENGTH_LONG).show();



            String api = "https://api.currencyapi.com/v3/latest?apikey=cur_live_TKeGlHQfuChT10oNhUVjxV2f60u1UvogBBB7LqGc";

            RequestQueue queue = Volley.newRequestQueue(this);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, api,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            JSONObject rootObject = null;
                            JSONObject dataObject = null;

                            JSONObject inrObject = null;
                            JSONObject dollarObject = null;
                            JSONObject euroObject = null;
                            JSONObject yenObject = null;

                            try {
                                rootObject = new JSONObject(response);

                                dataObject = rootObject.getJSONObject("data");
                                inrObject = dataObject.getJSONObject("INR");
                                dollarObject = dataObject.getJSONObject("USD");
                                euroObject = dataObject.getJSONObject("EUR");
                                yenObject = dataObject.getJSONObject("JPY");
                            } catch (JSONException e) {

                                dollar.setText("hi");

                            }


                            try {
                                inrValue = inrObject.getDouble("value");
                                dollarValue = dollarObject.getDouble("value");
                                euroValue = euroObject.getDouble("value");
                                yenValue = yenObject.getDouble("value");


                                Toast.makeText(getApplicationContext(), "success" , Toast.LENGTH_SHORT).show();



                                setValue();


                            } catch (JSONException e) {
                                euro.setText("error");
                            }


                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            euro.setText("hi");

                        }
                    });


            queue.add(stringRequest);


        }else
        {
            setValue();
        }


    }

    public void setValue()
    {

        loading.setVisibility(View.INVISIBLE);
        input.setEnabled(true);

        String input_string ;
        int input_int ;

        input_string = input.getText().toString();

        input_int = Integer.parseInt(input_string);

        double temp_temp ;

        temp_temp = input_int/inrValue ;

        dollar.setText("$ "+ String.valueOf((int)temp_temp));

        temp_temp = temp_temp*euroValue ;

        euro.setText("€ "+String.valueOf((int)temp_temp));


        temp_temp = temp_temp*yenValue;

        yen.setText("¥ "+String.valueOf((int)temp_temp));




    }


}