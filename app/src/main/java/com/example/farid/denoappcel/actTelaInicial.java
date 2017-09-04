package com.example.farid.denoappcel;

import android.content.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class actTelaInicial extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spnSupermercados;
    TextView txtSelecionar;
    //String region;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_tela_inicial);

        spnSupermercados = (Spinner) findViewById(R.id.spnLojas);
        txtSelecionar = (TextView) findViewById(R.id.txtSelecionar);

        ArrayAdapter<CharSequence> adpLojas = ArrayAdapter.createFromResource(this, R.array.Lojas, android.R.layout.simple_spinner_item);
        adpLojas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnSupermercados.setAdapter(adpLojas);
        spnSupermercados.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int pos = spnSupermercados.getSelectedItemPosition();
        final Intent intent;

        switch (pos){
            case 0:
                break;
            case 1:
                intent = new Intent(this, actBuscaPreco.class);
                intent.putExtra("region","1");
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, actBuscaPreco.class);
                intent.putExtra("region","2");
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(this, actBuscaPreco.class);
                intent.putExtra("region","3");
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(actTelaInicial.this, actBuscaPreco.class);
                intent.putExtra("region","4");
                startActivity(intent);
                break;
            case 5:
                intent = new Intent(actTelaInicial.this, actBuscaPreco.class);
                intent.putExtra("region","5");
                startActivity(intent);
                break;
            case 6:
                intent = new Intent(actTelaInicial.this, actBuscaPreco.class);
                intent.putExtra("region","6");
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
