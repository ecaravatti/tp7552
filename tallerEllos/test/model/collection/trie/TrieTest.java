package model.collection.trie;

import model.collection.trie.Trie;

import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class TrieTest {

  @Test
  public void testInsert(){
    Trie<String> trie = new Trie<String>();
    
    trie.insert("la", "1");
    assertEquals( "1", trie.search("la") );
    assertEquals( null, trie.search("l") );
    assertEquals( null, trie.search("las") );
    
    trie.insert("las", "2");
    assertEquals( "1", trie.search("la") );
    assertEquals( "2", trie.search("las") );
    
    trie.insert("laser", "3");
    assertEquals( "1", trie.search("la") );
    assertEquals( "2", trie.search("las") );
    assertEquals( "3", trie.search("laser") );
    assertEquals( null, trie.search("lasu") );
    
    trie.insert("en", "4");
    assertEquals( "1", trie.search("la") );
    assertEquals( "2", trie.search("las") );
    assertEquals( "3", trie.search("laser") );
    assertEquals( "4", trie.search("en") );
    
    trie.insert("entero", "5");
    assertEquals( "4", trie.search("en") );
    assertEquals( "5", trie.search("entero") );
    assertEquals( null, trie.search("entera") );
    
    trie.insert("entera", "6");
    assertEquals( "4", trie.search("en") );
    assertEquals( "5", trie.search("entero") );
    assertEquals( "6", trie.search("entera") );
    
    trie.insert("entre", "7");
    assertEquals( "4", trie.search("en") );
    assertEquals( "5", trie.search("entero") );
    assertEquals( "6", trie.search("entera") );
    assertEquals( "7", trie.search("entre") );
    
  }

  @Test
  public void testRemove(){
    Trie<String> trie = new Trie<String>();
    
    trie.insert("las", "1");
    trie.insert("la", "2");
    trie.insert("laura", "3");
    
    trie.remove("laura");
    assertEquals( "1", trie.search("las") );
    assertEquals( "2", trie.search("la") );
    assertEquals( null, trie.search("laura") );
    
    
    trie.insert("en", "4");
    trie.insert("entre", "5");
    
    
    trie.remove("la");
    assertEquals( "1", trie.search("las"));
    assertEquals( null, trie.search("la") );
    assertEquals( "5", trie.search("entre"));
    assertEquals( "4", trie.search("en") );
  
    
    trie.remove("entre");
    assertEquals( "1", trie.search("las"));
    assertEquals( null, trie.search("entre"));
    assertEquals( "4", trie.search("en") );
    
   
    trie.remove("las");
    assertEquals( null, trie.search("las"));
    assertEquals( "4", trie.search("en") );
   
    trie.remove( "en" );
    assertEquals( null, trie.search("las"));
    assertEquals( null, trie.search("en") );
    
    
  }
}
