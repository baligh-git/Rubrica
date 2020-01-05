package it.corso.java.rubrica.view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

import it.corso.java.rubrica.business.RubricaBusiness;
import it.corso.java.rubrica.model.Contatto;

public class Rubrica {

	private JFrame frame;
	private JTextField txtNome;
	private JTextField txtCognome;
	private JTextField txtTelefono;
	private JTable listaContatti;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Rubrica window = new Rubrica();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Rubrica() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1101, 772);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 23, 1059, 672);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Inserisci contatto", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNome = new JLabel("Nome");
		lblNome.setBounds(31, 24, 56, 16);
		panel.add(lblNome);
		
		txtNome = new JTextField();
		txtNome.setBounds(133, 21, 116, 22);
		panel.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblCognome = new JLabel("Cognome");
		lblCognome.setBounds(31, 84, 56, 16);
		panel.add(lblCognome);
		
		txtCognome = new JTextField();
		txtCognome.setBounds(133, 81, 116, 22);
		panel.add(txtCognome);
		txtCognome.setColumns(10);
		
		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setBounds(31, 150, 56, 16);
		panel.add(lblTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(133, 147, 116, 22);
		panel.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Contatto nuovoContatto = new Contatto();
				nuovoContatto.setCognome(txtCognome.getText());
				nuovoContatto.setNome(txtNome.getText());
				nuovoContatto.setTelefono(txtTelefono.getText());
				
				try {
					int id = RubricaBusiness.getInstance().aggiungiContatto(nuovoContatto);
					
					if(id > 0) {
						JOptionPane.showMessageDialog(null, "Contatto inserito con successo!");
						txtCognome.setText("");
						txtNome.setText("");
						txtTelefono.setText("");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnAggiungi.setBounds(170, 217, 97, 25);
		panel.add(btnAggiungi);
		
		JButton btnAnnulla = new JButton("Annulla");
		btnAnnulla.setBounds(292, 217, 97, 25);
		panel.add(btnAnnulla);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Ricerca contatti", null, panel_1, null);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(12, 13, 1030, 616);
		panel_1.add(scrollPane);
		
		listaContatti = new JTable();
		listaContatti.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nome", "Cognome", "Telefono"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, true, true, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		listaContatti.getColumnModel().getColumn(0).setPreferredWidth(103);
		listaContatti.getColumnModel().getColumn(1).setPreferredWidth(215);
		listaContatti.getColumnModel().getColumn(2).setPreferredWidth(207);
		listaContatti.getColumnModel().getColumn(3).setPreferredWidth(244);
		
		DefaultTableModel dtm = (DefaultTableModel) listaContatti.getModel();
		
		List<Contatto> contatti;
		try {
			contatti = RubricaBusiness.getInstance().ricercaContatti();
			
			for (Contatto c : contatti) {
				Vector rowData = new Vector();
				rowData.add(c.getId());
				rowData.add(c.getNome());
				rowData.add(c.getCognome());
				rowData.add(c.getTelefono());
				
				dtm.addRow(rowData);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		scrollPane.setViewportView(listaContatti);
	}

}
