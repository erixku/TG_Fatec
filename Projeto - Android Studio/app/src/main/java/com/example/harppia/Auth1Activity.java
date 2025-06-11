package com.example.harppia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class Auth1Activity extends AppCompatActivity {

    TextView tvSMS;
    Button btProximo3;
    TextInputEditText edCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth1);

        tvSMS = findViewById(R.id.tvSMS);
        btProximo3 = findViewById(R.id.btProximo3);
        edCodigo = findViewById(R.id.edCodigo);

        tvSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Auth1Activity.this, Auth2Activity.class);
                startActivity(email);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        btProximo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edCodigo.getText().toString().isEmpty()){
                    edCodigo.setError("Informe o código");
                }else{
                    Intent church = new Intent(Auth1Activity.this, Cadastro3Activity.class);
                    startActivity(church);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}