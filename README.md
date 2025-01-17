## Proyecto de Multimedia

Este es el repositorio de uno de los proyetos que tuve que hacer para la asignatura de multimedia, se muestra el enunciado para que se sepan cuales son los objetivos de la aplicacion y posterioirmente imagenes con explicaciones de como he llegado a los diferentes objetivos tratando de documentar de la manera más profesional posible.

ENUNCIADO

El alumno deberá crear una aplicación en Android Studio. Los requisitos mínimos para
poder aprobar:
- Temática de la Aplicación: Cada estudiante deberá seleccionar un tema único
para su aplicación, no podrán repetir entre compañeros (en el caso de no usar la
API de Twitter hecha para acceso).
- Componentes Obligatorios:
o Activities: La aplicación deberá contar con al menos,
▪ una actividad de login,
▪ y tras el inicio de sesión exitoso, se redirigirá al usuario a la
actividad principal.

o Fragments: La actividad principal deberá contener varios fragments:
▪ Fragment "Home" que realice automáticamente una consulta para
obtener todos los elementos y mostrarlos en una lista.
▪ Fragment para crear un nuevo elemento.
▪ Fragment para modificar un elemento existente.
▪ Fragment para eliminar un elemento.
o Botones:
▪ Integrar botones.
o Campos de Texto:
▪ Incluir campos de texto para introducir información.
o Listas:
▪ Mostrar los elementos en listas, por cada elemento deberá
aparecer al menos el nombre del elemento y una breve descripción
debajo del nombre. Debe haber una candidad de elementos los
suficientes como para poder hacer scroll en la lista.

o Barra de Navegación Inferior:
▪ Implementar una barra de navegación inferior para facilitar la
transición entre los fragments, debe tener un botón que cierre
nuestra sesión y nos lleve a la activity de login.

o Toasts:
▪ Notificar mediante Toast la creación, modificación o eliminación de
elementos.
▪ Notificar mediante Toast si el inicio de sesión es correcto o
incorrecto.
- Para el 5 se usará SQLLite
- El código deberá estar comentado con javadoc.
- Deberá subir un README con pantallazos de cada pantalla con la explicación de
cada una de ellas. Deberás también comentar las versiones usadas, dependencias
etc, un README completo.

Funcionalidades Adicionales (Opción de Obtener Puntuación de 10):

- Opción 1: Consumir un API Público y Loguearse mediante Google:
o Consumir un API público diferente por cada estudiante (sin repeticiones).
o Implementar el inicio de sesión utilizando las credenciales de Google.
▪ Puntuación:
• Consumir API público: 3.5 puntos.
• Loguearse con Google: 1.5 puntos.

- Opción 2: Consumir API de twitter y loguearse mediante la API de twitter:
o Crear y consumir tu propio API.
o Implementar el inicio de sesión utilizando tu propia API.
▪ Puntuación:
• Consumir tu propio API: 3.5 puntos.
• Loguearse con tu propio API: 1.5 puntos.

Observaciones Importantes:
- Si un estudiante opta por alguna de las opciones para obtener la puntuación
máxima de 10, pero no completa alguna parte, deberá cumplir con los requisitos
mínimos.
- Por ejemplo, si se elige consumir un API público pero no se implementa el inicio
de sesión con Google, el estudiante deberá realizar el inicio de sesión utilizando
SQLLite para cumplir con los requisitos mínimos.








Solo he realizado los requisitos para llegar al 5 por la falta de tiempo por lo que a continuacion iré adjuntando las capturas de las diferentes pantallas y explicaré como he hecho cada una.


Iniciaré indicando las dependencias que he necesitado para poder trabajar

