package com.example.harppia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class Auth2Activity extends AppCompatActivity {

    ImageView ivVoltar;
    TextView tvEmail;
    TextInputEditText edCodigo;
    Button btProximo4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth2);

        tvEmail = findViewById(R.id.tvEmail);
        edCodigo = findViewById(R.id.edCodigo);
        btProximo4 = findViewById(R.id.btProximo4);
        ivVoltar = findViewById(R.id.ivVoltar);

        String tipoAuth = getIntent().getStringExtra("tipo_auth");

        ivVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
            }
        });

        tvEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
            }
        });

        btProximo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edCodigo.getText().toString().isEmpty()){
                    edCodigo.setError("Informe o código");
                }else{
                    if(edCodigo.getText().toString().isEmpty()){
                        edCodigo.setError("Informe o código");
                    }else{
                        if (tipoAuth.equals("cadastro")) {
                            Intent church = new Intent(Auth2Activity.this, Cadastro3Activity.class);
                            startActivity(church);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        } else if (tipoAuth.equals("esqueci")) {
                            Intent redefinr = new Intent(Auth2Activity.this, RedefinirActivity.class);
                            startActivity(redefinr);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    }
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