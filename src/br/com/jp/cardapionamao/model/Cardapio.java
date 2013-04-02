package br.com.jp.cardapionamao.model;

import java.util.Date;
import java.util.List;

public class Cardapio {

	public Date data;
	
	public String tipo;
	
	public List<Item> items;
	
	public String get(String string) {
		for(Item item: items)
		{
			if(string.equals(item.categoria))
			{
				return item.descricao;
			}
		}
		return "";
	}

}
