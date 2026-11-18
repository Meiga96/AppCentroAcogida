1. ¿Qué hace el Sync de Gradle? ¿Qué se pone en gradle/libs.versions.toml y qué en app/build.gradle.kts? Para añadir Navigation Compose, ¿qué cambiaste en cada uno?
El sync lee los .gradle descarga dependencias y plugins. En versions.toml es el catálogo de versiones. Gradle.kts dice qué usa el módulo. Se cambió en el toml un version un libraries y en gradle kts un implementation 
2. La biblioteca de iconos (material-icons-core) no lleva versión. ¿Quién la fija y para qué sirve?
La biblioteca de iconos la fija el BOM de Compose y garantiza que todas las bibliotecas de Compose sean compatibles entre sí.
3. La plantilla tiene minSdk = 24. ¿Qué significa? ¿En qué dispositivos se puede instalar la app?
Nivel de API minimo que se exige para poder ser instalada, asi que dispositivos Android 7.0 o superior.
4. ¿Para qué sirve Logcat? ¿Qué hace Log.d(TAG, "onStart") y para qué sirve el filtro tag:Ciclo?
Es la consola que muestra mensajes del sistema y de la app. onStart escribe un mensaje de depuración con la etiqueta TAG y el filtro muestra solo mensajes de esa etiqueta.
5. ¿Qué hay en cada uno de estos archivos del proyecto: Animal.kt, DatosAnimales.kt, TarjetaAnimal.kt, PantallaLista.kt, PantallaDetalle.kt, PantallaSolicitud.kt, AcogidaUiState.kt, FormularioUiState.kt, AcogidaViewModel.kt, AcogidaApp.kt y MainActivity.kt?
En Animal.kt data class con datos de animales, 
DatosAnimales.kt tiene la lista de animales de ejemplo, 
TarjetaAnimal es un composable de una tarjeta de la lista, 
PantallaLista lista de animales y filtro.
PantallaDetalle detalle de animales
PantallaSolicitud formulario de adopción
AcogidaUiState estado de la lista 
FormularioUiState estado del formulario y sus errores
AcogidaViewModel guarda el estado la lógica
AcogidaApp NavHost y rutas
Main punto de entrada, llama a setContent
6. Al girar el móvil, ¿qué métodos del ciclo de vida salen en Logcat y en qué orden? ¿Por qué con remember el interruptor «Adopciones urgentes» volvía a estar desactivado y con el ViewModel no?
Como se destruye la actividad y se vuelve a crear aparecen, onPause, onStop, onCreate, onStart y onResume, remember guarda el estado en la composición pero se pierde al recrear por eso el interruptor pasa a desactivado, ViewModel sobrevive a esa rotación y conserva el estado.
7. ¿Qué es un composable? En TarjetaAnimal, ¿para qué sirve el parámetro modifier? ¿Y Modifier.padding(innerPadding) en la pantalla de lista?
Es una función que describe parte de la interfaz. modifier permite cambiar forma tamaño márgenes.... el padding aplica unos márgenes para adaptar como se distribuye el contenido y nada quede tapado.
8. ¿Qué hace remember? ¿Qué es la recomposición y cuándo ocurre?
Como hemos visto en la anterior pregunta guarda un valor en composición, recomposición vuelve a ejecutar los composables cuando cambia un estado que leen.
9. Pega este código en MainActivity.kt, dentro de onCreate, justo después de super.onCreate(savedInstanceState) (borra antes el código de la pregunta anterior). Ejecuta la app y copia los mensajes de Logcat con el filtro tag:Pregunta.
val luna = Animal(1, "Luna", "Perro", 3, R.drawable.ic_launcher_foreground, urgente = true)
val copia = luna.copy(edad = 4)
Log.d("Pregunta", "${luna.nombre}, ${luna.edad} -> ${copia.nombre}, ${copia.edad}")
Log.d("Pregunta", "Iguales: ${luna == luna.copy()}, ${luna == copia}")
Log.d("Pregunta", "Descripción: '${luna.descripcion}'")

Luna, 3 -> Luna, 4
Iguales: true, false
Descripción: ''

