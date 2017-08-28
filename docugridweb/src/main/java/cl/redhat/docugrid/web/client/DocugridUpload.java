package cl.redhat.docugrid.web.client;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import cl.redhat.docugrid.main.DocugridResponse;
import cl.redhat.docugrid.main.Document;
import cl.redhat.docugrid.util.DocugridUtil;

/**
 * DocugridUpload: ingresa un nuevo documento y lo actualiza.
 * 
 * @author Luz Mestre
 *
 */
public class DocugridUpload extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5380079853410189210L;

	/*
	 * Metodo post, recibe los parametros del formulario multipart y procesa el
	 * ingreso o actualizacion de un documento.
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String operacion = "";

		String id = "";
		String nofile = "";
		String filename = "";
		InputStream is = null;
		if (ServletFileUpload.isMultipartContent(req)) {
			try {
				List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(req);
				for (FileItem item : multiparts) {
					if (!item.isFormField()) {

						String name = new File(item.getName()).getName();
						if (name.length() == 0) {
							nofile = "nofile";

						} else {
							is = item.getInputStream();
							filename = item.getName();

						}
					} else {

						String fieldName = item.getFieldName();
						String fieldValue = item.getString();

						if (fieldName.equals("operacion")) {

							if (fieldValue.equals("ingresar") && nofile.equals("nofile")) {

								req.setAttribute("resultingresar", "(*) Debe seleccionar un archivo.");
							}

							if (fieldValue.equals("actualizar") && nofile.equals("nofile")) {

								req.setAttribute("resultactualizar", "(*) Debe seleccionar un archivo.");
							}
							operacion = fieldValue;
						}
						if (fieldName.equals("id")) {
							id = fieldValue;
							if (id.length() == 0) {
								req.setAttribute("resultactualizar", "(*) Debe ingresar un Id de documento.");

							}
						}
					}
				}

			} catch (Exception ex) {
				req.setAttribute("resultingresar", "(*) Error al subir archivo.");

			}
		} else {
			req.setAttribute("resultingresar", "(*) Error al subir archivo.");

		}

		if (operacion.equals("ingresar") && !nofile.equals("nofile")) {

			ingresar(req, resp, is, filename);
		}
		if (operacion.equals("actualizar") && nofile.equals("nofile")) {
			req.setAttribute("resultactualizar", "(*) Debe seleccionar un archivo.");

		} else {

			if (operacion.equals("actualizar") && !nofile.equals("nofile") && id.length() > 0) {

				actualizar(req, resp, is, id, filename);

			}
		}
		req.getRequestDispatcher("/index.jsp").forward(req, resp);
	}

	/**
	 * Ingresa un documento al repositorio
	 * 
	 * @param req
	 * @param resp
	 * @param is
	 * @throws IOException
	 * @throws ServletException
	 */
	private void ingresar(HttpServletRequest req, HttpServletResponse resp, InputStream is, String filename)
			throws IOException, ServletException {

		String urlRest = DocugridUtil.docugridProperty("urlingresar");
		Document document = new Document();

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();

		byte[] bytes = buffer.toByteArray();

		String filestring = new String(Base64.getEncoder().encode(bytes));

		document.setBin(filestring);
		document.setFilename(filename);

		String respuesta = DocugridUtil.insertarDocumento(document, urlRest);

		JSONParser parser = new JSONParser();
		JSONObject obj;
		String message = "";
		try {
			obj = (JSONObject) parser.parse(respuesta);
			String result = (String) obj.get("result");
			if (result.equals("error")) {
				message = (String) obj.get("errormessage");
				req.setAttribute("resultingresar", "(*)" + message + ".");

			} else {
				message = (String) obj.get("id");
				req.setAttribute("resultingresar", "(*) El documento " + message + " ha sido ingresado exitosamente.");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return;
	}

	/**
	 * Actualiza un documento
	 * 
	 * @param req
	 * @param resp
	 * @param is
	 * @param id
	 * @throws IOException
	 * @throws ServletException
	 */
	private void actualizar(HttpServletRequest req, HttpServletResponse resp, InputStream is, String id,
			String filename) throws IOException, ServletException {

		String urlRest = DocugridUtil.docugridProperty("urlactualizar");
		DocugridResponse docugridResponse = new DocugridResponse();

		docugridResponse.setId(id);
		docugridResponse.setFilename(filename);

		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		buffer.flush();

		byte[] bytes = buffer.toByteArray();

		String filestring = new String(Base64.getEncoder().encode(bytes));
		docugridResponse.setBin(filestring);

		String response = DocugridUtil.actualizarDocumento(docugridResponse, urlRest);

		JSONParser parser = new JSONParser();
		JSONObject obj;
		String message = "";
		try {
			obj = (JSONObject) parser.parse(response);
			String result = (String) obj.get("result");
			if (result.equals("error")) {
				if (id.length() == 0) {
					req.setAttribute("resultactualizar", "(*) Debe ingresar un id de documento.");

				} else {
					message = (String) obj.get("errormessage");
					req.setAttribute("resultactualizar", "(*)" + message + ".");

				}

			} else {

				req.setAttribute("resultactualizar", "(*) El documento ha sido actualizado exitosamente.");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

		return;
	}

}
