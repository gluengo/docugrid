/*<
 * JBoss, Home of Professional Open Source
 * Copyright 2014, Red Hat, Inc. and/or its affiliates, and individual
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
package cl.redhat.docugrid.main;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

//import cl.redhat.docugrid.jdg.JDGProcessor;
import cl.redhat.docugrid.jdg.JDGProcessorHotRod;;

/**
 * 
 * @author Luz Mestre
 *
 */
@RolesAllowed("redhat")
@Path("/")
public class DocugridManager {

	@POST
	@Path("leer")
	@Produces({ "application/json" })
	@Consumes("application/json")
	public String leer(DocugridResponse docugridResponse) {
		JDGProcessorHotRod jdgProcessor = new JDGProcessorHotRod();
		String str = jdgProcessor.leer(docugridResponse);
		return str;
	}

	@POST
	@Path("leertodos")
	@Produces({ "application/json" })
	@Consumes("application/json")
	public String leertodos(DocugridResponse docugridResponse) {
		JDGProcessorHotRod jdgProcessor = new JDGProcessorHotRod();
		String str = jdgProcessor.printcache();
		return str;
	}

	@POST
	@Path("leerversiones")
	@Produces({ "application/json" })
	@Consumes("application/json")
	public String leerversiones(DocugridResponse docugridResponse) {
		JDGProcessorHotRod jdgProcessor = new JDGProcessorHotRod();
		String str = jdgProcessor.leerversiones(docugridResponse);
		return str;
	}

	@POST
	@Path("ingresar")
	@Produces({ "application/json" })
	@Consumes("application/json")
	public String ingresar(Document document) {
		JDGProcessorHotRod jdgProcessor = new JDGProcessorHotRod();
		String str = jdgProcessor.ingresar(document);
		return str;
	}

	@POST
	@Path("actualizar")
	@Produces({ "application/json" })
	@Consumes("application/json")
	public String actualizar(DocugridResponse docugridResponse) {
		JDGProcessorHotRod jdgProcessor = new JDGProcessorHotRod();
		String str = jdgProcessor.actualizar(docugridResponse);
		return str;
	}

	@POST
	@Path("eliminar")
	@Produces({ "application/json" })
	@Consumes("application/json")
	public String eliminar(DocugridResponse docugridResponse) {
		JDGProcessorHotRod jdgProcessor = new JDGProcessorHotRod();
		String str = jdgProcessor.eliminar(docugridResponse);
		return str;
	}

	@POST
	@Path("eliminarcache")
	@Produces({ "application/json" })
	@Consumes("application/json")
	public String eliminarcache(DocugridResponse docugridResponse) {
		JDGProcessorHotRod jdgProcessor = new JDGProcessorHotRod();
		String str = jdgProcessor.eliminarcache();
		return str;
	}
}
