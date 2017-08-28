package cl.redhat.docugrid;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import cl.redhat.docugrid.model.AttaDocument;
import cl.redhat.docugrid.model.Document;


/**
 * @author David Espinosa
 */

public class DocugridManager {

    private static final String JDG_HOST = "jdg.host";
    // REST specific properties
    public static final String HTTP_PORT = "jdg.http.port";
    public static final String REST_CONTEXT_PATH = "jdg.rest.context.path";

    private static final String PROPERTIES_FILE = "jdg.properties";
    private Console con;
    private RESTCache<String, Object> cache;
    private static final String attaDocument1Key = "attaDocument1";
    private static final String attaDocumentsKey = "attaDocuments";
    
    private static final String initialPrompt = "Choose action:\n" + "============= \n" + "ad  -  add a document\n"
            + "rd  -  remove a document\n"
            + "ud  -  update a document\n"
            + "pd  -  print all documents and versions\n" + "q   -  quit\n";
    private static final String msgEnterDocumentId = "Enter document Id: ";
    private static final String msgDocumentExits = "The specified document \"%s\" exists in the cache, choose next operation\n";
    private static final String msgDocumentNotExits = "The specified document \"%s\" not exists in the cache, choose next operation\n";
    private static final String msgDocumentIsKeyOfDocuments = "The specified document \"%s\" is the key of the list of documents, choose next operation\n";
    private static final String msgNoDocuments = "The cache is empty, choose next operation\n";
    
    public DocugridManager(Console con) {
        this.con = con;
        String contextPath = jdgProperty(REST_CONTEXT_PATH);
        if (contextPath.length() > 0 && !contextPath.startsWith("/")) {
            contextPath = "/" + contextPath;
        }
        cache = new RESTCache<String, Object>("documentCache", "http://" + jdgProperty(JDG_HOST) + ":" + jdgProperty(HTTP_PORT)
                + contextPath + "/");
        
        @SuppressWarnings("unchecked")
		List<String> documents = (List<String>) cache.get(attaDocumentsKey);
        AttaDocument attaDocument = new AttaDocument();
        
        //Si cache esta vacia, se agrega un documento
        if (documents == null) {
        	documents = new ArrayList<String>();
        	attaDocument = generateAttaDocument(attaDocument1Key, attaDocument);
            cache.put(attaDocument.getId(), attaDocument);
            documents.add(attaDocument.getId());
        }
        cache.put(attaDocumentsKey, documents);
    }
    
