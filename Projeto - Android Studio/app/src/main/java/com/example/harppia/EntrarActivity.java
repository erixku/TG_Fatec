package com.example.harppia;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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

public class EntrarActivity extends AppCompatActivity {

    ImageView ivVoltar, btnMostrarSenha;
    Button btEntrar;
    TextInputEditText edSenha, edEmail;
    TextView tvEsqueci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_entrar);

        ivVoltar = findViewById(R.id.ivVoltar);
        btnMostrarSenha = findViewById(R.id.btnMostrarSenha);
        btEntrar = findViewById(R.id.btEntrar);
        edSenha = findViewById(R.id.edSenha);
        edEmail = findViewById(R.id.edEmail);
        tvEsqueci = findViewById(R.id.tvEsqueci);

        final boolean[] mostrarSenha = {false};

        ivVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.entrada_esquerda, R.anim.saida_direita);
            }
        });

        btnMostrarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mostrarSenha[0]){
                    edSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnMostrarSenha.setImageResource(R.mipmap.ic_eye_close);
                } else {
                    edSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    btnMostrarSenha.setImageResource(R.mipmap.ic_eye);
                }
                edSenha.setSelection(edSenha.getText().length());
                mostrarSenha[0] = !mostrarSenha[0];
            }
        });

        tvEsqueci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent esqueci = new Intent(EntrarActivity.this, Auth1Activity.class);
                esqueci.putExtra("tipo_auth", "esqueci");
                startActivity(esqueci);
                overridePendingTransition(R.anim.entrada_direita, R.anim.saida_esquerda);
            }
        });

        btEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailValido(edEmail.getText().toString().trim()) && senhaValida(edSenha.getText().toString().trim()))
                    avancar();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void avancar() {
        Intent auth = new Intent(EntrarActivity.this, FimActivity.class);
        startActivity(auth);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private Boolean emailValido(String email) {
        if(email.isEmpty())
            edEmail.setError("Campo obrigatório");
        else if (!email.matches(".+@.+\\..+"))
            edEmail.setError("E-mail inválido");
        else
            return true;
        return false;
    }

    private Boolean senhaValida(String senha) {
        if(senha.isEmpty())
            edSenha.setError("Campo obrigatório");
        else if (senha.length() < 8)
            edSenha.setError("A senha deve ter no mínimo 8 caracteres");
        else if(!senha.matches(".*\\d.*"))
            edSenha.setError("A senha deve conter ao menos um número");
        else if (!senha.matches(".*[!@#$%^&*()_+=\\\\[\\\\]{};':\\\"\\\\\\\\|,.<>/?`~\\\\-].*"))
            edSenha.setError("A senha deve conter ao menos um caractere especial");
        else if (!senha.matches(".*[A-Z].*"))
            edSenha.setError("A senha deve ter ao menos uma letra maiúscula");
        else
            return true;
        return false;
    }
}