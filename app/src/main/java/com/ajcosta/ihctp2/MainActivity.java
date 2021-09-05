package com.ajcosta.ihctp2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText op1, op2, enviar;
    Button somar;
    TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        op1 = findViewById(R.id.editTextOp1);
        op2 = findViewById(R.id.editTextOp2);
        resultado = findViewById(R.id.textViewResultado);
        somar = findViewById(R.id.buttonSomar);
        enviar = findViewById(R.id.editTextEnviar);

        somar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String op1_text = op1.getText().toString();
                String op2_text = op2.getText().toString();

                if (op1_text.isEmpty() || op2_text.isEmpty())
                    resultado.setText("Preencha os campos");
                else {
                    int res = Integer.parseInt(op1_text) + Integer.parseInt(op2_text);

                    resultado.setText("Resultado: " + res);
                }
            }
        });
    }

    public void enviar(View view)
    {
        Intent i = new Intent(this, MessageActivity.class);
        i.putExtra("data", enviar.getText().toString());
        startActivity(i);
    }

    public void sensores(View view)
    {
        Intent i = new Intent(this, SensorActivity.class);
        startActivity(i);
    }
}