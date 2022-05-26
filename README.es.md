# Architecture Utils

## Introducción

A continuación presentamos un módulo que engloba una serie de funcionalidades que podrían ser útiles a lo largo del desarrollo de microservicios. Este módulo ayuda al usuario a simplificar su estructuración del proyecto, ahorrando tiempo de programación que se suele invertir en funcionalidades frecuentes. De este modo, mediante a la importación de una librería externa, se proporcionan al usuario dichas funcionalidades implementadas en la aplicación, y además, las importaciones de tecnologías que son utilizadas habitualmente en el ámbito de arquitectura de Onesait Tecnology. En el apartado de componentes y utilidades se listan todas aquellas utilidades que están disponibles actualmente en la librería.

## Tecnologías

Para el desarrollo del proyecto se hace uso de las siguientes tecnologías:

Maven: Para la resolución de dependencias.
Spring-boot: Para la implementación del módulo de aplicación.

## Componentes y utilidades

- Componente de excepciones y generación automática de errores HTTP para servicios REST: Para la utilización de excepciones personalizadas de manera que se unifique el uso de las mismas entre diferentes productos de arquitectura. Además de las excepciones se ha definido un handler para la excepciones que en función del tipo de excepción lanzada devuelve el código http correcto. A continuación se muestran las excepciones disponibles:
  - OnesaitException: para excepciones genéricas
  - BadRequestException (extiende de OnseaitException): Para solicitudes erróneas o corruptas
  - NotFoundException : Excepción para acceso a ontologias o BBDD en los que no se encuentra el recurso
  - UserNotFoundException : Caso en el que el usuario con existe en Identity manager. Devuelve un HttpStatus. UNAUTHORIZED (codigo 401 )
- Componente de formateo y validación de fechas: proporciona funciones para la conversión de fechas entre diferentes formatos y zonas horarias, generación de fechas a tiempo real, copiado de fechas...
- Componente de manipulación de objetos JSON: consiste en una serie de funciones que permiten el parseo y manipulación de los atributos de un objeto JSON de manera sencilla.
- Componente de Ontologías: QueryBuilder para el manejo de querys muy comunes y transversales a cualquier ontología. Ejemplo: Ordenación, Filtrado, Busqueda, etc...
- Implementación de la interfaz WebMvcConfigurer para configurar la parte web del producto.
- Swagger: Importación interna de esta herramienta para la documentación de APIs. Requiere cierta configuración en la aplicación.
- Lombok: Importación interna para la simplificación del uso de métodos de clase.
- Apache commons: Librerias de apache centradas en todos los aspectos reutilizables de los componentes de Java.

## Instrucciones de uso generales de la librería

Este proyecto tiene una versión utilizable subida al nexus de Arquitectura. Para tener acceso al nexus de arquitectura deberemos configurar maven. Para utilizar la funcionalidad que el servicio de la aplicación proporciona, deberemos seguir los siguientes pasos:

Añadir repositorios al pom.xml:

```xml
<repositories>
    <repository>
        <id>onesait-platform</id>
        <url>https://nexus.onesaitplatform.com/nexus/content/repositories/releases/</url>
    </repository>
</repositories>
```

Añadir a nuestro pom.xml la dependencia de la libreria del nexus:

```xml
<dependency>
  <groupId>com.minsait.architecture</groupId>
  <artifactId>architecture-utils</artifactId>
  <version>1.1.0</version>
</dependency>
```