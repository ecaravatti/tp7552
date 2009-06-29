package com.draw
{
	import mx.core.UIComponent;
	
	public interface DrawableElement
	{
		
		/**
		 * Draws an element, placed in the given x,y coordinates. Displays
		 * the given value.
		 * 
		 * @param x a Number representing an x coordinate.
		 * @param y a Number representing a y coordinate. 
		 * @param value the alphanumeric value to be displayed by the Element.
		 */
		function draw(x:Number = 0, y:Number = 0, value:String = ""):void;
		
		/**
		 * Removes the DrawableElement from its parent. 
		 */
		function erase():void;
		
		/**
		 *  Swaps the positions of this DrawableElement and the
		 *  specified by the parameter. This funcion should check 
		 *  that this and the given DrawableElement
		 * 	both be children of the same parent DisplayObject.
		 *  
		 * @param de a DrawableElement to be swapped with this.
		 */
		function swap(de:DrawableElement):void;
		
		/**
		 *	Highlights this GenericNode so the user can tell which
		 *  is the current node being processed. 
		 */
		function highlight():void;
		
		/**
		 * Draws a line connecting the center of two GenericNodes.
		 */ 
		function link(de:DrawableElement):void;
		
		/**
		 * Erases a line connecting two GenericNodes. If the nodes
		 * weren't previously linked, this does nothing. 
		 */
		function unlink(de:DrawableElement):void;
		
		/**
		 * Return the parent container of this DrawableElement. 
		 */
		function get canvas():UIComponent;
		
		/**
		 * Return x position of this DrawableElement in its
		 * DisplayObject parent. 
		 */
		function get posx():Number;
		
		/**
		 * Return y position of this DrawableElement in its
		 * DisplayObject parent. 
		 */
		function get posy():Number;
		
				/**
		 * Sets x position of this DrawableElement in its
		 * DisplayObject parent. 
		 */
		function set posx(x:Number):void;
		
		/**
		 * Sets y position of this DrawableElement in its
		 * DisplayObject parent. 
		 */
		function set posy(y:Number):void;
		
	}
}