package com.ajcosta.ihctp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    TextView recebido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        recebido = (TextView) findViewById(R.id.labelEnviar);

        String texto = getIntent().getStringExtra("data");

        if (texto.isEmpty())
            recebido.setText("Não encontrei mensagem...");
        else
            recebido.setText(texto);



    }

    public void voltar(View view)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}