# 5 diciembre #

  * Me propone un proyecto a largo plazo(3 años, estima) del que el tfm sería una parte.

  * Acordamos que abriré un proyecto en Assembla o similar para alojar el trabajo que vaya realizando, incluyendo resúmenes de nuestras reuniones y código, etc.

  * Acordamos que realizaré y les presentaré en 2/3 semanas una planificación del trabajo que tendré que realizar para el tfm.
    * Importante ir marcándome una serie de hitos para ir cumpliéndolos o de lo contrario esto puede no darme tiempo.

  * Decidimos que el trabajo de la asignatura de AI consistirá en buscar un modelo demográfico basado en agentes e implementarlo en MASON (sin necesidad de mayores complicaciones).
    * Así, una primera tarea de la planificación del proyecto, ha de ser ponerme al día en el estado del arte.

  * Mis tareas a corto plazo son:
    * Leer Simulation for the social scientist.
    * Leer artículos sobre demografía y modelos demográficos, no necesariamente basados en agentes (basados y no basados).
    * Realizar planificación del TFM.


# 23 abril #

  * En primer lugar comento las dificultades que he tenido y mi situación actual con respecto al trabajo: estancamiento total. Dificultades a raíz de no saber muy bien cómo enfocar el tema, falta de algo concreto.

  * Juan me comenta que, para tener algo concreto, sería bueno que comenzara por buscar una fuente de datos (bien en el INE o similar) a partir de los cuales desarrollar el modelo. Además, deben ser tales que permitan contrastar los resultados de la simulación.

  * Ver artículos no sólo de simulaciones con agentes, sino también de sociólogos sobre demografía (aquí estaba fallando un poco yo) para extraer las características comunes a los mismos y que permitan hacer luego una arquitectura flexible para tratar el mayor número de problemas (esto era la idea original).

  * Integrar GIS en el modelo.

  * Samer propone el uso de la herramienta Zotero, que ayuda para tener los artículos y las referencias bien clasificados.

  * Para cada artículo que se vaya leyendo ir guardando una reseña breve sobre su utilidad o no. Y se puede guardar el abstract para recordar de qué trata.

  * Escribir todas las semanas un email para comentar lo que estoy haciendo o lo que tengo pensado hacer en esa semana.

## Acciones para esta semana y la próxima ##
  * Encontrar datos estadísticos a partir de los cuales comenzar.
  * Artículos sobre demografía (sociológicos) y empezar a sacar características y requisitos comunes.
  * Empezar a pensar en la arquitectura.


# 14 mayo #

  * En primer lugar, les muestro un poco la estructura en la que he estado pensando. Observaciones:
    * Hay que hacer un poco más flexible ciertas cosas como los comportamientos, mediante métodos template y/o agregación de diferentes módulos que se puedan añadir y quitar dinámicamente, para adaptarse a la también dinámica de los agentes en la simulacion (por ejemplo, si una persona pasa de estar en paro a empleado, su comportamiento debería variar).
    * También debería escribir en inglés.
    * La clase persona ya debería implementar la interfaz Steppable, por dos motivos, no se reescribe innecesariamente código más abajo, y se permite así que una simulación pueda estar protagonizada por otros individuos a parte de hombres y mujeres (animales, por ejemplo).
    * De igual manera, abstraer más la noción de Persona, haciendo por encima de esa clase otra más abstracta y básica que, simplemente pueda ser ubicada en el espacio y dibujada en el mismo.
    * No obligar a que una persona tenga que tener un household, más flexibilidad.
    * Redes sociales: no pasar por alto su importancia. Que sean más distribuidas que centralizadas (tipo MASON).

  * Acordamos que para el final de la semana que viene tendré terminada una primera versión funcional del modelo, que subiré al repositorio, para en la siguiente reunión corregirla, refinarla, mejorar la arquitectura, etc.

  * Para antes del verano deberá estar terminada la aplicación (y de ser posible el estado del arte).

# 18 junio #

  * Comentamos un poco la evolución y el estado actual del trabajo: partes casi terminadas (redes sociales, comportamientos) y partes menos avanzadas (aspectos del entorno).

  * Necesidad de tener la aplicación terminada en 2 o 3 semanas y tener una planificación clara, pues el tiempo se echa encima y en agosto la cosa debe estar ya para que yo sólo tenga que redactar.

  * Centrarme en UN modelo e implementarlo mediante un applet, para que la gente a la que le pueda interesar verlo funcionando no tenga más que ir, ejecutarlo y ver el resultado.

  * Por ejemplo, implementar el problema de los Anasazi que está en versión de Marco Jansen en OpenABM.

  * De momento, para el final de esta semana, applet de algo sencillito para que se vea que el asunto funciona.