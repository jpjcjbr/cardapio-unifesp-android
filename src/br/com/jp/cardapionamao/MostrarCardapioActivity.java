package br.com.jp.cardapionamao;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import br.com.jp.cardapionamao.model.Cardapio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

public class MostrarCardapioActivity extends Activity {

	private SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy");
	private SimpleDateFormat sdfJSON = new SimpleDateFormat("dd-MM-yyyy");
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_cardapio);
        
        TextView hoje = (TextView) findViewById(R.id.hoje);
        hoje.setText(sdf.format(new Date()));
        
        Cardapio cardapio = buscarDados(getIntent().getStringExtra("refeicao"));
        
        TextView quentes = (TextView) findViewById(R.id.quentes);
        concatenarTexto(quentes, cardapio.get("Quentes"));
        
        TextView guarnicao = (TextView) findViewById(R.id.guarnicao);
        concatenarTexto(guarnicao, cardapio.get("Guarnição"));
        
        TextView saladas = (TextView) findViewById(R.id.saladas);
        concatenarTexto(saladas, cardapio.get("Saladas"));
        
        TextView pratoPrincipal = (TextView) findViewById(R.id.pratoPrincipal);
        concatenarTexto(pratoPrincipal, cardapio.get("Prato Principal"));
        
        TextView sucos = (TextView) findViewById(R.id.sucos);
        concatenarTexto(sucos, cardapio.get("Sucos"));
        
        TextView sobremesa = (TextView) findViewById(R.id.sobremesa);
        concatenarTexto(sobremesa, cardapio.get("Sobremesa"));
    }
    
	private Cardapio buscarDados(String tipo) {
        
        try {
        	HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(new HttpGet("http://cardapio-unifesp.herokuapp.com/search.json?tipo=" + tipo + "&data=" + sdfJSON.format(new Date())));
			
			InputStream content = response.getEntity().getContent();
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Cardapio cardapio = gson.fromJson(new InputStreamReader(content), Cardapio.class);
			
			return cardapio;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return null;
	}

	private void concatenarTexto(TextView textView, String texto) {
		textView.setText(" " + texto);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
