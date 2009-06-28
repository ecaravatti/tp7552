package com.command.tree
{
	import com.command.Command;
	
	public class InsertCommand extends Command
	{
		public static const INSERT_COMMAND:String = "treeInsertCommand";
		
		/**
		 * Valor del elemento a insertar.
		 */
		private var insertedData:int;
	
		/**
		 * Id interno del elemento padre al elemento a insertar. Si null, el
		 * elemento a insertar es la raíz del Tree.
		 */
		private var parentId:int;
	
		/**
		 * true si el elemento a insertar es hijo izquierdo de su padre. Si
		 * <code>parentId == null</code>, se ignora.
		 */
		private var isLeftChild:Boolean;
		
		/**
		 * Balance del elemento.
		 */
		private var balance:int;
		
		public function InsertCommand(remoteCommand:Object)
		{ 
			super(remoteCommand.id, INSERT_COMMAND);
			
			this.insertedData = remoteCommand.insertedData;
			this.isLeftChild = remoteCommand.isLeftChild;
			this.parentId = remoteCommand.parentId;
			this.balance = remoteCommand.balance;
		}
		
		public function execute():String
		{
			if (parentId == null) {
				return "Inserta el elemento " + insertedData + " como raíz";
			} else {
				return "Inserta el elemento " + insertedData + " como hijo "
						+ (isLeftChild ? "izquierdo" : "derecho") + " de "
						+ parentId;
			}
		}

	}
}