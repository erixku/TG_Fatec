package com.example.harppia;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class Cadastro2Activity extends AppCompatActivity {

    TextInputEditText edEmail, edConfirmEmail, edSenha, edConfirmSenha;
    ImageView ivVoltar,btnMostrarSenha, btnMostrarSenha2;

    Button btProximo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro2);

        ivVoltar = findViewById(R.id.ivVoltar);
        btnMostrarSenha = findViewById(R.id.btnMostrarSenha);
        btnMostrarSenha2 = findViewById(R.id.btnMostrarSenha2);
        edSenha = findViewById(R.id.edSenha);
        edConfirmSenha = findViewById(R.id.edConfirmSenha);
        edEmail = findViewById(R.id.edEmail);
        edConfirmEmail = findViewById(R.id.edConfirmEmail);
        btProximo2 = findViewById(R.id.btProximo2);

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

        btProximo2.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.VANILLA_ICE_CREAM)
            @Override
            public void onClick(View v) {
                if(edEmail.getText().isEmpty()){
                    edEmail.setError("Campo obrigatório");
                } else if(edConfirmEmail.getText().isEmpty()){
                    edConfirmEmail.setError("Campo obrigatório");
                } else if(!edEmail.getText().toString().equals(edConfirmEmail.getText().toString())){
                    edConfirmEmail.setError("E-mails não coincidem");
                } else if(edSenha.getText().isEmpty()){
                    edSenha.setError("Campo obrigatório");
                } else if(edConfirmSenha.getText().isEmpty()){
                    edConfirmSenha.setError("Campo obrigatório");
                } else if(!edSenha.getText().toString().equals(edConfirmSenha.getText().toString())) {
                    edConfirmSenha.setError("Senhas não coincidem");
                } else {
                    avancar();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void avancar() {
        Intent auth = new Intent(Cadastro2Activity.this, Auth1Activity.class);
        startActivity(auth);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}