10. Pega este código en MainActivity.kt, dentro de onCreate, justo después de super.onCreate(savedInstanceState) (borra antes el código de la pregunta anterior). Ejecuta la app y copia los mensajes de Logcat con el filtro tag:Pregunta.
val animales = listOf(
Animal(1, "Luna", "Perro", 3, R.drawable.ic_launcher_foreground, urgente = true),
Animal(2, "Misi", "Gato", 1, R.drawable.ic_launcher_foreground, urgente = false),
Animal(3, "Kira", "Perro", 4, R.drawable.ic_launcher_foreground, urgente = true)
)
val urgentes = animales.filter { it.urgente }
val buscado: Animal? = animales.find { it.id == 7 }
Log.d("Pregunta", "Urgentes: ${urgentes.size}")
Log.d("Pregunta", "id 7: ${buscado?.nombre ?: "Desconocido"}")
Log.d("Pregunta", "Edad del id 7: ${buscado?.edad}")

Urgentes: 2
id 7: Desconocido
Edad del id 7: null

11. En la lista se escribe items(uiState.animalesVisibles, key = { it.id }). ¿Para qué sirve key? ¿Qué pasa si dos animales tienen el mismo id?
key da una identidad estable de manera que Compose reutilice bien los elementos y conserve su estado si la lista cambia, si hay ids repetidos al app se cierra con error por claves duplicadas.
12. En AcogidaViewModel, ¿por qué hay dos variables, _uiState (MutableStateFlow, privada) y uiState (StateFlow, pública)? ¿Qué hace _uiState.update { it.copy(soloUrgentes = valor) }?

13. Pega este código en MainActivity.kt, dentro de onCreate, justo después de super.onCreate(savedInstanceState) (borra antes el código de la pregunta anterior). Ejecuta la app y copia los mensajes de Logcat con el filtro tag:Pregunta.
val estado = AcogidaUiState(
animales = listOf(
Animal(1, "Luna", "Perro", 3, R.drawable.ic_launcher_foreground, urgente = true),
Animal(2, "Misi", "Gato", 1, R.drawable.ic_launcher_foreground, urgente = false)
)
)
val urgente = estado.copy(soloUrgentes = true)
Log.d("Pregunta", "Visibles: ${estado.animalesVisibles.size} y ${urgente.animalesVisibles.size}")
Log.d("Pregunta", "Solo urgentes: ${estado.soloUrgentes} y ${urgente.soloUrgentes}")
Log.d("Pregunta", "Animales: ${estado.animales.size} y ${urgente.animales.size}")

Visibles: 2 y 1
Solo urgentes: false y true
Animales: 2 y 2

14. Las pantallas (PantallaLista, PantallaDetalle, PantallaSolicitud) no reciben el ViewModel ni el navController: reciben el estado y lambdas. ¿Qué es el flujo unidireccional de datos? ¿Qué se gana haciéndolo así?
Flujo unidireccional implica que el estado baja hacia la interfaz y los eventos suben mediante lambdas. Lo que se gana haciéndolo así son pantallas reutilizables y fáciles de previsualizar y probar.
15. La lista navega con navController.navigate("detalle/${animal.id}") y el destino tiene la ruta "detalle/{animalId}". ¿Cómo llega el id del animal a PantallaDetalle? ¿Qué se ve si llega un id que no existe?
Si el id, no existe, da null y saca un mensaje informativo.
16. En la lista pulsas en Misi y, en su detalle, «Solicitar adopción». ¿Qué pantallas hay en la pila de navegación? ¿A dónde lleva la flecha de «Volver» (popBackStack())? ¿Y el botón «Volver a la lista» (popBackStack("lista", inclusive = false))?
La pila es lista → detalle(Misi) → solicitud. La flecha de volver (popBackStack()) quita la pantalla actual y lleva al detalle. popBackStack("lista", inclusive = false) quita todo hasta lista sin quitarla, y lleva a la lista.
17. Pega este código en MainActivity.kt, dentro de onCreate, justo después de super.onCreate(savedInstanceState) (borra antes el código de la pregunta anterior). Ejecuta la app y copia los mensajes de Logcat con el filtro tag:Pregunta.

