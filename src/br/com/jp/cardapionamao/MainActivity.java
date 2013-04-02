package br.com.jp.cardapionamao;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.google.ads.AdView;

public class MainActivity extends TabActivity {
	
	private AdView adView;
	 
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TabHost tabHost = getTabHost();
		
		Intent intentAlmoco = new Intent().setClass(this, MostrarCardapioActivity.class);
		intentAlmoco.putExtra("refeicao", "ALMOCO");
		
		TabSpec tabSpecAlmoco = tabHost
		  .newTabSpec("almoco")
		  .setIndicator("Almoço")
		  .setContent(intentAlmoco);
		
		Intent intentJantar = new Intent().setClass(this, MostrarCardapioActivity.class);
		intentJantar.putExtra("refeicao", "JANTAR");
 
		TabSpec tabSpecJantar = tabHost
		  .newTabSpec("jantar")
		  .setIndicator("Jantar")
		  .setContent(intentJantar);
 
		tabHost.addTab(tabSpecAlmoco);
		tabHost.addTab(tabSpecJantar);
 
		tabHost.setCurrentTab(0);
	}
	
	@Override
	  public void onDestroy() {
	    if (adView != null) {
	      adView.destroy();
	    }
	    super.onDestroy();
	  }
 
}