package com.events
{
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;

	public class HeapAddEvent extends Event
	{
		public static const HEAP_ADD:String = "heapAddEvent";
		private var _commands:ArrayCollection;
		
		public function HeapAddEvent(type:String, bubbles:Boolean=true, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
		
		public function get commands():ArrayCollection{
			return _commands;
		}
		
		public function set commands(array:ArrayCollection):void{
			_commands = array;
		}
	}
}