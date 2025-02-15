# 🧪 Pruebas Automatizadas con Kotest

Este directorio contiene las pruebas automatizadas para el backend de Gamerly, utilizando el framework **Kotest**.

## 📁 Estructura de los Tests

Los tests están organizados en los siguientes paquetes:

- `unit/` → Pruebas unitarias (clases y métodos individuales)
- `integration/` → Pruebas de integración (interacción entre componentes) [Aún no cubiertas]
- `functional/` → Pruebas funcionales (flujo de trabajo) [Aún no cubiertas]

## 🚀 Configuración en `build.gradle.kts`

Para asegurarse de que Kotest está bien configurado en el proyecto, verifica que en `build.gradle.kts` se incluyan estas dependencias:

```kotlin
dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.5.5")
    testImplementation("io.kotest:kotest-assertions-core:5.5.5")
    testImplementation("io.kotest:kotest-property:5.5.5")
}
```

Además, asegúrate de usar JUnit 5 para la ejecución de pruebas:

```kotlin
tasks.withType<Test> {
    useJUnitPlatform()
}
```

## 🏃 Ejecutar Pruebas

Ejecutar todas las pruebas:
```bash
./gradlew test
```

Ejecutar solo las pruebas unitarias:
```bash
./gradlew test --tests "com.gamerly.backend.unit.*"
```

Ejecutar solo las pruebas de integración:
```bash
./gradlew test --tests "com.gamerly.backend.integration.*"
```

## 📌 Ejemplo de Test con Kotest

Aquí tienes un ejemplo de cómo escribir una prueba en Kotest:

```kotlin
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class ExampleTest : StringSpec({
    "la suma de dos números debe ser correcta" {
        val resultado = 2 + 2
        resultado shouldBe 4
    }
})
```

# 📊 Verificar la Cobertura de Pruebas con JaCoCo

Para analizar la cobertura de código de los tests, utiliza **JaCoCo** ejecutando el siguiente comando:

```bash
./gradlew jacocoTestReport
```

📌 **Ubicación del reporte:**  
Una vez generado, puedes abrir el informe de cobertura en:

```
build/reports/jacoco/test/html/index.html
```

Para visualizarlo, simplemente ábrelo en un navegador.

📢 **Nota:**  
Si el reporte no se genera, asegúrate de que **JaCoCo** está correctamente configurado en `build.gradle.kts`. Debe incluirse lo siguiente:

```kotlin
plugins {
    id("jacoco")
}

tasks.jacocoTestReport {
    reports {
        html.required.set(true)
        xml.required.set(true)
    }
}
```

✅ **Ahora puedes ejecutar tus pruebas y analizar la cobertura fácilmente!** 🚀

## 📖 Buenas Prácticas

- **Usar nombres descriptivos** en los tests.
- **Hacer que los tests sean independientes** (evitar dependencias entre ellos).
- **Limpiar los datos de prueba** al finalizar cada test.
- **Priorizar la velocidad de ejecución**, evitando llamadas innecesarias a la base de datos.
