package com.events
{
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.managers.IBrowserManager;

	public class HeapEvent extends Event
	{
		public static const HEAP_ADD:String = "heapAddEvent";
		public static const HEAP_POP:String = "heapPopEvent";
		
		private var _commands:ArrayCollection;
		
		public function HeapEvent(type:String, bubbles:Boolean=true, cancelable:Boolean=false)
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