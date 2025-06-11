package com.example.harppia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Cadastrar1Activity extends AppCompatActivity {

    ImageView ivVoltar;
    AutoCompleteTextView ddSexo;
    Button btProximo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastrar1);

        ivVoltar = findViewById(R.id.ivVoltar);
        ddSexo = findViewById(R.id.ddSexo);
        popularEColetarSexo();
        btProximo = findViewById(R.id.btProximo);

        ivVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.entrada_esquerda, R.anim.saida_direita);
            }
        });

        btProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent prox = new Intent(Cadastrar1Activity.this, Cadastro2Activity.class);
                startActivity(prox);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.entrada_esquerda, R.anim.saida_direita);
    }

    private void popularEColetarSexo() {
        String sexo[] = {"Masculino", "Feminino"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.dropdown_item, sexo);
        ddSexo.setAdapter(adapter);
        ddSexo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String sexoSelecionado = ddSexo.getText().toString();
                Toast.makeText(Cadastrar1Activity.this, "Sexo selecionado: " + sexoSelecionado, Toast.LENGTH_SHORT).show();
            }
        });
    }
}