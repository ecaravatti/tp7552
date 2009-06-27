package com.command.heap
{
	import com.command.Command;
	
	public class InsertCommand extends Command
	{
		public static const INSERT_COMMAND:String = "insertCommand";
		
		/**
		 * Id interno del elemento a insertar.
		 */
		private var insertedId:Number;
	
		/**
		 * Valor del elemento a insertar.
		 */
		private var insertedData:Number;
	
		/**
		 * Id interno del elemento padre al elemento a insertar. Si null, el
		 * elemento a insertar es la raíz del Heap.
		 */
		private var parentId:Number;
	
		/**
		 * Valor del elemento padre al elemento a insertar.
		 */
		private var parentData:Number;
	
		/**
		 * true si el elemento a insertar es hijo izquierdo de su padre. Si
		 * <code>parentId == null</code>, se ignora.
		 */
		private var isLeftChild:Boolean;
		
		
		public function InsertCommand(insertedId:Number, insertedData:Number, 
			parentId:Number, parentData:Number, isLeftChild:Boolean)
		{
			super(insertedId, INSERT_COMMAND);
	
			this.insertedId = insertedId;
			this.insertedData = insertedData;
			this.parentId = parentId;
			this.parentData = parentData;
			this.isLeftChild = isLeftChild;
	
		}
		
		public function execute():String {
		return (parentId == -1) ? "Inserta como raíz el elemento con clave "
				+ insertedData + " (id=" + insertedId + ")"
				: "Inserta el elemento con clave " + insertedData + " (id="
						+ insertedId + ") como hijo "
						+ (isLeftChild ? "izquierdo" : "derecho")
						+ " del elemento con clave " + parentData + " (id="
						+ parentId + ")";
	}

	}
}