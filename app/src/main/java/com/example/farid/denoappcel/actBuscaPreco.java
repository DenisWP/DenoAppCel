package com.example.farid.denoappcel;
/**
 * Created by Farid on 04/09/2017.
 */
import android.content.*;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import android.os.AsyncTask;

import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class actBuscaPreco extends AppCompatActivity {

    Button btnVoltar, btnLimpar, btnLeitor;
    EditText edtCodBarras;
    TextView txtCodProd, txtDescProduto, txtSifrao, txtValor, txtCodBarras;
    String[] objetos = new String[3];
    String url, CB;
    JSONObject jsonObjectTexts;
    private ZXingScannerView ScannearCodigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); /*Retirando a barra de título do app*/
        setContentView(R.layout.act_busca_preco);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.icone_app);

        txtCodProd = (TextView) findViewById(R.id.txtCodProduto);
        txtDescProduto = (TextView) findViewById(R.id.txtDescProduto);
        txtSifrao = (TextView) findViewById(R.id.txtSifrao);
        txtSifrao.setVisibility(View.INVISIBLE);
        txtValor = (TextView) findViewById(R.id.txtValor);
        edtCodBarras = (EditText) findViewById(R.id.edtCodBarras);
        btnVoltar = (Button) findViewById(R.id.btnVoltar);
        btnVoltar.setBackgroundResource(R.color.Cor);
        btnLimpar = (Button) findViewById(R.id.btnLimpar);
        btnLimpar.setBackgroundResource(R.color.Cor);
        btnLeitor = (Button) findViewById(R.id.btnLeitor);

        try {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm.getActiveNetworkInfo() != null
                    && cm.getActiveNetworkInfo().isAvailable()
                    && cm.getActiveNetworkInfo().isConnected()){

                Bundle bundle = getIntent().getExtras();
                final String regiao = bundle.getString("region");
                edtCodBarras.setFocusableInTouchMode(true);
                edtCodBarras.requestFocus();
                edtCodBarras.setOnKeyListener(new View.OnKeyListener() {
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                            CB = edtCodBarras.getText().toString();
                            url = "http://192.168.0.12:8001/api/Produtos?regiao=" +regiao+ "&codigobarra="+CB;
                            edtCodBarras.setText("");
                            new AsyncTaskExample().execute(url);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Thread.sleep(6000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            limparDados();
                                        }
                                    });
                                }
                            }).start();
                            return true;
                        }
                        return false;
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(), " Seu telefone não está conectado ! \n" +
                        "O aplicativo não irá funcionar ! ", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            Toast.makeText(getApplicationContext(), "Ocorreu um erro interno, comunique ao Setor de TI", Toast.LENGTH_SHORT).show();
        }
    }

    public void voltarInicio(View v) {
        startActivity(new Intent(getBaseContext(), actTelaInicial.class));
    }

    public void limparDados() {
        txtCodProd.setText("");
        txtCodBarras.setText("");
        txtDescProduto.setText("");
        txtValor.setText("");
        txtSifrao.setVisibility(View.INVISIBLE);
    }

    public void limpar(View v) {
        txtCodProd.setText("");
        txtDescProduto.setText("");
        txtValor.setText("");
        txtSifrao.setVisibility(View.INVISIBLE);
    }

    public void Scannear (View v){
        ScannearCodigo = new ZXingScannerView(this);
        ScannearCodigo.setResultHandler(new ZxingScanner());
        setContentView(ScannearCodigo);
        ScannearCodigo.startCamera();
    }

    public class  ZxingScanner implements ZXingScannerView.ResultHandler{
        @Override
        public void handleResult(Result codigo) {
            String dados = codigo.getText();
            setContentView(R.layout.act_busca_preco);
            ScannearCodigo.stopCamera();

            Bundle bundle = getIntent().getExtras();
            final String regiao = bundle.getString("region");

            txtCodProd = (TextView) findViewById(R.id.txtCodProduto);
            txtDescProduto = (TextView) findViewById(R.id.txtDescProduto);
            txtSifrao = (TextView) findViewById(R.id.txtSifrao);
            txtSifrao.setVisibility(View.INVISIBLE);
            txtValor = (TextView) findViewById(R.id.txtValor);

            txtCodBarras = (TextView) findViewById(R.id.txtCodBarras);
            txtCodBarras.setText(dados);

            url = "http://192.168.0.12:8001/api/Produtos?regiao=" +regiao+ "&codigobarra="+dados;
            new AsyncTaskExample().execute(url);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(6000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            limparDados();
                        }
                    });
                }
            }).start();
        }
    }

    public class AsyncTaskExample extends AsyncTask<String, String, String[]> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String[] doInBackground(String... url) {
            try {
                jsonObjectTexts = JsonParser.LerJsonUrl(url[0]);
                objetos[0] = jsonObjectTexts.getString("Codigo");
                objetos[1] = jsonObjectTexts.getString("Descricao");
                objetos[2] = jsonObjectTexts.getString("Preco");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return objetos;
        }

        @Override
        protected void onPostExecute(String[] jsonWinthor) {
            txtCodProd.setText(jsonWinthor[0]);
            txtDescProduto.setText(jsonWinthor[1]);

            double preco = Double.parseDouble(jsonWinthor[2]);
            String strPreco = String.format("%.2f", preco );
            txtValor.setText(strPreco);

            txtSifrao.setVisibility(View.VISIBLE);
        }
    }
}