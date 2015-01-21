package control;

/**
 * 
 * @author musef - fmsSoft musef2904ARROBAgmail.com
 * 
 * @version 1.1 2015-01-20
 *
 */

public class GeneradorSudokus implements Runnable {

	private static Thread newThread;
	private static GeneradorSudokus sudoku;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		sudoku=new GeneradorSudokus();
		newThread=new Thread(sudoku);
		newThread.start();	
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		@SuppressWarnings("unused")
		CreaSudoku crea=new CreaSudoku();
	}

}
