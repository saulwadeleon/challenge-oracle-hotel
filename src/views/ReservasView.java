package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.toedter.calendar.JDateChooser;

import jdbc.controller.FormaPagoController;
import jdbc.controller.HuespedesController;
import jdbc.controller.ReservasController;
import jdbc.controller.TipoHabitacionController;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huespedes;
import jdbc.modelo.Reserva;

/**
 * @wbp.parser.constructor
 */
public class ReservasView extends JFrame {

	private JPanel contentPane;
	public static JComboBox<String> txtTipoHabitacion;
	public static JTextField txtValor;
	public static JDateChooser txtFechaE;
	public static JDateChooser txtFechaS;
	public static JComboBox<String> txtFormaPago;
	int xMouse, yMouse;
	private JLabel labelExit;
	private JLabel labelAtras;

	private Map<String, Integer> tiposHabitacionMap;
	private Map<String, Integer> formasPagoMap;
	private Map<String, Integer> huespedMap;
	private ConnectionFactory connectionFactory;
	private ReservasController reservasController;
	private TipoHabitacionController tipoHabitacionController;
	private HuespedesController huespedesController;
	private FormaPagoController formaPagoController;

	private JComboBox<String> txtHuesped;
	private JTextField txtNumeroReserva;
	private Integer nvoReservacioId;

	JPanel btnGuardar;
	JPanel btnActualizar;
	JPanel btnAgregarHuesped;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionFactory conexion = new ConnectionFactory(); // Crea la instancia de ConnectionFactory
					ReservasView frame = new ReservasView(conexion);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * @wbp.parser.constructor
	 */
	public ReservasView(ConnectionFactory conexion) {
		connectionFactory = conexion;
		inicializarVariables(connectionFactory);
		initComponents(false);
	}

	public ReservasView(Huespedes huespedes, ConnectionFactory conexion) {
		connectionFactory = conexion;
		inicializarVariables(connectionFactory);
		initComponents(true);
		cargarHuesped(huespedes);
	}

	private void inicializarVariables(ConnectionFactory conexion) {
		// Inicializar los controladores
		reservasController = new ReservasController(connectionFactory);
		tipoHabitacionController = new TipoHabitacionController(connectionFactory);
		huespedesController = new HuespedesController(connectionFactory);
		formaPagoController = new FormaPagoController(connectionFactory);
	}

