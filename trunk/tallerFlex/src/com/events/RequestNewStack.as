package com.events
{
	import flash.events.Event;

	public class RequestNewStack extends Event
	{
		// Constante indicadora del tipo de Evento.
		public static const NEW_STACK:String = "newStackPanel";

		public function RequestNewStack(type:String, bubbles:Boolean=true, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
	}
}