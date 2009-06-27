package com.command.heap
{
	import com.command.Command;
	
	public class HeapCommandFactory
	{		
		public function HeapCommandFactory()
		{
			// Clase utilitaria
		}
		
		public static function getCommand(obj:Object):Command
		{
			var command:Command;
			
			if (obj.type == InsertCommand.INSERT_COMMAND){
				command = new InsertCommand(obj);
			} else if (obj.type == RemoveRootCommand.REMOVE_ROOT_COMMAND){
				command = new RemoveRootCommand(obj);
			} else if (obj.type == SwapCommand.SWAP_COMMAND){
				command = new SwapCommand(obj);
			}
						
			return command;
		}

	}
}