val form = FormularioUiState(nombre = "Ana", email = "ana@correo", telefono = "612345678")
Log.d("Pregunta", "Nombre: ${form.errorNombre}")
Log.d("Pregunta", "Correo: ${form.errorEmail}")
Log.d("Pregunta", "Teléfono: ${form.errorTelefono}")
Log.d("Pregunta", "Condiciones: ${form.errorCondiciones}")
Log.d("Pregunta", "Válido: ${form.esValido}")

Nombre: null
Correo: <mensaje de error>
Teléfono: null
Condiciones: <mensaje de error>
Válido: false

18. Pega este código en MainActivity.kt, dentro de onCreate, justo después de super.onCreate(savedInstanceState) (borra antes el código de la pregunta anterior). Ejecuta la app y copia los mensajes de Logcat con el filtro tag:Pregunta.
val vm = AcogidaViewModel()
vm.cambiarTelefono("612")
vm.cambiarTelefono("612a")
Log.d("Pregunta", "1: ${vm.formulario.value.telefono}")
vm.cambiarTelefono("6123456789")
Log.d("Pregunta", "2: ${vm.formulario.value.telefono}")
vm.enviarSolicitud()
Log.d("Pregunta", "3: ${vm.formulario.value.intentadoEnviar} ${vm.formulario.value.enviado}")
vm.reiniciarFormulario()
Log.d("Pregunta", "4: '${vm.formulario.value.telefono}' ${vm.formulario.value.intentadoEnviar}")

1: 612
2: 612
3: true false
4: '' false

