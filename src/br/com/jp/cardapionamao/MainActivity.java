package br.com.jp.cardapionamao;

import android.app.TabActivity;
import android.content.Intent;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Fullscreen;
import com.googlecode.androidannotations.annotations.NoTitle;

@NoTitle
@Fullscreen
@EActivity(R.layout.activity_main)
public class MainActivity extends TabActivity {
	
	@AfterViews
    void inicializarTela() {
		TabHost tabHost = getTabHost();
		
		Intent intentAlmoco = new Intent().setClass(this, MostrarCardapioActivity_.class);
		intentAlmoco.putExtra("refeicao", "ALMOCO");
		
		TabSpec tabSpecAlmoco = tabHost
		  .newTabSpec("almoco")
		  .setIndicator("Almoço")
		  .setContent(intentAlmoco);
		
		Intent intentJantar = new Intent().setClass(this, MostrarCardapioActivity_.class);
		intentJantar.putExtra("refeicao", "JANTAR");
 
		TabSpec tabSpecJantar = tabHost
		  .newTabSpec("jantar")
		  .setIndicator("Jantar")
		  .setContent(intentJantar);
 
		tabHost.addTab(tabSpecAlmoco);
		tabHost.addTab(tabSpecJantar);
 
		tabHost.setCurrentTab(0);
    }
}