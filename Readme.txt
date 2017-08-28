Proyecto: ATTA, cambio de repositorio de documentos.
Requerimientos Funcionales:

1. Exponer metodos para leer, crear, borrar y actualizar un documento
2. Los documentos al ser actualizados, se genera una nueva version
3. Debe existir una aplicacion manual para examinacion de documentos

Requerimientos no funcionales:
*******************************
La cantidad de usuarios actuales del sistema productivo es 3.
Se estima que el repositorio local son 3000 archivos.
El tiempo de acceso a un documento deberia de ser acerca de 1 segundo.
En general los documentos tienen a los mas 5 versiones
Se utilizara rest para comunicar con jdg

Para probar
********************
Importar el proyecto eclipse docugrid/docugrid

compilar mvn package

desplegar la app en EAP


para probar, ejecutar desde eclipse el test standalone
DocugridReadTest
DocugridEnterTest














