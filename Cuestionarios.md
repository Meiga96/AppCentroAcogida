1. ¿Qué hace el Sync de Gradle? ¿Qué se pone en gradle/libs.versions.toml y qué en app/build.gradle.kts? Para añadir Navigation Compose, ¿qué cambiaste en cada uno?

2. La biblioteca de iconos (material-icons-core) no lleva versión. ¿Quién la fija y para qué sirve?

3. La plantilla tiene minSdk = 24. ¿Qué significa? ¿En qué dispositivos se puede instalar la app?

4. ¿Para qué sirve Logcat? ¿Qué hace Log.d(TAG, "onStart") y para qué sirve el filtro tag:Ciclo?

5. ¿Qué hay en cada uno de estos archivos del proyecto: Animal.kt, DatosAnimales.kt, TarjetaAnimal.kt, PantallaLista.kt, PantallaDetalle.kt, PantallaSolicitud.kt, AcogidaUiState.kt, FormularioUiState.kt, AcogidaViewModel.kt, AcogidaApp.kt y MainActivity.kt?

6. Al girar el móvil, ¿qué métodos del ciclo de vida salen en Logcat y en qué orden? ¿Por qué con remember el interruptor «Adopciones urgentes» volvía a estar desactivado y con el ViewModel no?

7. ¿Qué es un composable? En TarjetaAnimal, ¿para qué sirve el parámetro modifier? ¿Y Modifier.padding(innerPadding) en la pantalla de lista?

8. ¿Qué hace remember? ¿Qué es la recomposición y cuándo ocurre?

9. Pega este código en MainActivity.kt, dentro de onCreate, justo después de super.onCreate(savedInstanceState) (borra antes el código de la pregunta anterior). Ejecuta la app y copia los mensajes de Logcat con el filtro tag:Pregunta.
val luna = Animal(1, "Luna", "Perro", 3, R.drawable.ic_launcher_foreground, urgente = true)
val copia = luna.copy(edad = 4)
Log.d("Pregunta", "${luna.nombre}, ${luna.edad} -> ${copia.nombre}, ${copia.edad}")
Log.d("Pregunta", "Iguales: ${luna == luna.copy()}, ${luna == copia}")
Log.d("Pregunta", "Descripción: '${luna.descripcion}'")
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
11. En la lista se escribe items(uiState.animalesVisibles, key = { it.id }). ¿Para qué sirve key? ¿Qué pasa si dos animales tienen el mismo id?

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
14. Las pantallas (PantallaLista, PantallaDetalle, PantallaSolicitud) no reciben el ViewModel ni el navController: reciben el estado y lambdas. ¿Qué es el flujo unidireccional de datos? ¿Qué se gana haciéndolo así?

15. La lista navega con navController.navigate("detalle/${animal.id}") y el destino tiene la ruta "detalle/{animalId}". ¿Cómo llega el id del animal a PantallaDetalle? ¿Qué se ve si llega un id que no existe?

16. En la lista pulsas en Misi y, en su detalle, «Solicitar adopción». ¿Qué pantallas hay en la pila de navegación? ¿A dónde lleva la flecha de «Volver» (popBackStack())? ¿Y el botón «Volver a la lista» (popBackStack("lista", inclusive = false))?

17. Pega este código en MainActivity.kt, dentro de onCreate, justo después de super.onCreate(savedInstanceState) (borra antes el código de la pregunta anterior). Ejecuta la app y copia los mensajes de Logcat con el filtro tag:Pregunta.

val form = FormularioUiState(nombre = "Ana", email = "ana@correo", telefono = "612345678")
Log.d("Pregunta", "Nombre: ${form.errorNombre}")
Log.d("Pregunta", "Correo: ${form.errorEmail}")
Log.d("Pregunta", "Teléfono: ${form.errorTelefono}")
Log.d("Pregunta", "Condiciones: ${form.errorCondiciones}")
Log.d("Pregunta", "Válido: ${form.esValido}")
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
19. Cuenta, paso a paso, qué pasa desde que escribes una cifra en el campo «Teléfono» hasta que aparece en pantalla. Di qué función de qué archivo se ejecuta en cada paso. ¿Por qué no se puede escribir una letra?

20. Cuenta, paso a paso, qué pasa al pulsar «Enviar» con el formulario vacío: qué función se ejecuta, qué cambia en el estado y por qué ahora sí se ven los errores. ¿Para qué se llama a viewModel.reiniciarFormulario() antes de abrir el formulario?

Cuestiones sobre los apuntes
Estas preguntas se responden con los apuntes de cada unidad, en apuntes/.

21. Di cuatro limitaciones de un móvil frente a un ordenador y cómo afecta cada una a cómo se programa una app.

22. ¿En qué se diferencian una app nativa, una multiplataforma y una web (PWA)? Di una tecnología de cada una.

23. ¿Qué capas tiene Android? ¿Qué es el nivel de API? ¿Qué diferencia hay entre targetSdk y compileSdk?

24. ¿Qué son los sp y en qué se diferencian de los dp? ¿Para qué sirven carpetas de recursos como values-es o values-night?

