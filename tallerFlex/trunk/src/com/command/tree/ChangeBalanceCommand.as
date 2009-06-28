package com.command.tree
{
	import com.command.Command;
	
	public class ChangeBalanceCommand extends Command
	{
		public static const CHANGE_BALANCE_COMMAND:String = "treeChangeBalanceCommand";
		
		/**
		 * Valor del nodo.
		 */
		private var nodeValue:int;
	
		/**
		 * Antiguo balance del nodo.
		 */
		private var oldBalance:int;
	
		/**
		 * Nuevo balance del nodo.
		 */
		private var newBalance:int;
		
		public function ChangeBalanceCommand(remoteCommand:Object)
		{ 
			super(remoteCommand.id, CHANGE_BALANCE_COMMAND);
			
			this.nodeValue = remoteCommand.nodeValue;
			this.oldBalance = remoteCommand.oldBalance;
			this.newBalance = remoteCommand.newBalance;
		}
		
		public function execute():String
		{
			return "Cambia el balance del elemento " + nodeValue + " de " + oldBalance + " a " + newBalance;
		}

	}
}