/*
 * JBoss, Home of Professional Open Source
 * Copyright 2015, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cl.redhat.docugrid.web.client;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import cl.redhat.docugrid.main.DocugridResponse;
import cl.redhat.docugrid.util.DocugridUtil;

/**
 * <p>
 * DocugridWeb: Realiza las operaciones de 1. consultar un documento 2.
 * consultar todas las versiones de un documento 3. actualizar documento 4.
 * Insertar documento
 * <p>
 * 
 * @author Luz Mestre
 *
 */
@SuppressWarnings("serial")
@WebServlet("/DocugridWebServlet")
public class DocugridWebServlet extends HttpServlet {

	/*
	 * Procesa las operaciones leer, leer version y eliminar (no expuesto) via
	 * web
	 * 
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		String operacion = req.getParameter("operacion");
		
		if (operacion != null) {
			if (operacion.equals("leer")) {
				leer(req, resp);
			}
			if (operacion.equals("leerversion")) {
				leerVersion(req, resp);
			}

		}

	}

	/**
	 * Entrega la ultima version de un documento
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	private void leerUltima(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String docId = req.getParameter("idleer");

		if (docId != null) {
			try {

				String urlRest = DocugridUtil.docugridProperty("urlleer");
				DocugridResponse docugridResponse = new DocugridResponse();
				docugridResponse.setId(docId);
				// invoca leer documento
				String response = DocugridUtil.leerDocumento(docugridResponse, urlRest);
				JSONParser parser = new JSONParser();
				JSONObject obj;

				obj = (JSONObject) parser.parse(response);
				String result = (String) obj.get("result");
				if (result.equals("error")) {
					if (docId.length() == 0) {

						req.setAttribute("resultleer", "(*) Debe ingresar un Id de documento.");

					} else {
						String message = (String) obj.get("errormessage");
						req.setAttribute("resultleer", "(*) Error:"+message+".");

					}

					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
					dispatcher.forward(req, resp);

				} else {
					String bin = (String) obj.get("bin");
					String filename = (String) obj.get("filename");
					
					
					byte[] bytes = Base64.getDecoder().decode(bin);
					resp.setContentType("application/pdf");
					resp.setHeader("Content-Disposition", "attachment;filename="+ filename);
					resp.getOutputStream().write(bytes);
				}
			} catch (Exception e1) {

				e1.printStackTrace();
				req.setAttribute("resultleer", "(*) Debe ingresar un id de documento.");
				RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				try {
					dispatcher.forward(req, resp);
				} catch (ServletException e) {

					e.printStackTrace();
				}
			}

		} else {
			
			req.setAttribute("resultleer", "(*) Debe ingresar un id de documento.");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
			try {
				dispatcher.forward(req, resp);
			} catch (ServletException e) {

				e.printStackTrace();
			}
		}

	}

	/**
	 * Entrega una version especifica de un documento
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void leerVersion(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String urlRest = DocugridUtil.docugridProperty("urlleerversiones");
		String docId = req.getParameter("idleer");
		String numeroversion = req.getParameter("numeroversion");

		DocugridResponse docugridResponse = new DocugridResponse();
		docugridResponse.setId(docId);

		try {

			// invoca leer documento
			String response = DocugridUtil.leerVersiones(docugridResponse, urlRest);
			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(response);

			String result = (String) obj.get("result");
			if (result.equals("error")) {
				if (docId.length() == 0) {
					req.setAttribute("resultleer", "(*) Debe ingresar un id de documento.");
				} else {
					String errormessage = (String) obj.get("errormessage");
					req.setAttribute("resultleer","(*)" +errormessage+".");
				}

			} else {
				JSONArray jsonArray = (JSONArray) obj.get("versiones");

				JSONObject jsonObject_i = (JSONObject) jsonArray.get((new Integer(numeroversion)).intValue()-1);

				String bin = (String) jsonObject_i.get("bin");
				String filename = (String) jsonObject_i.get("filename");
				
				byte[] bytes = Base64.getDecoder().decode(bin);

				resp.setContentType("application/pdf");
				resp.setHeader("Content-Disposition", "attachment;filename="+filename);
				resp.getOutputStream().write(bytes);

			}

		} catch (ParseException e1) {

			e1.printStackTrace();
		}

	}

	/**
	 * Entrega todas las versiones de un documento
	 * 
	 * @param req
	 * @param resp
	 * @throws ServletException
	 * @throws IOException
	 */
	private void leerTodas(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String urlRest = DocugridUtil.docugridProperty("urlleerversiones");
		String docId = req.getParameter("idleer");
		if (docId != null) {

			DocugridResponse docugridResponse = new DocugridResponse();
			docugridResponse.setId(docId);

			try {

				// invoca leer documento
				String response = DocugridUtil.leerVersiones(docugridResponse, urlRest);
				JSONParser parser = new JSONParser();
				JSONObject obj = (JSONObject) parser.parse(response);

				String result = (String) obj.get("result");
				if (result.equals("error")) {
					if (docId.length() == 0) {
						req.setAttribute("resultleer", "(*) Debe ingresar un id de documento.");
					} else {
						String errormessage = (String) obj.get("errormessage");
						req.setAttribute("resultleer","(*)"+ errormessage+".");
					}

				} else {
					JSONArray jsonArray = (JSONArray) obj.get("versiones");
					req.setAttribute("jsonArray", jsonArray);
					req.setAttribute("id", docId);
				}

			} catch (ParseException e1) {

				e1.printStackTrace();
			}
		} else {
			req.setAttribute("resultleer", "(*) Debe ingresar un id de documento.");
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
		dispatcher.forward(req, resp);

	}

	/**
	 * Procesa leer la ultima version o todas las versiones.
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 * @throws ServletException
	 */
	private void leer(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String versiones = req.getParameter("versiones");
		if (versiones == null) {
			leerUltima(req, resp);
		} else {
			leerTodas(req, resp);
		}

	}

}
