package view.structures.trees;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import controllers.BSTHeightBalancedController;

public class TreeGenerator extends Thread {
  public static int LEFT = 0;
  public static int RIGHT = 1;
  
  private BSTHeightBalancedController controller;
  private int mode;
  static Map< Integer, List<Integer> > map;
  static{
    List<Integer> list = new ArrayList<Integer>();
    map = new Hashtable< Integer, List<Integer> >();

    list = new ArrayList<Integer>();
    list.add( new Integer(25) );
    list.add( new Integer(26) );
    list.add( new Integer(27) );
    list.add( new Integer(28) );
    list.add( new Integer(29) );
    list.add( new Integer(30) );
    list.add( new Integer(31) );
    list.add( new Integer(32) );
    list.add( new Integer(33) );
    list.add( new Integer(34) );
    map.put(new Integer(0), list);
    
    list = new ArrayList<Integer>();
    /*list.add( new Integer(10) );
    list.add( new Integer(9) );
    list.add( new Integer(8) );
    list.add( new Integer(7) );
    list.add( new Integer(6) );
    list.add( new Integer(5) );
    list.add( new Integer(4) );*/
    list.add( new Integer(7) );
    list.add( new Integer(1) );
    list.add( new Integer(3) );

    map.put(new Integer(1), list);

    list = new ArrayList<Integer>();
    /*list.add( new Integer(71) );
    list.add(new Integer(12));
    list.add(new Integer(73));
    list.add(new Integer(9));
    list.add( new Integer(31) );
    list.add( new Integer(76) );
    list.add( new Integer(64) );
    /*  */  list.add( new Integer(73) );
    list.add( new Integer(9) );
    list.add( new Integer(12) );
    list.add( new Integer(31) );
    list.add( new Integer(71) );
    list.add( new Integer(76) );
    list.add( new Integer(64) );
    list.add( new Integer(32) ); //aca pincha
//    list.add( new Integer(45) );

    map.put(new Integer(2), list);
    
    list = new ArrayList<Integer>();
    list.add( new Integer(21) );
    list.add( new Integer(12) );
    list.add( new Integer(63) );
    list.add( new Integer(22) );
    list.add( new Integer(99) );
    list.add( new Integer(67) );
    list.add( new Integer(77) );
    list.add( new Integer(86) );
    list.add( new Integer(45) );
    list.add( new Integer(18) );
    list.add( new Integer(59) );

    map.put(new Integer(3), list);

    list = new ArrayList<Integer>();
    list.add( new Integer(100) );
    list.add( new Integer(50) );
    list.add( new Integer(75) );
    list.add( new Integer(20) );
    list.add( new Integer(30) );
    list.add( new Integer(40) );
    list.add( new Integer(35) );
    list.add( new Integer(37) );
    list.add( new Integer(25) );
    list.add( new Integer(23) );
    map.put(new Integer(4), list);

    list = new ArrayList<Integer>();
    list.add( new Integer(50) );
    list.add( new Integer(70) );
    list.add( new Integer(60) );
    list.add( new Integer(80) );
    list.add( new Integer(75) );
    list.add( new Integer(40) );
    list.add( new Integer(74) );
    /*list.add( new Integer(73) );
    list.add( new Integer(45) );
    list.add( new Integer(55) );
    list.add( new Integer(54) );
    list.add( new Integer(44) );
    list.add( new Integer(43) );*/
    //list.add( new Integer(25) );
    //list.add( new Integer(23) );
    map.put(new Integer(5), list);

    list = new ArrayList<Integer>();
    list.add( new Integer(860) );
    list.add( new Integer(735) );
    list.add( new Integer(92) );
    list.add( new Integer(406) );
    list.add( new Integer(659) );
    list.add( new Integer(6) );
    list.add( new Integer(197) );
    list.add( new Integer(346) );
    list.add( new Integer(718) );
    list.add( new Integer(897) );
    list.add( new Integer(641) );
    list.add( new Integer(1) );
    list.add( new Integer(671) );
    list.add( new Integer(559) );
    list.add( new Integer(472) );
    list.add( new Integer(813) );
    map.put(new Integer(6), list);
  }
  
  public TreeGenerator(BSTHeightBalancedController controller, int mode) {
    super();
    this.controller = controller;
    this.mode = mode;
  }

  @Override
  public void run () {
    int i = 0;
    List<Integer> list = map.get(mode);
    
    while (i < list.size()){
      
      if ( !this.controller.running() ){
        controller.setRunning(true);
        controller.insert(list.get(i).toString());
        i++;
      } 
     try {
        Thread.sleep( 500 );
      } catch (InterruptedException e) {
        e.printStackTrace();
      } 
      
    }
  }

 
}