	/**
	 * Create the frame.
	 */
	private void initComponents(Boolean esEdicion) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ReservasView.class.getResource("/imagenes/aH-40px.png")));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 560);
		setResizable(false); // Evita que la ventana sea redimensionada
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setLocationRelativeTo(null);// Hace que las ventanas aparezcan siempre en medio de la pantalla
		setUndecorated(true);// retira la barra superior de la ventana

		// Código que crea los elementos de la interfáz gráfica
		// Creación del panel principal
		JPanel panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 910, 560);
		panel.setLayout(null); // Asegura que los componentes se coloquen correctamente
		contentPane.add(panel); // Agrega el panel al contenido principal

		// Etiqueta de título
		JLabel lblTitulo = new JLabel("SISTEMA DE RESERVAS");
		lblTitulo.setBounds(109, 40, 219, 24);
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Dialog", Font.BOLD, 16));
		panel.add(lblTitulo);

		// Etiqueta para el campo de búsqueda y selección de huéspedes
		JLabel lblCliente = new JLabel("Búsqueda de Huéspedes");
		lblCliente.setBounds(68, 80, 200, 14);
		lblCliente.setForeground(SystemColor.textInactiveText);
		lblCliente.setFont(new Font("Roboto", Font.PLAIN, 14));
		panel.add(lblCliente);

		// Campo para buscar y seleccionar huéspedes
		txtHuesped = new JComboBox<>();
		txtHuesped.setBounds(68, 100, 250, 35);
		txtHuesped.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.add(txtHuesped);

		// Crear un modelo para el JComboBox
		DefaultComboBoxModel<String> modeloComboBoxHuesped = new DefaultComboBoxModel<>();

		// Obtener la lista de nombres de huéspedes desde el controlador
		huespedMap = huespedesController.obtenerHuespedes();

		// Agregar los nombres de huéspedes al modelo del JComboBox
		for (Map.Entry<String, Integer> entry : huespedMap.entrySet()) {
			modeloComboBoxHuesped.addElement(entry.getKey());
		}

		// Asignar el modelo al JComboBox
		txtHuesped.setModel(modeloComboBoxHuesped);

		// Establecer el JComboBox como editable
		txtHuesped.setSelectedItem(null);
		txtHuesped.setEditable(true);

		// Decorar el JComboBox con AutoCompleteDecorator
		AutoCompleteDecorator.decorate(txtHuesped);

		// Botón para agregar nuevo huésped junto al campo de búsqueda
		btnAgregarHuesped = new JPanel();
		btnAgregarHuesped.setBounds(322, 100, 35, 35);
		panel.add(btnAgregarHuesped);
		btnAgregarHuesped.setBackground(new Color(12, 138, 199));
		btnAgregarHuesped.setLayout(null);
		btnAgregarHuesped.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		JLabel lblGuardar_1 = new JLabel("...");
		lblGuardar_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				agregarNvoHuesped();
			}
		});
		lblGuardar_1.setToolTipText("Registrar Huesped");
		lblGuardar_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblGuardar_1.setForeground(Color.WHITE);
		lblGuardar_1.setFont(new Font("Dialog", Font.PLAIN, 18));
		lblGuardar_1.setBounds(0, 0, 35, 35);
		btnAgregarHuesped.add(lblGuardar_1);

		btnAgregarHuesped.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				agregarNvoHuesped();
			}
		});

		// Separador después del campo de cliente
		JSeparator separatorHuesped = new JSeparator();
		separatorHuesped.setForeground(SystemColor.textHighlight);
		separatorHuesped.setBounds(68, 135, 289, 2);
		separatorHuesped.setBackground(SystemColor.textHighlight);
		panel.add(separatorHuesped);

		// ComboBox para seleccionar el tipo de habitación
		txtTipoHabitacion = new JComboBox<>();
		txtTipoHabitacion.setBounds(68, 170, 289, 35);
		txtTipoHabitacion.setFont(new Font("Roboto", Font.PLAIN, 16));

		// Crear un modelo para el JComboBox
		DefaultComboBoxModel<String> modeloComboBoxTH = new DefaultComboBoxModel<>();

		// Obtener la lista de nombres de países desde el controlador
		tiposHabitacionMap = tipoHabitacionController.listarTipoHabitacion();

		// Agregar los nombres de países al modelo del JComboBox
		for (String tipoHabitacion : tiposHabitacionMap.keySet()) {
			modeloComboBoxTH.addElement(tipoHabitacion);
		}

		// Asignar el modelo al JComboBox
		txtTipoHabitacion.setModel(modeloComboBoxTH);

		// Establecer Habitación Doble como el valor predeterminado seleccionado
		txtTipoHabitacion.setSelectedItem("Habitación Doble");
		panel.add(txtTipoHabitacion);

		txtTipoHabitacion.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				calcularValor(txtFechaE, txtFechaS);
			}
		});

		// Separador después del ComboBox de tipo de habitación
		JSeparator separatorTipoHabitacion = new JSeparator();
		separatorTipoHabitacion.setForeground(SystemColor.textHighlight);
		separatorTipoHabitacion.setBounds(68, 206, 289, 2);
		separatorTipoHabitacion.setBackground(SystemColor.textHighlight);
		panel.add(separatorTipoHabitacion);

		// Separadores originales (mantenidos)
		JSeparator separator_fechaE = new JSeparator();
		separator_fechaE.setForeground(SystemColor.textHighlight);
		separator_fechaE.setBounds(68, 276, 289, 2);
		separator_fechaE.setBackground(SystemColor.textHighlight);
		panel.add(separator_fechaE);

		JSeparator separator_formaPago = new JSeparator();
		separator_formaPago.setForeground(SystemColor.textHighlight);
		separator_formaPago.setBackground(SystemColor.textHighlight);
		separator_formaPago.setBounds(68, 486, 289, 2);
		panel.add(separator_formaPago);

		JSeparator separator_fechaS = new JSeparator();
		separator_fechaS.setForeground(SystemColor.textHighlight);
		separator_fechaS.setBounds(68, 346, 289, 2);
		separator_fechaS.setBackground(SystemColor.textHighlight);
		panel.add(separator_fechaS);

		// Etiquetas (mantenidas)
		JLabel lblValor = new JLabel("Valor de la Reserva");
		lblValor.setForeground(SystemColor.textInactiveText);
		lblValor.setBounds(68, 360, 196, 14);
		lblValor.setFont(new Font("Roboto", Font.PLAIN, 14));
		panel.add(lblValor);

		JLabel lblFormaPago = new JLabel("Forma de Pago");
		lblFormaPago.setForeground(SystemColor.textInactiveText);
		lblFormaPago.setBounds(68, 430, 187, 14);
		lblFormaPago.setFont(new Font("Roboto", Font.PLAIN, 14));
		panel.add(lblFormaPago);

		// Panel para la imagen de fondo
		JPanel fondoImagen = new JPanel();
		fondoImagen.setBounds(428, 0, 482, 560);
		fondoImagen.setBackground(new Color(12, 138, 199));
		panel.add(fondoImagen);
		fondoImagen.setLayout(null);

		// Campo de texto para mostrar el número de reserva generado automáticamente
		txtNumeroReserva = new JTextField();
		txtNumeroReserva.setBounds(10, 514, 225, 35);
		txtNumeroReserva.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtNumeroReserva.setEditable(false); // Hace que el campo no sea editable
		panel.add(txtNumeroReserva);

		// Campos que guardaremos en la base de datos
		txtFechaE = new JDateChooser();
		txtFechaE.getCalendarButton().setBackground(SystemColor.textHighlight);
		txtFechaE.getCalendarButton()
				.setIcon(new ImageIcon(ReservasView.class.getResource("/imagenes/icon-reservas.png")));
		txtFechaE.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 12));
		txtFechaE.setBounds(68, 240, 289, 35);
		txtFechaE.getCalendarButton().setBounds(268, 0, 21, 33);
		txtFechaE.setBackground(Color.WHITE);
		txtFechaE.setBorder(new LineBorder(SystemColor.window));
		txtFechaE.setDateFormatString("yyyy-MM-dd");
		txtFechaE.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.add(txtFechaE);
		txtFechaE.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				calcularValor(txtFechaE, txtFechaS);
			}
		});

		txtFechaS = new JDateChooser();
		txtFechaS.getCalendarButton()
				.setIcon(new ImageIcon(ReservasView.class.getResource("/imagenes/icon-reservas.png")));
		txtFechaS.getCalendarButton().setFont(new Font("Roboto", Font.PLAIN, 11));
		txtFechaS.setBounds(68, 310, 289, 35);
		txtFechaS.getCalendarButton().setBounds(267, 1, 21, 31);
		txtFechaS.setBackground(Color.WHITE);
		txtFechaS.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtFechaS.setDateFormatString("yyyy-MM-dd");
		txtFechaS.getCalendarButton().setBackground(SystemColor.textHighlight);
		txtFechaS.setBorder(new LineBorder(new Color(255, 255, 255), 0));
		panel.add(txtFechaS);

		txtFechaS.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				calcularValor(txtFechaE, txtFechaS);
			}
		});

		txtValor = new JTextField();
		txtValor.setBackground(SystemColor.text);
		txtValor.setHorizontalAlignment(SwingConstants.CENTER);
		txtValor.setForeground(Color.BLACK);
		txtValor.setBounds(68, 380, 289, 35);
		txtValor.setEditable(false);
		txtValor.setFont(new Font("Roboto", Font.BOLD, 18));
		txtValor.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		panel.add(txtValor);
		txtValor.setColumns(10);

		txtFormaPago = new JComboBox<String>();
		txtFormaPago.setBounds(68, 450, 289, 35);
		txtFormaPago.setBackground(SystemColor.text);
		txtFormaPago.setBorder(new LineBorder(new Color(255, 255, 255), 1, true));
		txtFormaPago.setFont(new Font("Roboto", Font.PLAIN, 16));

		// Crear un modelo para el JComboBox
		DefaultComboBoxModel<String> modeloComboBox = new DefaultComboBoxModel<>();

		// Obtener la lista de nombres de países desde el controlador
		formasPagoMap = formaPagoController.metodosDePago();

		// Agregar los nombres de países al modelo del JComboBox
		for (String formaPago : formasPagoMap.keySet()) {
			modeloComboBox.addElement(formaPago);
		}

		// Asignar el modelo al JComboBox
		txtFormaPago.setModel(modeloComboBox);

		// Establecer Tarjeta de Crédito como el valor predeterminado seleccionado
		txtFormaPago.setSelectedItem("Tarjeta de crédito");
		panel.add(txtFormaPago);

		JSeparator separator_valorReserva = new JSeparator();
		separator_valorReserva.setForeground(SystemColor.textHighlight);
		separator_valorReserva.setBounds(68, 416, 289, 2);
		separator_valorReserva.setBackground(SystemColor.textHighlight);
		panel.add(separator_valorReserva);

		// Botón Siguiente
		btnGuardar = new JPanel();
		btnGuardar.setLayout(null);
		btnGuardar.setBackground(SystemColor.textHighlight);
		btnGuardar.setBounds(296, 514, 122, 35);
		btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		panel.add(btnGuardar);

		JLabel lblGuardar = new JLabel("Guardar");
		lblGuardar.setHorizontalAlignment(SwingConstants.CENTER);
		lblGuardar.setForeground(Color.WHITE);
		lblGuardar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblGuardar.setBounds(0, 0, 112, 35);
		btnGuardar.add(lblGuardar);

		btnGuardar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ((txtFechaE.getDate() != null && txtFechaS.getDate() != null)) {
					guardarReserva();
				} else {
					JOptionPane.showMessageDialog(null, "Debes llenar todos los campos");
				}
			}
		});

		btnActualizar = new JPanel();
		btnActualizar.setLayout(null);
		btnActualizar.setBackground(SystemColor.textHighlight);
		btnActualizar.setBounds(296, 514, 122, 35);
		btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		panel.add(btnActualizar);
		btnActualizar.setVisible(false);
		btnActualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ((txtFechaE.getDate() != null && txtFechaS.getDate() != null)) {
					actualizarReservacion();
				} else {
					JOptionPane.showMessageDialog(null, "Debes llenar todos los campos");
				}
			}
		});

		JLabel lblActualizar = new JLabel("Actualizar");
		lblActualizar.setHorizontalAlignment(SwingConstants.CENTER);
		lblActualizar.setForeground(Color.WHITE);
		lblActualizar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblActualizar.setBounds(0, 0, 112, 35);
		btnActualizar.add(lblActualizar);

		JLabel Logo = new JLabel("");
		Logo.setBounds(197, 68, 104, 107);
		fondoImagen.add(Logo);
		Logo.setIcon(new ImageIcon(ReservasView.class.getResource("/imagenes/Ha-100px.png")));

		JLabel ImagenFondo = new JLabel("");
		ImagenFondo.setBounds(0, 140, 500, 409);
		fondoImagen.add(ImagenFondo);
		ImagenFondo.setBackground(Color.WHITE);
		ImagenFondo.setIcon(new ImageIcon(ReservasView.class.getResource("/imagenes/reservas-img-3.png")));

		// Componentes para dejar la interfaz con estilo Material Design
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuPrincipal principal = new MenuPrincipal();
				principal.setVisible(true);
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnexit.setBackground(new Color(12, 138, 199));
				labelExit.setForeground(Color.white);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(new Color(12, 138, 199));
		btnexit.setBounds(429, 0, 53, 36);
		fondoImagen.add(btnexit);

		labelExit = new JLabel("X");
		labelExit.setForeground(Color.WHITE);
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));

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
		panel.add(header);

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
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));

		JLabel lblTipoHabitacion = new JLabel("Tipo de Habitación");
		lblTipoHabitacion.setForeground(SystemColor.textInactiveText);
		lblTipoHabitacion.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblTipoHabitacion.setBounds(68, 150, 200, 14);
		panel.add(lblTipoHabitacion);

		JLabel lblFechaEntrada = new JLabel("Fecha de Entrada");
		lblFechaEntrada.setForeground(SystemColor.textInactiveText);
		lblFechaEntrada.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblFechaEntrada.setBounds(68, 220, 200, 14);
		panel.add(lblFechaEntrada);

		JLabel lblFechaSalida = new JLabel("Fecha de Salida");
		lblFechaSalida.setForeground(SystemColor.textInactiveText);
		lblFechaSalida.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblFechaSalida.setBounds(68, 290, 200, 14);
		panel.add(lblFechaSalida);

	}

	private boolean validarFechas(String fechaE, String fechaS) {
		Date fechaEntrada = txtFechaE.getDate();
		Date fechaSalida = txtFechaS.getDate();
		Calendar calEntrada = Calendar.getInstance();
		calEntrada.setTime(fechaEntrada);
		Calendar calSalida = Calendar.getInstance();
		calSalida.setTime(fechaSalida);

		if (fechaE == null || fechaS == null) {
			// Alguna de las fechas es nula
			JOptionPane.showMessageDialog(null,
					"Tienes que seleccionar la fecha de entrada y la fecha de salida para poder generar la reservación.");
			return false;
		}

		if (calEntrada.after(calSalida) || fechaE.equals(fechaS)) {
			JOptionPane.showMessageDialog(null,
					"La fecha de entrada debe ser anterior a la fecha de salida. \nLa fecha de salida debe ser posterior a la fecha de entrada.");

			txtFechaE.setDate(null);
			txtFechaS.setDate(null);

			return false;
		} else {
			return true;
		}

	}

	private void guardarReserva() {

		if (txtHuesped.getSelectedItem() != null && txtTipoHabitacion.getSelectedItem() != null
				&& txtFechaE.getDate() != null && txtFechaS.getDate() != null
				&& txtFormaPago.getSelectedItem() != null) {
			String fechaE = ((JTextField) txtFechaE.getDateEditor().getUiComponent()).getText();
			String fechaS = ((JTextField) txtFechaS.getDateEditor().getUiComponent()).getText();

			if (validarFechas(fechaE, fechaS)) {
				calcularValor(txtFechaE, txtFechaS);
				// Obtén el nombre del tipo de habitacion y forma de pago seleccionado
				String nombreHuespedSeleccionado = txtHuesped.getSelectedItem().toString();
				String tipoHabitacionSeleccionado = txtTipoHabitacion.getSelectedItem().toString();
				String formaPagoSeleccionada = txtFormaPago.getSelectedItem().toString();

				// Obtén el ID del tipo de habitacion y forma de pago desde el mapa
				Integer idHuesped = huespedMap.get(nombreHuespedSeleccionado);
				Integer idTipoHabitacion = tiposHabitacionMap.get(tipoHabitacionSeleccionado);
				Integer idFormaPago = formasPagoMap.get(formaPagoSeleccionada);

				// Valida que el huesped exista en la tabla de huespedes
				boolean huespedExiste = huespedesController.existeHuesped(idHuesped);
				if (!huespedExiste) {
					JOptionPane.showMessageDialog(null,
							"El huesped no existe. Para realizar una reserva, debe registrar un huesped");
					return;
				}

				String numeroReservacion = generarNumeroReserva();
				txtNumeroReserva.setText(numeroReservacion);

				Reserva nuevaReserva = new Reserva(idHuesped,
						idTipoHabitacion, java.sql.Date.valueOf(fechaE),
						java.sql.Date.valueOf(fechaS),
						txtValor.getText(), idFormaPago,
						numeroReservacion);
				reservasController.guardar(nuevaReserva);

				Exito exito = new Exito();
				String mensaje = "<html>Reserva guardada con exito.<br/>Reserva Num.: </html>" + numeroReservacion;
				exito.mensaje(mensaje);
				exito.setVisible(true);
				limpiarCampos();
			}
		} else {
			JOptionPane.showMessageDialog(this, "Debes llenar todos los campos.");
		}

		// Busqueda verReserva = new Busqueda(connectionFactory);
		// verReserva.setVisible(true);
		// dispose();
	}

	private void calcularValor(JDateChooser fechaE, JDateChooser fechaS) {
		if (fechaE.getDate() != null && fechaS.getDate() != null) {
			Calendar inicio = fechaE.getCalendar();
			Calendar fin = fechaS.getCalendar();
			int dias = -1; // Usamos -1 para contar a partir del día siguiente
			double valor = 0;

			if (inicio.before(fin)) {
				while (inicio.before(fin) || inicio.equals(fin)) {
					dias++;
					inicio.add(Calendar.DATE, 1); // Días a ser aumentados
				}

				// Obtener el tipo de habitación seleccionado
				String tipoHabitacionSeleccionado = txtTipoHabitacion.getSelectedItem().toString();

				// Obtener el precio de la habitación desde la base de datos
				Map<String, Integer> tiposHabitacion = tipoHabitacionController.listarTipoHabitacion();
				if (tiposHabitacion.containsKey(tipoHabitacionSeleccionado)) {
					int idTipoHabitacion = tiposHabitacion.get(tipoHabitacionSeleccionado);
					double precioHabitacion = tipoHabitacionController.obtenerPrecioHabitacion(idTipoHabitacion);

					valor = dias * precioHabitacion;
					txtValor.setText("$ " + valor);
				} else {
					txtValor.setText("Tipo de habitación no válido");
				}
			}
		}
	}

	// Funciones que permiten mover la ventana por la pantalla
	private void headerMousePressed(java.awt.event.MouseEvent evt) {
		xMouse = evt.getX();
		yMouse = evt.getY();
	}

	private void headerMouseDragged(java.awt.event.MouseEvent evt) {
		int x = evt.getXOnScreen();
		int y = evt.getYOnScreen();
		this.setLocation(x - xMouse, y - yMouse);
	}

	private String generarNumeroReserva() {
		String numeroReserva = txtNumeroReserva.getText();
		if (numeroReserva.isEmpty()) {
			// Obten la fecha actual
			SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
			String fechaActual = dateFormat.format(new Date());
			String tipoHabitacionSeleccionado = txtTipoHabitacion.getSelectedItem().toString();

			// Obtén el ID del tipo de habitación seleccionado (reemplaza "idTipoHabitacion"
			// con la variable real)
			int idTipoHabitacion = tiposHabitacionMap.get(tipoHabitacionSeleccionado);

			// Genera una cadena aleatoria de 8 dígitos y letras
			String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
			StringBuilder cadenaAleatoria = new StringBuilder();
			Random rnd = new Random();
			while (cadenaAleatoria.length() < 8) { // Longitud de 8 caracteres
				int index = (int) (rnd.nextFloat() * caracteres.length());
				cadenaAleatoria.append(caracteres.charAt(index));
			}

			// Combina los elementos para formar el número de reserva
			numeroReserva = fechaActual + idTipoHabitacion + cadenaAleatoria.toString();
		}

		return numeroReserva;
	}

	public void abrirVentanaEdicionReserva(int selectedIdReserva) {
		this.nvoReservacioId = selectedIdReserva;
		btnGuardar.setVisible(false);
		btnActualizar.setVisible(true);

		// Llamar a "huespedesController" para obtener un huésped por su ID.
		Reserva reserva = reservasController.obtenerReservaPorId(nvoReservacioId);

		// Obtén el nombre del huesped
		Integer idHuesped = reserva.getHuesped_id();

		String nombreHuespedSeleccionado = "";

		for (Map.Entry<String, Integer> entry : huespedMap.entrySet()) {
			if (entry.getValue().equals(idHuesped)) {
				nombreHuespedSeleccionado = entry.getKey(); // Retorna el nombre del huésped correspondiente al ID
			}
		}

		if (reserva != null) {
			// Llena los campos de la ventana con los datos del huésped
			// txtHuesped.setSelectedItem(reserva.getHuesped_id());
			txtHuesped.setSelectedItem(nombreHuespedSeleccionado);
			txtTipoHabitacion.setSelectedItem(reserva.getHabitacion_id());
			txtFechaE.setDate(reserva.getFechaE());
			txtFechaS.setDate(reserva.getFechaS());
			txtValor.setText(reserva.getPrecioReserva());
			txtFormaPago.setSelectedItem(reserva.getFormaPago());
			txtNumeroReserva.setText(reserva.getNumeroReserva());

			// Muestra la ventana de edición:
			this.setVisible(true);
		}

	}

	private void actualizarReservacion() {
		// Obtén el nombre del tipo de habitacion y forma de pago seleccionado
		String nombreHuespedSeleccionado = txtHuesped.getSelectedItem().toString();
		String tipoHabitacionSeleccionado = txtTipoHabitacion.getSelectedItem().toString();
		String formaPagoSeleccionada = txtFormaPago.getSelectedItem().toString();

		// Obtén el ID del tipo de habitacion y forma de pago desde el mapa
		Integer idHuesped = huespedMap.get(nombreHuespedSeleccionado);
		Integer idTipoHabitacion = tiposHabitacionMap.get(tipoHabitacionSeleccionado);
		Integer idFormaPago = formasPagoMap.get(formaPagoSeleccionada);

		String fechaE = ((JTextField) txtFechaE.getDateEditor().getUiComponent()).getText();
		String fechaS = ((JTextField) txtFechaS.getDateEditor().getUiComponent()).getText();
		String valor = txtValor.getText();
		String numeroReserva = txtNumeroReserva.getText();

		this.reservasController.actualizar(idHuesped, idTipoHabitacion, java.sql.Date.valueOf(fechaE),
				java.sql.Date.valueOf(fechaS), valor, idFormaPago,
				numeroReserva, nvoReservacioId);
		JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));

		Busqueda busqueda = new Busqueda(connectionFactory);
		busqueda.setVisible(true);
		this.dispose();
		this.setVisible(false);

		btnGuardar.setVisible(true);
		btnActualizar.setVisible(false);

	}

	private void agregarNvoHuesped() {
		RegistroHuesped huespedRegistro = new RegistroHuesped(connectionFactory, true);
		huespedRegistro.setVisible(true);
		dispose();

	}

	private void cargarHuesped(Huespedes huesped) {
		// Crear un modelo para el JComboBox
		DefaultComboBoxModel<String> modeloComboBoxHuesped = new DefaultComboBoxModel<>();

		// Obtener la lista de nombres de huéspedes desde el controlador
		huespedMap = huespedesController.obtenerHuespedes();

		// Agregar los nombres de huéspedes al modelo del JComboBox
		modeloComboBoxHuesped.addAll(huespedMap.keySet());

		// Asignar el modelo al JComboBox
		txtHuesped.setModel(modeloComboBoxHuesped);

		// Establecer el JComboBox como editable
		txtHuesped.setSelectedItem(huespedesController.obtenerNombreHuesped(huesped.getId()));

	}

	private void limpiarCampos() {
		txtHuesped.setSelectedItem(null);
		txtTipoHabitacion.setSelectedItem(null);
		txtFechaE.setDate(null);
		txtFechaS.setDate(null);
		txtValor.setText(null);
		txtFormaPago.setSelectedItem(null);
		txtNumeroReserva.setText(null);
	}

}
