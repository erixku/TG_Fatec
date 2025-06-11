package com.example.harppia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class Cadastro3Activity extends AppCompatActivity {

    TextInputEditText edMinisterio;
    Button btCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro3);

        edMinisterio = findViewById(R.id.edMinisterio);
        btCadastrar = findViewById(R.id.btCadastrar);

        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edMinisterio.getText().toString().isEmpty()) {
                    edMinisterio.setError("Campo obrigatório");
                } else {
                    // Lógica para cadastrar o usuário
                    Intent inicio = new Intent(Cadastro3Activity.this, InicioActivity.class);
                    startActivity(inicio);
                    finish();
                    overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
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