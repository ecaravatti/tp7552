package com.command.trie
{
	import com.command.Command;
	
	public class AddCommand extends Command
	{
		public static const ADD_COMMAND:String = "AddCommand";
		
		/** 
		 * Content of the node to insert. 
		 */
		private var content:String;
		
		/** 
		 * Id of the node where the new element will 
		 * be inserted as child 
		 */
		private var parentId:int;
		
		/** 
		 * Whether the node to insert contains a key or not 
		 */
		private var hasKey:Boolean;
	
	
		/**
		 * The remote object will be a java instance of the
		 * AddCommand. All its properties must be mapped
		 * to this instance of the command.
		 */
		public function AddCommand(remoteCommand:Object)
		{
			super(remoteCommand.insertedId, ADD_COMMAND);
			
			this.content = remoteCommand.content;
			this.parentId = remoteCommand.parentId;
			this.hasKey = remoteCommand.hasKey;
		}
		
		public function execute():String
		{
			return "Add child node with id '" + this.getId() + "' and content '"
					+ content + "' to node with id '" + parentId 
					+ hasKeyMessage();
		}
		
		public function undo():String
		{
			return "Remove child node with id '" + getId() + "' and content '"
			+ content + "' from node with id '" + parentId + "'.";
		}
		
		private function hasKeyMessage():String
		{
			return hasKey ? "'. This node has a key." : "'.";
		}
	}
}