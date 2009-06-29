package com.draw
{
	import flash.display.BlendMode;
	
	import mx.controls.Image;
	import mx.controls.Label;
	import mx.core.UIComponent;
	
//--------------------------------------
//  Styles
//--------------------------------------

/**
 *  The skin for the node appearance
 *
 *  @default none
 */
[Style(name="nodeSkin", type="Class", inherit="no")]

	public class GenericNode extends UIComponent implements DrawableElement
	{
		/**
		 * This GenericNode's internal ID.
		 */
		protected var nodeID:int;
		
		/**
		 * Image placeholder for the node's icon. 
		 * Setted via CSS style "nodeSkin". 
		 */
		private var nodeSkin:Image;
		
		/**
		 * true if a CSS script specifies an
		 * image as a skin.
		 */
		private var nodeSkinChanged:Boolean;
		
		/**
		 * This GenericNode's parent container. The
		 * node will be drawn in it.
		 */
		private var _canvas:UIComponent;
		
		
		/**
		 * A text representing the value held by this node. 
		 */
		private var _nodeValue:String;
		
		public function GenericNode(canvas:UIComponent, id:int)
		{
			super();
			nodeID = id;
			this._canvas = canvas;
			nodeSkinChanged = true;
			nodeSkin = null;
			_nodeValue = null;
			
    		//this.width = 50;
    		//this.height = 50;
		}
		        
        override public function styleChanged(styleProp:String):void
    	{
    		super.styleChanged(styleProp);
    	
    		if (styleProp == "nodeSkin")
    		{    		
    			nodeSkinChanged = true;
    			invalidateProperties();
    		}
     	}
     
	    override protected function createChildren():void
	    {
	    	super.createChildren();
	    	
 		    if (_nodeValue)
		    {
		    	var nodeValueLabel:Label = new Label();
		    	nodeValueLabel.setActualSize(21,22);
		    	nodeValueLabel.setStyle("color", "white");
		    	nodeValueLabel.blendMode = BlendMode.NORMAL;
		    	nodeValueLabel.text = _nodeValue;
		    	this.addChild(nodeValueLabel);
		    } 
	    	
	    	if (!nodeSkin)
	    	{
		    	nodeSkin = new Image();
		    	nodeSkin.mouseEnabled = false;
		    	nodeSkin.setActualSize(21,22);
		    	nodeSkin.blendMode = BlendMode.DIFFERENCE;
		    	this.addChild(nodeSkin);
		    }
		    
		    invalidateProperties();
    	}
	        	
    	override protected function commitProperties():void
    	{
    		super.commitProperties();
    	
    		if (nodeSkinChanged)
    	    {
    	    	nodeSkin.source  = this.getStyle("nodeSkin");
    	        nodeSkinChanged  = false;
    	    }
    	    
    	    invalidateDisplayList();
    		invalidateSize();
    	}
    
    	public function draw(x:Number = 0, y:Number = 0, value:String = ""):void
    	{
    		this.posx = x;
    		this.posy = y;
    		
    		_nodeValue = value;
    		
    		canvas.addChild(this);
    	}
    	
    	public function erase():void
    	{
    		canvas.removeChild(this);
    	}
    	
    	public function swap(de:DrawableElement):void
    	{
    		if  (!de || de.canvas != this.canvas)
    		{
    			return;
    		}
    		
    		var currentX:Number = this.posx;
    		var currentY:Number = this.posy;
    		
    		this.posx = de.posx;
    		this.posy = de.posy;
    		
    		de.posx = currentX;
    		de.posy = currentY;    			    			
    	}
    	
    	public function highlight():void
 		{
 			//TODO
 		}
 		
 		public function link(de:DrawableElement):void
 		{
 			//TODO	
 		}
 		
 		public function unlink(de:DrawableElement):void
 		{
 			//TODO
 		}
    	
    	public function get canvas():UIComponent
    	{
			return _canvas;
		}
		
		public function get nodeValue():String
    	{
			return _nodeValue;
		}
		
		public function set nodeValue(value:String):void
    	{
			this._nodeValue = value;
		}
		
		public function get posx():Number
		{
			return this.x;
		}
		
		public function get posy():Number
		{
			return this.y;
		}
		
		public function set posx(x:Number):void
		{
			this.x = x;	
		}
		
		public function set posy(y:Number):void
		{
			this.y = y;
		}

	}
}