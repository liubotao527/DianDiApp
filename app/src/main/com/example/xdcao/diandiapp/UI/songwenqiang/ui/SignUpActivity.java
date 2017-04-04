package com.example.xdcao.diandiapp.UI.songwenqiang.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xdcao.diandiapp.R;
import com.example.xdcao.diandiapp.UI.songwenqiang.prefs.UserPrefs;


public class SignUpActivity extends AppCompatActivity {
    public UserPrefs userPrefs;
    public TextView skipTextView;
    private EditText telephone;
    private EditText password;
    private Button login;
    private Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        skipTextView = (TextView) findViewById(R.id.skip);

        telephone=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);



        userPrefs = new UserPrefs(SignUpActivity.this);
        if(!TextUtils.isEmpty(userPrefs.getAuthToken())){
            startActivity(new Intent(SignUpActivity.this,MainActivity.class));
            finish();
            return;
        }
        skipTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this,MainActivity.class));
            }
        });
    }
}
