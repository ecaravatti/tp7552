package com.command.heap
{
	import com.command.Command;
	
	public class InsertCommand extends Command
	{
		public static const INSERT_COMMAND:String = "insertCommand";
		
		/**
		 * Id interno del elemento a insertar.
		 */
		private var insertedId:int;
	
		/**
		 * Valor del elemento a insertar.
		 */
		private var insertedData:int;
	
		/**
		 * Id interno del elemento padre al elemento a insertar. Si null, el
		 * elemento a insertar es la raíz del Heap.
		 */
		private var parentId:int;
	
		/**
		 * Valor del elemento padre al elemento a insertar.
		 */
		private var parentData:int;
	
		/**
		 * true si el elemento a insertar es hijo izquierdo de su padre. Si
		 * <code>parentId == null</code>, se ignora.
		 */
		private var isLeftChild:Boolean;
		
		public function InsertCommand(remoteCommand:Object)
		{ 
			super(remoteCommand.id, INSERT_COMMAND);
			
			this.insertedId = remoteCommand.insertedId;
			this.insertedData = remoteCommand.insertedData;
			this.isLeftChild = remoteCommand.isLeftChild;
			this.parentId = remoteCommand.parentId;
			this.parentData = remoteCommand.parentData;
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