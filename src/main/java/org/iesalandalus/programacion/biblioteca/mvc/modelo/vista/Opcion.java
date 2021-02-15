package org.iesalandalus.programacion.biblioteca.mvc.modelo.vista;

public enum Opcion {

	INSERTAR_ALUMNO("Introduzca al Alumno") {
		public void ejecutar() {
			vista.insertarAlumno();
		}
	},
	BUSCAR_ALUMNO("Buscar al Alumno") {
		public void ejecutar() {
			vista.buscarAlumno();
		}
	},
	BORRAR_ALUMNO("Borrar al Alumno") {
		public void ejecutar() {
			vista.borrarAlumno();
		}
	},
	LISTAR_ALUMNOS("Listar todos los Alumnos") {
		public void ejecutar() {
			vista.listarAlumnos();
		}
	},
	INSERTAR_LIBRO("Introducir Libro") {
		public void ejecutar() {
			vista.insertarLibro();
		}
	},
	BUSCAR_LIBRO("Buscar el Libro") {
		public void ejecutar() {
			vista.buscarLibro();
		}
	},
	BORRAR_LIBRO("Borrar el Libro") {
		public void ejecutar() {
			vista.borrarLibro();
		}
	},
	LISTAR_LIBROS("Listar todos los Libros") {
		public void ejecutar() {
			vista.listarLibros();
		}
	},
	PRESTAR_LIBRO("Prestar el Libro") {
		public void ejecutar() {
			vista.prestarLibro();
		}
	},
	DEVOLVER_LIBRO("Devolver el Libro") {
		public void ejecutar() {
			vista.devolverLibro();
		}
	},
	BUSCAR_PRESTAMO("Buscar Prestamo") {
		public void ejecutar() {
			vista.buscarPrestamo();
		}
	},
	BORRAR_PRESTAMO("Borrar Prestamo") {
		public void ejecutar() {
			vista.borrarPrestamo();
		}
	},
	LISTAR_PRESTAMOS("Listar todos los Prestamos") {
		public void ejecutar() {
			vista.listarPrestamos();
		}
	},
	LISTAR_PRESTAMOS_ALUMNO("Listar los prestamos de un alumno") {
		public void ejecutar() {
			vista.listarPrestamosAlumno();
		}
	},
	LISTAR_PRESTAMOS_LIBRO("Listar los prestamos de un alumno") {
		public void ejecutar() {
			vista.listarPrestamosLibro();
		}
	},
	LISTAR_PRESTAMOS_FECHA("Listar los prestamos en una fecha") {
		public void ejecutar() {
			vista.listarPrestamosFecha();
		}
	},
	SALIR("Salir") {
		public void ejecutar() {
			vista.terminar();
		}
	};

	private String mensaje;
	private static Vista vista;

	private Opcion(String mensaje) {
		this.mensaje = mensaje;
	}

	public abstract void ejecutar();

	protected static void setVista(Vista vista) {
		Opcion.vista = vista;
	}

	public static Opcion getOpcionSegunOridnal(int ordinal) {
		if (esOrdinalValido(ordinal))
			return values()[ordinal];
		else
			throw new IllegalArgumentException("El Ordinal de la opción no es válido");
	}

	public static boolean esOrdinalValido(int ordinal) {
		return (ordinal >= 0 && ordinal <= values().length - 1);
	}

	@Override
	public String toString() {
		return String.format("%d.- %s", ordinal(), mensaje);
	}

}
