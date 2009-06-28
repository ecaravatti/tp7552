package com.events.collection
{
	import com.command.CommandFactory;
	import com.events.HeapAddEvent;
	
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	
	import mx.collections.ArrayCollection;
	import mx.rpc.events.FaultEvent;
	import mx.rpc.events.ResultEvent;
	import mx.rpc.remoting.RemoteObject;
	
	
	public class HeapEventDispatcher extends EventDispatcher
	{		
		public var heap:RemoteObject;

		public function HeapEventDispatcher(target:IEventDispatcher=null)
		{
			super(target);
			heap = new RemoteObject();
			heap.destination = "heap";
			heap.source = "collection.heap.Heap";
			
			// Event handlers para evento de push
			heap.add.addEventListener(ResultEvent.RESULT, addHandler);
			heap.add.addEventListener(FaultEvent.FAULT, genericFaultHandler);
			
			heap.pop.addEventListener(ResultEvent.RESULT, popHandler);
			heap.pop.addEventListener(FaultEvent.FAULT, genericFaultHandler);
			
			heap.getCommandQueue.addEventListener(ResultEvent.RESULT, getCommandQueueHandler);
		}
		
		private function getCommandQueueHandler(event:ResultEvent):void
		{
			var commandList:ArrayCollection = new ArrayCollection();
			
 			for (var i:Number = 0; i < event.result.length; i++){
				commandList.addItem(CommandFactory.getCommand(event.result[i]));
			} 
			
			var addEvent:HeapAddEvent = new HeapAddEvent(HeapAddEvent.HEAP_ADD);
			addEvent.commands = commandList;
			 
			this.dispatchEvent(addEvent);
		}
		
		private function addHandler(event:ResultEvent):void
		{	
			heap.getCommandQueue();
		}
		
		private function popHandler(event:ResultEvent):void
		{
			//TODO
		}
		
		private function genericFaultHandler(event:FaultEvent):void
		{
			//TODO Manejo de fallas
		}
	}		
}
