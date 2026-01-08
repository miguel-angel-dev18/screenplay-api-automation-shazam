# 🚀 Shazam API Automation Framework

Este proyecto es un framework de automatización de alto nivel diseñado para validar los endpoints de la API de Shazam. Se ha implementado utilizando **Java 17**, **Serenity BDD** y el patrón de diseño **Screenplay**, garantizando pruebas legibles, mantenibles y con un enfoque de ingeniería de software.

## 🛠️ Stack Tecnológico y Dependencias

El proyecto utiliza un stack moderno para asegurar la compatibilidad y el rendimiento:

* **Lenguaje:** Java 17.
* **Gestión de Dependencias:** Maven.
* **Framework de BDD:** Cucumber 7 integrado con Serenity BDD.
* **Interacciones REST:** Serenity REST (basado en Rest-Assured).
* **Aserciones:** Hamcrest y JSON Schema Validator.
* **Reportes:** Serenity BDD Reports (v4.1.14).

---

## 🏗️ Decisiones de Arquitectura y Diseño

### 1. Patrón de Diseño: Screenplay
Se seleccionó **Screenplay** sobre el modelo tradicional de Page Object Model (POM) debido a que:
* **Principio de Responsabilidad Única (SOLID):** Cada clase tiene una sola razón para cambiar, facilitando el mantenimiento.
* **Actor-Centric:** Las pruebas se centran en lo que el "Actor" puede hacer y preguntar, mejorando la legibilidad.
* **Escalabilidad:** Al ser modular, permite agregar nuevos endpoints y validaciones sin afectar el código existente.

### 2. Capas del Proyecto
La arquitectura se divide en capas claras para separar responsabilidades:
* **Features:** Escenarios de negocio definidos en Gherkin.
* **Runners:** Configuración de ejecución mediante JUnit y Serenity.
* **Step Definitions:** Traducción de Gherkin a acciones de código.
* **Tasks:** Encapsulamiento de las acciones de la API (GET para detalles, POST para detección).
* **Questions:** Verificaciones del estado de la respuesta (Status Code, JSON Content).
* **Utils / Constants:** Centralización de URLs, Keys y mensajes esperados.
```
├── src
│   ├── test
│   │   ├── java
│   │   │   └── com.rappipay
│   │   │       ├── questions        # Validaciones (LastResponseContent, ResponseIsValid)
│   │   │       ├── runners          # Ejecución de pruebas (RunnerTestSuite)
│   │   │       ├── stepdefinitions  # Puentes entre Gherkin y código (SongSteps)
│   │   │       ├── tasks            # Peticiones API (DetectSong, GetSongDetails)
│   │   │       └── utils            # Clases de soporte y ayuda técnica
│   │   └── resources
│   │       ├── features             # Archivos Gherkin (song.feature)
│   │       └── schemas              # Contratos JSON para validación
├── pom.xml                          # Configuración de Maven y Dependencias
└── serenity.properties              # Propiedades del framework Serenity

```

### 3. Estrategia de Validación Exhaustiva
Para cumplir con los estándares de calidad, se implementaron:
* **Contract Testing:** Validación de **JSON Schemas** para asegurar que la estructura de la respuesta cumpla con el contrato definido.
* **Response Analysis:** Verificación de códigos de estado (200, 204, 401).
* **Header Validation:** Confirmación de metadatos como `Content-Type` y `Server`.
* **SLA Performance:** Validación de tiempos de respuesta menores a 10 segundos.

---

## 📋 Instrucciones de Ejecución

### Prerrequisitos
1.  **Java JDK 17** instalado.
2.  **Apache Maven** instalado.
3.  Configuración de la **API Key** en la clase `ApiConfig` o archivo de propiedades.

### Ejecutar todas las pruebas
Desde la terminal en la raíz del proyecto, ejecute:

```
mvn clean verify
```
Este comando limpia ejecuciones previas, ejecuta todos los escenarios y genera el reporte agregado de Serenity.

## 📊 Resultados y Reportes

Serenity genera reportes interactivos detallados que facilitan la auditoría de las pruebas.

**Ubicación del reporte:** `target/site/serenity/index.html`

### El reporte incluye:
* **REST Query Details:** Registro completo de la petición (URL, Headers, Body) y la respuesta del servidor.
* **Traceability:** Seguimiento paso a paso desde el lenguaje Gherkin hasta la ejecución técnica.

---

## 📝 Notas de Implementación

* **Manejo del Código 204:** Se validó que el endpoint de detección de canciones (`/v2/detect`) retorna `204 No Content`. El framework confirma este estado como exitoso de acuerdo con la especificación de la API.
* **Seguridad:** Los escenarios negativos validan que el sistema responde correctamente ante peticiones sin autorización (`401 Unauthorized`).
