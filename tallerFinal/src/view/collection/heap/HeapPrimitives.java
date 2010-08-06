/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.collection.heap;

/**
 *
 * 
 */
public enum HeapPrimitives {

	insert() {

		private static final String INSERT_CODE = "función Insertar ( x, heap ) {\n"
				+ "  si (heap.Tamaño = Tamaño_maximo ) entonces {\n"
				+ "    error \"Heap lleno\";\n"
				+ "  } sino {\n"
				+ "      heap.Tamaño := heap.Tamaño + 1; \n"
				+ "  } \n"
				+ "  heap.Vector[heap.Tamanio] := x; \n"
				+ "  Flotar( heap, heap.Tamaño ); \n"
				+ "}; \n"
				+ "\n"
				+ "funcion Flotar( heap, i ) {\n"
				+ "  Mientras (i > 1 y heap.Vector[i div 2] < heap.Vector[i]) {\n"
				+ "    intercambiar heap.Vector[i div 2] y heap.Vector[i]; \n"
				+ "    i := i div 2 \n" + "  } \n" + "}";

		@Override
		public String getCode() {
			return INSERT_CODE;
		}
	},
	delete() {

		private static final String DELETE_CODE = "función EliminarMax( heap ) : Tipo_elemento { \n"
				+ "  si (heap_vacio( heap )) entonces {\n"
				+ "    error \"Heap vacío\";\n"
				+ "  } sino {\n"
				+ "    x := heap.Vector[1];  \n"
				+ "    heap.Vector[1] := heap.Vector[heap.Tamaño];  \n"
				+ "    heap.Tamaño := heap.Tamaño - 1;  \n"
				+ "    si (heap.Tamaño > 0) entonces {\n"
				+ "      Hundir ( heap, 1);  \n"
				+ "    }  \n"
				+ "    devolver x;\n"
				+ "  }\n"
				+ "}\n"
				+ " \n"
				+ "función Hundir( heap, i ) {\n"
				+ "  Repetir {\n"
				+ "    HijoIzq := 2*i;\n"
				+ "    HijoDer := 2*i+1;\n"
				+ "    j := i;\n"
				+ "    si ( HijoDer <= heap.Tamaño y heap.Vector[HijoDer] > heap.Vector[i] ) entonces {\n"
				+ "      i := HijoDer;  \n"
				+ "    }\n"
				+ "    si (HijoIzq <= heap.Tamaño y heap.Vector[HijoIzq] > heap.Vector[i] ) entonces {\n"
				+ "      i := HijoIzq;  \n"
				+ "    }\n"
				+ "    intercambiar heap.Vector[j] y heap.Vector[i];  \n"
				+ "  } hasta j=i /* Si j=i el nodo alcanzó su posición final */\n"
				+ "}";

		@Override
		public String getCode() {
			return DELETE_CODE;
		}
	};

	public abstract String getCode();
}
