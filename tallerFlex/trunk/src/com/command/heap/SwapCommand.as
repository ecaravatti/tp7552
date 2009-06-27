package com.command.heap
{
	import com.command.Command;
	
	public class SwapCommand extends Command
	{
		public static const SWAP_COMMAND:String = "swapCommand";
		
		/**
		 * Id interno del elemento primario a ser intercambiado.
		 */
		private var primaryId: int;

		/**
		 * Id interno del elemento secundario a ser intercambiado.
		 */
		private var secondaryId:int;

		/**
		 * Si true idPrimary es el id del elemento que desciende. Si false,
		 * idPrimary es el id del elemento que es promovido.
		 */
		private var isSwapDown:Boolean;

		/**
		 * Valor del elemento primario a ser intercambiado.
		 */
		private var primaryData:int;
	
		/**
		 * Valor del elemento secundario a ser intercambiado.
		 */
		private var secondaryData:int;
	
		
		public function SwapCommand(remoteCommand:Object)
		{
			super(primaryId, SWAP_COMMAND);
	
			this.primaryId = remoteCommand.primaryId;
			this.secondaryId = remoteCommand.secondaryId;
			this.primaryData = remoteCommand.primaryData;
			this.secondaryData = remoteCommand.secondaryData;
			this.isSwapDown = remoteCommand.isSwapDown;
		}
		
		public function execute():String {
			return "Promueve el elemento con clave " + (isSwapDown ? secondaryData : primaryData)
					+ " (id=" + (isSwapDown ? secondaryId : primaryId) + ")"
					+ " y desciende el elemento con clave " + (isSwapDown ? primaryData : secondaryData)
					+ " (id=" + (isSwapDown ? primaryId : secondaryId) + ")";
	}


	}
}