[![dependencias.png](https://i.postimg.cc/NG8qqbTB/dependencias.png)](https://postimg.cc/rzpY51QP)


Algunas de estas dependencias ya vienen por defecto en el proyecto pero destacaré las dependencias de androidx.navigation:navigation-fragment:2.7.6,androidx.navigation:navigation-ui:2.7.6 y androidx.constraintlayout:constraintlayout:2.1.4 que sirven para poder utilizar el constraint layout para que los elementos de la aplicacion esten correctamente situados y poder navegar entre los diferentes fragments.


En las versiones he utilizado el sdk de API 34 y la version de gradle 8.0 ya que son las versiones con las que mejor rendimiento tenía mi proyecto.



Dicho esto ahora comenzaré a explicar las pantallas



LOGIN:

[![login.png](https://i.postimg.cc/wThhkySW/login.png)](https://postimg.cc/MvpM6TZR)


Para el login he realizado una estructura basica compuesta por TextViews,EditText  y botones para Introducir el texto de la portada, los campos que debe rellenar el usuario e iniciar sesion respectivamente. 
En la clase ActivityLogin se encuentra la actividad donde se gestiona el inicio de sesion a la aplicacion y donde comienza la aplicacion nada mas ejecutarla, esto ultimo se consigue modificando el archivo Android Manifest para que la Activity de login tenga la category de LAUNCHER en vez del Activity Main.
Se compone de un metodo de loguear que tras verificar que el usuario y contraseña pertenece al que hay guardado en sqlite permite el cambio de ventana al de la aplicacion y cerrando la ventana del login notificando mediante un Toast el exitoso inicio de sesion, y, en caso contrario, manteniendote en el ActivityLogin y notificando en el Toast que el usuario o la contraseña son incorrectos.




AdminSQliteOpenHelper:

Esta clase es la que se encarga de gestionar la base de datos SQLite. 
Tiene un context en el que se le indica el nombre de la base de datos y la version. En mi caso, debido a las modificaciones que he realizado he creado una variable static final para no tener que ir aumentando los numeros en cada fragment.
En el metodo oncreate creo las tablas de usuarios donde se almacenara las credenciales para el login y la tabla libros donde se guardaran los atributos que recibe el libro.
En el metodo OnUpgrade he configurado que haga Drop de las tablas que sean pertenecientes a versiones anteriores para evitar errores.

Tambien tiene el metodo ObtenerLibros que hace la peticion de obtener el nombre y la descripcion de todos los libros para posteriormente aplicarla en el fragment del home del cual hablaré después.
Por último tiene el metodo insertarLibro que hace el insert de los libros en la  base de datos para despues poder ser modificados mediante los fragments mas adelante.




MAIN/HOME:

[![home.png](https://i.postimg.cc/zvHkNx25/home.png)](https://postimg.cc/0KxSd0qH)

Una vez hemos iniciado sesion la aplicacion nos lleva al ActivityMain. El cual se encarga de mostrarnos una barra de navegacion con los diferentes fragments para navegar. La aplicacion comienza llevandonos al fragmentHome por defecto. Este fragment tiene una lista de todos los libros que hay actualmente en la Base de datos, pero antes hablemos del contenido del MainActivity: Este se encarga de configurar la barra de navegacion para que dependiendo de que item de la barra seleccionemos, nos lleve a un fragment u otro. Tambien guarda algunos metodos como el de cerrarSesion (del cual hablaremos mas adelante) y el del GetAll que se encarga de mostrar todos los libros comom hemos mencionado antes.

En el HomeFragment tenemos almacenados los metodos mostrarLibros y AlmacenarListaLibros que se encargan de llenar y mostrar la lista de los libros para poder obtenerla en el metodo del Main. El home fragment se compone de un ListView en el que se almacenaran los libros con sus atributos. Para separar cada item de la lista y que se haga notar cada item individual he utilizado la propiedad dividerHeight en el xml y para mostrar los atributos he utilizado el metodo obtenerIbros de la clase AdminSQliteOpenHelper e indicando de que manera quiero que se muestre cada atributo en la lista.





CREATE:

[![create.png](https://i.postimg.cc/pTbj8PsN/create.png)](https://postimg.cc/jWcCYYVv)

En el CreateFragment se guardan los metodos para poder crear nuevos libros y que se muestren en la lista de home. Para ello asocio los campos de la interfaz de usaurio con la clase del fragment. La interfaz grafica se basa en en dos editText para añadir los atributos del nuevo libro y despues un boton para crearlo. Tras pulsar el boton se utiliza el metodo createLibro que tras unas verificaciones aplica los metodos insertarLibro y actualizarListaLibros de los que ya hemos hablado anteriormente. Si todo se crea correctamente se notifica con un Toast, tambien en caso contrario





EDIT:

[![edit.png](https://i.postimg.cc/ZKMxnzzm/edit.png)](https://postimg.cc/F70LCqKC)

En el EditFragment se aplica algo parecido que en el de create pero añadiendo en la interfaz un campo adicional pidiendo el nombre del libro que se quiere modificar y despues los nuevos atributos.Al darle al boton de editar se aplica el metodo editarLibro en el que con la ayuda de la instancia de AdminSQliteOpenHelper se hace una query con un uptade sustituyendo las variables que representan los campos de la interfaz. Al igual que en Create si todo sale bien se notifica con un Toast, tambien en caso contrario.



DELETE

[![delete.png](https://i.postimg.cc/904zNS8P/delete.png)](https://postimg.cc/hQBSXCqh)

El DeleteFragment consta de un EditText que pide el identifiador unico del libro a eliminar y el boton de eliminar. EL boton invoca el metodo eliminarLibro que hace un delete utiliando la instancia de AdminSQliteOpenHelper. Si el libro se elimina correctamente se notifica con un toast y en caso contrario también.
