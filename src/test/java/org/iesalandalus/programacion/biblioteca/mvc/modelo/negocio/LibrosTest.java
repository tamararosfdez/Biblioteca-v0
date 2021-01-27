package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.junit.BeforeClass;
import org.junit.Test;

public class LibrosTest {

	private static final String ERROR_CAPACIDAD_NO_CORRECTA = "ERROR: La capacidad debe ser mayor que cero.";
	private static final String ERROR_INSERTAR_LIBRO_NULO = "ERROR: No se puede insertar un libro nulo.";
	private static final String ERROR_BORRAR_LIBRO_NULO = "ERROR: No se puede borrar un libro nulo.";
	private static final String ERROR_BUSCAR_LIBRO_NULO = "ERROR: No se puede buscar un libro nulo.";
	private static final String ERROR_NO_MAS_LIBROS = "ERROR: No se aceptan más libros.";
	private static final String ERROR_LIBRO_EXISTE = "ERROR: Ya existe un libro con ese título y autor.";
	private static final String ERROR_LIBRO_BORRAR_NO_EXISTE = "ERROR: No existe ningún libro con ese título y autor.";
	private static final String OPERACION_NO_PERMITIDA = "Debería haber saltado una excepción indicando que dicha operación no está permitida.";
	private static final String LIBRO_NULO = "Debería haber saltado una excepción indicando que no se puede operar con un libro nulo.";
	private static final String MENSAJE_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
	private static final String TIPO_NO_CORRECTO = "El tipo de la excepción no es correcto.";
	private static final String EXCEPCION_NO_PROCEDE = "No debería haber saltado la excepción.";
	private static final String OPERACION_NO_REALIZADA = "La operación no la ha realizado correctamente.";
	private static final String LIBROS_NO_CREADOS = "Debería haber creado los libros correctamente.";
	private static final String REFERENCIA_NO_ESPERADA = "La referencia devuelta es la misma que la pasada.";
	private static final String TAMANO_NO_ESPERADO = "El tamaño devuelto no es el esperado.";
	private static final String LIBRO_NO_ESPERADO = "El libro devuelto no es el que debería ser.";
	private static final String OBJETO_DEBERIA_SER_NULO = "No se debería haber creado el objeto.";
	
	private static Libro libro1;
	private static Libro libro2;
	private static Libro libro3;
	private static Libro libroRepetido;
	
	@BeforeClass
	public static void asignarValoresAtributos() {
		libro1 = new Libro("Cien años de soledad", "Gabriel García Márquez", 471);
		libro2 = new Libro("El retrato de Dorian Gray", "Oscar Wilde", 275);
		libro3 = new Libro("Viaje al centro de la tierra", "Julio Verne", 320);
		libroRepetido = new Libro(libro1);
	}
	
