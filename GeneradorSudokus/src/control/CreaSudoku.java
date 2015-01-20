package control;

import vista.MainFrame;

public class CreaSudoku {

	
	private int[][] sudoku=new int[9][9];
	private int[][] sudokuProblema=new int[9][9];
	private int[][] prim1=new int[3][3];
	private int[][] prim2=new int[3][3];
	private int[][] prim3=new int[3][3];
	
	@SuppressWarnings("unused")
	private MainFrame mainFrame;

	
	public CreaSudoku() {
		
		makeSudoku(0,0,1);
		
	}  // end of main method
	
	public CreaSudoku(int x, int y) {
		
		makeSudoku(x,y,1);
		
	}  // end of main method

	/**
	 * Este método genera un sudoku de forma aleatoria.
	 * @param locX - parámetro X de posición de la ventana.
	 * @param locY - parámetro Y de posición de la ventana.
	 */
	protected void makeSudoku(int locX, int locY, int dificulty) {
		
		/*
		 * El sudoku se crea mediante la generación independiente de los 9 subcuadrados que
		 * componen un sudoku. Este proceso se realiza iterativamente hasta suministrar un
		 * sudoku correcto.
		 * 
		 * Los cuadrados independientes son 3, y recorren la diagona de izq-superior a dcha-inferior.
		 * Esos cuadrados se generan aleatorios.
		 * 
		 * El paso siguiente es crear las dos esquinas restantes. Esas esquinas se generan de forma
		 * aleatoria, pero se tienen que comprobar su corrección con las dos esquinas primarias creadas.
		 * En caso de no ser correctas (numeros duplicados) se deben generar nuevas hasta hallar una
		 * que sea adecuada.
		 * 
		 * Lo siguiente procesa los cuadrados verticales restantes. Para ello comprueba fila por fila
		 * si hay alguna combinación idónea, y las va probando recursivamente hasta cuadrar una 
		 * combinación adecuada.
		 * 
		 *  Finalmente, una vez rellenado completamente el sudoku, realiza una comprobación de
		 *  verificación mediante la suma individual de todas las filas y las columnas, lo que
		 *  garantiza la no repetición. 
		 */
		
		boolean OK=false;
		
		do {
			// initialize sudoku with zero
			for (int n=0;n<9;n++) {
				for (int j=0;j<9;j++) {
					sudoku[n][j]=0;
				}
			}

			// creates independents squares
			square1();
			square2();
			square3();
			
			// loop building sudoku
			boolean proc1;
			boolean proc2;
			int counter=0;
			// intenta generar el sudoku durante un maximo de 729 iteraciones
			// si no lo consigue, el programa finaliza
			do {	
				deleteEmptySquares();
				// last corner squares
				square4();
				square5();
				// processing vertical cross
				proc1=proceso6();
				// processing horizontal cross
				proc2=proceso7();
				counter++;		

			} while (!(proc1 && proc2) && counter<729);
			
			
			// messages
			if (proc1 && proc2) {
				if (checkSudoku(sudoku)) {
					sudokuProblema=generateSudoku(sudoku,dificulty);
					mainFrame=new MainFrame(sudokuProblema,sudoku,locX,locY);	
					OK=true;
				}
			}
			
		} while (!OK);	
	}
	
	
	/**
	 * Confecciona de modo aleatorio el primer cuadrado diagonal (esq. sup. izquierda)
	 */
	private void square1() {
		int x=0;
		int y=0;
		for (int n=1;n<10;n++) {
			do {
			x=(int) Math.round(Math.random()*2);
			y=(int) Math.round(Math.random()*2);
			} while (sudoku[x][y]!=0);
			prim1[x][y]=n;
			sudoku[x][y]=n;
		}
	}
	
