package com.command.stack
{
	import com.command.Command;

	public class PopCommand extends Command
	{
		public var content:String;
		
		public static const POP_COMMAND:String = "popCommand";
		
		public function PopCommand(remoteCommand:Object)
		{
			super(remoteCommand.id, POP_COMMAND);
			this.content = remoteCommand.content;
		}
		
		public function execute():String {
			return "Pop del elemento " + content;
		}
		
	}
}