	@Test
	public void constructorCapacidadValidaCreaLibrosCorrectamente() {
		Libros libros = new Libros(5);
		assertThat(LIBROS_NO_CREADOS, libros, not(nullValue()));
		assertThat(LIBROS_NO_CREADOS, libros.getCapacidad(), is(5));
		assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(0));
	}
	
	@Test
	public void constructorCapacidadNoValidaLanzaExcepcion() {
		Libros libros = null;
		try {
			libros = new Libros(0);
			fail(OPERACION_NO_PERMITIDA);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CAPACIDAD_NO_CORRECTA));
			assertThat(OBJETO_DEBERIA_SER_NULO, libros, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		try {
			libros = new Libros(-1);
			fail(OPERACION_NO_PERMITIDA);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CAPACIDAD_NO_CORRECTA));
			assertThat(OBJETO_DEBERIA_SER_NULO, libros, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void insertarLibroValidoConLibrosVaciosInsertaLibroCorrectamente() {
		Libros libros = new Libros(5);
		try {
			libros.insertar(libro1);
			Libro[] copiaLibros = libros.get();
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(1));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro1), is(libro1));
			assertThat(REFERENCIA_NO_ESPERADA, copiaLibros[0], not(sameInstance(libro1)));
			assertThat(OPERACION_NO_REALIZADA, copiaLibros[0], is(libro1));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void insertarDosLibrosValidosInsertaLibrosCorrectamente() {
		Libros libros = new Libros(5);
		try {
			libros.insertar(libro1);
			libros.insertar(libro2);
			Libro[] copiaLibros = libros.get();
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(2));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro1), is(libro1));
			assertThat(REFERENCIA_NO_ESPERADA, copiaLibros[0], not(sameInstance(libro1)));
			assertThat(OPERACION_NO_REALIZADA, copiaLibros[0], is(libro1));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro2), is(libro2));
			assertThat(REFERENCIA_NO_ESPERADA, copiaLibros[1], not(sameInstance(libro2)));
			assertThat(OPERACION_NO_REALIZADA, copiaLibros[1], is(libro2));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void insertarTresLibrosValidosInsertaLibrosCorrectamente() {
		Libros libros = new Libros(5);
		try {
			libros.insertar(libro1);
			libros.insertar(libro2);
			libros.insertar(libro3);
			Libro[] copiaLibros = libros.get();
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(3));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro1), is(libro1));
			assertThat(REFERENCIA_NO_ESPERADA, copiaLibros[0], not(sameInstance(libro1)));
			assertThat(OPERACION_NO_REALIZADA, copiaLibros[0], is(libro1));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro2), is(libro2));
			assertThat(REFERENCIA_NO_ESPERADA, copiaLibros[1], not(sameInstance(libro2)));
			assertThat(OPERACION_NO_REALIZADA, copiaLibros[1], is(libro2));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro3), is(libro3));
			assertThat(REFERENCIA_NO_ESPERADA, copiaLibros[2], not(sameInstance(libro3)));
			assertThat(OPERACION_NO_REALIZADA, copiaLibros[2], is(libro3));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void insertarLibroNuloLanzaExcepcion() {
		Libros libros = new Libros(5);
		try {
			libros.insertar(null);
			fail(LIBRO_NULO);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_INSERTAR_LIBRO_NULO));
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(0));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void insertarLibroRepetidoLanzaExcepcion() {
		Libros libros = new Libros(5);
		try {
			libros.insertar(libro1);
			libros.insertar(libro2);
			libros.insertar(libro3);
			libros.insertar(libroRepetido);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_LIBRO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		libros = new Libros(5);
		try {
			libros.insertar(libro2);
			libros.insertar(libro1);
			libros.insertar(libro3);
			libros.insertar(libroRepetido);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_LIBRO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		libros = new Libros(5);
		try {
			libros.insertar(libro2);
			libros.insertar(libro3);
			libros.insertar(libro1);
			libros.insertar(libroRepetido);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_LIBRO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void insertarLibroValidoConLibrosLlenosLanzaExcepcion() {
		Libros libros = new Libros(2);
		try {
			libros.insertar(libro1);
			libros.insertar(libro2);
			libros.insertar(libro3);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_NO_MAS_LIBROS));
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(2));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro1), is(libro1));
			assertThat(REFERENCIA_NO_ESPERADA, libros.buscar(libro1), not(sameInstance(libro1)));
			assertThat(OPERACION_NO_REALIZADA, libros.get()[0], is(libro1));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro2), is(libro2));
			assertThat(REFERENCIA_NO_ESPERADA, libros.buscar(libro2), not(sameInstance(libro2)));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void borrarLibroExistenteBorraLibroCorrectamente() throws OperationNotSupportedException {
		Libros libros = new Libros(5);
		try {
			libros.insertar(libro1);
			libros.borrar(libro1);
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(0));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro1), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		libros = new Libros(5);
		try {
			libros.insertar(libro1);
			libros.insertar(libro2);
			libros.borrar(libro1);
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(1));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro2), is(libro2));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro1), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		libros = new Libros(5);
		try {
			libros.insertar(libro1);
			libros.insertar(libro2);
			libros.borrar(libro2);
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(1));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro1), is(libro1));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro2), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		libros = new Libros(5);
		try {
			libros.insertar(libro1);
			libros.insertar(libro2);
			libros.insertar(libro3);
			libros.borrar(libro1);
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(2));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro1), is(nullValue()));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro2), is(libro2));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro3), is(libro3));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		libros = new Libros(5);
		try {
			libros.insertar(libro1);
			libros.insertar(libro2);
			libros.insertar(libro3);
			libros.borrar(libro2);
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(2));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro2), is(nullValue()));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro1), is(libro1));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro3), is(libro3));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		libros = new Libros(5);
		try {
			libros.insertar(libro1);
			libros.insertar(libro2);
			libros.insertar(libro3);
			libros.borrar(libro3);
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(2));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro3), is(nullValue()));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro1), is(libro1));
			assertThat(LIBRO_NO_ESPERADO, libros.buscar(libro2), is(libro2));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void borrarLibroNoExistenteLanzaExcepcion() {
		Libros citas = new Libros(5);
		try {
			citas.insertar(libro1);
			citas.borrar(libro2);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_LIBRO_BORRAR_NO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		citas = new Libros(5);
		try {
			citas.insertar(libro1);
			citas.insertar(libro2);
			citas.borrar(libro3);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_LIBRO_BORRAR_NO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(2));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void borrarLibroNuloLanzaExcepcion() {
		Libros libros = new Libros(5);
		try {
			libros.insertar(libro1);
			libros.borrar(null);
			fail(LIBRO_NULO);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_BORRAR_LIBRO_NULO));
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void buscarLibroNuloLanzaExcepcion() {
		Libros libros = new Libros(5);
		try {
			libros.insertar(libro1);
			libros.buscar(null);
			fail(LIBRO_NULO);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_BUSCAR_LIBRO_NULO));
			assertThat(TAMANO_NO_ESPERADO, libros.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}

}