	/**
	 * Confecciona de modo aleatorio el segundo cuadrado diagonal (centro)
	 */
	private void square2() {
		int x=0;
		int y=0;
		for (int n=1;n<10;n++) {
			do {
			x=(int) Math.round(Math.random()*2)+3;
			y=(int) Math.round(Math.random()*2)+3;
			} while (sudoku[x][y]!=0);
			prim2[x-3][y-3]=n;
			sudoku[x][y]=n;
		}
	}
	
	/**
	 * Confecciona de modo aleatorio el tercer cuadrado diagonal (esq. inf. derecha)
	 */
	private void square3() {
		int x=0;
		int y=0;
		for (int n=1;n<10;n++) {
			do {
			x=(int) Math.round(Math.random()*2)+6;
			y=(int) Math.round(Math.random()*2)+6;
			} while (sudoku[x][y]!=0);
			prim3[x-6][y-6]=n;
			sudoku[x][y]=n;
		}
	}
	
	/**
	 * Confecciona de modo aleatorio el cuarto cuadrado diagonal (esq. sup. derecha).
	 * No es independiente. Debe ser compatible con las otras dos esquinas 1 y 3.
	 */
	private void square4() {
		int x=0;
		int y=0;
		do {			
			for (int n=1;n<10;n++) {
				do {
				x=(int) Math.round(Math.random()*2)+6;
				y=(int) Math.round(Math.random()*2)+0;
				} while (sudoku[x][y]!=0);
				sudoku[x][y]=n;
			}
		} while(!verificaR4());	
	}
	
	/**
	 * Confecciona de modo aleatorio el cuarto cuadrado diagonal (esq. inf. izquierda).
	 * No es independiente. Debe ser compatible con las otras dos esquinas 1 y 3.
	 */
	private void square5() {
		int x=0;
		int y=0;
		do {
			for (int n=1;n<10;n++) {
				do {
				x=(int) Math.round(Math.random()*2)+0;
				y=(int) Math.round(Math.random()*2)+6;
				} while (sudoku[x][y]!=0);
				sudoku[x][y]=n;
			}		
		} while(!verificaR5());
	}
	
	/**
	 * Este método realiza una verificación de si el cuadrado
	 * superior derecho es correcto en relación a las dos esquinas primitivas (1-3)
	 * @return
	 */
	private boolean verificaR4() {
		
		for (int n=6;n<9;n++) {
			for (int j=0;j<3;j++) {
				int x=n;
				int y=j;
				int target=sudoku[n][j];
				// verifica primero con el square1
				for (int x1=0;x1<9;x1++) {
					if (target==sudoku[x1][y] && x1!=x) {
						for (int k=6;k<9;k++) {
							for (int l=0;l<3;l++) {
								sudoku[k][l]=0;
							}
						}
						return false;
					}
				}
				// verifica ahora con el square 3
				for (int y1=0;y1<9;y1++) {
					if (target==sudoku[x][y1] && y1!=y) {
						for (int k=6;k<9;k++) {
							for (int l=0;l<3;l++) {
								sudoku[k][l]=0;
							}
						}
						return false;
					}
				}				
			}
		}	
		return true;
	}
	
