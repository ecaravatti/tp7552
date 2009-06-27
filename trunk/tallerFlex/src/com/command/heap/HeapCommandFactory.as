package com.command.heap
{
	import com.command.Command;
	
	public class HeapCommandFactory
	{		
		public function HeapCommandFactory()
		{
		}
		
		public function getCommand(obj:Object):Command
		{
			var command:Command;
			
			if (obj.type == InsertCommand.INSERT_COMMAND){
				command = new InsertCommand(obj.insertedId as Number, obj.insertedData as Number, 
					obj.parentId as Number, obj.parentData as Number, obj.isLeftChild as Boolean);
			} else if (obj.type == RemoveRootCommand.REMOVE_ROOT_COMMAND){
				command = new RemoveRootCommand(obj.rootId, obj.rootData, 
					obj.newRootId, obj.newRootData, obj.parentId, obj.parentData, 
					obj.isLeftChild);
			} else if (obj.type == SwapCommand.SWAP_COMMAND){
				command = new SwapCommand(obj.primaryId, obj.primaryData, obj.secondaryData,
					obj.isSwapDown);
			}
			
			return command;
		}

	}
}