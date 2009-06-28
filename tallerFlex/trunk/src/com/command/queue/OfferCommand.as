package com.command.queue
{
	import com.command.Command;
	
	public class OfferCommand extends Command
	{
		/* Valor insertado */
		public var content:String;
		
		public static const OFFER_COMMAND:String = "offerCommand";
		 
		public function OfferCommand(remoteCommand:Object)
		{
			super(remoteCommand.id, OFFER_COMMAND);
			this.content = remoteCommand.content;
		}
		
		public function execute():String {
			return "Inserta el elemento " + content;
		}

	}
}