	/**
	 * Este método realiza una verificación de si el cuadrado
	 * inferior izquierdo es correcto en relación a las dos esquinas primitivas (1-3)
	 * @return
	 */
	private boolean verificaR5() {
		
		for (int n=0;n<3;n++) {
			for (int j=6;j<9;j++) {
				int x=n;
				int y=j;
				int target=sudoku[n][j];
				// verifica primero con el square1
				for (int x1=0;x1<9;x1++) {
					if (target==sudoku[x1][y] && x1!=x) {
						for (int k=0;k<3;k++) {
							for (int l=6;l<9;l++) {
								sudoku[k][l]=0;
							}
						}
						return false;
					}
				}
				// verifica primero con el square3
				for (int y1=0;y1<9;y1++) {
					if (target==sudoku[x][y1] && y1!=y) {
						for (int k=0;k<3;k++) {
							for (int l=6;l<9;l++) {
								sudoku[k][l]=0;
							}
						}
						return false;
					}
				}				
			}
		}
		
		return true;
	}
	
	
	/**
	 * Este método procesa fila a fila los cuadrados de las columnas centrales vacías, llamando 
	 * a métodos individuales que procesan cada fila de manera independiente. Si algún subproceso
	 * de fila devuelve un error, se aborta el proceso entero.
	 * @return
	 */
	private boolean proceso6() {
		if (!fila(0,3,9)) {
			return false;
		}
		if (!fila(1,3,9)) {
			return false;
		}
		if (!fila(2,3,9)) {
			return false;
		}
		if (!fila(6,0,5)) {
			return false;
		}
		if (!fila(7,0,5)) {
			return false;
		}
		if (!fila(8,0,5)) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * 
	 * @param row
	 * @param start
	 * @param finish
	 * @return
	 */
	private boolean fila(int row, int start, int finish) {
		// obtenemos los numeros que faltan en esta fila
		int[]faltan=new int[3];
		int idx=0;
		boolean noExist;
		for (int n=1;n<10;n++) {
			noExist=true;
			for (int j=0;j<9;j++) {
				if (sudoku[row][j]==n) {
					noExist=false;
					break;
				}
			}
			if (noExist) {
				faltan[idx]=n;
				idx++;
			}
		}
		int[][] posibles=new int[6][3];
		posibles[0][0]=faltan[0];
		posibles[1][0]=faltan[0];
		posibles[2][1]=faltan[0];
		posibles[3][2]=faltan[0];
		posibles[4][1]=faltan[0];
		posibles[5][2]=faltan[0];
		posibles[0][1]=faltan[1];
		posibles[1][2]=faltan[1];
		posibles[2][0]=faltan[1];
		posibles[3][0]=faltan[1];
		posibles[4][2]=faltan[1];
		posibles[5][1]=faltan[1];
		posibles[0][2]=faltan[2];
		posibles[1][1]=faltan[2];
		posibles[2][2]=faltan[2];
		posibles[3][1]=faltan[2];
		posibles[4][0]=faltan[2];
		posibles[5][0]=faltan[2];
		
		boolean posible = false;
		for (int n=0;n<6;n++) {
			posible = true;
			for (int j=start;j<finish;j++) {
				if (sudoku[j][3]==posibles[n][0]) {
					posible=false;
					break;
				}
				if (sudoku[j][4]==posibles[n][1]) {
					posible=false;
					break;
				}
				if (sudoku[j][5]==posibles[n][2]) {
					posible=false;
					break;
				}
			}
			if (posible) {
				sudoku[row][3]=posibles[n][0];
				sudoku[row][4]=posibles[n][1];
				sudoku[row][5]=posibles[n][2];
				break;
			}
		}	
		return posible;
	}
	

	/**
	 * Este método procesa fila a fila los cuadrados de las columnas centrales vacías, llamando 
	 * a métodos individuales que procesan cada fila de manera independiente. Si algún subproceso
	 * de fila devuelve un error, se aborta el proceso entero.
	 * @return
	 */
	private boolean proceso7() {
		if (!column(0,3,9)) {
			return false;
		}
		if (!column(1,3,9)) {
			return false;
		}
		if (!column(2,3,9)) {
			return false;
		}
		if (!column(6,0,5)) {
			return false;
		}
		if (!column(7,0,5)) {
			return false;
		}
		if (!column(8,0,5)) {
			return false;
		}
		return true;
	}
	
	private boolean column(int column, int start, int finish) {
		// obtenemos los numeros que faltan en esta fila
		int[]faltan=new int[3];
		int idx=0;
		boolean noExist;
		for (int n=1;n<10;n++) {
			noExist=true;
			for (int j=0;j<9;j++) {
				if (sudoku[j][column]==n) {
					noExist=false;
					break;
				}
			}
			if (noExist) {
				faltan[idx]=n;
				idx++;
			}
		}

		int[][] posibles=new int[6][3];
		posibles[0][0]=faltan[0];
		posibles[1][0]=faltan[0];
		posibles[2][1]=faltan[0];
		posibles[3][2]=faltan[0];
		posibles[4][1]=faltan[0];
		posibles[5][2]=faltan[0];
		posibles[0][1]=faltan[1];
		posibles[1][2]=faltan[1];
		posibles[2][0]=faltan[1];
		posibles[3][0]=faltan[1];
		posibles[4][2]=faltan[1];
		posibles[5][1]=faltan[1];
		posibles[0][2]=faltan[2];
		posibles[1][1]=faltan[2];
		posibles[2][2]=faltan[2];
		posibles[3][1]=faltan[2];
		posibles[4][0]=faltan[2];
		posibles[5][0]=faltan[2];
		
		boolean posible = false;
		for (int n=0;n<6;n++) {
			posible=true;
			for (int j=start;j<finish;j++) {
				if (sudoku[3][j]==posibles[n][0]) {
					posible=false;
					break;
				}
				if (sudoku[4][j]==posibles[n][1]) {
					posible=false;
					break;
				}
				if (sudoku[5][j]==posibles[n][2]) {
					posible=false;
					break;
				}
			}
			if (posible) {
				sudoku[3][column]=posibles[n][0];
				sudoku[4][column]=posibles[n][1];
				sudoku[5][column]=posibles[n][2];
				break;
			}
		}	
		return posible;
	}

	/**
	 * Este método realiza un borrado de todos los cuadrados
	 * excepto los primitivos
	 * 
	 */
	private void deleteEmptySquares() {
		// initialize sudoku with zero
		for (int n=0;n<9;n++) {
			for (int j=0;j<9;j++) {
				sudoku[n][j]=0;
			}
		}
		for (int n=0;n<3;n++) {
			for (int j=0;j<3;j++) {
				sudoku[n][j]=prim1[n][j];
				sudoku[n+3][j+3]=prim2[n][j];
				sudoku[n+6][j+6]=prim3[n][j];
			}
		}
	}
	
	/**
	 * Este método verifica que el sudoku es correcto,
	 * mediante la suma de sus valores.
	 * Suma verticalmente y horizontalmente,haciendo doble check.
	 * @param sudoku
	 * @return
	 */
	private boolean checkSudoku(int[][] sudoku ) {
		
		int acumulaH;
		int acumulaV;
		for (int n=0;n<9;n++) {
			acumulaH=0;
			acumulaV=0;
			for (int j=0;j<9;j++) {
				acumulaH+=sudoku[n][j];
				acumulaV+=sudoku[j][n];
			}
			if (acumulaH!=45 || acumulaV!=45) {
				return false;
			}
		}		
		return true;
		
	}
	

	/**
	 * Este método genera el problema del sudoku mediante la eliminación de un 
	 * número determinado de casillas en la vista del usuario.
	 * El mínimo de partida es 2x9 filas = 18 casillas. Cada nivel quitará 1
	 * casilla por nivel: nivel 1-->3 por fila; nivel 2-->4 por fila y así.
	 * 
	 * @param sudoku - el sudoku original (solucion).
	 * @param level - int, con el nivel de dificultad deseado.
	 * @return - array[][] sudoku con el problema
	 */
	private int[][] generateSudoku(int[][] sudoku, int level ) {
		
		int blanks=6+(level);
		int[][] sudokuProb=new int[9][9]; 
		
		for (int n=0;n<9;n++) {
			int contador=0;
			// repite la linea hasta que tenga los blancos correspondientes al nivel
			while (contador<blanks) {
				contador=0;
				for (int j=0;j<9;j++) {
					sudokuProb[n][j]=sudoku[n][j];
					if (contador<blanks && (int)Math.round(Math.random()*3)==2) {
						sudokuProb[n][j]=0;
						contador++;
					}
				}
			}
		}
		
		return sudokuProb;
	}
	
	
} // **************** END OF CLASS
