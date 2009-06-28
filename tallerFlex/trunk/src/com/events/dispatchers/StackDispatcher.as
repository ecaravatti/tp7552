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

	public class StackDispatcher extends EventDispatcher
	{
		public var stack:RemoteObject;
		private var commandList:ArrayCollection;
		private var resultType:String;

		public function StackEventDispatcher(target:IEventDispatcher=null)
		{
			super(target);
			stack = new RemoteObject();
			stack.destination = "stack";
			heap.source = "collection.stack.Stack";

			heap.add.addEventListener(ResultEvent.RESULT, addHandler);
			heap.add.addEventListener(FaultEvent.FAULT, genericFaultHandler);
			heap.pop.addEventListener(ResultEvent.RESULT, popHandler);
			heap.pop.addEventListener(FaultEvent.FAULT, genericFaultHandler);
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
			var stackEvent:StackEvent = new StackEvent(eventType);
			stackEvent.commands = commandList;			
			this.dispatchEvent(stackEvent);
		}
		
		private function addHandler(event:ResultEvent):void
		{
			//this.resultType = HeapEvent.HEAP_ADD;
			//heap.getCommandQueue();
		}
						
		private function popHandler(event:ResultEvent):void
		{	
			//this.resultType = HeapEvent.HEAP_POP;
			//heap.getCommandQueue();	
		}
		
		private function genericFaultHandler(event:FaultEvent):void
		{
			//Alert.show("Fail! " + event.type);
		}

		
	}
}