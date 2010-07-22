/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.heap;

/**
 *
 * @author Pato
 */
public enum HeapPrimitives {

    insert() {

        private static final String INSERT_CODE = "funcion Insertar ( x, heap ) \n" +
                                                  "  Si heap.Tamaño == Tamaño_maximo entonces \n" +
                                                  "    error Heap_lleno \n" +
                                                  "  fin si \n" +
                                                  "    Sino \n" +
                                                  "      heap.Tamaño := heap.Tamaño + 1; \n" +
                                                  "    fin sino \n" +
                                                  "      heap.Vector[heap.Tamanio] := x; \n" +
                                                  "      Flotar ( heap, heap.Tamaño ) \n" +
                                                  "  fin funcion; \n" +
                                                  " \n" +
                                                  "funcion Flotar ( heap, i ) \n "+
                                                  "  Mientras (i > 1 y heap.Vector[i div 2] < heap.Vector[i]) \n" +
                                                  "    intercambiar heap.Vector[i div 2] y heap.Vector[i]; \n" +
                                                  "    i := i div 2 \n" +
                                                  "  fin mientras \n" +
                                                  "fin funcion \n";

        @Override
        public String getCode() {
            return INSERT_CODE;
        }
    },
    delete() {

        private static final String DELETE_CODE = "funcion EliminarMax ( heap ) : Tipo_elemento \n" +
                                                  "  Si heap_vacio ( heap ) entonces  \n" +
                                                  "    error Monticulo_vacio  \n" +
                                                  "  fin Si  \n" +
                                                  "  Sino  \n" +
                                                  "    x := heap.Vector[1];  \n" +
                                                  "    heap.Vector[1] := heap.Vector[heap.Tamaño];  \n" +
                                                  "    heap.Tamaño := heap.Tamaño - 1;  \n" +
                                                  "    Si heap.Tamaño > 0 entonces  \n" +
                                                  "      Hundir ( heap, 1);  \n" +
                                                  "    fin si  \n" +
                                                  "    devolver x  \n" +
                                                  "  fin sino  \n" +
                                                  " fin funcion  \n" +
                                                  " \n" +
                                                  "funcion Hundir ( heap, i )  \n" +
                                                  " Repetir  \n" +
                                                  "	  HijoIzq := 2*i;  \n" +
                                                  "   HijoDer := 2*i+1;  \n" +
                                                  "	  j := i;  \n" +
                                                  "	  Si ( HijoDer <= heap.Tamaño y heap.Vector[HijoDer] > heap.Vector[i] ) entonces  \n" +
                                                  "	 	  i := HijoDer;  \n" +
                                                  "   fin si  \n" +
                                                  "   Si (HijoIzq <= heap.Tamaño y heap.Vector[HijoIzq] > heap.Vector[i] ) entonces  \n" +
                                                  "	    i := HijoIzq;  \n" +
                                                  "	  fin si  \n" +
                                                  "     intercambiar heap.Vector[j] y heap.Vector[i];  \n" +
                                                  " hasta j=i {Si j=i el nodo alcanzó su posición final}  \n" +
                                                  "fin funcion  \n";

        @Override
        public String getCode() {
            return DELETE_CODE;
        }
    };

    public abstract String getCode();
}
