package edu.birzeit.students.currency_exchange_rates_assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private EditText  editTextEmail, editTextPassword, editTextPassword2;
    private ImageView imageViewSignUp;
    private TextView loginTextView;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ViewSetup();
        setupSharedPrefs();


        imageViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                handle login text click
                Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

    }
    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }
    private void ViewSetup() {
        editTextEmail = findViewById(R.id.editTextTextEmail);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        editTextPassword2 = findViewById(R.id.editTextTextPassword2);
        imageViewSignUp = findViewById(R.id.imageView5);

        loginTextView = findViewById(R.id.textView5);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"ondestroy");
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String password2 = editTextPassword2.getText().toString().trim();
        editor.putString("Email",email);
        editor.putString("Password",password);
        editor.putString("Password2",password2);
        editor.apply();
        Log.d("Emai",email);

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LC888","OnStart()");
        String email = prefs.getString("Email", "");
        String password = prefs.getString("Password", "");
        String password2 = prefs.getString("Password2", "");
        if(email!=null||password!=null||password2!=null) {
            editTextEmail.setText(email);
            editTextPassword.setText(password);
            editTextPassword2.setText(password2);
        }
    }

    private void signUp() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String password2 = editTextPassword2.getText().toString().trim();


        if ( email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
            Toast.makeText(SignupActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(password2)) {
            Toast.makeText(SignupActivity.this, "Password do not match", Toast.LENGTH_SHORT).show();
            return;
        }
        editor.putString("Email",email);
        editor.putString("Password",password);
        editor.putString("Password2",password2);
        editor.apply();
        Toast.makeText(SignupActivity.this, "user registered.", Toast.LENGTH_LONG).show();
    }
}
