package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import jdbc.controller.HuespedesController;
import jdbc.controller.ReservasController;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huespedes;
import jdbc.modelo.Reserva;

public class Busqueda extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private ReservasController reservaController;
	private HuespedesController huespedesController;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	String reserva;
	String huespedes;
	private ConnectionFactory connectionFactory;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionFactory conexion = new ConnectionFactory(); // Crea la instancia de ConnectionFactory
					Busqueda frame = new Busqueda(conexion);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 */
	public Busqueda(ConnectionFactory conexion) {
		this.connectionFactory = conexion;
		this.reservaController = new ReservasController(connectionFactory);
		this.huespedesController = new HuespedesController(connectionFactory);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setUndecorated(true);
		contentPane.setLayout(null);
		JScrollPane scrollPane = new JScrollPane(tbReservas);
		scrollPane.setViewportBorder(null);

		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);

		JLabel lblTitulo = new JLabel("SISTEMA DE BÚSQUEDA");
		lblTitulo.setBounds(331, 62, 280, 42);
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Dialog", Font.BOLD, 22));
		contentPane.add(lblTitulo);

		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBounds(20, 169, 865, 328);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		contentPane.add(panel);

		tbHuespedes = new JTable();
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), tbHuespedes,
				null);
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("Numero de Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nacimiento");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("eMail");
		modeloHuesped.addColumn("Telefono");
		LlenarTablaHuespedes();

		tbReservas = new JTable();
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), tbReservas,
				null);
		modelo = (DefaultTableModel) tbReservas.getModel();
		modelo.addColumn("Id de Reserva");
		modelo.addColumn("Id del Huesped");
		modelo.addColumn("Id del Tipo de Habitacion");
		modelo.addColumn("Fecha Check In");
		modelo.addColumn("Fecha Check Out");
		modelo.addColumn("Valor");
		modelo.addColumn("Forma de Pago");
		modelo.addColumn("Numero de Reservacion");
		tbReservas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		/*
		 * tbReservas = new JTable(); tbReservas.setFont(new Font("Roboto", Font.PLAIN,
		 * 16)); panel.addTab("Reservas", new
		 * ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), tbReservas,
		 * null);
		 * 
		 * modelo = (DefaultTableModel) tbReservas.getModel();
		 * modelo.addColumn("Id de Reserva"); modelo.addColumn("Id del Huesped");
		 * modelo.addColumn("Id del Tipo de Habitacion");
		 * modelo.addColumn("Fecha Check In"); modelo.addColumn("Fecha Check Out");
		 * modelo.addColumn("Valor"); modelo.addColumn("Forma de Pago");
		 * modelo.addColumn("Numero de Reservacion");
		 * 
		 * // Activa el autoresize de las columnas
		 * tbReservas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		 * 
		 * // Crea un ordenador de filas y columnas para permitir la ordenación
		 * TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelo);
		 * tbReservas.setRowSorter(sorter);
		 * 
		 * // Ajusta el ancho de las columnas automáticamente para que los nombres de
		 * las // columnas sean visibles for (int column = 0; column <
		 * tbReservas.getColumnCount(); column++) { TableColumn tableColumn =
		 * tbReservas.getColumnModel().getColumn(column); int preferredWidth =
		 * tableColumn.getMinWidth(); int maxWidth = tableColumn.getMaxWidth();
		 * 
		 * for (int row = 0; row < tbReservas.getRowCount(); row++) { TableCellRenderer
		 * cellRenderer = tbReservas.getCellRenderer(row, column); Component c =
		 * tbReservas.prepareRenderer(cellRenderer, row, column); int width =
		 * c.getPreferredSize().width + tbReservas.getIntercellSpacing().width;
		 * preferredWidth = Math.max(preferredWidth, width);
		 * 
		 * // No deja que el ancho de la columna exceda el valor máximo if
		 * (preferredWidth >= maxWidth) { preferredWidth = maxWidth; break; } }
		 * 
		 * tableColumn.setPreferredWidth(preferredWidth); }
		 */

		// Esto permite que los nombres de las columnas sean visibles cuando se desplaza
		// horizontalmente
		panel.add(scrollPane);

		LlenarTablaReservas();

		JLabel logo = new JLabel("");
		logo.setBounds(56, 51, 104, 107);
		logo.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		contentPane.add(logo);

		JPanel header = new JPanel();
		header.setBounds(0, 0, 910, 36);
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);

			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario(connectionFactory);
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario(connectionFactory);
				usuario.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnexit.setBackground(Color.white);
				labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setBounds(539, 159, 193, 2);
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2);

		JPanel btnbuscar = new JPanel();
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				limpiarTabla();
				if (txtBuscar.getText().equals("")) {
					LlenarTablaHuespedes();
					LlenarTablaReservas();
				} else {
					LlenarTablaReservasId();
					LlenarTablaHuespedesId();
				}
			}
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int filaReservas = tbReservas.getSelectedRow();
				int filaHuespedes = tbHuespedes.getSelectedRow();

				if (filaReservas >= 0) {
					ActualizarReservas();
					limpiarTabla();
					LlenarTablaReservas();
					LlenarTablaHuespedes();
				} else if (filaHuespedes >= 0) {
					ActualizarHuesped();
					limpiarTabla();
					LlenarTablaHuespedes();
					LlenarTablaReservas();
				}
			}
		});
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		JPanel btnEliminar = new JPanel();
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int filaReservas = tbReservas.getSelectedRow();
				int filaHuespedes = tbHuespedes.getSelectedRow();

				if (filaReservas >= 0) {

					reserva = tbReservas.getValueAt(filaReservas, 0).toString();
					int confirmar = JOptionPane.showConfirmDialog(null, "¿Desea Eliminar los datos?");

					if (confirmar == JOptionPane.YES_OPTION) {

						String valor = tbReservas.getValueAt(filaReservas, 0).toString();
						reservaController.Eliminar(Integer.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane, "Registro Eliminado");
						limpiarTabla();
						LlenarTablaReservas();
						LlenarTablaHuespedes();
					}
				}

				else if (filaHuespedes >= 0) {

					huespedes = tbHuespedes.getValueAt(filaHuespedes, 0).toString();
					int confirmarh = JOptionPane.showConfirmDialog(null, "¿Desea Eliminar los datos?");

					if (confirmarh == JOptionPane.YES_OPTION) {

						String valor = tbHuespedes.getValueAt(filaHuespedes, 0).toString();
						huespedesController.Eliminar(Integer.valueOf(valor));
						JOptionPane.showMessageDialog(contentPane, "Registro Eliminado");
						limpiarTabla();
						LlenarTablaHuespedes();
						LlenarTablaReservas();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Error fila no seleccionada, por favor realice una busqueda y seleccione una fila para eliminar");
				}
			}
		});
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}

	private List<Reserva> BuscarReservas() {
		return this.reservaController.buscar();
	}

	private List<Reserva> BuscarReservasId() {
		return this.reservaController.buscarId(txtBuscar.getText());
	}

	private List<Huespedes> BuscarHuespedes() {
		return this.huespedesController.listarHuespedes();
	}

	private List<Huespedes> BuscarHuespedesId() {
		return this.huespedesController.listarHuespedesId(txtBuscar.getText());
	}

	private void limpiarTabla() {
		((DefaultTableModel) tbHuespedes.getModel()).setRowCount(0);
		((DefaultTableModel) tbReservas.getModel()).setRowCount(0);
	}

	private void LlenarTablaReservas() {

		// Llenar tabla
		List<Reserva> reserva = BuscarReservas();
		try {
			for (Reserva reservas : reserva) {
				modelo.addRow(new Object[] { reservas.getId(), reservas.getHuesped_id(), reservas.getHabitacion_id(),
						reservas.getFechaE(), reservas.getFechaS(),
						reservas.getPrecioReserva(), reservas.getFormaPago(), reservas.getNumeroReserva() });
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void LlenarTablaReservasId() {

		// Llenar tabla
		List<Reserva> reserva = BuscarReservasId();
		try {
			for (Reserva reservas : reserva) {
				modelo.addRow(new Object[] { reservas.getId(), reservas.getHuesped_id(), reservas.getHabitacion_id(),
						reservas.getFechaE(), reservas.getFechaS(),
						reservas.getPrecioReserva(), reservas.getFormaPago(), reservas.getNumeroReserva() });
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void LlenarTablaHuespedes() {
		// Llenar Tabla
		List<Huespedes> huesped = BuscarHuespedes();
		try {
			for (Huespedes huespedes : huesped) {
				modeloHuesped.addRow(new Object[] { huespedes.getId(), huespedes.getNombre(), huespedes.getApellido(),
						huespedes.getFechaNacimiento(), huespedes.getNacionalidad(), huespedes.getEmailHuesped(),
						huespedes.getTelefono() });
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void LlenarTablaHuespedesId() {
		// Llenar Tabla
		List<Huespedes> huesped = BuscarHuespedesId();
		try {
			for (Huespedes huespedes : huesped) {
				modeloHuesped.addRow(new Object[] { huespedes.getId(), huespedes.getNombre(), huespedes.getApellido(),
						huespedes.getFechaNacimiento(), huespedes.getNacionalidad(), huespedes.getEmailHuesped(),
						huespedes.getTelefono() });
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void ActualizarReservas() {
		Optional.ofNullable(modelo.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
				.ifPresentOrElse(fila -> {

					Integer huespedId = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 1).toString());
					Integer habitacionId = Integer
							.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 2).toString());
					Date fechaE = Date.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 3).toString());
					Date fechaS = Date.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 4).toString());
					String valor = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 5);
					Integer formaPago = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 6).toString());
					String numReservacion = (String) modelo.getValueAt(tbReservas.getSelectedRow(), 7);
					Integer id = Integer.valueOf(modelo.getValueAt(tbReservas.getSelectedRow(), 0).toString());
					this.reservaController.actualizar(huespedId, habitacionId, fechaE, fechaS, valor, formaPago,
							numReservacion, id);
					JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));
				}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un registro"));

	}

	private void ActualizarHuesped() {
		Optional.ofNullable(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
				.ifPresentOrElse(filaHuesped -> {

					String nombre = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 1);
					String apellido = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 2);
					Date fechaN = Date.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 3).toString());
					Integer nacionalidad = Integer
							.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 4).toString());
					String email = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 5);
					String telefono = (String) modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 6);
					Integer id = Integer.valueOf(modeloHuesped.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());
					this.huespedesController.actualizar(nombre, apellido, fechaN, nacionalidad, email, telefono,
							id);
					JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));
				}, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un registro"));

	}

	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}
}