25. ¿Qué hay en AndroidManifest.xml y qué en la carpeta res? ¿Cómo se usa desde el código una imagen de res/drawable?

26. ¿Qué hace !! en Kotlin y por qué conviene evitarlo? ¿Qué se pierde al girar el móvil si se guarda con remember, y qué no se pierde con rememberSaveable o con un ViewModel?

1. ¿Cómo se comprueba que la app funciona en dos tamaños de pantalla? ¿Para qué sirve la vista previa ListaTabletaPreview del paso 8 y qué indica widthDp = 840?

2. En PantallaLista.kt, ¿qué hace LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 320.dp), …)? ¿Cuántas columnas salen en el móvil en vertical y cuántas en la tableta? ¿Por qué no hace falta ningún if?

3. ¿Qué son los dp? ¿Por qué se usan en lugar de píxeles?

4. ¿Para qué sirve .widthIn(max = 600.dp) en las columnas del detalle y del formulario? ¿Qué pasaría en la tableta sin él?

5. ¿Qué es KSP y por qué lo necesita Room? En 9.1, ¿qué añadiste en libs.versions.toml, en el build.gradle.kts del proyecto y en el de app?

6. En el build.gradle.kts del proyecto se escribe alias(libs.plugins.ksp) apply false y en el de app, alias(libs.plugins.ksp). ¿Qué significa apply false?

7. ¿Qué es Room? ¿Cuáles son sus tres piezas, con qué anotación se marca cada una y qué archivo del proyecto es cada una?

8. Pega este código y ejecuta la app. Copia los mensajes de Logcat.

val s = Solicitud(animalId = 2, animalNombre = "Misi", nombre = "Ana", email = "ana@correo.es", telefono = "612345678")
val otra = s.copy(nombre = "Luis")
Log.d("Pregunta", "id: ${s.id}")
Log.d("Pregunta", "Tiene fecha: ${s.fecha > 0}")
Log.d("Pregunta", "${otra.animalNombre}, ${otra.nombre}, id ${otra.id}")
Log.d("Pregunta", "Misma fecha: ${s.fecha == otra.fecha}")
9. En Solicitud, ¿por qué el id se escribe @PrimaryKey(autoGenerate = true) val id: Int = 0? ¿Quién pone el id de verdad y cuándo?

10. En SolicitudDao, insertar es suspend y todas() no lo es, pero devuelve un Flow<List<Solicitud>>. ¿Por qué cada una es así? ¿Qué gana la pantalla con un Flow?

11. ¿Por qué AcogidaDatabase.obtener(context) devuelve siempre la misma base de datos? ¿Qué pasaría si cada pantalla creara la suya?

12. ¿Por qué AcogidaViewModel pasa de heredar de ViewModel() a heredar de AndroidViewModel(application)? ¿Hubo que cambiar cómo se crea con viewModel()?

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
14. ¿Por qué insertar se llama dentro de viewModelScope.launch { }? ¿Qué error da Room si se accede a la base de datos en el hilo de la interfaz?

15. En el ViewModel, ¿qué hace dao.todas().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())? ¿Por qué emptyList()?

16. Cuenta, paso a paso, qué pasa desde que pulsas «Enviar» con datos correctos hasta que la solicitud aparece en «Mis solicitudes». Di qué función de qué archivo se ejecuta en cada paso.

17. ¿Por qué enviarSolicitud pasó a recibir el animal? ¿Qué hubo que cambiar en AcogidaApp.kt? ¿Qué pasa si el animal es null?

18. ¿Cómo se llega a «Mis solicitudes» y cómo se vuelve? ¿Por qué el parámetro nuevo de PantallaLista se escribe onVerSolicitudes: () -> Unit = {}, con un valor por defecto?

19. ¿Por qué las solicitudes siguen ahí después de cerrar la app y el interruptor «Adopciones urgentes» no? Las pantallas no saben que existe Room: ¿quién habla con la base de datos? ¿Qué cambiaría si mañana los animales vinieran de un servicio web?

20. Para hacer una foto, ¿dónde se declara el permiso de cámara y por qué, además, hay que pedirlo mientras se usa la app? ¿Qué pasa si lo deniegas?

21. En el paso 10, ¿por qué animalesVisibles encadena dos filter? ¿Qué sale con el texto vacío? Cuenta qué pasa desde que escribes una letra en el campo de búsqueda hasta que la lista cambia: qué hacen onValueChange, onBuscar, cambiarBusqueda y uiState.

Cuestiones sobre los apuntes
Estas preguntas se responden con los apuntes de cada unidad, en apuntes/.

22. ¿Cuándo usarías DataStore y cuándo Room? Pon un ejemplo de cada uno en la app del centro de acogida.

23. ¿Qué diferencia hay entre un permiso normal y uno peligroso? Pon un ejemplo de cada uno. ¿Qué tres respuestas puede dar la persona cuando se le pide uno peligroso?

24. ¿Qué hace falta para que una app hable con un servicio web y por qué HTTPS? ¿Qué diferencia hay entre un APK y un AAB, y entre una versión de depuración y una de publicación?