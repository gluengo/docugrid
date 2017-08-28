/*
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

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Documento RedHat, contiene id y un ArrayList de objetos tipo documento
 * @author Luz Mestre 
 *         
 * 
 */
public class RedhatDocument implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1900344488872440101L;

	private String id;

	private ArrayList<Document> version = null;

	public RedhatDocument() {
		this.id = "";
		this.version = new ArrayList<Document>();
	}

	public String toString() {
		String result = "{ \"id\":";

		result = result + "\"" + getId() + "\"" + ",\"versiones\": [";

		for (int i = 0; i < this.version.size(); i++) {
			result = result + version.get(i).toString();
			if (i + 1 > this.version.size()) {
				result = result + ",";
			}
		}
		result = result + "] }";

		return result;
	}

	public ArrayList<Document> getVersiones() {
		return version;
	}

	public Document getUltimaVersion() {
		if (getVersiones().size() > 0) {
			return getVersiones().get(getVersiones().size() - 1);

		} else {
			return null;
		}

	}

	public void setVersiones(ArrayList<Document> versiones) {
		this.version = versiones;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
