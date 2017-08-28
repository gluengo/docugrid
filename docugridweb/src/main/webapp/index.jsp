<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="org.json.simple.JSONArray"%>

<%@ page import="org.json.simple.JSONObject"%>
<%@ page import="java.io.File"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">


<title>Repositorio de Documentos Redhat</title>
<style>
.button {
	background-color: #4CAF50; /* Green */
	border: none;
	color: white;
	padding: 8px 22px;
	text-align: center;
	text-decoration: none;
	display: inline-block;
	font-size: 16px;
	margin: 4px 2px;
	cursor: pointer;
}

.button1 {
	background-color: #e7e7e7;
	color: black;
} /* Gray */
</style>
</head>
<body>
	<br>
	<table>
		<tr>
			<td><b> <IMG SRC="redhat.png" width="60" height="60" /></td>
			<td><font color="#172E74" size="6">Repositorio de
					Documentos Red Hat</font> </b></td>
		</tr>
	</table>
	<br>
	<hr size="2px" color="#e5e8e8" />
	<br>
	<br>
	<form name="leer" action="DocugridWebServlet" method="post">

		<table>
			<tr>
				<td WIDTH="400"><font color="#566573">Id del documento a
						buscar</font></td>
				<td width="50"></td>
				<td WIDTH="500"><input type="text" name="idleer" size="40px"></td>
				<td><button type="submit" value="Submit" class="button button1">Buscar</button></td>
			</tr>
			<tr>

				<td><font color="#808080"> <SMALL>
							<p align="justify">Ingrese el id de un documento ingresado al
								repositorio docugrid, por ejemplo:
								b1882a5b-b0c9-4036-9fee-f41c3d3412bc. Luego presione el boton
								Buscar. Para buscar las versiones, seleccione la opción Incluir
								todas las versiones.</p>
					</small>
				</font>
				<td></td>
				</td>
				<td><input type="checkbox" name="versiones" value="versiones">
					<font color="#808080">Incluir todas las versiones </font><br></td>

				<td><input type="hidden" name="operacion" value="leer"
					size="20px"></td>
			</tr>
		</table>

	</form>

	<%
		JSONArray jsonArray = (JSONArray) request.getAttribute("jsonArray");
		String id = (String) request.getAttribute("id");
		if (jsonArray != null) {
	%>


	<%
		for (int i = 0; i < jsonArray.size(); i++) {
				JSONObject jsonObject_i = (JSONObject) jsonArray.get(i);

				String version = (String) jsonObject_i.get("version");
	%>
	<form action="DocugridWebServlet" method="post">

		<table>
			<tr>
				<td><b></b></td>
			</tr>
			<tr>
				<td WIDTH="200"><input type="hidden" name="numeroversion"
					value="<%=version%>"><font color="#172E74"> Version
						<%=version%></font></td>
				<td WIDTH="10"><input type="hidden" name="idleer"
					value="<%=id%>"></td>
				<td WIDTH="100">
					<button type="submit" value="Submit" class="button button1">Descargar</button>
				</td>
				<td><input type="hidden" name="operacion" value="leerversion"
					size="20px"></td>
				<td></td>
			</tr>
		</table>

	</form>
	<%
		}
		}
	%>

	<%
		String result = (String) request.getAttribute("resultleer");
		if (result == null) {
			result = "";
		}
	%>
	<font color="#808080"> <%=result%></font>
	<hr size="2px" color="#e5e8e8" />


	<br>
	<br>
	<form name="ingresar" action="DocugridUpload" method="post"
		enctype="multipart/form-data">


		<table>
			<tr>
				<td WIDTH="400"><font color="#566573"> Documento a
						ingresar</font></td>
				<td WIDTH="50"></td>
				<td WIDTH="500"><font color="#808080"><input type="file"
						name="file" id="file" /></font></td>
				<td><font color="#808080">
						<button type="submit" value="submit" class="button button1">Ingresar</button>
				</font></td>

			</tr>
			<tr>
				<td><font color="#808080"> <SMALL>
							<p align="justify">Presione el botón examinar y seleccione el
								archivo pdf que ingresará al repositorio. Presione el botón
								Ingresar para almacenar el documento en el repositorio.</p>
					</small>
				</font> <input type="hidden" name="operacion" value="ingresar" size="20px"></td>
				<td></td>
				<td></td>
			</tr>
		</table>


	</form>
	<br>
	<table>
		<tr>
			<td>
				<%
					String result2 = (String) request.getAttribute("resultingresar");
					if (result2 == null) {
						result2 = "";
					}
				%> <br> <font color="#808080"> <%=result2%></font>
			</td>
		</tr>
	</table>
	<hr size="2px" color="#e5e8e8" />

	<br>
	<br>
	<form action="DocugridUpload" method="post"
		enctype="multipart/form-data">


		<table>

			<tr>
				<td WIDTH="400"><font color="#566573"> Id del documento
						a actualizar</font></td>
				<td WIDTH="50"></td>
				<td WIDTH="500"><input type="text" name="id" size="40px"></td>
				<td><font color="#808080">
						<button type="submit" value="Submit" class="button button1">Actualizar</button>
				</font> <input type="hidden" name="operacion" value="actualizar"
					size="20px"></td>
			</tr>
			<tr>
				<td>
				<font color="#808080"> <SMALL>
							<p align="justify">Ingrese el id de un documento ingresado al
								repositorio docugrid, por ejemplo:
								b1882a5b-b0c9-4036-9fee-f41c3d3412bc. Luego, presione el botón
								examinar y seleccione el archivo pdf que ingresara al
								repositorio. Finalmente, presione el botón Actualizar para
								ingresar una nueva versión del documento al repositorio.</p>
					</small>
				</font></td>
				<td></td>
				<td><font color="#808080"><input type="file" name="file"
						id="file" /></font></td>
				<td></td>
			</tr>

		</table>
		<table>
			<tr>
				<td>
					<%
						String result3 = (String) request.getAttribute("resultactualizar");
						if (result3 == null) {
							result3 = "";
						}
					%> <br> <font color="#808080"> <%=result3%></font>
				</td>
			</tr>
		</table>
	</form>

	<hr size="2px" color="#e5e8e8" />

	<br>
	<br>
</body>
</html>