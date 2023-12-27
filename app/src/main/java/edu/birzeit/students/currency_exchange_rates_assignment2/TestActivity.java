package edu.birzeit.students.currency_exchange_rates_assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.text.DecimalFormat;

public class TestActivity extends AppCompatActivity {//web services 1
    private final String url = "https://v6.exchangerate-api.com/v6/e2be9620d59c6d974ac7fb35/latest/";
    private final String code = "e2be9620d59c6d974ac7fb35";
    Spinner spinner;
    Spinner spinner2;
    private EditText edtfrom;
    private RequestQueue queue;
    //private EditText edtto;
    private TextView txtto;
    private String conversionvalue,convertFromvalue,convertTovalue;
    private Button btnfind;
private  Button btnUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ViewSetup();
        spinnerSetup();
        btnfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
try {
    Double amountToConvert = Double.valueOf(TestActivity.this.edtfrom.getText().toString());
    String convertFrom = spinner.getSelectedItem().toString();
    String convertTo = spinner2.getSelectedItem().toString();
    getConversionRate(convertFrom, convertTo, amountToConvert);
}catch (Exception e){

}
            }


        });
        btnUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Intent = new Intent(TestActivity.this, FragmentActivity.class);
                startActivity(Intent);
            }
        });
    }
    private void getConversionRate(String convertFrom, String convertTo, Double amountToConvert) {
        String Url = url + convertFrom;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Double conversionRate = jsonObject.getJSONObject("conversion_rates").getDouble(convertTo);
                    Double convertedAmount = round(conversionRate * amountToConvert, 2);
                    txtto.setText(String.valueOf(convertedAmount));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(TestActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }


public  static double round(double value,int replace){
        if(replace<0)throw new IllegalArgumentException();
    BigDecimal bd= BigDecimal.valueOf(value);
    bd=bd.setScale(replace, RoundingMode.HALF_UP);
    return bd.doubleValue();

}
    private void ViewSetup() {
        spinner = findViewById(R.id.spinfrom);
        spinner2 = findViewById(R.id.spinto);
        edtfrom = findViewById(R.id.edtconfrom);
        txtto = findViewById(R.id.txtconto);
        queue = Volley.newRequestQueue(this);
        btnfind=findViewById(R.id.btnfind);
      btnUrl=findViewById(R.id.btnUrl);
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