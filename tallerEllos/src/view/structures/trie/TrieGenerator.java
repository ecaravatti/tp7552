package view.structures.trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import controllers.TrieController;

public class TrieGenerator{
  private int count = 0;
  static List<String> list; 
  static{
    list = new ArrayList<String>();

    list = new ArrayList<String>();
    list.add( "cara" );
    list.add( "hola" );
    list.add( "hoy" );
    list.add( "la" );
    list.add( "lata" );
    list.add( "hoyo" );
    list.add( "lo" );
    list.add( "coma" );
    list.add( "lote" );
    list.add( "cosa" );
    list.add( "dado" );
    list.add( "dedo" );
    list.add( "dedal" );
    list.add( "mia" );
    list.add( "mas" );  
    list.add( "para" );
    list.add( "por" );
    list.add( "poro" );
    list.add( "dia" );
    list.add( "puro" );
    list.add( "mar" );    
    list.add( "aro" );
    list.add( "hay" );
    list.add( "haya" );
    list.add( "aros" );
    list.add( "rio" );
    list.add( "roca" );
    list.add( "roma" );
    list.add( "arco" );
    list.add( "rota" );
    list.add( "hizo" );
    list.add( "arma" );
  }
  
  private TrieController controller;
  
  
  public TrieGenerator(TrieController controller) {
    super();
    this.controller = controller;
    this.count = 0;
  }

  public void insert(){
	
    reset();
    
    if ( !this.controller.running() ){
      controller.setRunning(true);
	    controller.insertWord(list.get(count));
	    count++;
	  } 
	  
  }
  
  private void reset(){
    
    if ( count > list.size() ){
      Collections.shuffle( list );
      count = 0;
    }
  }
}
