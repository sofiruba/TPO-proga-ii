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
The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
