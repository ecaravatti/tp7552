package com.events.dispatchers
{
	import com.command.CommandFactory;
	import com.events.HeapEvent;
	
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;

//--------------------------------------
//  Events
//--------------------------------------
	
	/**
	 *  Dispatched an item is added to the heap.
	 *
	 *  @eventType HeapEvent.HEAP_ADD
	 */
	[Event(name="heapAddEvent", type="com.events.HeapEvent")]
	
	/**
	 *  Dispatched an item is popped from the heap.
	 *
	 *  @eventType HeapEvent.HEAP_POP
	 */
	[Event(name="heapPopEvent", type="com.events.HeapEvent")]

	public class HeapEventDispatcher extends EventDispatcher
	{		
		public var heap:RemoteObject;
		private var commandList:ArrayCollection;
		private var resultType:String;

		public function HeapEventDispatcher(target:IEventDispatcher=null)
		{
			super(target);
			heap = new RemoteObject();
			heap.destination = "heap";
			heap.source = "collection.heap.Heap";
			
			// Event handlers para evento de add
			heap.add.addEventListener(ResultEvent.RESULT, addHandler);
			heap.add.addEventListener(FaultEvent.FAULT, genericFaultHandler);
			
			// Event handlers para evento de pop
			heap.pop.addEventListener(ResultEvent.RESULT, popHandler);
			heap.pop.addEventListener(FaultEvent.FAULT, genericFaultHandler);
			
			// Event handlers para capturar lista de comandos
			heap.getCommandQueue.addEventListener(ResultEvent.RESULT, getCommandQueueHandler);
		}
		
		private function getCommandQueueHandler(event:ResultEvent):void
		{
			commandList = new ArrayCollection();
			
 			for (var i:Number = 0; i < event.result.length; i++){
				commandList.addItem(CommandFactory.getCommand(event.result[i]));
			}

			createAndDispatchEvent(resultType);
		}
		
		private function createAndDispatchEvent(eventType:String):void {
			var heapEvent:HeapEvent = new HeapEvent(eventType);
			heapEvent.commands = commandList;			
			this.dispatchEvent(heapEvent);
		}
		
		private function addHandler(event:ResultEvent):void
		{
			this.resultType = HeapEvent.HEAP_ADD;
			heap.getCommandQueue();
		}
						
		private function popHandler(event:ResultEvent):void
		{	
			this.resultType = HeapEvent.HEAP_POP;
			heap.getCommandQueue();	
		}
		
		private function genericFaultHandler(event:FaultEvent):void
		{
			//Alert.show("Fail! " + event.type);
		}
	}		
}
