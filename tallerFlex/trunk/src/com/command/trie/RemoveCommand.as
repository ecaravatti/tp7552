package com.command.trie
{
	import com.command.Command;
	
	public class RemoveCommand extends Command
	{
		public static const REMOVE_COMMAND:String = "RemoveCommand";
		
		/** 
		 * Content of the node to remove. 
		 */
		private var content:String;
		
		/** 
		 * Id of the parent node to the one that's
		 * going to be removed. 
		 */
		private var parentId:int;
		
		/** 
		 * Whether the node to remove contains a key or not 
		 */
		private var hasKey:Boolean;
	
	
		/**
		 * The remote object will be a java instance of the
		 * RemoveCommand. All its properties must be mapped
		 * to this instance of the command.
		 */
		public function RemoveCommand(remoteCommand:Object)
		{
			super(remoteCommand.insertedId, REMOVE_COMMAND);
			
			this.content = remoteCommand.content;
			this.parentId = remoteCommand.parentId;
			this.hasKey = remoteCommand.hasKey;
		}
		
		public function execute():String
		{
			return "Remove child node with id '" + getId() + "' and content '"
			+ content + "' from node with id '" + parentId + "'.";
		}
		
		public function undo():String
		{
			return "Add child node with id '" + getId() + "' and content '"
			+ content + "' to node with id '" + parentId 
			+ hasKeyMessage();
		}

		private function hasKeyMessage():String
		{
			return hasKey ? "'. This node has a key." : "'.";	
		}
	}
}