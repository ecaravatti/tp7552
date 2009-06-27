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
		private var rootData:Number;
	
		/**
		 * Id interno del elemento a ocupar la posición de la raíz.
		 */
		private var newRootId:Number;
	
		/**
		 * Valor del elemento a ocupar la posición de la raíz.
		 */
		private var newRootData:Number;
	
		/**
		 * Id del elemento padre al elemento a ocupar la posición de la raíz.
		 */
		private var parentId:Number;
	
		/**
		 * Valor del elemento padre al elemento a ocupar la posición de la raíz.
		 */
		private var parentData:Number;
	
		/**
		 * true si el elemento a ocupar la posición de la raíz es hijo izquierdo de
		 * su padre.
		 */
		private var isLeftChild:Boolean;
		
		
		public function RemoveRootCommand(rootId:Number, rootData:Number,
			newRootId:Number, newRootData:Number, parentId:Number, parentData:Number,
			isLeftChild:Boolean)
		{

			super(rootId, REMOVE_ROOT_COMMAND);
	
			this.rootId = rootId;
			this.rootData = rootData;
			this.newRootId = newRootId;
			this.newRootData = newRootData;
			this.parentId = parentId;
			this.parentData = parentData;
			this.isLeftChild = isLeftChild;
	
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