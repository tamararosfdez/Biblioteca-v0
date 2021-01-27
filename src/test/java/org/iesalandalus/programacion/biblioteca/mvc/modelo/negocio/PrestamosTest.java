package org.iesalandalus.programacion.biblioteca.mvc.modelo.negocio;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.time.LocalDate;

import javax.naming.OperationNotSupportedException;

import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Alumno;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Curso;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Prestamo;
import org.junit.BeforeClass;
import org.junit.Test;

public class PrestamosTest {

	private static final String ERROR_CAPACIDAD_NO_CORRECTA = "ERROR: La capacidad debe ser mayor que cero.";
	private static final String ERROR_PRESTAR_PRESTAMO_NULO = "ERROR: No se puede prestar un préstamo nulo.";
	private static final String ERROR_DEVOLVER_PRESTAMO_NULO = "ERROR: No se puede devolver un préstamo nulo.";
	private static final String ERROR_ALUMNO_NULO = "ERROR: El alumno no puede ser nulo.";
	private static final String ERROR_LIBRO_NULO = "ERROR: El libro no puede ser nulo.";
	private static final String ERROR_FECHA_NULA = "ERROR: La fecha no puede ser nula.";
	private static final String ERROR_BORRAR_PRESTAMO_NULO = "ERROR: No se puede borrar un préstamo nulo.";
	private static final String ERROR_BUSCAR_PRESTAMO_NULO = "ERROR: No se puede buscar un préstamo nulo.";
	private static final String ERROR_NO_MAS_PRESTAMOS = "ERROR: No se aceptan más préstamos.";
	private static final String ERROR_PRESTAMO_EXISTE = "ERROR: Ya existe un préstamo igual.";
	private static final String ERROR_PRESTAMO_NO_EXISTE = "ERROR: No existe ningún préstamo igual.";
	private static final String OPERACION_NO_PERMITIDA = "Debería haber saltado una excepción indicando que dicha operación no está permitida.";
	private static final String PRESTAMO_NULO = "Debería haber saltado una excepción indicando que no se puede operar con un préstamo nulo.";
	private static final String ALUMNO_NULO = "Debería haber saltado una excepción indicando que no se puede operar con un alumno nulo.";
	private static final String LIBRO_NULO = "Debería haber saltado una excepción indicando que no se puede operar con un libro nulo.";
	private static final String FECHA_NULA = "Debería haber saltado una excepción indicando que no se puede operar con una fecha nula.";
	private static final String MENSAJE_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
	private static final String TIPO_NO_CORRECTO = "El tipo de la excepción no es correcto.";
	private static final String EXCEPCION_NO_PROCEDE = "No debería haber saltado la excepción.";
	private static final String OPERACION_NO_REALIZADA = "La operación no la ha realizado correctamente.";
	private static final String PRESTAMOS_NO_CREADOS = "Debería haber creado los préstamos correctamente.";
	private static final String REFERENCIA_NO_ESPERADA = "La referencia devuelta es la misma que la pasada.";
	private static final String TAMANO_NO_ESPERADO = "El tamaño devuelto no es el esperado.";
	private static final String PRESTAMO_NO_ESPERADO = "El préstamo devuelto no es el que debería ser.";
	private static final String OBJETO_DEBERIA_SER_NULO = "No se debería haber creado el objeto.";
	
	private static Prestamo prestamo1;
	private static Prestamo prestamo2;
	private static Prestamo prestamo3;
	private static Prestamo prestamo4;
	private static Prestamo prestamoRepetido;
	private static LocalDate hoy = LocalDate.now();
	private static LocalDate mesPasado = hoy.minusMonths(1);
	
	@BeforeClass
	public static void asignarValoresAtributos() {
		Libro libro1 = new Libro("Cien años de soledad", "Gabriel García Márquez", 471);
		Libro libro2 = new Libro("El retrato de Dorian Gray", "Oscar Wilde", 275);
		Alumno alumno1 = new Alumno("Bob Esponja", "bob@gmail.com", Curso.PRIMERO);
		Alumno alumno2 = new Alumno("Patricio Estrella", "patricio@gmail.com", Curso.SEGUNDO);
		prestamo1 = new Prestamo(alumno1, libro1, hoy);
		prestamo2 = new Prestamo(alumno1, libro2, mesPasado);
		prestamo3 = new Prestamo(alumno2, libro2, hoy);
		prestamo4 = new Prestamo(alumno2, libro1, hoy.minusYears(1));
		prestamoRepetido = new Prestamo(prestamo1);
	}
	
