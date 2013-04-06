package br.com.jp.cardapionamao;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.widget.TextView;
import br.com.jp.cardapionamao.model.Cardapio;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.Fullscreen;
import com.googlecode.androidannotations.annotations.NoTitle;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.ViewById;

@NoTitle
@Fullscreen
@EActivity(R.layout.activity_mostrar_cardapio)
public class MostrarCardapioActivity extends Activity {

	private SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd/MM/yyyy");
	private SimpleDateFormat sdfJSON = new SimpleDateFormat("dd-MM-yyyy");
	
	@Extra("refeicao")
	String tipo;
	
	private ProgressDialog progressDialog;
	private boolean deuErro;
	
	@ViewById TextView hoje;
	
	@ViewById TextView quentes;
	@ViewById TextView guarnicao;
	@ViewById TextView saladas;
	@ViewById TextView pratoPrincipal;
	@ViewById TextView sucos;
	@ViewById TextView sobremesa;
	
    @AfterViews
    void inicializarTela() {
    	hoje.setText(sdf.format(new Date()));
    	atualizarDados();
    }
    
    @UiThread
    public void atualizarCampos(Cardapio cardapio)
    {
    	quentes.setText(" " + cardapio.get("Quentes"));
    	guarnicao.setText(" " + cardapio.get("Guarnição"));
    	saladas.setText(" " + cardapio.get("Saladas"));
    	pratoPrincipal.setText(" " + cardapio.get("Prato Principal"));
    	sucos.setText(" " + cardapio.get("Sucos"));
    	sobremesa.setText(" " + cardapio.get("Sobremesa"));
    }
    
    @Background
    void buscarCardapios() {
    	atualizarCampos(getCardapio());
		progressDialog.dismiss();
		if(deuErro) mostrarMensagemErro();
    }

	private Cardapio getCardapio() {
		deuErro = false;
		
		HttpClient httpClient = new DefaultHttpClient();
		try {
			String requestURL = "http://cardapio-unifesp.herokuapp.com/search.json?tipo=" + tipo + "&data=" + sdfJSON.format(new Date());
			HttpResponse response = httpClient.execute(new HttpGet(requestURL));
			
			InputStream content = response.getEntity().getContent();
			
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			Cardapio cardapio = gson.fromJson(new InputStreamReader(content), Cardapio.class);
			
			return cardapio;
			
		} catch (Exception e) {
			e.printStackTrace();
			progressDialog.dismiss();
			deuErro = true;
		}
		
		return new Cardapio();
	}
    
	public void atualizarDados() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Carregando cardápios...");
		progressDialog.show();
        buscarCardapios();
	}
	
	@UiThread
	public void mostrarMensagemErro() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
		
		alert.setTitle("Erro ao atualizar cardápios");
		alert.setMessage("Verifique sua conexão com a internet.");
		alert.setPositiveButton("Tentar novamente?", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int id) {
				dialog.cancel();
				atualizarDados();
			}
		});
		
		AlertDialog alertDialog = alert.create();
		
		alertDialog.show();
	}
}
