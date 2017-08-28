package cl.redhat.docugrid.jdg;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import cl.redhat.docugrid.main.DocugridResponse;
import cl.redhat.docugrid.main.Document;
import cl.redhat.docugrid.main.RedhatDocument;

/**
 * Envia las peticiones a JDG
 * 
 * @author Luz Mestre
 * 
 * 
 */
@SuppressWarnings("unchecked")
public class JDGProcessor {
	private RESTCache<String, Object> cache;
	public static final String REST_CONTEXT_PATH = "jdg.rest.context.path";
	private static final String PROPERTIES_FILE = "jdg.properties";
	private static final String JDG_HOST = "jdg.host";
	public static final String HTTP_PORT = "jdg.http.port";
	private static final String redhatIds = "redhatIds";

	public static String jdgProperty(String name) {
		Properties props = new Properties();

		try {
			InputStream inputStream = new FileInputStream(System.getProperty("jboss.home.dir") + "/" + PROPERTIES_FILE);

			props.load(inputStream);
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		return props.getProperty(name);
	}

	public JDGProcessor() {
		String contextPath = jdgProperty(REST_CONTEXT_PATH);
		if (contextPath.length() > 0 && !contextPath.startsWith("/")) {
			contextPath = "/" + contextPath;
		}
		cache = new RESTCache<String, Object>("documentCache",
				"http://" + jdgProperty(JDG_HOST) + ":" + jdgProperty(HTTP_PORT) + contextPath + "/");
	}

	public String eliminar(DocugridResponse docugridResponse) {
		JSONObject obj = new JSONObject();
		synchronized (redhatIds) {
			try {

				List<java.lang.String> documents = (List<java.lang.String>) cache.get(redhatIds);

				RedhatDocument redhatDocumentCache = (RedhatDocument) cache.get(docugridResponse.getId());
				if (redhatDocumentCache != null) {
					documents.remove(docugridResponse.getId());
					cache.replace(redhatIds, documents);
					cache.remove(docugridResponse.getId());
					obj.put("result", "ok");
				} else {
					obj.put("result", "error");
					obj.put("errormessage", "El documento no existe");

				}
			} catch (Exception e) {
				obj.put("result", "error");
				if (e.getMessage() == null) {
					obj.put("errormessage", "Error al eliminar documento");
				} else {
					obj.put("errormessage", e.getMessage());
				}
			}
		}
		return obj.toString();

	}

	public String leer(DocugridResponse docugridResponse) {

		JSONObject obj = new JSONObject();

		try {
			RedhatDocument redhatDocument = (RedhatDocument) cache.get(docugridResponse.getId());

			if (redhatDocument != null) {
				Document d = redhatDocument.getUltimaVersion();

				obj.put("bin", d.getBin());
				obj.put("result", "ok");
				obj.put("filename", d.getFilename());
				if (redhatDocument != null) {
					return obj.toString();
				}
			} else {

				obj.put("result", "error");
				obj.put("errormessage", "El documento no existe");
			}
		} catch (Exception e) {
			obj.put("result", "error");

			if (e.getMessage() == null) {
				obj.put("errormessage", "Error al leer documento");
			} else {
				obj.put("errormessage", e.getMessage());
			}
		}
		return obj.toString();

	}

	public String leerversiones(DocugridResponse docugridResponse) {
		JSONObject obj = new JSONObject();

		synchronized (redhatIds) {

			try {
				RedhatDocument redhatDocument = (RedhatDocument) cache.get(docugridResponse.getId());

				JSONArray jsonArray = new JSONArray();

				if (redhatDocument != null) {
					for (int i = 0; i < redhatDocument.getVersiones().size(); i++) {
						JSONObject obj_i = new JSONObject();
						Document d = redhatDocument.getVersiones().get(i);

						obj_i.put("bin", d.getBin());

						obj_i.put("version", new Integer(i + 1).toString());
						obj_i.put("filename", d.getFilename());

						jsonArray.add(obj_i);
					}
					obj.put("result", "ok");
					obj.put("versiones", jsonArray);

					return obj.toString();

				}

				obj.put("result", "error");
				obj.put("errormessage", "El documento no existe");
			} catch (Exception e) {
				obj.put("result", "error");

				if (e.getMessage() == null) {
					obj.put("errormessage", "Error al leer versiones del documento");
				} else {
					obj.put("errormessage", e.getMessage());
				}
			}
		}
		return obj.toString();

	}

	public String ingresar(Document documento) {
		JSONObject obj = new JSONObject();
		synchronized (redhatIds) {

			String key = UUID.randomUUID().toString();
			try {
				List<String> documents = (List<String>) cache.get(redhatIds);
				if (documents == null) {
					documents = new ArrayList<String>();

				}
				try {
					byte[] validar = Base64.getDecoder().decode(documento.getBin());
					documents.add(key);
					cache.replace(redhatIds, documents);
					RedhatDocument redhatDocument = new RedhatDocument();
					redhatDocument.setId(key);
					redhatDocument.getVersiones().add(documento);
					cache.put(key, redhatDocument);

					obj.put("id", key);
					obj.put("result", "ok");

				} catch (Exception e) {
					obj.put("result", "error");
					if (e.getMessage() == null) {
						obj.put("errormessage", "El campo bin no esta en formato Base64");
					} else {
						obj.put("errormessage", e.getMessage());
					}

				}
			} catch (Exception e) {
				obj.put("result", "error");
				if (e.getMessage() == null) {
					obj.put("errormessage", "Error al ingresar documento");
				} else {
					obj.put("errormessage", e.getMessage());
				}
			}
		}
		return obj.toString();
	}

	public String actualizar(DocugridResponse docugridResponse) {
		JSONObject obj = new JSONObject();
		synchronized (redhatIds) {


			try {

				String key = docugridResponse.getId();
				RedhatDocument redhatDocumentCache = (RedhatDocument) cache.get(key);
				if (redhatDocumentCache != null) {
					try {
						byte[] validar = Base64.getDecoder().decode(docugridResponse.getBin());

						Document document = new Document();
						document.setBin(docugridResponse.getBin());
						//en caso de que no venga el nombre del archivo se utiliza el de la ultima version
						if ((docugridResponse.getFilename()) == null) {
							docugridResponse.setFilename("");
						}
						if ((docugridResponse.getFilename()).length() == 0) {

							RedhatDocument redhatDocument = (RedhatDocument) cache.get(docugridResponse.getId());

							if (redhatDocument != null) {
								Document d = redhatDocument.getUltimaVersion();
								docugridResponse.setFilename(d.getFilename());
							}

						}
						//ya se tiene el nombre del archivo
						
						
						document.setFilename(docugridResponse.getFilename());
						redhatDocumentCache.getVersiones().add(document);
						cache.replace(key, redhatDocumentCache);
						obj.put("result", "ok");
					} catch (Exception e) {
						obj.put("result", "error");
						if (e.getMessage() == null) {
							obj.put("errormessage", "El campo bin no esta en formato Base64");
						} else {
							obj.put("errormessage", e.getMessage());
						}

					}

				} else {

					obj.put("result", "error");
					obj.put("errormessage", "El documento no existe");
				}

			} catch (Exception e) {
				obj.put("result", "error");
				if (e.getMessage() == null) {
					obj.put("errormessage", "Error al actualizar documento");
				} else {
					obj.put("errormessage", e.getMessage());
				}
				e.printStackTrace();
			}
		}
		return obj.toString();
	}

	public String printcache() {
		JSONObject obj = new JSONObject();
		String str = "";

		synchronized (redhatIds) {

			try {

				List<String> documents = (List<String>) cache.get(redhatIds);

				if (documents == null) {
					obj.put("result", "error");
					obj.put("errormessage", "Cache vacio");
					str = obj.toString();
				} else {
					for (int i = 0; i < documents.size(); i++) {
						str = str + "\n " + i + ": 	" + documents.get(i);
					}
				}
			} catch (Exception e) {
				obj.put("result", "error");
				if (e.getMessage() == null) {
					obj.put("errormessage", "Error al imprimir cache");
				} else {
					obj.put("errormessage", e.getMessage());
				}
				str = obj.toString();
			}
		}
		return str;

	}

	public String eliminarcache() {
		JSONObject obj = new JSONObject();

		synchronized (redhatIds) {

			try {
				List<String> documents = (List<String>) cache.get(redhatIds);
				if (documents == null) {
					obj.put("errormessage", "cache nulo");
				} else {
					int size = documents.size();

					for (int i = 0; i < size; i++) {
						String key = "";
						try {

							key = documents.get(i);
							try {
								cache.remove(key);

							} catch (Exception e) {
								if (e.getMessage() == null) {
									obj.put("errormessage", "Error al eliminar cache");
								} else {
									obj.put("errormessage", e.getMessage());
								}

							}

						} catch (Exception e) {
							obj.put("errormessage", "ERROR key" + key + " i" + i);

						}

					}
					cache.remove(redhatIds);
				}

				obj.put("result", "ok");
			} catch (Exception e) {
				obj.put("result", "error");
				if (e.getMessage() == null) {
					obj.put("errormessage", "Error al eliminar cache");
				} else {
					obj.put("errormessage", e.getMessage());
				}
			}

		}
		return obj.toString();

	}

}
