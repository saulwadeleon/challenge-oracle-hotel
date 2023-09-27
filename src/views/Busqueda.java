package views;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import jdbc.controller.HuespedesController;
import jdbc.controller.ReservasController;
import jdbc.controller.TipoHabitacionController;
import jdbc.controller.ListaPaisesController;
import jdbc.controller.FormaPagoController;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huespedes;
import jdbc.modelo.Reserva;

public class Busqueda extends JFrame {

	private JPanel contentPane;
	JTabbedPane panel;
	private JTextField txtBuscar;
	private JTable tbHuespedes;
	private JTable tbReservas;
	private DefaultTableModel modeloReservas;
	private DefaultTableModel modeloHuesped;
	private ReservasController reservaController;
	private HuespedesController huespedesController;
	private ListaPaisesController listaPaisesController;
	private TipoHabitacionController tipoHabitacionController;
	private FormaPagoController formaPagoController;
	private ConnectionFactory connectionFactory;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;
	String reserva;
	String huespedes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionFactory conexion = new ConnectionFactory();
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
		connectionFactory = conexion;

		// Inicializar los controladores
		reservaController = new ReservasController(connectionFactory);
		huespedesController = new HuespedesController(connectionFactory);
		listaPaisesController = new ListaPaisesController(connectionFactory);
		tipoHabitacionController = new TipoHabitacionController(connectionFactory);
		formaPagoController = new FormaPagoController(connectionFactory);

		// Configuración de la ventana y componentes
		configurarVentana();

		// Configuración de las tablas
		configurarTablaHuespedes();
		configurarTablaReservas();

