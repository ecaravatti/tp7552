package com.command.stack
{
	import com.command.Command;

	public class PushCommand extends Command
	{
		public var content:String;
		
		public static const PUSH_COMMAND:String = "pushCommand";
		
		public function PushCommand(remoteCommand:Object)
		{
			super(remoteCommand.id, PUSH_COMMAND);
			this.content = remoteCommand.content;
		}
		
		public function execute():String {
			return "Push del elemento " + content;
		}
		
	}
}