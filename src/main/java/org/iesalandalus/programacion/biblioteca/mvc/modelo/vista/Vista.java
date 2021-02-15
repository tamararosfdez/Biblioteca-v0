package org.iesalandalus.programacion.biblioteca.mvc.modelo.vista;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.controlador.Controlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;

public class Vista {

	private Controlador controlador;

	public Vista() {
		Opcion.setVista(this);
	}

	public void setControlador(Controlador controlador) {
		this.controlador = controlador;
	}

	public void comenzar() {
		Consola.mostrarCabecera("Gestión de la Biblioteca");
		int ordinalOpcion;
		do {
			Consola.mostrarMenu();
			ordinalOpcion = Consola.elegirOpcion();
			Opcion opcion = Opcion.getOpcionSegunOridnal(ordinalOpcion);
			opcion.ejecutar();
		} while (ordinalOpcion != Opcion.SALIR.ordinal());
	}

	public void terminar() {
		controlador.terminar();
	}

	public void insertarAlumno() {
		Consola.mostrarCabecera("Introducir Alumno");
		try {
			controlador.insertar(Consola.leerAlumno());
			System.out.println("Alumno introducido perfectamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarAlumno() {
		Consola.mostrarCabecera("Buscar Alumno");
		Alumno alumno;
		try {
			alumno = controlador.buscar(Consola.leerAlumnoFicticio());
			String mensaje = (alumno != null) ? alumno.toString() : "No existe el alumno introducido.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarAlumno() {
		Consola.mostrarCabecera("Borrar Alumno");
		try {
			controlador.borrar(Consola.leerAlumnoFicticio());
			System.out.println("Alumno borrado con éxito.");
		}  catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarAlumnos() {
		Consola.mostrarCabecera("Listado de Alumnos");
		Alumno[] alumnos = controlador.getAlumnos();
		if (alumnos[0] != null) {
			for (Alumno alumno : alumnos) {
				if (alumno != null) 
					System.out.println(alumno);
			}
		} else {
			System.out.println("No hay alumnos que mostrar.");
		}
	}

	public void insertarLibro() {
		Consola.mostrarCabecera("Introduce Libro");
		try {
			controlador.insertar(Consola.leerLibro());
			System.out.println("Libro introducido correctamente.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarLibro() {
		Consola.mostrarCabecera("Buscar Libro");
		Libro libro;
		try {
			libro = controlador.buscar(Consola.leerLibro());
			String mensaje = (libro != null) ? libro.toString() : "No existe el libro introducido.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarLibro() {
		Consola.mostrarCabecera("Borrar Libro");
		try {
			controlador.borrar(Consola.leerLibro());
			System.out.println("Libro borrado.");
		}  catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarLibros() {
		Consola.mostrarCabecera("Lista de los Libros");
		Libro[] libros = controlador.getLibros();
		if (libros[0] != null) {
			for (Libro libro : libros) {
				if (libro != null) 
					System.out.println(libro);
			}
		} else {
			System.out.println("No hay libros que mostrar.");
		}
	}

	public void prestarLibro() {
		Consola.mostrarCabecera("Prestar Libro");
		try {
			controlador.prestar(Consola.leerPrestamo());
			System.out.println("Libro prestado.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void devolverLibro() {
		Consola.mostrarCabecera("Devolver Libro");
		try {
			controlador.devolver(Consola.leerPrestamo(), Consola.leerFecha());
			System.out.println("Libro devuelto.");
		} catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarPrestamo() {
		Consola.mostrarCabecera("Buscar Prestamo");
		Prestamo prestamo;
		try {
			prestamo = controlador.buscar(Consola.leerPrestamoFicticio());
			String mensaje = (prestamo != null) ? prestamo.toString() : "No existe el prestamo.";
			System.out.println(mensaje);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void borrarPrestamo() {
		Consola.mostrarCabecera("Borrar Prestamo");
		try {
			controlador.borrar(Consola.leerPrestamoFicticio());
			System.out.println("Prestamo borrado.");
		}  catch (OperationNotSupportedException | IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarPrestamos() {
		Consola.mostrarCabecera("Lista de Prestamos");
		Prestamo[] prestamos = controlador.getPrestamos();
		if (prestamos[0] != null) {
			for (Prestamo prestamo : prestamos) {
				if (prestamo != null) 
					System.out.println(prestamo);
			}
		} else {
			System.out.println("No hay prestamos que mostrar.");
		}
	}

	public void listarPrestamosAlumno() {
		Consola.mostrarCabecera("Listado de Prestamos por Alumno");
		try {
			Prestamo[] prestamos = controlador.getPrestamos(Consola.leerAlumnoFicticio());
			if (prestamos[0] != null) {
				for (Prestamo prestamo : prestamos) {
					if (prestamo != null) 
						System.out.println(prestamo);
				}
			} else {
				System.out.println("No hay prestamos a mostrar para dicho alumno.");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarPrestamosLibro() {
		Consola.mostrarCabecera("Listado de Prestamos por Libro");
		try {
			Prestamo[] prestamos = controlador.getPrestamos(Consola.leerLibroFicticio());
			if (prestamos[0] != null) {
				for (Prestamo prestamo : prestamos) {
					if (prestamo != null) 
						System.out.println(prestamo);
				}
			} else {
				System.out.println("No hay prestamos a mostrar para dicho libro.");
			}
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
		}
	}

	public void listarPrestamosFecha() {
		Consola.mostrarCabecera("Listado de Prestamos por Fecha");
		try {
			Prestamo[] prestamos = controlador.getPrestamos(Consola.leerFecha());
			if (prestamos[0] != null) {
				for (Prestamo prestamo : prestamos) {
					if (prestamo != null) 
						System.out.println(prestamo);
				}
			} else {
				System.out.println("No hay prestamos a mostrar para dicho libro.");
			}
		} catch (NullPointerException e) {
			e.getMessage();
		}
	}

}