		// Manejo de eventos
		configurarEventos();
	}

	private void configurarVentana() {
		// Configuración de la ventana principal
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

		panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBounds(20, 169, 865, 328);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		contentPane.add(panel);

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
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
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

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		JPanel btnexit = new JPanel();
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
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
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
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

		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));

		JPanel btnEditar = new JPanel();
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		btnEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				editarRegistro();
			}
		});

		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);

		JPanel btnEliminar = new JPanel();
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		btnEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				eliminarRegistro();
			}
		});

		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}

	private void configurarTablaHuespedes() {
		// Configuración de la tabla de Huéspedes
		tbHuespedes = new JTable();
		tbHuespedes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbHuespedes.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuesped = (DefaultTableModel) tbHuespedes.getModel();
		modeloHuesped.addColumn("ID del Huesped");
		modeloHuesped.addColumn("Nombre");
		modeloHuesped.addColumn("Apellido");
		modeloHuesped.addColumn("Fecha de Nac");
		modeloHuesped.addColumn("Nacionalidad");
		modeloHuesped.addColumn("eMail");
		modeloHuesped.addColumn("Telefono");
		JScrollPane scroll_Huespedes = new JScrollPane(tbHuespedes);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")),
				scroll_Huespedes, null);
		scroll_Huespedes.setVisible(true);
		LlenarTablaHuespedes();

		DefaultTableCellRenderer fechaNacHuespRenderer = getDateRenderer();
		TableColumn fechaNacimientoColumn = tbHuespedes.getColumnModel().getColumn(3);
		fechaNacimientoColumn.setCellRenderer(fechaNacHuespRenderer);

		// Configuración del renderizador para la columna de Nacionalidad
		DefaultTableCellRenderer nationalityRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (value instanceof Integer) {
					int idNacionalidad = (int) value;
					String nombreNacionalidad = listaPaisesController.obtenerNombrePais(idNacionalidad);
					value = nombreNacionalidad;
				}
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
		TableColumn nacionalidadColumn = tbHuespedes.getColumnModel().getColumn(4);
		nacionalidadColumn.setCellRenderer(nationalityRenderer);

		// Configuración para acciones doble clic en registro de la tabla
		tbHuespedes.setDefaultEditor(Object.class, null);
		tbHuespedes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // Doble clic
					int selectedRow = tbHuespedes.getSelectedRow();
					if (selectedRow != -1) {
						// Obtener el ID del huésped seleccionado y abrir la ventana de edición
						int selectedIdHuesped = (int) modeloHuesped.getValueAt(selectedRow, 0);
						RegistroHuesped edicionHuesped = new RegistroHuesped(
								huespedesController.obtenerHuespedPorId(selectedIdHuesped), connectionFactory);
						edicionHuesped.setVisible(true);
						// edicionHuesped.abrirVentanaEdicionHuesped(selectedIdHuesped);
						dispose();
					}
				}
			}
		});

	}

	private void configurarTablaReservas() {
		// Configuración de la tabla de Reservas
		tbReservas = new JTable();
		tbReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tbReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloReservas = (DefaultTableModel) tbReservas.getModel();
		modeloReservas.addColumn("Id Reserva");
		modeloReservas.addColumn("Nombre Huesped");
		modeloReservas.addColumn("Habitacion");
		modeloReservas.addColumn("Fecha Check In");
		modeloReservas.addColumn("Fecha Check Out");
		modeloReservas.addColumn("Valor");
		modeloReservas.addColumn("Forma de Pago");
		modeloReservas.addColumn("Reservacion No.");
		tbReservas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scroll_Reservas = new JScrollPane(tbReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_Reservas,
				null);
		scroll_Reservas.setVisible(true);
		LlenarTablaReservas();

		// Configuración del renderizadores para la columna de nombre
		DefaultTableCellRenderer nombreHuespRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (value instanceof Integer) {
					int idHuesped = (int) value;
					String nombreHuespedReserva = huespedesController
							.obtenerNombreHuesped(idHuesped);
					value = nombreHuespedReserva;
				}
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
		// Asignar el renderer a la columna de nombre
		TableColumn huespedColumn = tbReservas.getColumnModel().getColumn(1);
		huespedColumn.setCellRenderer(nombreHuespRenderer);

		// Configuración de los renderizadores para las columnas de fechas
		DefaultTableCellRenderer fechaERenderer = getDateRenderer();
		TableColumn fechaEColumn = tbReservas.getColumnModel().getColumn(3);
		fechaEColumn.setCellRenderer(fechaERenderer);

		DefaultTableCellRenderer fechaSRenderer = getDateRenderer();
		TableColumn fechaSColumn = tbReservas.getColumnModel().getColumn(4);
		fechaSColumn.setCellRenderer(fechaSRenderer);

		// Configuración del renderizador para la columna de Tipo de Habitacion
		DefaultTableCellRenderer tipoHabitacionRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (value instanceof Integer) {
					int idTipoHabitacion = (int) value;
					String nombreTipoHabitacion = tipoHabitacionController
							.obtenerNombreTipoHabitacion(idTipoHabitacion);
					value = nombreTipoHabitacion;
				}
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
		TableColumn tipoHabitacionColumn = tbReservas.getColumnModel().getColumn(2);
		tipoHabitacionColumn.setCellRenderer(tipoHabitacionRenderer);

		// Configuración del renderizador para la columna de Forma de Pago
		DefaultTableCellRenderer formaPagoRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (value instanceof Integer) {
					int idFormaPago = (int) value;
					String nombreFormaPago = formaPagoController
							.obtenerNombreFormaPago(idFormaPago);
					value = nombreFormaPago;
				}
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
		TableColumn formaPagoColumn = tbReservas.getColumnModel().getColumn(6);
		formaPagoColumn.setCellRenderer(formaPagoRenderer);

		// Configuración para acciones doble clic en registro de la tabla
		tbReservas.setDefaultEditor(Object.class, null);
		tbReservas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) { // Doble clic
					int selectedRow = tbReservas.getSelectedRow();
					if (selectedRow != -1) {
						// Obtener el ID del huésped seleccionado y abrir la ventana de edición
						int selectedIdHuesped = (int) modeloReservas.getValueAt(selectedRow, 0);
						ReservasView edicionReserva = new ReservasView(connectionFactory);
						edicionReserva.abrirVentanaEdicionReserva(selectedIdHuesped);
						dispose();
					}
				}
			}
		});

	}

	private void configurarEventos() {
		// Evento al hacer clic en el botón Buscar
		txtBuscar.addMouseListener(new MouseAdapter() {
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

	}

	private void editarRegistro() {
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

	private void eliminarRegistro() {
		int filaReservas = tbReservas.getSelectedRow();
		int filaHuespedes = tbHuespedes.getSelectedRow();

		if (filaReservas >= 0) {
			String reservaId = tbReservas.getValueAt(filaReservas, 0).toString();
			int confirmar = JOptionPane.showConfirmDialog(null,
					"¿Desea eliminar los datos de la reserva seleccionada?");

			if (confirmar == JOptionPane.YES_OPTION) {
				reservaController.Eliminar(Integer.valueOf(reservaId));
				JOptionPane.showMessageDialog(contentPane, "Registro de reserva eliminado");
				limpiarTabla();
				LlenarTablaReservas();
				LlenarTablaHuespedes();
			}
		} else if (filaHuespedes >= 0) {
			String huespedId = tbHuespedes.getValueAt(filaHuespedes, 0).toString();
			int confirmarh = JOptionPane.showConfirmDialog(null, "¿Desea eliminar los datos del huésped seleccionado?");

			if (confirmarh == JOptionPane.YES_OPTION) {
				huespedesController.Eliminar(Integer.valueOf(huespedId));
				JOptionPane.showMessageDialog(contentPane, "Registro de huésped eliminado");
				limpiarTabla();
				LlenarTablaHuespedes();
				LlenarTablaReservas();
			}
		} else {
			JOptionPane.showMessageDialog(null, "Error: No se ha seleccionado ninguna fila para eliminar.");
		}
	}

	private DefaultTableCellRenderer getDateRenderer() {
		// Formatear la columna de "Fecha de Nac"
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DefaultTableCellRenderer dateRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (value instanceof Date) {
					value = dateFormat.format(value);
				}
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
		return dateRenderer;
	}

	private List<Reserva> BuscarReservas() {
		return this.reservaController.buscar();
	}

	private List<Reserva> BuscarReservasPorNum() {
		return this.reservaController.buscarPorNumReserva(txtBuscar.getText());
	}

	private List<Huespedes> BuscarHuespedes() {
		return this.huespedesController.listarHuespedes();
	}

	// private List<Huespedes> BuscarHuespedesId() {
	// return this.huespedesController.listarHuespedesId(txtBuscar.getText());
	// }

	private List<Huespedes> BuscarHuespedesApellido() {
		return this.huespedesController.buscarHuespedApellido(txtBuscar.getText());
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
				modeloReservas
						.addRow(new Object[] { reservas.getId(), reservas.getHuesped_id(), reservas.getHabitacion_id(),
								reservas.getFechaE(), reservas.getFechaS(),
								reservas.getPrecioReserva(), reservas.getFormaPago(), reservas.getNumeroReserva() });
			}
		} catch (Exception e) {
			throw e;
		}
	}

	private void LlenarTablaReservasId() {
		// Llenar tabla
		List<Reserva> reserva = BuscarReservasPorNum();
		try {
			for (Reserva reservas : reserva) {
				modeloReservas
						.addRow(new Object[] { reservas.getId(), reservas.getHuesped_id(), reservas.getHabitacion_id(),
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
		List<Huespedes> huesped = BuscarHuespedesApellido();
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
		Optional.ofNullable(modeloReservas.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
				.ifPresentOrElse(fila -> {

					Integer huespedId = Integer
							.valueOf(modeloReservas.getValueAt(tbReservas.getSelectedRow(), 1).toString());
					Integer habitacionId = Integer
							.valueOf(modeloReservas.getValueAt(tbReservas.getSelectedRow(), 2).toString());
					Date fechaE = Date.valueOf(modeloReservas.getValueAt(tbReservas.getSelectedRow(), 3).toString());
					Date fechaS = Date.valueOf(modeloReservas.getValueAt(tbReservas.getSelectedRow(), 4).toString());
					String valor = (String) modeloReservas.getValueAt(tbReservas.getSelectedRow(), 5);
					Integer formaPago = Integer
							.valueOf(modeloReservas.getValueAt(tbReservas.getSelectedRow(), 6).toString());
					String numReservacion = (String) modeloReservas.getValueAt(tbReservas.getSelectedRow(), 7);
					Integer id = Integer.valueOf(modeloReservas.getValueAt(tbReservas.getSelectedRow(), 0).toString());
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
