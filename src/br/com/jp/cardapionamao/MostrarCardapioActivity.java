package br.com.jp.cardapionamao;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import br.com.jp.cardapionamao.model.Cardapio;

public class MostrarCardapioActivity extends Activity {

	private SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy");
	private SimpleDateFormat sdfJSON = new SimpleDateFormat("dd-MM-yyyy");
	private String tipo;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_cardapio);
        
        TextView hoje = (TextView) findViewById(R.id.hoje);
        hoje.setText(sdf.format(new Date()));
        
        tipo = getIntent().getStringExtra("refeicao");
		atualizarDados(tipo);
    }
    
    public void atualizarCampos(Cardapio cardapio)
    {
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
    
	public void atualizarDados(String tipo) {
		
		String requestURL = "http://cardapio-unifesp.herokuapp.com/search.json?tipo=" + tipo + "&data=" + sdfJSON.format(new Date());
		
		ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Carregando cardápios...");
        new AtualizacaoCardapioTask(this, progress, requestURL).execute();
	}
	
	public void mostrarMensagemErro() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		alert.setTitle("Erro ao atualizar cardápios");
		alert.setMessage("Verifique sua conexão com a internet.");
		alert.setPositiveButton("Tentar novamente?", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				atualizarDados(tipo);
			}
		});
		
		AlertDialog alertDialog = alert.create();
		
		alertDialog.show();
	}

	private void concatenarTexto(TextView textView, String texto) {
		textView.setText(" " + texto);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
