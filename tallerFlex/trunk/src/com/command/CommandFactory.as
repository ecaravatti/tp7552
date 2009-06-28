package com.command
{
	import com.command.heap.InsertCommand;
	import com.command.heap.RemoveRootCommand;
	import com.command.heap.SwapCommand;
	import com.command.queue.OfferCommand;
	import com.command.queue.PollCommand;
	import com.command.stack.PopCommand;
	import com.command.stack.PushCommand;
	import com.command.trie.AddCommand;
	import com.command.trie.RemoveCommand;
	
	
	public class CommandFactory
	{		
		public function CommandFactory()
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
			} else if (obj.type == OfferCommand.OFFER_COMMAND){
				command = new OfferCommand(obj);
			} else if (obj.type == PollCommand.POLL_COMMAND){
				command = new PollCommand(obj);
			} else if (obj.type == PopCommand.POP_COMMAND){
				command = new PopCommand(obj);
			} else if (obj.type == PushCommand.PUSH_COMMAND){
				command = new PushCommand(obj);
			} else if (obj.type == AddCommand.ADD_COMMAND) {
				command = new AddCommand(obj);
			} else if (obj.type == RemoveCommand.REMOVE_COMMAND) {
				command = new RemoveCommand(obj);
			}
						
			return command;
		}

	}
}