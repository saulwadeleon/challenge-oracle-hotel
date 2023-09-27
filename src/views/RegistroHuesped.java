package views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;

import jdbc.controller.HuespedesController;
import jdbc.factory.ConnectionFactory;
import jdbc.controller.ListaPaisesController;
import jdbc.modelo.Huespedes;
import jdbc.modelo.ListaPaises;

public class RegistroHuesped extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextField txtTelefono;
	private JTextField txtEmail;
	private JDateChooser txtFechaN;
	private JComboBox<String> txtNacionalidad;

	private JLabel labelAtras;
	private JLabel labelExit;
	JPanel btnguardar;
	JPanel btnactualizar;
	int xMouse, yMouse;
	private int actualizaHuespedId;
	private boolean nvoHuesped;
	private boolean esEdicion;

	private ConnectionFactory connectionFactory;
	private HuespedesController huespedesController;
	ListaPaisesController listaPaisesController;
	ListaPaises listaPaises;
	Huespedes huespedes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionFactory conexion = new ConnectionFactory(); // Crea la instancia de ConnectionFactory
					RegistroHuesped frame = new RegistroHuesped(conexion);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public RegistroHuesped(ConnectionFactory conexion) {
		connectionFactory = conexion;
		inicializarVariables(connectionFactory);
		initComponents();
	}

	public RegistroHuesped(Huespedes huespedes, ConnectionFactory conexion) {
		connectionFactory = conexion;
		esEdicion = true;
		inicializarVariables(connectionFactory);
		initComponents();
		cargarEdicionHuesped(huespedes);
	}

	public RegistroHuesped(ConnectionFactory conexion, boolean nvoHuesped) {
		connectionFactory = conexion;
		this.nvoHuesped = nvoHuesped;
		inicializarVariables(connectionFactory);
		initComponents();
	}

	private void inicializarVariables(ConnectionFactory conexion) {
		// Inicializar controladores
		huespedesController = new HuespedesController(connectionFactory);
		listaPaisesController = new ListaPaisesController(connectionFactory);
		listaPaises = listaPaisesController.obtenerListaPaises();

	}

	private void initComponents() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(RegistroHuesped.class.getResource("/imagenes/lOGO-50PX.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 634);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.text);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setUndecorated(true);
		contentPane.setLayout(null);

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
		header.setBackground(SystemColor.text);
		header.setOpaque(false);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);

		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario menuUsuario = new MenuUsuario(connectionFactory);
				menuUsuario.setVisible(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(Color.white);
				labelAtras.setForeground(Color.black);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(new Color(12, 138, 199));
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);

		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setForeground(Color.WHITE);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);

		// Componentes para dejar la interfaz con estilo Material Design
		JPanel btnexit = new JPanel();
		btnexit.setLayout(null);
		btnexit.setBackground(new Color(12, 138, 199));
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);

		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuPrincipal principal = new MenuPrincipal();
				principal.setVisible(true);
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

		labelExit = new JLabel("X");
		labelExit.setForeground(Color.WHITE);
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));

		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtNombre.setBounds(560, 135, 285, 33);
		txtNombre.setBackground(Color.WHITE);
		txtNombre.setColumns(10);
		txtNombre.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtNombre);

		txtApellido = new JTextField();
		txtApellido.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtApellido.setBounds(560, 204, 285, 33);
		txtApellido.setColumns(10);
		txtApellido.setBackground(Color.WHITE);
		txtApellido.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtApellido);

		txtFechaN = new JDateChooser();
		txtFechaN.setBounds(560, 278, 285, 36);
		txtFechaN.getCalendarButton()
				.setIcon(new ImageIcon(RegistroHuesped.class.getResource("/imagenes/icon-reservas.png")));
		txtFechaN.getCalendarButton().setBackground(SystemColor.textHighlight);
		txtFechaN.setDateFormatString("yyyy-MM-dd");
		contentPane.add(txtFechaN);

		txtNacionalidad = new JComboBox<String>();
		txtNacionalidad.setBounds(558, 350, 289, 36);
		txtNacionalidad.setBackground(SystemColor.text);
		txtNacionalidad.setFont(new Font("Roboto", Font.PLAIN, 16));

		// Crear un modelo para el JComboBox
		DefaultComboBoxModel<String> modeloComboBox = new DefaultComboBoxModel<>();

		// Agregar los nombres de países al modelo del JComboBox
		for (String nombrePais : listaPaises.obtenerNombresPaises()) {
			modeloComboBox.addElement(nombrePais);
		}

		// Asignar el modelo al JComboBox
		txtNacionalidad.setModel(modeloComboBox);

		// Establecer México como el valor predeterminado seleccionado
		txtNacionalidad.setSelectedItem("México");
		contentPane.add(txtNacionalidad);

		JLabel lblNombre = new JLabel("NOMBRE");
		lblNombre.setBounds(562, 119, 253, 14);
		lblNombre.setForeground(SystemColor.textInactiveText);
		lblNombre.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblNombre);

		JLabel lblApellido = new JLabel("APELLIDO");
		lblApellido.setBounds(560, 189, 255, 14);
		lblApellido.setForeground(SystemColor.textInactiveText);
		lblApellido.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblApellido);

		JLabel lblFechaN = new JLabel("FECHA DE NACIMIENTO");
		lblFechaN.setBounds(560, 256, 255, 14);
		lblFechaN.setForeground(SystemColor.textInactiveText);
		lblFechaN.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblFechaN);

		JLabel lblNacionalidad = new JLabel("NACIONALIDAD");
		lblNacionalidad.setBounds(560, 326, 255, 14);
		lblNacionalidad.setForeground(SystemColor.textInactiveText);
		lblNacionalidad.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblNacionalidad);

		JLabel lblTelefono = new JLabel("TELÉFONO");
		lblTelefono.setBounds(562, 406, 253, 14);
		lblTelefono.setForeground(SystemColor.textInactiveText);
		lblTelefono.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblTelefono);

		txtTelefono = new JTextField();
		txtTelefono.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtTelefono.setBounds(560, 424, 285, 33);
		txtTelefono.setColumns(10);
		txtTelefono.setBackground(Color.WHITE);
		txtTelefono.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtTelefono);

		JLabel lblTitulo = new JLabel("REGISTRO HUÉSPED");
		lblTitulo.setBounds(606, 55, 234, 42);
		lblTitulo.setForeground(new Color(12, 138, 199));
		lblTitulo.setFont(new Font("Dialog", Font.PLAIN, 20));
		contentPane.add(lblTitulo);

		JLabel lblEmail = new JLabel("EMAIL");
		lblEmail.setBounds(560, 474, 253, 14);
		lblEmail.setForeground(SystemColor.textInactiveText);
		lblEmail.setFont(new Font("Roboto Black", Font.PLAIN, 18));
		contentPane.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Roboto", Font.PLAIN, 16));
		txtEmail.setBounds(560, 495, 285, 33);
		txtEmail.setColumns(10);
		txtEmail.setBackground(Color.WHITE);
		txtEmail.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtEmail);

		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setBounds(560, 170, 289, 2);
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2);

		JSeparator separator_1_2_1 = new JSeparator();
		separator_1_2_1.setBounds(560, 240, 289, 2);
		separator_1_2_1.setForeground(new Color(12, 138, 199));
		separator_1_2_1.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_1);

		JSeparator separator_1_2_2 = new JSeparator();
		separator_1_2_2.setBounds(560, 314, 289, 2);
		separator_1_2_2.setForeground(new Color(12, 138, 199));
		separator_1_2_2.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_2);

		JSeparator separator_1_2_3 = new JSeparator();
		separator_1_2_3.setBounds(560, 386, 289, 2);
		separator_1_2_3.setForeground(new Color(12, 138, 199));
		separator_1_2_3.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_3);

		JSeparator separator_1_2_4 = new JSeparator();
		separator_1_2_4.setBounds(560, 457, 289, 2);
		separator_1_2_4.setForeground(new Color(12, 138, 199));
		separator_1_2_4.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_4);

		JSeparator separator_1_2_5 = new JSeparator();
		separator_1_2_5.setBounds(560, 529, 289, 2);
		separator_1_2_5.setForeground(new Color(12, 138, 199));
		separator_1_2_5.setBackground(new Color(12, 138, 199));
		contentPane.add(separator_1_2_5);

		btnguardar = new JPanel();
		btnguardar.setBounds(723, 560, 122, 35);
		btnguardar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				guardarHuesped();
			}
		});
		btnguardar.setLayout(null);
		btnguardar.setBackground(new Color(12, 138, 199));
		contentPane.add(btnguardar);
		btnguardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

		JLabel labelGuardar = new JLabel("GUARDAR");
		labelGuardar.setHorizontalAlignment(SwingConstants.CENTER);
		labelGuardar.setForeground(Color.WHITE);
		labelGuardar.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelGuardar.setBounds(0, 0, 122, 35);
		btnguardar.add(labelGuardar);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 489, 634);
		panel.setBackground(new Color(12, 138, 199));
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel imagenFondo = new JLabel("");
		imagenFondo.setBounds(0, 121, 479, 502);
		panel.add(imagenFondo);
		imagenFondo.setIcon(new ImageIcon(RegistroHuesped.class.getResource("/imagenes/registro.png")));

		JLabel logo = new JLabel("");
		logo.setBounds(194, 39, 104, 107);
		panel.add(logo);
		logo.setIcon(new ImageIcon(RegistroHuesped.class.getResource("/imagenes/Ha-100px.png")));

		btnactualizar = new JPanel();
		btnactualizar.setLayout(null);
		btnactualizar.setBackground(new Color(12, 138, 199));
		btnactualizar.setBounds(560, 560, 122, 35);
		btnactualizar.setVisible(false);
		contentPane.add(btnactualizar);

		if (esEdicion) {
			btnguardar.setVisible(false);
			btnactualizar.setVisible(true);
		} else {
			btnguardar.setVisible(true);
			btnactualizar.setVisible(false);
		}

		btnactualizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				actualizarHuesped();
			}
		});

		JLabel labelActualizar = new JLabel("Actualizar");
		labelActualizar.setHorizontalAlignment(SwingConstants.CENTER);
		labelActualizar.setForeground(Color.WHITE);
		labelActualizar.setFont(new Font("Dialog", Font.PLAIN, 18));
		labelActualizar.setBounds(0, 0, 122, 35);
		btnactualizar.add(labelActualizar);

	}

	// Código que permite mover la ventana por la pantalla según la posición de "x"
	// y "y"
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
		if (txtFechaN.getDate() != null && !txtNombre.equals("") && !txtApellido.equals("")) {
			String fechaN = ((JTextField) txtFechaN.getDateEditor().getUiComponent()).getText();
			// Obtén el nombre del país seleccionado
			String nombrePaisSeleccionado = txtNacionalidad.getSelectedItem().toString();

			// Obtén el ID del país desde el mapa
			Integer idPais = listaPaises.obtenerIdPais(nombrePaisSeleccionado);

			if (idPais != null) {
				huespedes = new Huespedes(txtNombre.getText(), txtApellido.getText(),
						java.sql.Date.valueOf(fechaN), idPais, txtEmail.getText(), txtTelefono.getText());
				this.huespedesController.guardar(huespedes);

				Exito exito = new Exito();
				exito.setVisible(true);
				limpiarCampos();

				// Verifica si nvoHuesped es true antes de abrir ReservasView
				if (nvoHuesped) {
					ReservasView view = new ReservasView(huespedes, connectionFactory);
					view.setVisible(true);
					dispose();

				}

			} else {
				JOptionPane.showMessageDialog(this, "Selecciona un país válido.");
			}
		} else {
			JOptionPane.showMessageDialog(this, "Debes llenar todos los campos.");
		}
	}

	public void cargarEdicionHuesped(Huespedes huesped) {
		this.actualizaHuespedId = huesped.getId();
		btnguardar.setVisible(false);

		// Obtener el nombre del país desde el mapa
		String nombrePaisSeleccionado = listaPaises.obtenerNombrePais(huesped.getNacionalidad());

		// Llena los campos de la ventana con los datos del huésped
		txtNombre.setText(huesped.getNombre());
		txtApellido.setText(huesped.getApellido());
		txtFechaN.setDate(huesped.getFechaNacimiento());
		txtNacionalidad.setSelectedItem(nombrePaisSeleccionado);
		txtEmail.setText(huesped.getEmailHuesped());
		txtTelefono.setText(huesped.getTelefono());

		// btnactualizar.setVisible(true);

	}

	private void actualizarHuesped() {
		if (txtFechaN.getDate() != null && !txtNombre.equals("") && !txtApellido.equals("")) {
			String fechaN = ((JTextField) txtFechaN.getDateEditor().getUiComponent()).getText();
			// Obtén el nombre del país seleccionado
			String nombrePaisSeleccionado = txtNacionalidad.getSelectedItem().toString();

			// Obtén el ID del país desde el mapa
			Integer idPais = listaPaises.obtenerIdPais(nombrePaisSeleccionado);

			if (idPais != null) {
				huespedesController.actualizar(txtNombre.getText(), txtApellido.getText(),
						java.sql.Date.valueOf(fechaN), idPais, txtEmail.getText(), txtTelefono.getText(),
						actualizaHuespedId);
				JOptionPane.showMessageDialog(this, String.format("Registro modificado con éxito"));

			} else {
				JOptionPane.showMessageDialog(this, "Selecciona un país válido.");

			}
		} else {
			JOptionPane.showMessageDialog(this, "Debes llenar todos los campos.");
		}
		Busqueda busqueda = new Busqueda(connectionFactory);
		busqueda.setVisible(true);
		this.dispose();
		this.setVisible(false);

		btnactualizar.setVisible(false);
		btnguardar.setVisible(true);

	}

	private void limpiarCampos() {
		txtNombre.setText("");
		txtApellido.setText("");
		txtFechaN.setDate(null);
		txtNacionalidad.setSelectedItem(null);
		txtEmail.setText("");
		txtTelefono.setText("");
	}

	/*
	 * // Configura un temporizador para abrir ReservasView después de un retraso
	 * import java.util.Timer;
	 * Timer timer = new Timer();
	 * TimerTask task = new TimerTask() {
	 * 
	 * @Override
	 * public void run() {
	 * // Verifica si nvoHuesped es true antes de abrir ReservasView
	 * if (nvoHuesped) {
	 * ReservasView view = new ReservasView(huespedes, connectionFactory);
	 * view.setVisible(true);
	 * dispose();
	 * }
	 * }
	 * };
	 * 
	 * // Establece el retraso en milisegundos (por ejemplo, 2000 ms = 2 segundos)
	 * int delay = 5000; // Cambia esto según la duración deseada
	 * timer.schedule(task, delay);
	 */
}
