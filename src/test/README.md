# Pruebas Automatizadas con Kotest

Este directorio contiene las pruebas automatizadas para el proyecto Gamerly Backend.

## Estructura de Pruebas

- `unit`: Pruebas unitarias para componentes individuales
- `integration`: Pruebas de integración que verifican la interacción entre múltiples componentes

## Ejecución de Pruebas

Para ejecutar todas las pruebas:
```bash
./gradlew test
```

Para ejecutar un conjunto específico de pruebas:
```bash
./gradlew test --tests "com.gamerly.backend.unit.*"
```

## Añadir Nuevas Pruebas

Al añadir nuevas pruebas, sigue estas directrices:

- Coloca las pruebas unitarias en el paquete `unit` y las de integración en `integration`.
- Usa nombres descriptivos para los métodos de prueba.
- Asegúrate de que las pruebas sean independientes y puedan ejecutarse en cualquier orden.
