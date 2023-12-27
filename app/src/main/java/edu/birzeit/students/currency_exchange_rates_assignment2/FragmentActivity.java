package edu.birzeit.students.currency_exchange_rates_assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FragmentActivity extends AppCompatActivity {
    Spinner spinner;
    Spinner spinner2;
    private EditText edtfrom;
    private RequestQueue queue;
    private TextView txtto;
    private Button btnfind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//web services 2
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        ViewSetup();
        spinnerSetup();

        btnfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Double amountToConvert = Double.valueOf(FragmentActivity.this.edtfrom.getText().toString());
                    String convertFrom = spinner.getSelectedItem().toString();
                    String convertTo = spinner2.getSelectedItem().toString();
                    getConversionRate(convertFrom, convertTo, amountToConvert);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getConversionRate(String convertFrom, String convertTo, Double amountToConvert) {
        String apiUrl = "https://api.freecurrencyapi.com/v1/latest?base=" + convertFrom + "&apikey=fca_live_nt3V2D1pxpH1QojzQKe2ipiiGoLdrSj2mUu0fd6O";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("API Response", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    // Check the actual structure of the JSON response
                    if (jsonObject.has("data")) {
                        Double conversionRate = jsonObject.getJSONObject("data").getDouble(convertTo);

                        Double convertedAmount = round(conversionRate * amountToConvert, 2);
                        txtto.setText(String.valueOf(convertedAmount));
                        Log.d("hh", String.valueOf(convertedAmount));
                    } else {
                        // Handle the case where "rates" key is not present
                        Log.e("JSON Error", "No 'rates' key in the JSON response");
                        Toast.makeText(FragmentActivity.this, "Invalid JSON response", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("API Error", "Error: " + error.toString());
                Toast.makeText(FragmentActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }


    public static double round(double value, int replace) {
        if (replace < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(replace, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void ViewSetup() {
        spinner = findViewById(R.id.spinfrom);
        spinner2 = findViewById(R.id.spinto);
        edtfrom = findViewById(R.id.edtconfrom);
        txtto = findViewById(R.id.txtconto);
        queue = Volley.newRequestQueue(this);
        btnfind = findViewById(R.id.btnfind);
    }

    private void spinnerSetup() {
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(
                this,
                R.array.currencies,
                android.R.layout.simple_spinner_item
        );
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.currencies2,
                android.R.layout.simple_spinner_item
        );
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
    }
}