19. Cuenta, paso a paso, qué pasa desde que escribes una cifra en el campo «Teléfono» hasta que aparece en pantalla. Di qué función de qué archivo se ejecuta en cada paso. ¿Por qué no se puede escribir una letra?
PantallaSolicitud recibe el texto, AcogidaApp lambda llama a viewModel.cambiarTelefono(texto),
AcogidaViewModel filtra dígitos y el StateFlow emite el estado nuevo y collectAsState lo lee, se recompone el campo con el valor nuevo.
20. Cuenta, paso a paso, qué pasa al pulsar «Enviar» con el formulario vacío: qué función se ejecuta, qué cambia en el estado y por qué ahora sí se ven los errores. ¿Para qué se llama a viewModel.reiniciarFormulario() antes de abrir el formulario?
Se llama a viewModel.enviarSolicitud() está función marca intentadoEnviar = true. Si esValido es falso no envía, la pantalla los muestra cuando intentadoEnviar es true.
reiniciarFormulario() limpia los datos y errores de uso anteriores antes de abrir.
21. Di cuatro limitaciones de un móvil frente a un ordenador y cómo afecta cada una a cómo se programa una app.
Pantalla pequeña, batería limitada, conexión variable e interacción táctil. Consecuencias: diseño adaptable, ahorrar energía y trabajo en segundo plano, funcionar sin conexión o con datos intermitentes, y botones y controles grandes.
22. ¿En qué se diferencian una app nativa, una multiplataforma y una web (PWA)? Di una tecnología de cada una.
Nativa: código propio de la plataforma (Kotlin para Android). Multiplataforma: un código para varias plataformas (Flutter, React Native). Web/PWA: se ejecuta en el navegador y se instala como app (HTML, CSS y JavaScript).
23. ¿Qué capas tiene Android? ¿Qué es el nivel de API? ¿Qué diferencia hay entre targetSdk y compileSdk?
Capas: kernel de Linux, HAL, runtime y bibliotecas nativas, framework de Android y apps. El nivel de API es el número de versión de la API del framework. compileSdk es la versión contra la que se compila. targetSdk es la versión para la que se ha probado la app y cuyo comportamiento se aplica.
24. ¿Qué son los sp y en qué se diferencian de los dp? ¿Para qué sirven carpetas de recursos como values-es o values-night?
Los sp son como los dp pero escalan además con el tamaño de fuente del usuario, y se usan para texto. values-es y values-night son recursos alternativos que el sistema elige según idioma o tema oscuro.
25. ¿Qué hay en AndroidManifest.xml y qué en la carpeta res? ¿Cómo se usa desde el código una imagen de res/drawable?
AndroidManifest.xml declara componentes, permisos y datos de la app. res contiene recursos (imágenes, textos, colores). Una imagen de res/drawable se usa con painterResource(R.drawable.nombre)
26. ¿Qué hace !! en Kotlin y por qué conviene evitarlo? ¿Qué se pierde al girar el móvil si se guarda con remember, y qué no se pierde con rememberSaveable o con un ViewModel?
!! fuerza que un valor no sea null y lanza excepción si lo es, por eso conviene usar ?., ?: o let. Con remember se pierde el estado al girar. rememberSaveable y el ViewModel lo conservan.
1. ¿Cómo se comprueba que la app funciona en dos tamaños de pantalla? ¿Para qué sirve la vista previa ListaTabletaPreview del paso 8 y qué indica widthDp = 840?
Probando con diferentes running devices y que correspondan a pantallas de diferentes tamaños. Sirve para comprobar como responde la app en la tableta, y widthDp es el ancho de la pantalla en dp.
2. En PantallaLista.kt, ¿qué hace LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 320.dp), …)? ¿Cuántas columnas salen en el móvil en vertical y cuántas en la tableta? ¿Por qué no hace falta ningún if?
GridCells.Adaptive(minSize = 320.dp) pone tantas columnas como quepan con al menos 320 dp cada una. En un móvil vertical de tamaño medio sale 1 columna, y en una tableta de 840 dp salen 2. 
No hace falta if porque la cuadrícula calcula las columnas según el ancho.
3. ¿Qué son los dp? ¿Por qué se usan en lugar de píxeles?
Son píxeles independientes de la densidad. Miden lo mismo en pantallas de densidades distintas, se usan porque los píxeles sí cambian el tamaño según densidad.
4. ¿Para qué sirve .widthIn(max = 600.dp) en las columnas del detalle y del formulario? ¿Qué pasaría en la tableta sin él?
Limita el ancho del contenido a 600 dp. Sin él, en la tableta los campos y textos se estirarían por toda la pantalla y quedarían incómodos de leer y de usar.
5. ¿Qué es KSP y por qué lo necesita Room? En 9.1, ¿qué añadiste en libs.versions.toml, en el build.gradle.kts del proyecto y en el de app?
KSP (Kotlin Symbol Processing) procesa anotaciones y genera código. Room lo necesita para generar la implementación del DAO y la base de datos a partir de las anotaciones. Se añade el plugin y versión en libs.versions.toml, el plugin con apply false en el build.gradle.kts del proyecto y el plugin más ksp(libs.androidx.room.compiler) y las dependencias de Room en el de app
6. En el build.gradle.kts del proyecto se escribe alias(libs.plugins.ksp) apply false y en el de app, alias(libs.plugins.ksp). ¿Qué significa apply false?
apply false declara el plugin y su versión sin aplicarlo en el proyecto raíz. Cada módulo lo aplica donde lo necesita.
7. ¿Qué es Room? ¿Cuáles son sus tres piezas, con qué anotación se marca cada una y qué archivo del proyecto es cada una?
La tres piezas de Room: 
Entity (@Entity): Solicitud.kt.
DAO (@Dao): SolicitudDao.kt.
Database (@Database): AcogidaDatabase.kt.
8. Pega este código y ejecuta la app. Copia los mensajes de Logcat.

val s = Solicitud(animalId = 2, animalNombre = "Misi", nombre = "Ana", email = "ana@correo.es", telefono = "612345678")
val otra = s.copy(nombre = "Luis")
Log.d("Pregunta", "id: ${s.id}")
Log.d("Pregunta", "Tiene fecha: ${s.fecha > 0}")
Log.d("Pregunta", "${otra.animalNombre}, ${otra.nombre}, id ${otra.id}")
Log.d("Pregunta", "Misma fecha: ${s.fecha == otra.fecha}")

