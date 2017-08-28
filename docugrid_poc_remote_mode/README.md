docugrid-poc-remote-mode
=========================================
Author: David Espinosa

Instalacion
------------------------------------

1- Configurar servidor Data Grid 7.1 (standalone.xml), en Infinispan subsystem definition:

    <subsystem xmlns="urn:infinispan:server:core:8.0" default-cache-container="local">
    ...
    ...
	
	<local-cache name="documentCache" start="EAGER" batching="false">
	     <leveldb-store path="/tmp/data">
	        <expiration path="/tmp/expired"/>
	        <compression type="SNAPPY"/>
	        <implementation/>
	     </leveldb-store>
	</local-cache>

2- Comentar autenticación para el end-point rest:

    <rest-connector socket-binding="rest" cache-container="local">
       <!-- <authentication security-realm="ApplicationRealm" auth-method="BASIC"/> -->
    </rest-connector>
            
            
Start JDG
---------

1. Open a command line and navigate to the root of the JDG directory.
2. The following shows the command line to start the server with the web profile:

        For Linux:   $JDG_HOME/bin/standalone.sh
        
        
Build and Run the Quickstart
-------------------------

1. Make sure you have started the JDG as described above.
2. Open a command line and navigate to the root directory of this quickstart.
3. Type this command to build and deploy the archive:

        mvn clean package 
                
4. This will create a file at `target/docugrid-poc-remote-mode.jar` 

5. Run the example application in its directory:

        mvn exec:java
