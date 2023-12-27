package edu.birzeit.students.currency_exchange_rates_assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    public static final String EMAIL = "EMAIL";
    public static final String PASS = "PASS";
    public static final String FLAG = "FLAG";
    private boolean flag = false;
    private EditText edtEmail;
    private EditText edtPassword;
    private CheckBox chk;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private ImageView imageViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupViews();



        setupSharedPrefs();
        checkPrefs();
        }


    private void checkPrefs() {
        flag = prefs.getBoolean(FLAG, false);

        if (flag) {
            String name = prefs.getString(EMAIL, "");
            String password = prefs.getString(PASS, "");
            edtEmail.setText(name);
            edtPassword.setText(password);
            chk.setChecked(true);
        }
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    private void setupViews() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        chk = findViewById(R.id.chk);
          imageViewLogin = findViewById(R.id.imageView5);
    }

    public void enterButton(View view) {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String registeredEmail = prefs.getString("Email", "");
        String registeredPassword = prefs.getString("Password", "");
        if (email.equals(registeredEmail) && password.equals(registeredPassword)) {
            if (chk.isChecked()) {
                if (!flag) {
                    editor.putString(EMAIL, email);
                    editor.putString(PASS, password);
                    editor.putBoolean(FLAG, true);
                    editor.commit();

                }

            }
            Intent convertIntent = new Intent(LoginActivity.this, TestActivity.class);
            startActivity(convertIntent);
            // authenticate the user

        }
        else{
            Toast.makeText(LoginActivity.this, "The entry does not match.please Enter the values again!!", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}