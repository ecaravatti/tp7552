package com.command.tree
{
	import com.command.Command;
	
	public class DeleteCommand extends Command
	{
		public static const DELETE_COMMAND:String = "treeDeleteCommand";
		
		/**
		 * Valor del elemento eliminado.
		 */
		private var deletedValue:int;
	
		/**
		 * Id interno del elemento padre al elemento eliminado. Si null, el
		 * elemento eliminado era la raíz del Tree.
		 */
		private var parentId:int;
	
		/**
		 * true si el elemento eliminado es hijo izquierdo de su padre. Si
		 * <code>parentId == null</code>, se ignora.
		 */
		private var isLeftChild:Boolean;
		
		/**
		 * Id del nodo hijo.
		 */
		private var childId:int;
		
		public function DeleteCommand(remoteCommand:Object)
		{ 
			super(remoteCommand.id, DELETE_COMMAND);
			
			this.deletedValue = remoteCommand.deletedValue;
			this.parentId = remoteCommand.parentId;
			this.isLeftChild = remoteCommand.isLeftChild;
			this.childId = remoteCommand.childId;
		}
		
		public function execute():String
		{
			if (parentId == null) {
				var string:String = "Borro la raíz (elemento " + deletedValue + ")";
				if (childId != null) {
					string += ". El elemento " + childId + " es la nueva raíz";
				}
				return string;
			} else {
				return "Borro el elemento " + deletedValue;
			}
		}

	}
}