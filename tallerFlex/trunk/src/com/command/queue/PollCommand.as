package com.command.queue
{
	import com.command.Command;
	
	public class PollCommand extends Command
	{
		public var content:String;
		
		public static const POLL_COMMAND:String = "pollCommand";
		
		public function PollCommand(remoteCommand:Object)
		{
			super(remoteCommand.id, POLL_COMMAND);
			this.content = remoteCommand.content;
		}
		
		public function execute():String {
			return "Quita el elemento " + content;
		}


	}
}