    public static String jdgProperty(String name) {
        Properties props = new Properties();
        try {
            props.load(DocugridManager.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return props.getProperty(name);
    }
    

    public static void main(String[] args) {
        Console con = System.console();
        DocugridManager manager = new DocugridManager(System.console());
        con.printf(initialPrompt);

        while (true) {
            String action = con.readLine(">");
            if ("ad".equals(action)) {
                manager.addAttaDocument();
            } else if ("rd".equals(action)) {
                manager.removeAttaDocument();
            } else if ("ud".equals(action)) {
                manager.updateAttaDocument();
            } else if ("pd".equals(action)) {
                manager.printDocuments();
            } else if ("q".equals(action)) {
                break;
            }
        }
    }
    
    //Método Prueba no ocupar
	protected AttaDocument generateAttaDocument(String attaDocumentKey, AttaDocument attaDocument){
    	
        Document documento1;
        Document documento2;
        Document documento3;
        ArrayList<Document> versiones = new ArrayList<Document>();
        attaDocument = new AttaDocument();
        
    	documento1 = new Document();
    	documento1.setNombre("redhattest1");
    	documento1.setTipo("pdf");
    	documento1.setUsuario("Davo");
    	documento1.setVersion("1");
    	documento1.setFecha("14/06/2017");
    	
    	documento2 = new Document();
    	documento2.setNombre("redhattest2");
    	documento2.setTipo("pdf");
    	documento2.setUsuario("Davo");
    	documento2.setVersion("2");
    	documento2.setFecha("15/06/2017");
    	
    	documento3 = new Document();
    	documento3.setNombre("redhattest3");
    	documento3.setTipo("pdf");
    	documento3.setUsuario("Davo");
    	documento3.setVersion("3");
    	documento3.setFecha("16/06/2017");
		
        File file=new File ("/home/luz/atta/docugrid/testcases/redhattest1.pdf"); 
		byte[] bytes=null;
		try {
			bytes = Files.readAllBytes(file.toPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}   
	    String filestring=new String(Base64.getEncoder().encode(bytes));
	    documento1.setBin(filestring);
	    documento2.setBin(filestring);
	    documento3.setBin(filestring);
		 
		//System.out.println(documento1.toString());
		//System.out.println(documento2.toString());
		//System.out.println(documento3.toString());
		
		//System.out.println("+++");
		//System.out.println("Documentos generados correctamente");
		//System.out.println("+++");
		
		versiones.add(documento1);
		versiones.add(documento2);
		versiones.add(documento3);
		
		//System.out.println("+++");
		//System.out.println("Documentos agregados correctamente al arreglo de versiones");
		//System.out.println("+++");
		
		attaDocument.setId(attaDocumentKey);
		attaDocument.setVersiones(versiones);
		
		//System.out.println("attaDocument generado correctamente: " + attaDocument.getId());
		return attaDocument;

    }
    
	//Metodo Ingresar Documento al cache
    public void addAttaDocument() {
        String documentID = con.readLine(msgEnterDocumentId);

        //Si documento que se intenta agregar es igual al key del objeto que almacena los ids de los docs, no agrega
        if (attaDocumentsKey == documentID){
        	con.printf(msgDocumentIsKeyOfDocuments, documentID);
        } else {
            @SuppressWarnings("unchecked")
			List<String> documents = (List<String>) cache.get(encode(attaDocumentsKey));
            if (documents == null) {
            	documents = new ArrayList<String>();
            }
            
            AttaDocument attaDocument = (AttaDocument) cache.get(encode(documentID));
            if (attaDocument == null) {
            	attaDocument = new AttaDocument();
                attaDocument = generateAttaDocument(documentID, attaDocument);
                cache.put(encode(documentID), attaDocument);
                documents.add(attaDocument.getId());
            } else {
            	 con.printf(msgDocumentExits, documentID);
            }
            cache.put(attaDocumentsKey, documents);
        }
    }
    
    private void removeAttaDocument() {
    	String documentID = con.readLine(msgEnterDocumentId);
    	
        //Si documento que se intenta agregar es igual al key del objeto que almacena los ids de los docs, no lo elimina
        if (attaDocumentsKey == documentID){
        	con.printf(msgDocumentIsKeyOfDocuments, documentID);
        } else {
            @SuppressWarnings("unchecked")
			List<String> documents = (List<String>) cache.get(encode(attaDocumentsKey));
            if (documents == null) {
            	con.printf(msgNoDocuments, documentID);
            } else {                
                AttaDocument attaDocument = (AttaDocument) cache.get(encode(documentID));
                if (attaDocument != null) {
                	cache.remove(documentID);

                	if (documents != null) {
                		documents.remove(documentID);
                	}
                	cache.put(attaDocumentsKey, documents);
                } else {
                	con.printf(msgDocumentNotExits, documentID);
                }
            }
            
        }
	}

	private void updateAttaDocument() {
        String documentID = con.readLine(msgEnterDocumentId);

        //Si documento que se intenta modificar es igual al key del objeto que almacena los ids de los docs, no modifica
        if (attaDocumentsKey == documentID){
        	con.printf(msgDocumentIsKeyOfDocuments, documentID);
        } else {
            @SuppressWarnings("unchecked")
			List<String> documents = (List<String>) cache.get(encode(attaDocumentsKey));
            if (documents == null) {
            	con.printf(msgNoDocuments, documentID);
            } else {                
                AttaDocument attaDocument = (AttaDocument) cache.get(encode(documentID));
                if (attaDocument != null) {
                    attaDocument = updateAttaDocument(documentID, attaDocument);
                    cache.put(encode(documentID), attaDocument);
                } else {
                	 con.printf(msgDocumentNotExits, documentID);
                }
            }
        }
	}
    
	//Metodo obtener Documento del cache
    public AttaDocument getAttaDocument(String documentID) {
        //Si documento que se intenta agregar es igual al key del objeto que almacena los ids de los docs, no agrega
        if (attaDocumentsKey == documentID){
        	con.printf(msgDocumentIsKeyOfDocuments, documentID);
        	return null;
        } else {
            @SuppressWarnings("unchecked")
			List<String> documents = (List<String>) cache.get(encode(attaDocumentsKey));
            if (documents == null) {
            	con.printf(msgNoDocuments, documentID);
            	return null;
            } else {
                AttaDocument attaDocument = (AttaDocument) cache.get(encode(documentID));
                if (attaDocument != null) {
                	return attaDocument;
                } else {
                	 con.printf(msgDocumentNotExits, documentID);
                	 return null;
                }
            }
        }
    }
    
    public void printDocuments() {
        @SuppressWarnings("unchecked")
        List<String> documents = (List<String>) cache.get(attaDocumentsKey);
        if (documents != null) {
            for (String documentID : documents) {
            	AttaDocument attaDocument = (AttaDocument) cache.get(encode(documentID));
            	ArrayList<Document> versiones = attaDocument.getVersiones();
                con.printf("Documento: " + attaDocument.getId() + " tiene " + versiones.size() + " versiones\n" );
                for (Document version: versiones){
                	con.printf("    Version " + version.getVersion() + "\n");
                	con.printf("           Nombre: " + version.getNombre() + "\n");
                	con.printf("           Tipo: " + version.getTipo() + "\n");
                	con.printf("           Usuario: " + version.getUsuario() + "\n");
                	con.printf("           Fecha:" + version.getFecha() + "\n");
                }
            }
        }
    }
    
    //Metodo Prueba no ocupar, es solo para formato
    public static String encode(String key) {
        try {
            return URLEncoder.encode(key, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    //Metodo Prueba no ocupar
	protected AttaDocument updateAttaDocument(String attaDocumentKey, AttaDocument attaDocument){
    	
        Document documento1;
        Document documento2;
        Document documento3;
        Document documento4;
        ArrayList<Document> versiones = new ArrayList<Document>();
        attaDocument = new AttaDocument();
        
    	documento1 = new Document();
    	documento1.setNombre("redhattest1");
    	documento1.setTipo("pdf");
    	documento1.setUsuario("Davo");
    	documento1.setVersion("1");
    	documento1.setFecha("14/06/2017");
    	
    	documento2 = new Document();
    	documento2.setNombre("redhattest2");
    	documento2.setTipo("pdf");
    	documento2.setUsuario("Davo");
    	documento2.setVersion("2");
    	documento2.setFecha("15/06/2017");
    	
    	documento3 = new Document();
    	documento3.setNombre("redhattest3");
    	documento3.setTipo("pdf");
    	documento3.setUsuario("Davo");
    	documento3.setVersion("3");
    	documento3.setFecha("16/06/2017");
    	
    	documento4 = new Document();
    	documento4.setNombre("redhattest4");
    	documento4.setTipo("pdf");
    	documento4.setUsuario("Davo");
    	documento4.setVersion("4");
    	documento4.setFecha("17/06/2017");
		
        File file=new File ("/home/luz/atta/docugrid/testcases/redhattest1.pdf"); 
		byte[] bytes=null;
		try {
			bytes = Files.readAllBytes(file.toPath());
		} catch (IOException e1) {
			e1.printStackTrace();
		}   
	    String filestring=new String(Base64.getEncoder().encode(bytes));
	    documento1.setBin(filestring);
	    documento2.setBin(filestring);
	    documento3.setBin(filestring);
	    documento4.setBin(filestring);
		 
		//System.out.println(documento1.toString());
		//System.out.println(documento2.toString());
		//System.out.println(documento3.toString());
		
		//System.out.println("+++");
		//System.out.println("Documentos generados correctamente");
		//System.out.println("+++");
		
		versiones.add(documento1);
		versiones.add(documento2);
		versiones.add(documento3);
		versiones.add(documento4);
		
		//System.out.println("+++");
		//System.out.println("Documentos agregados correctamente al arreglo de versiones");
		//System.out.println("+++");
		
		attaDocument.setId(attaDocumentKey);
		attaDocument.setVersiones(versiones);
		
		//System.out.println("attaDocument generado correctamente: " + attaDocument.getId());
		return attaDocument;

    }
}


