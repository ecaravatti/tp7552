package com.command.tree
{
	import com.command.Command;
	
	public class ChangeWeightCommand extends Command
	{
		public static const CHANGE_WEIGHT_COMMAND:String = "treeChangeWeightCommand";
		
		/**
		 * Valor del nodo.
		 */
		private var nodeValue:int;
	
		/**
		 * Antiguo peso del nodo.
		 */
		private var oldWeight:int;
	
		/**
		 * Nuevo peso del nodo.
		 */
		private var newWeight:int;
		
		public function ChangeWeightCommand(remoteCommand:Object)
		{ 
			super(remoteCommand.id, CHANGE_WEIGHT_COMMAND);
			
			this.nodeValue = remoteCommand.nodeValue;
			this.oldWeight = remoteCommand.oldWeight;
			this.newWeight = remoteCommand.newWeight;
		}
		
		public function execute():String
		{
			return "Cambia el peso del elemento " + nodeValue + " de " + oldWeight + " a " + newWeight;
		}

	}
}