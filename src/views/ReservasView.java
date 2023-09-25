package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import com.toedter.calendar.JDateChooser;

import jdbc.controller.HuespedesController;
import jdbc.controller.ReservasController;
import jdbc.controller.TipoHabitacionController;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;

public class ReservasView extends JFrame {

	private JPanel contentPane;
	public static JComboBox<String> txtTipoHabitacion;
	public static JTextField txtValor;
	public static JDateChooser txtFechaE;
	public static JDateChooser txtFechaS;
	public static JComboBox<String> txtFormaPago;
	private Map<String, Integer> tiposHabitacionMap;
	private Map<String, Integer> formasPagoMap;
	int xMouse, yMouse;
	private JLabel labelExit;
	private JLabel labelAtras;

	private ConnectionFactory connectionFactory;
	private ReservasController reservasController;
	private TipoHabitacionController tipoHabitacionController;
	private HuespedesController huespedesController;

	private JTextField txtHuesped;
	private JTextField txtNumeroReserva;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionFactory conexion = new ConnectionFactory();
					ReservasView frame = new ReservasView(conexion);
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
	public ReservasView(ConnectionFactory conexion) {
		this.connectionFactory = conexion;
		// Inicializar los controladores
		reservasController = new ReservasController(connectionFactory);
		tipoHabitacionController = new TipoHabitacionController(connectionFactory);
		huespedesController = new HuespedesController(connectionFactory);

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
		txtHuesped = new JTextField();
		txtHuesped.setBounds(68, 100, 250, 35);
		txtHuesped.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.add(txtHuesped);

		// Botón para agregar nuevo huésped junto al campo de búsqueda
		JButton btnAgregarCliente = new JButton("+");
		btnAgregarCliente.setBounds(322, 100, 35, 35);
		btnAgregarCliente.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				guardarHuesped();
			}
		});
		panel.add(btnAgregarCliente);

		// Separador después del campo de cliente
		JSeparator separatorCliente = new JSeparator();
		separatorCliente.setForeground(SystemColor.textHighlight);
		separatorCliente.setBounds(68, 135, 289, 2);
		separatorCliente.setBackground(SystemColor.textHighlight);
		panel.add(separatorCliente);

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

		panel.add(txtTipoHabitacion);

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

		txtFechaE.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				validarFechas();
			}
		});

		txtFechaS.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				validarFechas();
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
		formasPagoMap = reservasController.metodosDePago();

		// Agregar los nombres de países al modelo del JComboBox
		for (String formaPago : formasPagoMap.keySet()) {
			modeloComboBox.addElement(formaPago);
		}

		// Asignar el modelo al JComboBox
		txtFormaPago.setModel(modeloComboBox);
		panel.add(txtFormaPago);

		JSeparator separator_valorReserva = new JSeparator();
		separator_valorReserva.setForeground(SystemColor.textHighlight);
		separator_valorReserva.setBounds(68, 416, 289, 2);
		separator_valorReserva.setBackground(SystemColor.textHighlight);
		panel.add(separator_valorReserva);

		JLabel lblSiguiente = new JLabel("SIGUIENTE");
		lblSiguiente.setHorizontalAlignment(SwingConstants.CENTER);
		lblSiguiente.setForeground(Color.WHITE);
		lblSiguiente.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblSiguiente.setBounds(0, 0, 112, 35);

		// Botón Siguiente
		JPanel btnsiguiente = new JPanel();
		btnsiguiente.setLayout(null);
		btnsiguiente.setBackground(SystemColor.textHighlight);
		btnsiguiente.setBounds(296, 514, 122, 35);
		panel.add(btnsiguiente);
		btnsiguiente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		btnsiguiente.add(lblSiguiente);

		btnsiguiente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if ((txtFechaE.getDate() != null && txtFechaS.getDate() != null)) {
					guardarReserva();
				} else {
					JOptionPane.showMessageDialog(null, "Debes llenar todos los campos");
				}
			}
		});

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

	private void validarFechas() {
		Date fechaEntrada = txtFechaE.getDate();
		Date fechaSalida = txtFechaS.getDate();

		if (fechaEntrada == null || fechaSalida == null) {
			// Alguna de las fechas es nula, no hagas nada
			return;
		}

		Calendar calEntrada = Calendar.getInstance();
		calEntrada.setTime(fechaEntrada);

		Calendar calSalida = Calendar.getInstance();
		calSalida.setTime(fechaSalida);

		if (calEntrada.after(calSalida) || calEntrada.equals(calSalida)) {
			JOptionPane.showMessageDialog(null,
					"La fecha de entrada debe ser anterior a la fecha de salida y tampoco se puede registrar una reserva con entrada y salida el mismo día.");

			txtFechaE.setDate(null);
			txtFechaS.setDate(null);
		}
	}

	private void guardarReserva() {
		String fechaE = ((JTextField) txtFechaE.getDateEditor().getUiComponent()).getText();
		String fechaS = ((JTextField) txtFechaS.getDateEditor().getUiComponent()).getText();

		// Obtén el nombre del tipo de habitacion y forma de pago seleccionado
		String tipoHabitacionSeleccionado = txtTipoHabitacion.getSelectedItem().toString();
		String formaPagoSeleccionada = txtFormaPago.getSelectedItem().toString();

		// Obtén el ID del tipo de habitacion y forma de pago desde el mapa
		Integer idTipoHabitacion = tiposHabitacionMap.get(tipoHabitacionSeleccionado);
		Integer idFormaPago = formasPagoMap.get(formaPagoSeleccionada);

		// Valida que el huesped exista en la tabla de huespedes
		Integer idHuesped = Integer.parseInt(txtHuesped.getText());
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

		JOptionPane.showMessageDialog(null, "Registro Guardado con éxito con el número: " + numeroReservacion);

		/*
		 * // RegistroHuesped huesped = new RegistroHuesped(nuevaReserva.getId());
		 * RegistroHuesped huesped = new RegistroHuesped();
		 * huesped.setVisible(true);
		 * dispose();
		 */

		Busqueda verReserva = new Busqueda(connectionFactory);
		verReserva.setVisible(true);
		dispose();
	}

	private void calcularValor(JDateChooser fechaE, JDateChooser fechaS) {
		if (fechaE.getDate() != null && fechaS.getDate() != null) {
			Calendar inicio = fechaE.getCalendar();
			Calendar fin = fechaS.getCalendar();
			int dias = -1; // Usamos -1 para contar a partir del día siguiente
			double valor = 0;

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
				txtValor.setText("$" + valor);
			} else {
				txtValor.setText("Tipo de habitación no válido");
			}
		}
	}

	/*
	 * private void calcularValor(JDateChooser fechaE, JDateChooser fechaS) {
	 * if (fechaE.getDate() != null && fechaS.getDate() != null) {
	 * Calendar inicio = fechaE.getCalendar();
	 * Calendar fin = fechaS.getCalendar();
	 * int dias = -1; // Usamos -1 para contar a partir del dia siguiente
	 * int diaria = 500;
	 * int valor;
	 * 
	 * while (inicio.before(fin) || inicio.equals(fin)) {
	 * dias++;
	 * inicio.add(Calendar.DATE, 1); // dias a ser aumentados
	 * }
	 * valor = dias * diaria;
	 * txtValor.setText("$" + valor);
	 * }
	 * }
	 */

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

	private void guardarHuesped() {
		// ... (código para guardar el nuevo cliente)

		// Obtén el huesped_id del cliente recién guardado (puedes obtenerlo de la base
		// de datos)
		// int huespedId = obtenerHuespedId(); // Reemplaza esto con la lógica real

		// Notifica a ReservasView sobre el cliente guardado
		// notificarClienteGuardado(huespedId);

		// Cierra la ventana de RegistroHuesped
		// dispose();
	}

	// Método para abrir la ventana de registro de huésped
	private void abrirRegistroHuesped() {
		RegistroHuesped registroHuesped = new RegistroHuesped(connectionFactory); // Pasa una referencia de ReservasView
		registroHuesped.setVisible(true);
	}

	// Luego, puedes llamar a este método cuando el usuario haga clic en el botón
	// "Agregar Cliente" o enlace similar
	@SuppressWarnings("unused")
	private void btnAgregarHuespedActionPerformed(ActionEvent evt) {
		abrirRegistroHuesped();
	}

	private String generarNumeroReserva() {
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
		String numeroReserva = fechaActual + idTipoHabitacion + cadenaAleatoria.toString();

		return numeroReserva;
	}
}