id: 0
Tiene fecha: true
Misi, Luis, id 0
Misma fecha: true
9. En Solicitud, ¿por qué el id se escribe @PrimaryKey(autoGenerate = true) val id: Int = 0? ¿Quién pone el id de verdad y cuándo?
0 es solo el valor provisional. Con autoGenerate = true, cuando Room hace el INSERT es SQLite quien asigna el id real y único.
10. En SolicitudDao, insertar es suspend y todas() no lo es, pero devuelve un Flow<List<Solicitud>>. ¿Por qué cada una es así? ¿Qué gana la pantalla con un Flow?
insertar es suspend porque escribe en disco y no debe bloquear el hilo principal. todas() devuelve un Flow, que ya es asíncrono, y emite la lista de nuevo cada vez que la tabla cambia. La pantalla se actualiza sola
11. ¿Por qué AcogidaDatabase.obtener(context) devuelve siempre la misma base de datos? ¿Qué pasaría si cada pantalla creara la suya?
Se usa un patrón singleton para que haya una única instancia. Si cada pantalla creara la suya, habría varias conexiones, un gasto innecesario y posibles inconsistencias, y los cambios no se propagarían entre ellas.
12. ¿Por qué AcogidaViewModel pasa de heredar de ViewModel() a heredar de AndroidViewModel(application)? ¿Hubo que cambiar cómo se crea con viewModel()?
Porque necesita el contexto de la aplicación para crear la base de datos. AndroidViewModel lo recibe. Con viewModel() no hubo que cambiar nada, porque la factoría por defecto sabe crear ViewModels con Application.
13. Pega este código y ejecuta la app. Copia los mensajes de Logcat, en el orden en que salen. Es una base de datos en memoria, solo para la pregunta: no toca tus solicitudes.

val bd = Room.inMemoryDatabaseBuilder(this, AcogidaDatabase::class.java).build()
val dao = bd.solicitudDao()
Log.d("Pregunta", "Antes de launch")
lifecycleScope.launch {
Log.d("Pregunta", "Empieza la corrutina")
dao.insertar(Solicitud(animalId = 1, animalNombre = "Luna", nombre = "Ana", email = "ana@correo.es", telefono = "612345678"))
dao.insertar(Solicitud(animalId = 2, animalNombre = "Misi", nombre = "Luis", email = "luis@correo.es", telefono = "698765432"))
val lista = dao.todas().first()
Log.d("Pregunta", "Guardadas: ${lista.size}, ids: ${lista.map { it.id }.sorted()}")
}
Log.d("Pregunta", "Después de launch")

