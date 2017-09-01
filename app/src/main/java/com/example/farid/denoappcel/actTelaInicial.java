package com.example.farid.denoappcel;

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
        

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