	@Test
	public void constructorCapacidadValidaCreaPrestamosCorrectamente() {
		Prestamos prestamos = new Prestamos(5);
		assertThat(PRESTAMOS_NO_CREADOS, prestamos, not(nullValue()));
		assertThat(PRESTAMOS_NO_CREADOS, prestamos.getCapacidad(), is(5));
		assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(0));
	}
	
	@Test
	public void constructorCapacidadNoValidaLanzaExcepcion() {
		Prestamos prestamos = null;
		try {
			prestamos = new Prestamos(0);
			fail(OPERACION_NO_PERMITIDA);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CAPACIDAD_NO_CORRECTA));
			assertThat(OBJETO_DEBERIA_SER_NULO, prestamos, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		try {
			prestamos = new Prestamos(-1);
			fail(OPERACION_NO_PERMITIDA);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_CAPACIDAD_NO_CORRECTA));
			assertThat(OBJETO_DEBERIA_SER_NULO, prestamos, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void prestarPrestamoValidoConPrestamosVaciosRealizaPrestamoCorrectamente() {
		Prestamos prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo1);
			Prestamo[] copiaPrestamos = prestamos.get();
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(1));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo1), is(prestamo1));
			assertThat(REFERENCIA_NO_ESPERADA, copiaPrestamos[0], not(sameInstance(prestamo1)));
			assertThat(OPERACION_NO_REALIZADA, copiaPrestamos[0], is(prestamo1));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void prestarDosPrestamosValidosRealizaPrestamosCorrectamente() {
		Prestamos prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamo2);
			Prestamo[] copiaPrestamos = prestamos.get();
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(2));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo1), is(prestamo1));
			assertThat(REFERENCIA_NO_ESPERADA, copiaPrestamos[0], not(sameInstance(prestamo1)));
			assertThat(OPERACION_NO_REALIZADA, copiaPrestamos[0], is(prestamo1));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo2), is(prestamo2));
			assertThat(REFERENCIA_NO_ESPERADA, copiaPrestamos[1], not(sameInstance(prestamo2)));
			assertThat(OPERACION_NO_REALIZADA, copiaPrestamos[1], is(prestamo2));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void prestarTresPrestamosValidosRealizaPrestamosCorrectamente() {
		Prestamos prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamo2);
			prestamos.prestar(prestamo3);
			Prestamo[] copiaPrestamos = prestamos.get();
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(3));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo1), is(prestamo1));
			assertThat(REFERENCIA_NO_ESPERADA, copiaPrestamos[0], not(sameInstance(prestamo1)));
			assertThat(OPERACION_NO_REALIZADA, copiaPrestamos[0], is(prestamo1));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo2), is(prestamo2));
			assertThat(REFERENCIA_NO_ESPERADA, copiaPrestamos[1], not(sameInstance(prestamo2)));
			assertThat(OPERACION_NO_REALIZADA, copiaPrestamos[1], is(prestamo2));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo3), is(prestamo3));
			assertThat(REFERENCIA_NO_ESPERADA, copiaPrestamos[2], not(sameInstance(prestamo3)));
			assertThat(OPERACION_NO_REALIZADA, copiaPrestamos[2], is(prestamo3));
		} catch (OperationNotSupportedException e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void prestarPrestamoNuloLanzaExcepcion() {
		Prestamos prestamos = new Prestamos(5);
		try {
			prestamos.prestar(null);
			fail(PRESTAMO_NULO);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_PRESTAR_PRESTAMO_NULO));
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(0));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void prestarPrestamoRepetidoLanzaExcepcion() {
		Prestamos prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamo2);
			prestamos.prestar(prestamo3);
			prestamos.prestar(prestamoRepetido);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_PRESTAMO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo2);
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamo3);
			prestamos.prestar(prestamoRepetido);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_PRESTAMO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo2);
			prestamos.prestar(prestamo3);
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamoRepetido);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_PRESTAMO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(3));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void prestarPrestamoValidoConPrestamosLlenosLanzaExcepcion() {
		Prestamos prestamos = new Prestamos(2);
		try {
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamo2);
			prestamos.prestar(prestamo3);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_NO_MAS_PRESTAMOS));
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(2));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo1), is(prestamo1));
			assertThat(REFERENCIA_NO_ESPERADA, prestamos.buscar(prestamo1), not(sameInstance(prestamo1)));
			assertThat(OPERACION_NO_REALIZADA, prestamos.get()[0], is(prestamo1));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo2), is(prestamo2));
			assertThat(REFERENCIA_NO_ESPERADA, prestamos.buscar(prestamo2), not(sameInstance(prestamo2)));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void getAlumnoValidoDevuelvePrestamosAlumno() {
		Prestamos prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamo2);
			prestamos.prestar(prestamo3);
			Prestamo[] prestamosAlumno = prestamos.get(Alumno.getAlumnoFicticio("bob@gmail.com"));
			assertThat(OPERACION_NO_REALIZADA, prestamosAlumno[0], is(prestamo1));
			assertThat(REFERENCIA_NO_ESPERADA, prestamosAlumno[0], not(sameInstance(prestamo1)));
			assertThat(OPERACION_NO_REALIZADA, prestamosAlumno[1], is(prestamo2));
			assertThat(REFERENCIA_NO_ESPERADA, prestamosAlumno[1], not(sameInstance(prestamo2)));
			assertThat(OBJETO_DEBERIA_SER_NULO, prestamosAlumno[2], is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void getAlumnoNuloLanzaExcepcion() {
		Prestamos prestamos = new Prestamos(5);
		Prestamo[] prestamosAlumno = null;
		try {
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamo2);
			prestamos.prestar(prestamo3);
			Alumno alumno = null;
			prestamosAlumno = prestamos.get(alumno);
			fail(ALUMNO_NULO);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_ALUMNO_NULO));
			assertThat(OBJETO_DEBERIA_SER_NULO, prestamosAlumno, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void getLibroValidoDevuelvePrestamosLibro() {
		Prestamos prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamo2);
			prestamos.prestar(prestamo3);
			Prestamo[] prestamosLibro = prestamos.get(new Libro("El retrato de Dorian Gray", "Oscar Wilde", 275));
			assertThat(OPERACION_NO_REALIZADA, prestamosLibro[0], is(prestamo2));
			assertThat(REFERENCIA_NO_ESPERADA, prestamosLibro[0], not(sameInstance(prestamo2)));
			assertThat(OPERACION_NO_REALIZADA, prestamosLibro[1], is(prestamo3));
			assertThat(REFERENCIA_NO_ESPERADA, prestamosLibro[1], not(sameInstance(prestamo3)));
			assertThat(OBJETO_DEBERIA_SER_NULO, prestamosLibro[2], is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void getLibroNuloLanzaExcepcion() {
		Prestamos prestamos = new Prestamos(5);
		Prestamo[] prestamosLibro = null;
		try {
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamo2);
			prestamos.prestar(prestamo3);
			Libro libro = null;
			prestamosLibro = prestamos.get(libro);
			fail(LIBRO_NULO);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_LIBRO_NULO));
			assertThat(OBJETO_DEBERIA_SER_NULO, prestamosLibro, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void getFechaValidaDevuelvePrestamosMes() {
		Prestamos prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamo2);
			prestamos.prestar(prestamo3);
			prestamos.prestar(prestamo4);
			Prestamo[] prestamosMes = prestamos.get(hoy);
			assertThat(OPERACION_NO_REALIZADA, prestamosMes[0], is(prestamo1));
			assertThat(REFERENCIA_NO_ESPERADA, prestamosMes[0], not(sameInstance(prestamo1)));
			assertThat(OPERACION_NO_REALIZADA, prestamosMes[1], is(prestamo3));
			assertThat(REFERENCIA_NO_ESPERADA, prestamosMes[1], not(sameInstance(prestamo3)));
			assertThat(OBJETO_DEBERIA_SER_NULO, prestamosMes[2], is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void getFechaNulaLanzaExcepcion() {
		Prestamos prestamos = new Prestamos(5);
		Prestamo[] prestamosFecha = null;
		try {
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamo2);
			prestamos.prestar(prestamo3);
			LocalDate fecha = null;
			prestamosFecha = prestamos.get(fecha);
			fail(FECHA_NULA);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_FECHA_NULA));
			assertThat(OBJETO_DEBERIA_SER_NULO, prestamosFecha, is(nullValue()));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void devolverPrestamoExistenteDevuelvePrestamoCorrectamente() {
		Prestamos prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo2);
			prestamos.devolver(prestamo2, hoy);
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo2).getFechaDevolucion(), is(hoy));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void devolverPrestamoNuloLanzaException() {
		Prestamos prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo2);
			prestamos.devolver(null, hoy);
			fail(OPERACION_NO_PERMITIDA);
		} catch (NullPointerException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_DEVOLVER_PRESTAMO_NULO));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void devolverPrestamoNoExistenteLanzaExcepcion() {
		Prestamos prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo2);
			prestamos.devolver(prestamo1, hoy);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_PRESTAMO_NO_EXISTE));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void borrarPrestamoExistenteBorraPrestamoCorrectamente() throws OperationNotSupportedException {
		Prestamos prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo1);
			prestamos.borrar(prestamo1);
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(0));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo1), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamo2);
			prestamos.borrar(prestamo1);
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(1));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo2), is(prestamo2));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo1), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamo2);
			prestamos.borrar(prestamo2);
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(1));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo1), is(prestamo1));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo2), is(nullValue()));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamo2);
			prestamos.prestar(prestamo3);
			prestamos.borrar(prestamo1);
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(2));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo1), is(nullValue()));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo2), is(prestamo2));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo3), is(prestamo3));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamo2);
			prestamos.prestar(prestamo3);
			prestamos.borrar(prestamo2);
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(2));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo2), is(nullValue()));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo1), is(prestamo1));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo3), is(prestamo3));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
		prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo1);
			prestamos.prestar(prestamo2);
			prestamos.prestar(prestamo3);
			prestamos.borrar(prestamo3);
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(2));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo3), is(nullValue()));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo1), is(prestamo1));
			assertThat(PRESTAMO_NO_ESPERADO, prestamos.buscar(prestamo2), is(prestamo2));
		} catch (Exception e) {
			fail(EXCEPCION_NO_PROCEDE);
		}
	}
	
	@Test
	public void borrarPrestamoNoExistenteLanzaExcepcion() {
		Prestamos citas = new Prestamos(5);
		try {
			citas.prestar(prestamo1);
			citas.borrar(prestamo2);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_PRESTAMO_NO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
		citas = new Prestamos(5);
		try {
			citas.prestar(prestamo1);
			citas.prestar(prestamo2);
			citas.borrar(prestamo3);
			fail(OPERACION_NO_PERMITIDA);
		} catch (OperationNotSupportedException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_PRESTAMO_NO_EXISTE));
			assertThat(TAMANO_NO_ESPERADO, citas.getTamano(), is(2));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void borrarPrestamoNuloLanzaExcepcion() {
		Prestamos prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo1);
			prestamos.borrar(null);
			fail(PRESTAMO_NULO);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_BORRAR_PRESTAMO_NULO));
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}
	
	@Test
	public void buscarPrestamoNuloLanzaExcepcion() {
		Prestamos prestamos = new Prestamos(5);
		try {
			prestamos.prestar(prestamo1);
			prestamos.buscar(null);
			fail(PRESTAMO_NULO);
		} catch (IllegalArgumentException e) {
			assertThat(MENSAJE_NO_CORRECTO, e.getMessage(), is(ERROR_BUSCAR_PRESTAMO_NULO));
			assertThat(TAMANO_NO_ESPERADO, prestamos.getTamano(), is(1));
		} catch (Exception e) {
			fail(TIPO_NO_CORRECTO);
		}
	}

}