Antes de launch
Empieza la corrutina
Después de launch
Guardadas: 2, ids: [1, 2]
14. ¿Por qué insertar se llama dentro de viewModelScope.launch { }? ¿Qué error da Room si se accede a la base de datos en el hilo de la interfaz?
Porque insertar es suspend y requiere una corrutina, y viewModelScope se cancela solo cuando se destruye el ViewModel. Si se accede a Room en el hilo principal, lanza una IllegalStateException
15. En el ViewModel, ¿qué hace dao.todas().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())? ¿Por qué emptyList()?
stateIn convierte el Flow en StateFlow. Mientras haya alguien observando (más 5 segundos de margen al girar), recoge los datos de Room. Por qué emptyList(), porque un StateFlow siempre necesita uno antes de que llegue la primera lista.
16. Cuenta, paso a paso, qué pasa desde que pulsas «Enviar» con datos correctos hasta que la solicitud aparece en «Mis solicitudes». Di qué función de qué archivo se ejecuta en cada paso.
-1. PantallaSolicitud.kt: se pulsa «Enviar» y se llama al lambda.
-2. AcogidaApp.kt: llama a viewModel.enviarSolicitud(animal).
-3. AcogidaViewModel.kt: comprueba esValido, crea Solicitud y la inserta con viewModelScope.launch { dao.insertar(...) }.
-4. SolicitudDao.kt: Room ejecuta el INSERT en otro hilo.
-5. Room avisa al Flow todas(), stateIn lo emite y la pantalla de «Mis solicitudes» se recompone con la nueva fila.
17. ¿Por qué enviarSolicitud pasó a recibir el animal? ¿Qué hubo que cambiar en AcogidaApp.kt? ¿Qué pasa si el animal es null?
Porque la solicitud debe guardar animalId y animalNombre, y el ViewModel no sabe qué animal se está solicitando. En AcogidaApp.kt hubo que buscar el animal por el id de la ruta y pasarlo en la llamada. Si es null, no se crea la solicitud (se sale sin hacer nada o se muestra el error)
18. ¿Cómo se llega a «Mis solicitudes» y cómo se vuelve? ¿Por qué el parámetro nuevo de PantallaLista se escribe onVerSolicitudes: () -> Unit = {}, con un valor por defecto?
Se llega con un botón/icono en la lista que llama a onVerSolicitudes. Se vuelve con popBackStack(). El valor por defecto = {} evita que se rompan las vistas previas y los usos antiguos que no pasan ese parámetro.
19. ¿Por qué las solicitudes siguen ahí después de cerrar la app y el interruptor «Adopciones urgentes» no? Las pantallas no saben que existe Room: ¿quién habla con la base de datos? ¿Qué cambiaría si mañana los animales vinieran de un servicio web?
Room guarda en una base de datos en disco, que persiste. El interruptor vive en el estado en memoria del ViewModel, que desaparece al cerrar la app. Quien habla con la base es el ViewModel, mediante el DAO. Si los animales vinieran de un servicio web, solo cambiaría esa capa de datos (idealmente un repositorio), y las pantallas seguirían igual
20. Para hacer una foto, ¿dónde se declara el permiso de cámara y por qué, además, hay que pedirlo mientras se usa la app? ¿Qué pasa si lo deniegas?
El permiso se declara en AndroidManifest.xml (<uses-permission android:name="android.permission.CAMERA"/>). Al ser peligroso, además hay que pedirlo en ejecución, porque el usuario debe poder decidir. Si lo deniega, no se puede usar la cámara y la app debe seguir funcionando y explicar por qué lo necesita.
21. En el paso 10, ¿por qué animalesVisibles encadena dos filter? ¿Qué sale con el texto vacío? Cuenta qué pasa desde que escribes una letra en el campo de búsqueda hasta que la lista cambia: qué hacen onValueChange, onBuscar, cambiarBusqueda y uiState.
animalesVisibles encadena un filter para urgentes y otro para el texto de búsqueda, de modo que se aplican los dos criterios a la vez. Con el texto vacío no se filtra por nombre. Flujo: el usuario escribe una letra, onValueChange de OutlinedTextField llama a onBuscar, que llama a viewModel.cambiarBusqueda(texto), que actualiza _uiState con update. uiState emite el nuevo estado, animalesVisibles se recalcula y la lista se recompone.
22. ¿Cuándo usarías DataStore y cuándo Room? Pon un ejemplo de cada uno en la app del centro de acogida.
DataStore es para preferencias pequeñas clave-valor, por ejemplo guardar el interruptor «Adopciones urgentes» o el tema. Room es para datos estructurados y consultables, por ejemplo las solicitudes de adopción.
23. ¿Qué diferencia hay entre un permiso normal y uno peligroso? Pon un ejemplo de cada uno. ¿Qué tres respuestas puede dar la persona cuando se le pide uno peligroso?
Un permiso normal se concede al instalar y no es sensible (por ejemplo INTERNET). Uno peligroso afecta a la privacidad y se pide en ejecución (por ejemplo CAMERA). Respuestas posibles: permitir mientras se usa la app, permitir solo esta vez, o denegar.
24. ¿Qué hace falta para que una app hable con un servicio web y por qué HTTPS? ¿Qué diferencia hay entre un APK y un AAB, y entre una versión de depuración y una de publicación?
Hace falta el permiso INTERNET y una biblioteca de red (Retrofit, Ktor, etc.). Se usa HTTPS para cifrar la comunicación y evitar que se lean o modifiquen los datos. Un APK es el paquete instalable directo, y un AAB es el formato que se sube a Google Play, que genera APKs optimizados por dispositivo. La versión de depuración permite depurar y no va firmada para publicar, mientras que la de publicación está firmada y optimizada