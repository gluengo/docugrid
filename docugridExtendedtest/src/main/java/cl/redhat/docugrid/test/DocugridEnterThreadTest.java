package cl.redhat.docugrid.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import cl.redhat.docugrid.main.Document;
import cl.redhat.docugrid.util.DocugridUtil;

/**
 * Clase de prueba para ingresar documento Se debe modificar la ruta al archivo
 * 
 * @author Luz Mestre
 *
 */
public class DocugridEnterThreadTest extends Thread {

	public static void main(String[] args) {
		DocugridEnterThreadTest4 obj4 = new DocugridEnterThreadTest4();
		obj4.start();
		DocugridEnterThreadTest2 obj2 = new DocugridEnterThreadTest2();
		obj2.start();
		DocugridEnterThreadTest3 obj3 = new DocugridEnterThreadTest3();
		obj3.start();

	}
}
