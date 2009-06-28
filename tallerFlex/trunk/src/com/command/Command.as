package com.command
{
	public class Command
	{
		public var id:int;
		public var type:String;
		
		public function Command(id:Number, type:String)
		{
			this.id = id;
			this.type = type;
		}
		
		public function getId():int 
		{
			return this.id;
		}
		
		public function getType():String
		{
			return this.type;
		}
	}
}