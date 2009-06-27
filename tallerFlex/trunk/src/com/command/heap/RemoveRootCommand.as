package com.command.heap
{
	import com.command.Command;
	
	public class RemoveRootCommand extends Command
	{
		public static const REMOVE_ROOT_COMMAND:String  = "removeRoot";
			
		/**
		 * Id interno del elemento raíz.
		 */
		private var rootId:Number;
	
		/**
		 * Valor del elemento raíz a remover.
		 */
		private var rootData:int;
	
		/**
		 * Id interno del elemento a ocupar la posición de la raíz.
		 */
		private var newRootId:int;
	
		/**
		 * Valor del elemento a ocupar la posición de la raíz.
		 */
		private var newRootData:int;
	
		/**
		 * Id del elemento padre al elemento a ocupar la posición de la raíz.
		 */
		private var parentId:int;
	
		/**
		 * Valor del elemento padre al elemento a ocupar la posición de la raíz.
		 */
		private var parentData:int;
	
		/**
		 * true si el elemento a ocupar la posición de la raíz es hijo izquierdo de
		 * su padre.
		 */
		private var isLeftChild:Boolean;
		
		
		public function RemoveRootCommand(remoteCommand:Object)
		{
			super(rootId, REMOVE_ROOT_COMMAND);
	
			this.rootId = remoteCommand.rootId;
			this.rootData = remoteCommand.rootData;
			this.newRootId = remoteCommand.newRootId;
			this.newRootData = remoteCommand.newRootData;
			this.parentId = remoteCommand.parentId;
			this.parentData = remoteCommand.parentData;
			this.isLeftChild = remoteCommand.isLeftChild;
	
		}
		
		public function execute():String {
			return "Remueve la raíz con clave " + rootData + " (id=" + rootId
					+ ") y es reemplazada por el elemento con clave "
					+ newRootData + " (id=" + newRootId + "), hijo "
					+ (isLeftChild ? "izquierdo" : "derecho")
					+ " del elememento con clave " + parentData + " (id="
					+ parentId + ")";
	}

	}
}