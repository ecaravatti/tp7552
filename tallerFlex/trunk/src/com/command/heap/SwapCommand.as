package com.command.heap
{
	import com.command.Command;
	
	public class SwapCommand extends Command
	{
		public static const SWAP_COMMAND:String = "swapCommand";
		
		/**
		 * Id interno del elemento primario a ser intercambiado.
		 */
		private var primaryId: Number;

		/**
		 * Id interno del elemento secundario a ser intercambiado.
		 */
		private var secondaryId:Number;

		/**
		 * Si true idPrimary es el id del elemento que desciende. Si false,
		 * idPrimary es el id del elemento que es promovido.
		 */
		private var isSwapDown:Boolean;

		/**
		 * Valor del elemento primario a ser intercambiado.
		 */
		private var primaryData:Number;
	
		/**
		 * Valor del elemento secundario a ser intercambiado.
		 */
		private var secondaryData:Number;
	
		
		public function SwapCommand(primaryId:Number, primaryData:Number,
			secondaryData:Number, isSwapDown:Boolean)
		{
			super(primaryId, SWAP_COMMAND);
	
			this.primaryId = primaryId;
			this.secondaryId = secondaryId;
			this.primaryData = primaryData;
			this.secondaryData = secondaryData;
			this.isSwapDown = isSwapDown;
		}
		
		public function execute():String {
		return "Promueve el elemento con clave " + (isSwapDown ? secondaryData : primaryData)
				+ " (id=" + (isSwapDown ? secondaryId : primaryId) + ")"
				+ " y desciende el elemento con clave " + (isSwapDown ? primaryData : secondaryData)
				+ " (id=" + (isSwapDown ? primaryId : secondaryId) + ")";
	}


	}
}