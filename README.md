# TRABAJO PRÁCTICO - SISTEMA DE CLIENTES CON CÁLCULO DE DISTANCIA

## 📋 DESCRIPCIÓN DEL PROYECTO
Sistema de gestión de clientes con funcionalidades de red social que incluye un mecanismo de cálculo de distancia (número de saltos) entre clientes en la red.

## ✅ FUNCIONALIDADES IMPLEMENTADAS

### 🔧 FUNCIONALIDADES BÁSICAS:
- ✅ Gestión de clientes (buscar, registrar acciones)
- ✅ Sistema de seguimientos y solicitudes
- ✅ Conexiones y amistades entre clientes
- ✅ Historial de acciones
- ✅ Gestión de privacidad

### 🆕 FUNCIONALIDADES NUEVAS - CÁLCULO DE DISTANCIA:
- ✅ **Opción 14**: 📏 Calcular distancia entre clientes
- ✅ **Opción 15**: 🗺️ Mostrar camino más corto entre clientes

## 🎯 ALGORITMO IMPLEMENTADO
**BFS (Breadth-First Search)** para encontrar el camino más corto entre dos clientes:
- Retorna el número mínimo de saltos necesarios
- Maneja casos especiales (mismo cliente, sin conexión, clientes inexistentes)
- Considera todas las conexiones: siguiendo, conexiones y amistades

## 🚀 CÓMO EJECUTAR EL PROGRAMA

### Compilar:
```bash
javac -cp "lib/*" -d bin src/*.java
```

### Ejecutar:
```bash
java -cp "bin;lib/*" App
```

## 📊 CASOS DE PRUEBA PARA DISTANCIA

### Con los datos del JSON (German, Sofi, Marco, Valen, Nati):
- German → Marco: 1 salto (conexión directa)
- German → Nati: 2 saltos (German → Marco → Nati)
- Sofi → Nati: 2 saltos (Sofi → Marco → Nati)
- German → German: 0 saltos (mismo cliente)

### Casos de error:
- Cliente inexistente: retorna -2
- Sin conexión: retorna -1
- Parámetros nulos: retorna -2

## 📁 ESTRUCTURA DE ARCHIVOS

```
src/
├── App.java                    # Clase principal con menú
├── Cliente.java               # Modelo de cliente
├── GestorClientes.java          # Gestión de clientes + ALGORITMO BFS
├── RedSocial.java     # Lógica de negocio
├── ManejodeJson.java          # Carga de datos JSON
├── Accion.java               # Modelo de acción
├── HistorialDeAcciones.java  # Gestión de historial
├── ColaDeSeguimientos.java   # Gestión de seguimientos
├── Seguimiento.java          # Modelo de seguimiento
├── SolicitudPendiente.java   # Modelo de solicitud
└── Listadeclientes.json      # Datos de clientes

bin/                          # Archivos compilados
lib/                          # Librerías (Gson, JUnit)
test/                         # Tests unitarios
```

## 🎨 MEJORAS VISUALES
- Menú con emojis para mejor experiencia de usuario
- Formato de salida profesional con marcos ASCII
- Mensajes de error claros y descriptivos

## 👨‍💻 IMPLEMENTACIÓN TÉCNICA
- **Algoritmo**: BFS implementado en `GestorClientes.calcularDistancia()`
- **Validaciones**: Manejo completo de errores y casos edge
- **Interfaz**: Integración perfecta con el sistema existente
- **Eficiencia**: O(V + E) donde V = vértices, E = aristas

---
**PROYECTO COMPLETADO Y LISTO PARA ENTREGA** ✅

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
