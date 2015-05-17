# Introduction #

Lista de tareas, responsables y estado de las mismas.

Ejemplos:
  * Tarea 1 _(Dami)_ **(Done)**
  * Tarea 2 _(Tifi)_ **(In Progress)**
  * Tarea 3 _(Lau)_

# Details #
  * Common
    * Uniformizar el idioma. Usar español (Ej, top, tail, etc). _(Tifi)_ **(Done)**
  * Paquetes **(Done)**
    * Refactor de nombres _(Dami)_ **(Done)**
    * Refactor de orden _(Dami)_ **(Done)**
  * Código Fuente
    * Refactor de comentarios
    * Refactor de convención de sintaxis
    * Eliminar warnings _(Dami / Tifi / Manu)_ **(Done)**
  * Ventana
    * Cambiar color, tabs, botones, etc. _(Nico)_ **(Done)**
    * Eliminar la opción “Guardar” porque tira una excepción _(Tifi)_ **(Cancelled)**
    * Header con logo de la facultad y texto: “Taller de Programación II – Cátedra Lic. A. Servetto – 1er. Cuat. 2009” _(Nico)_ **(Done)**
    * Reorganizar paneles (ver ejemplo en la ppt) _(Dami)_ **(Done)**
    * Arreglar re renderizado de la ventana en el resize _(Tifi)_ **(Done)**
    * Agregar Tab de Home, con el nombre del programa, alguna imagen y una descripción del mismo. Esta descripción se puede armar con los objetivos del tp: http://10459262283478758358-a-g.googlegroups.com/web/temas_tp.doc?gda=HuaqPT4AAACeejbg5RaIPOYr0yFdGONLN35wCPL_XRdo3jIdZVFa5Bi8qu_3zAQauRQAq_u4hFHjsKXVs-X7bdXZc5buSfmx
    * Achicar header al orden del botón de ayuda para liberar espacio. _(Nico)_ **(Done)**
  * Ayuda
    * Agregar info sobre cola circular _(Lau)_**(Done)**
    * Actualizar las imágenes de los nodos, los nombres de los botones, los colores, textos, etc._(Lau)_**(Done)**
    * Styles_(Lau)_**(Done)**
    * Acerca de…_(Lau)_**(Done)**
    * Cambiar tab ayuda por botoncito con signo de pregunta o algo similar _(Nico)_ **(Done)**
  * Imágenes
    * Trie
      * Nodo cuadrado _(Dami)_ **(Done)**
      * Nodo de lista _(Dami)_ **(Done)**
      * Nodo cuadrado con bordes redondeados _(Dami)_ **(Done)**
    * Stack
      * Nodo cuadrado con bordes redondeados _(Dami)_ **(Done)**
    * Queue
      * Nodo cuadrado con bordes redondeados _(Dami)_ **(Done)**
      * Nodo de cola circular _(Dami)_ **(Done)**
    * Heap
      * Nodo cuadrado (array) _(Dami)_ **(Done)**
      * Nodo circular (árbol) _(Dami)_ **(Done)**
    * Árboles
      * Nodo circular _(Dami)_ **(Done)**
  * Pila
    * Armar estructura en forma vertical _(Lau)_ **(Done)**
    * Permitir especificar el tamaño de la estructura al inicio _(Lau)_ **(Done)**
    * Deshabilitar botón apilar y desapilar según la pila esté vacía o llena (Mostrar cartel de aviso de pila vacía) _(Lau)_ **(Done)**
    * Poner nombres específicos de las funciones Apilar y Desapilar (Push, Pop) _(Lau)_ **(Done)**
    * Hacer que "Vaciar" funcione como en el Trie, porque sino tira ConcurrentModificationException. _(Lau)_ **(Done)**
    * Mostrar gráficamente como afecta la capacidad ingresada (El rectangulito de heap) _(Lau)_ **(Done)**
    * Mejorar la representación de la capacidad. _(Dami)_ **(Done)**
  * Cola
    * Agregar esquema simultáneo de cola circular _(Dami)_ **(Done)**
    * Permitir especificar el tamaño de la estructura al inicio _(Manu)_ **(Done)**
    * Mostrar cartel de aviso de cola vacía _(Manu)_ **(Done)**
    * Poner nombres específicos de las funciones (Encolar, Desencolar) _(Manu)_ **(Done)**
    * Hacer que "Vaciar" funcione como en el Trie, porque sino tira ConcurrentModificationException.  _(Manu)_ **(Done)**
    * Corregir bug que hay cuando se hace una inserción random, se eliminan un par de elementos, y al insertar uno nuevo, éste es insertado muy a la izquierda, como si la cola continuara con la cant. inicial de elementos. _(Manu)_ **(Done)**
    * Mejorar la representación de la capacidad. _(Dami)_ **(Done)**
  * Árbol Balanceado por Peso
    * Permitir ingresar el parámetro de peso _(Manu)_ **(Done)** (lo dejé oculto por falta de implementación en el modelo)
    * Permitir especificar el tamaño de la estructura al inicio (esto no se si tiene sentido) _(Manu)_ **(Cancelled)**
    * Mostrar cartel de aviso de árbol vacío _(Manu)_ **(Done)**
    * Deshabilitar botones Eliminar, Vaciar y Recorrer cuando el árbol esté vacío._(Nico)_ **(Done)**
  * Árbol Balanceado por Altura
    * Que variar la altura no sea cambiar el "PARAMETRO", o sea que se entienda. _(Manu)_ **(Done)**
    * Mostrar cartel de aviso de árbol vacío _(Manu)_ **(Done)**
    * Permitir especificar el tamaño de la estructura al inicio (esto no se si tiene sentido) _(Manu)_ **(Cancelled)**
    * Deshabilitar botones Eliminar, Vaciar y Recorrer cuando el árbol esté vacío._(Nico)_ **(Done)**
    * Acortar o sacar la instrucción de "Ingrese un número..." para que se vea el combo de selección de altura. _(Lau)_ **(Done)**
    * Modificar la animación de nodo eliminado, en vez de hacer que caiga fuera de la pantallar, hacer que desaparezca con un fade o algo por el estilo. _(Dami)_ **(Done)**
  * Trie
    * Cambiar “Demo” por “Random” _(Dami)_ **(Done)**
    * Cambiar ejemplos (del random) _(Dami)_ **(Done)**
    * Permitir especificar el tamaño de la estructura al inicio (esto no se si tiene sentido) _(Dami)_ **(Cancelled)**
    * Modificar la animación, por ej., hacer que no quede armada a la izquierda la palabra que se acaba de insertar. _(Dami / Tifi)_ **(Done)**
    * Deshabilitar botones Eliminar y Limpiar cuando el trie esté vacío. _(Nico)_ **(Done)**
  * Heap
    * Mostrar cartel de aviso de heap vacío _(Manu)_ **(Done)**
    * Hacer que no elimine nada si no se ingresó ningún número. Actualmente se está eliminando la raíz y es medio confuso. Tampoco es consistente con el resto de las estructuras. _(Manu)_ **(Cancelled)** (estaba bien como estaba)
  * Frame Principal
    * Agregar scroll _(Dami / Tifi)_ **(Done)**
    * Styles  _(Nico)_ **(Done)**
    * Arreglar el nombre y el orden de los botones de operaciones, para mantener coherencia. En algunos dice "limpiar", en otros "vaciar", en la mayoría aparece primero "random" y después "vaciar", en otros aparece primero "vaciar" y después "random", etc. _(Lau)_ **(Done)**
  * Frame de Operaciones Realizadas
    * Limpiar log de operaciones realizadas cuando se usa una nueva estructura (Voy a poner un log de operaciones por estructura) _(Tifi)_ **(Done)**
    * Styles  _(Nico)_ **(Done)**
  * Frame de Pseudocódigo
    * Poner un frame de pseudocodigo por estructura. _(Tifi)_ **(Done)**
    * Completar primitivas faltantes y arreglar alineación (faltan saltos de línea, hay funciones que tiene if anidados en una sola línea). _(Tifi)_ **(Done - solo idente y arregle estilos. las primitivas son las mismas que estaban)**
    * Styles  _(Nico)_ **(Done)**
  * Frame Inferior
    * Mejorar el Step by Step. Podemos permitir que se pueda deshacer completamente la operación que se acaba de realizar (inserción/eliminación). Actualmente, cuando se hace una inserción, por ej., hasta que no se completa el paso, no deja realizar nuevas operaciones. Podemos permitir que se pueda deshacer la operación actual (dejando todo en el estado anterior), y que deje realizar una nueva.
    * Styles _(Nico)_ **(Done)**
  * Página Web para correr el Applet _(Manu)_ **(Done)**
  * Documentación
    * Documento Técnico
    * Documento con el estándar de codificación