package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import control.CreaSudoku;

/**
 * 
 * @author musef - fmsSoft musef2904ARROBAgmail.com
 * 
 * @version 1.1 2015-01-20
 *
 */

public class MainFrame implements ActionListener {

	// elementos del formulario
	private JFrame window;
	private JButton boton1;
	private JButton boton2;
	private JTextField[][] text;
	private JComboBox<String> level;
	private Font myFont=new Font("Arial",Font.BOLD,14);
	private int locX;				// ubicacion X de la pantalla
	private int locY;				// ubicacion Y de la pantalla
	
	// variables de funcionamiento
	private int[][]dataSolucion;	// matriz conteniendo la solucion del sudoku
	private int[][]dataProblema;	// matriz conteniendo el problema propuesto
	private int numberEmpty;		// casillas a rellenar en el sudoku problema
	private int numberErrors;		// pulsada solucion, el numero de errores y/o casillas vacias

	
	
	/**
	 * Este método constructor genera la parte visual (vista) de la aplicación.
	 * 
	 * @param dataProblema - array[][] con el problema sudoku planteado
	 * @param dataSolucion - array[][] con el sudoku construido (también llamado solución)
	 * @param locX - int, coordenada X de la ventana
	 * @param locY - int, coordenada Y de la ventana
	 * @param sudokuLevel - int, nivel del sudoku planteado (usado para fijar el JComboBox)
	 */
	public MainFrame(int[][]dataProblema, int[][]dataSolucion,int locX, int locY, int sudokuLevel) {
		// CONSTRUCTOR
		
		// inicializacion de variables
		this.locY=locY;
		this.locX=locX;
		numberEmpty=0;
		numberErrors=0;
		this.dataSolucion=dataSolucion;
		this.dataProblema=dataProblema;
		text=new JTextField[9][9];
		level=new JComboBox<String>();
		level.addItem("Nivel 1");
		level.addItem("Nivel 2");
		level.addItem("Nivel 3");
		level.addItem("Nivel 4");
		level.addItem("Nivel 5");
		level.setSelectedIndex(sudokuLevel-1);
		
		// instanciacion del sudoku
		window=ventana(this.dataProblema,1);
		
	}
	
	
	/**
	 * Este método fabrica una ventana con un jpanel conteniendo el problema sudoku
	 * o la solución sudoku, en función del parámetro mode.
	 * @param data - array[][] con los datos a mostrar del problema o de la solución.
	 * @param mode - parámetro que le indica cual JPanel constructor a usar (1=problema ; 2=solución)
	 * @return - un JFrame.
	 */
	private JFrame ventana(int[][]data,int mode) {
		
		JFrame window=new JFrame();
		window.setBounds(this.locX, this.locY, 450, 450);
		window.setAlwaysOnTop(true);
		window.setTitle("Sudoku fmsSoft v1.0");
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		window.add(panelMain(data,mode));			
		window.setVisible(true);
		
		return window;
		
	}
	
	
	/**
	 * Este método construye un JPanel conteniendo el problema o la solución, en
	 * función del parámetro mode (1=problema ; 2=solución).
	 * @param data - array[][] con el sudoku problema planteado.
	 * @param mode - parámetro que le indica cual JPanel constructor a usar (1=problema ; 2=solución)
	 * @return - JPanel
	 */
	private JPanel panelMain(int[][] data, int mode) {
		JPanel panel=new JPanel();
		panel.setLayout(new BorderLayout());
		
		// componente norte
		JPanel north=new JPanel();
		north.add(new JLabel("Seleccione nivel:  "));
		north.add(level);	
		panel.add(north,BorderLayout.NORTH);
		
		// componente center
		String textoInfo=null;
		if (mode==1) {
			// construimos un panel con el problema sudoku propuesto
			panel.add(panelData(data),BorderLayout.CENTER);
			textoInfo="Número de casillas a rellenar: "+numberEmpty;
		} else {
			// construimos un panel con la solucion al sudoku
			panel.add(panelSolucion(data),BorderLayout.CENTER);
			if (numberErrors>0) {
				textoInfo="Número de casillas falladas: "+numberErrors;
			} else {
				textoInfo="¡¡ EXITO TOTAL Y ABSOLUTO !!";
			}
			
		}
		
		// componente sur
		JPanel south=new JPanel();
		south.setLayout(new BoxLayout(south,BoxLayout.Y_AXIS));
		south.add(new JLabel(" "));
		south.add(panelButtons());
		south.add(new JLabel(" "));		
		JLabel info=new JLabel(textoInfo);
		info.setFont(myFont);
		info.setForeground(Color.RED);
		south.add(info);		
		panel.add(south,BorderLayout.SOUTH);
		
		panel.setVisible(true);
		return panel;
		
	}
	
	
	/**
	 * Este método construye un JPanel con el problema sudoku planteado, según
	 * el array recibido como parámetro.
	 * @param data - array[][] con el sudoku problema planteado.
	 * @return - JPanel
	 */
	private JPanel panelData(int[][]data) {
		
		numberEmpty=0;
		
		JPanel panel=new JPanel();
		GridLayout gd=new GridLayout(9,9);
		panel.setLayout(gd);
		for (int n=0;n<9;n++) {
			for (int j=0;j<9;j++) {
				String dato=String.valueOf(data[n][j]);
				if (dato.equals("0")) {				
					dato="";
					text[n][j]=new JTextField(dato);
					text[n][j].setEditable(true);
					numberEmpty++;
				} else {
					text[n][j]=new JTextField(dato);
					text[n][j].setEditable(false);
				}	
				text[n][j].setFont(myFont);
				text[n][j].setHorizontalAlignment(JLabel.CENTER);
				Border border=BorderFactory.createLineBorder(Color.BLACK, 1, true);
				text[n][j].setBorder(border);
				panel.add(text[n][j]);
			}
		}
		panel.setVisible(true);
		return panel;
	}
	
	
	/**
	 * Este método construye un JPanel con el problema sudoku solucionado, según
	 * el array recibido como parámetro. Además, realiza un recuento de errores y/o 
	 * casillas no rellenadas, que guardará en el atributo correspondiente.
	 * @param data - array[][] con la solución del sudoku.
	 * @return JPanel
	 */
	private JPanel panelSolucion(int[][]data) {
		
		numberErrors=0;
		JPanel panel=new JPanel();
		GridLayout gd=new GridLayout(9,9);
		panel.setLayout(gd);
		
		for (int n=0;n<9;n++) {
			for (int j=0;j<9;j++) {
				String dato=String.valueOf(data[n][j]);
				if (dato.equals(text[n][j].getText())) {
					JLabel lab=new JLabel(dato);
					lab.setFont(myFont);
					lab.setHorizontalAlignment(JLabel.CENTER);
					Border border=BorderFactory.createLineBorder(Color.BLACK, 1, true);
					lab.setBorder(border);
					panel.add(lab);
				} else {
					JLabel lab=new JLabel(dato);
					lab.setFont(myFont);
					lab.setForeground(Color.RED);
					lab.setHorizontalAlignment(JLabel.CENTER);
					Border border=BorderFactory.createLineBorder(Color.BLACK, 1, true);
					lab.setBorder(border);
					panel.add(lab);
					numberErrors++;
				}
			}
		}
		panel.setVisible(true);
		return panel;
	}
	
	
	/**
	 * Este método construye un JPanel de botones para el JFrame principal
	 * @return - JPanel
	 */
	private JPanel panelButtons() {
		JPanel panel=new JPanel();

		GridLayout gd=new GridLayout(1,5);
		panel.setLayout(gd);
		boton1=new JButton("Nuevo");
		boton1.setToolTipText("Pulse para crear un nuevo Sudoku");
		boton1.addActionListener(this);
		boton2=new JButton("Solución");
		boton2.setToolTipText("Pulse ver la solución del Sudoku");
		boton2.addActionListener(this);
		
		panel.add(new JLabel(""));
		panel.add(boton1);
		panel.add(new JLabel(""));
		panel.add(boton2);
		panel.add(new JLabel(""));
		panel.setVisible(true);
		return panel;
	}

	
	@Override
	public void actionPerformed(ActionEvent arg0) {

		String source=arg0.getActionCommand();
		
		if (source.equals("Solución")) {
			window.setVisible(false);
			window.removeAll();
			locX=window.getX();
			locY=window.getY();
			window=ventana(this.dataSolucion,2);
			window.setVisible(true);
		}
		
		if (source.equals("Nuevo")) {
			window.setVisible(false);
			window.removeAll();
			locX=window.getX();
			locY=window.getY();
			@SuppressWarnings("unused")
			CreaSudoku crea=new CreaSudoku(locX,locY,level.getSelectedIndex()+1);
		}
		
	}
	
}
