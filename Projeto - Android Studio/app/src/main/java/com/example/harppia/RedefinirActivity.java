package com.example.harppia;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class RedefinirActivity extends AppCompatActivity {

    TextInputEditText edSenha, edConfirmSenha;
    ImageView ivVoltar,btnMostrarSenha, btnMostrarSenha2;

    Button btRedefinir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_redefinir);

        ivVoltar = findViewById(R.id.ivVoltar);
        btnMostrarSenha = findViewById(R.id.btnMostrarSenha);
        btnMostrarSenha2 = findViewById(R.id.btnMostrarSenha2);
        edSenha = findViewById(R.id.edSenha);
        edConfirmSenha = findViewById(R.id.edConfirmSenha);
        btRedefinir = findViewById(R.id.btRedefinir);

        final boolean[] mostrarSenha = {false};

        ivVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
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
        btnMostrarSenha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mostrarSenha[0]){
                    edConfirmSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnMostrarSenha2.setImageResource(R.mipmap.ic_eye_close);
                } else {
                    edConfirmSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    btnMostrarSenha2.setImageResource(R.mipmap.ic_eye);
                }
                edConfirmSenha.setSelection(edConfirmSenha.getText().length());
                mostrarSenha[0] = !mostrarSenha[0];
            }
        });

        btRedefinir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (senhaValida(edSenha.getText().toString().trim(), edConfirmSenha.getText().toString().trim()))
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
        Intent entrar = new Intent(RedefinirActivity.this, EntrarActivity.class);
        startActivity(entrar);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private Boolean senhaValida(String senha, String confirmSenha) {
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
        else if(confirmSenha.isEmpty())
            edConfirmSenha.setError("Campo obrigatório");
        else if(!senha.equals(confirmSenha))
            edConfirmSenha.setError("Senhas não coincidem");
        else
            return true;
        return false;
    }
}