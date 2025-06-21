# Sopa de Letras

**Sopa de Letras** es una aplicación de escritorio en Java que permite cargar y resolver sopas de letras personalizadas, con un diccionario propio de palabras y una interfaz gráfica amigable. El usuario puede cargar partidas desde archivos, buscar palabras usando diferentes algoritmos y guardar su progreso.

## Características principales

- **Carga de archivos personalizados**: Permite cargar un archivo que contiene tanto el tablero como el diccionario de palabras.
- **Diccionario editable**: Puedes añadir nuevas palabras al diccionario durante el juego.
- **Búsqueda de palabras**: Implementa algoritmos de búsqueda DFS y BFS para encontrar palabras en el tablero.
- **Resaltado visual**: Las palabras encontradas se resaltan en el tablero.
- **Modo de partida**: Puedes iniciar una nueva partida o continuar con la última cargada.
- **Persistencia**: Guarda la última partida abierta para facilitar la continuidad.
- **Interfaz gráfica intuitiva**: Uso de Swing, con personalización visual y botones claros.

## Estructura del proyecto

```
src/
 └─ sopadeletras/
      ├─ SopaDeLetras.java            # Clase principal (main)
      ├─ Diccionario.java             # Lógica del diccionario
      ├─ Tablero.java                 # Lógica del tablero y búsqueda de palabras
      ├─ InterfazInicio.java          # Ventana de inicio
      ├─ InterfazSeleccionarModo.java # Selección de modo de juego
      ├─ InterfazSopaDeLetras.java    # Ventana de juego principal
      ├─ Fondo1Panel.java             # Panel de fondo con imagen
      ├─ ResaltadoRenderer.java       # Renderer para resaltar celdas
      └─ (otros recursos e imágenes)
```

## Formato de archivo de entrada

El archivo de entrada debe tener el siguiente formato:

```
dic
PALABRA1
PALABRA2
...
/dic
tab
A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P
/tab
```

- Las palabras van entre las etiquetas `dic` y `/dic`.
- El tablero se define tras la etiqueta `tab`, con 16 letras separadas por coma (para un tablero 4x4).
- Puedes usar tus propios archivos `.txt` con este formato.

## ¿Cómo ejecutar?

1. **Clona este repositorio** y abre el proyecto en NetBeans, IntelliJ o tu IDE favorito.
2. **Compila el proyecto** (requiere JDK 8+).
3. **Ejecuta la clase `SopaDeLetras.java`**, que abrirá la ventana inicial.
4. Sigue las instrucciones en pantalla para cargar un archivo o iniciar una partida.

## Uso de la aplicación

- **START**: Inicia el juego y permite elegir entre cargar una nueva partida o la última guardada.
- **Cargar nueva partida**: Selecciona un archivo compatible desde tu PC.
- **Buscar palabra**: Ingresa una palabra y busca si está en el tablero y diccionario.
- **Buscar todas (DFS/BFS)**: Busca todas las palabras del diccionario en el tablero usando diferentes algoritmos.
- **Guardar en diccionario**: Añade una nueva palabra al diccionario.
- **Resaltado**: Las letras de palabras encontradas se mostrarán resaltadas en amarillo.
- **Reiniciar**: Limpia el tablero y el diccionario para comenzar de nuevo.

## Créditos

Desarrollado por:  
- [cesarilja](https://github.com/cesarilja)  
- [AlejandroSimanca](https://github.com/AlejandroSimanca)  
- [santiagosaturno](https://github.com/santiagosaturno)  