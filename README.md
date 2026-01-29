# bcncGroupTest

**Test Proyecto BCNC Group - Spain**

Aplicación Spring Boot que implementa una arquitectura hexagonal para gestionar productos y precios. Proporciona una API REST documentada con OpenAPI/Swagger para consultar información de precios de productos según fecha, cadena y producto específico.

---
---

## 📖 Descripción General

Este proyecto es una aplicación **Spring Boot 4.0.2** que implementa una solución para gestionar y recuperar información de precios de productos. 

**Casos de uso principales:**
- Obtener el precio de un producto específico para una cadena/marca determinada en una fecha concreta
- Aplicar lógica de prioridad y rango de fechas para determinar el precio vigente
- Exponer la funcionalidad a través de una API REST documentada con OpenAPI 3.0

---

## ✨ Características Principales

- ✅ **Arquitectura Hexagonal**: Separación clara entre capas (dominio, aplicación, infraestructura)
- ✅ **API REST RESTful**: Endpoints documentados con OpenAPI/Swagger
- ✅ **Base de Datos H2**: Base de datos embebida para pruebas y desarrollo
- ✅ **Mapeo de Entidades**: Uso de DTOs para desacoplamiento entre capas
- ✅ **Lombok**: Reducción de código boilerplate (getters, setters, constructores)
- ✅ **Logging y Auditoría**: Trazabilidad de operaciones
- ✅ **Pruebas Unitarias**: Con JUnit 5 y Mockito (infraestructura lista)
- ✅ **Consulta Dinámina de Precios**: Filtro por fecha, producto y cadena

---

## 🏗️ Arquitectura

El proyecto sigue el patrón **Arquitectura Hexagonal (Puertos y Adaptadores)**, también conocida como **Clean Architecture**. Este enfoque separa completamente la lógica de negocio de los detalles técnicos.

### Diagrama Completo de Capas

```
┌──────────────────────────────────────────────────────────────┐
│                   CLIENTE / CONSUMIDOR                        │
│         (Browser, Cliente HTTP, Postman, Mobile App)          │
└────────────────────────────┬─────────────────────────────────┘
                             │
                    ┌────────▼────────┐
                    │  HTTP REQUEST   │
                    └────────┬────────┘
                             │
┌──────────────────────────────────────────────────────────────┐
│             🔴 CAPA REST (PUERTO DE ENTRADA)                  │
│                  ProductoController                           │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ - @GetMapping("/api/v1/productos")                   │   │
│  │ - Recibe parámetros HTTP: fechaAplicacion, etc.      │   │
│  │ - Valida parámetros (obligatorios, formatos)         │   │
│  │ - Delega al servicio                                 │   │
│  │ - Serializa respuesta a JSON                         │   │
│  │ - Maneja códigos HTTP (200, 400, 404, 500)           │   │
│  └──────────────────────────────────────────────────────┘   │
└────────────────────────────┬─────────────────────────────────┘
                             │
                    ┌────────▼────────────┐
                    │ ProductoDto (DTO)   │
                    │ Datos transferencia │
                    └────────┬────────────┘
                             │
┌──────────────────────────────────────────────────────────────┐
│           🟡 CAPA APLICACIÓN (ORQUESTACIÓN)                   │
│          ProductoService (implementa IProductoUseCase)       │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ Responsabilidades:                                   │   │
│  │ - obtenerProducto(fechaAplicacion, productoId, cadenaId)│
│  │ - Coordina ProductoJpaAdapter (infraestructura)       │   │
│  │ - Mapea datos con ProductoDtoMapper                  │   │
│  │ - Gestiona transacciones                             │   │
│  │ - Aplica lógica de aplicación                         │   │
│  │ - Manejo de excepciones                              │   │
│  └──────────────────────────────────────────────────────┘   │
│                                                                │
│  ProductoDtoMapper:                                          │
│  ├─ toDto(Producto) → ProductoDto                            │
│  └─ toEntity(ProductoDto) → Producto                         │
└────────────────────────────┬─────────────────────────────────┘
                             │
                    ┌────────▼────────────┐
                    │ Producto (Domain)   │
                    │ Entidad de negocio  │
                    └────────┬────────────┘
                             │
┌──────────────────────────────────────────────────────────────┐
│         🟢 CAPA DOMINIO (LÓGICA DE NEGOCIO)                   │
│            (Completamente independiente)                     │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ Producto.java:                                       │   │
│  │ - productoId, cadenaId, priceList                    │   │
│  │ - precio, fechaInicio, fechaFin                      │   │
│  │ - Representa un producto con su precio vigente       │   │
│  │                                                       │   │
│  │ ProductoDto.java:                                    │   │
│  │ - Copia de Producto para REST                        │   │
│  │ - Sin detalles de persistencia                        │   │
│  │                                                       │   │
│  │ IProductoPort.java (Interfaz/Contrato):              │   │
│  │ - obtenerProducto(String, int, int): Producto        │   │
│  │ - Define cómo acceder a datos (sin detalles)         │   │
│  │                                                       │   │
│  │ Reglas de negocio:                                   │   │
│  │ - Validar rango de fechas (START_DATE - END_DATE)    │   │
│  │ - Aplicar PRIORITY (mayor valor = mayor prioridad)   │   │
│  │ - Seleccionar precio vigente en fecha solicitada     │   │
│  └──────────────────────────────────────────────────────┘   │
└────────────────────────────┬─────────────────────────────────┘
                             │
                    ┌────────▼──────────────┐
                    │ IProductoPort         │
                    │ (Interfaz/Contrato)   │
                    └────────┬──────────────┘
                             │
┌──────────────────────────────────────────────────────────────┐
│        🔵 CAPA INFRAESTRUCTURA (ADAPTADORES)                  │
│              (Detalles técnicos/Implementación)              │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ ProductoJpaAdapter.java:                             │   │
│  │ - Implementa IProductoPort                           │   │
│  │ - Traduce Producto (domain) ↔ PrecioEntity (BD)      │   │
│  │ - Invoca ProductoRepository                          │   │
│  │ - Ejecuta lógica de filtrado                         │   │
│  │                                                       │   │
│  │ ProductoRepository (Spring Data JPA):                │   │
│  │ - extends JpaRepository<PrecioEntity, Long>          │   │
│  │ - findByProductoIdAndCadenaId(int, int)              │   │
│  │ - Consultas personalizadas                           │   │
│  │ - Auto-genera SQL                                    │   │
│  │                                                       │   │
│  │ PrecioEntity (Entidad JPA):                          │   │
│  │ - @Entity, @Table("prices")                          │   │
│  │ - Mapeo O/R: Java ↔ Base de Datos                    │   │
│  │ - Anotaciones: @Id, @Column, @Temporal               │   │
│  └──────────────────────────────────────────────────────┘   │
└────────────────────────────┬─────────────────────────────────┘
                             │
                    ┌────────▼──────────────┐
                    │   H2 Database         │
                    │  (BD embebida)        │
                    │   Tabla: prices       │
                    └───────────────────────┘
```

### Explicación Detallada de Capas

#### 1. **🔴 Capa REST (Puerto de Entrada)**

**Ubicación:** `src/main/java/com/project/bcngroup/infraestructure/rest/`

**Componentes:**
- `ProductoController.java`: Controlador REST principal

**Responsabilidades:**
- Recibir peticiones HTTP GET/POST
- Validar parámetros (tipos, valores obligatorios)
- Serializar/Deserializar JSON
- Mapear parámetros HTTP a argumentos de método
- Devolver códigos HTTP apropiados (200, 400, 404, 500)

**Ejemplo de Solicitud:**
```
GET /api/v1/productos?fechaAplicacion=2020-06-14T10:00:00&productoId=35455&cadenaId=1
Authorization: Bearer token (opcional)
```

**Ejemplo de Respuesta:**
```
HTTP/1.1 200 OK
Content-Type: application/json

{
  "productoId": 35455,
  "cadenaId": 1,
  "priceList": 1,
  "precio": 29.99,
  "fechaInicio": "2020-06-14T10:00:00",
  "fechaFin": "2020-12-31T23:59:59"
}
```

#### 2. **🟡 Capa Aplicación (Orquestación)**

**Ubicación:** `src/main/java/com/project/bcngroup/application/`

**Componentes:**
- `ProductoService.java`: Servicio principal (implementa `IProductoUseCase`)
- `ProductoDtoMapper.java`: Mapper entre Producto y ProductoDto
- `IProductoUseCase.java`: Interfaz de casos de uso

**Responsabilidades:**
- Orquestar la lógica: combinar infraestructura y dominio
- Mapear DTOs ↔ Entidades
- Gestionar transacciones (@Transactional)
- Aplicar validaciones de aplicación
- Manejar excepciones

**Flujo en ProductoService:**
```java
public ProductoDto obtenerProducto(String fechaAplicacion, int productoId, int cadenaId) {
    1. Validar parámetros
    2. Llamar ProductoJpaAdapter.obtenerProducto()
    3. Recibir Producto (entity de dominio)
    4. Mapear con ProductoDtoMapper
    5. Devolver ProductoDto
}
```

#### 3. **🟢 Capa Dominio (Lógica de Negocio)**

**Ubicación:** `src/main/java/com/project/bcngroup/domain/`

**Componentes:**
- `model/Producto.java`: Entidad de dominio
- `dto/ProductoDto.java`: Data Transfer Object
- `port/IProductoPort.java`: Interfaz/Contrato de persistencia

**Responsabilidades:**
- Definir la lógica de negocio pura
- Validaciones de dominio
- Reglas de precios y fechas
- Ser completamente independiente de frameworks


#### 4. **🔵 Capa Infraestructura (Adaptadores)**

**Ubicación:** `src/main/java/com/project/bcngroup/infraestructure/`

**Componentes:**
- `adapter/ProductoJpaAdapter.java`: Adaptador que implementa `IProductoPort`
- `adapter/repository/ProductoRepository.java`: Spring Data JPA
- `adapter/entity/PrecioEntity.java`: Entidad JPA mapeada a BD

**Responsabilidades:**
- Implementar detalles técnicos de persistencia
- Traducir entre objetos de dominio y entidades JPA
- Generar queries SQL
- Manejar conexiones a BD
- Transformaciones O/R (Object-Relational Mapping)

**Flujo en ProductoJpaAdapter:**
```java
public Producto obtenerProducto(String fechaAplicacion, int productoId, int cadenaId) {
    1. Parsear fecha string → LocalDateTime
    2. Llamar ProductoRepository.findBy(filtros)
    3. Recibir PrecioEntity de BD
    4. Transformar PrecioEntity → Producto (dominio)
    5. Devolver Producto
}
```

### 3. Ejecutar la Aplicación

**La aplicación estará disponible en:**
- 🌐 API: `http://localhost:8080`
- 📊 Swagger UI: `http://localhost:8080/swagger-ui.html`
- 📄 OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- 💾 Consola H2: `http://localhost:8080/h2-console`

---

## 📂 Estructura del Proyecto

```
bcncGroupTest/
├── src/
│   ├── main/
│   │   ├── java/com/project/bcngroup/
│   │   │   ├── BcngroupApplication.java          (Clase principal Spring Boot)
│   │   │   ├── domain/                            (Capa de Dominio)
│   │   │   │   ├── model/
│   │   │   │   │   └── Producto.java             (Entidad de negocio)
│   │   │   │   ├── dto/
│   │   │   │   │   └── ProductoDto.java          (DTO para REST)
│   │   │   │   └── port/
│   │   │   │       └── IProductoPort.java        (Interfaz de persistencia)
│   │   │   ├── application/                       (Capa de Aplicación)
│   │   │   │   ├── service/
│   │   │   │   │   └── ProductoService.java      (Servicio principal)
│   │   │   │   ├── usecase/
│   │   │   │   │   └── IProductoUseCase.java     (Interfaz de casos de uso)
│   │   │   │   └── mapper/
│   │   │   │       └── ProductoDtoMapper.java    (Mapeo Entidad ↔ DTO)
│   │   │   └── infraestructure/                   (Capa de Infraestructura)
│   │   │       ├── adapter/
│   │   │       │   ├── ProductoJpaAdapter.java   (Implementa IProductoPort)
│   │   │       │   ├── entity/
│   │   │       │   ├── mapper/
│   │   │       │   └── repository/
│   │   │       │       └── ProductoRepository.java (Spring Data JPA)
│   │   │       ├── rest/
│   │   │       │   └── ProductoController.java   (Controlador REST)
│   │   │       ├── commons/
│   │   │       │   └── Utils.java                (Utilidades)
│   │   │       └── configuration/
│   │   └── resources/
│   │       ├── application.yaml                  (Configuración app)
│   │       ├── schema.sql                        (Script creación BD)
│   │       └── data.sql                          (Script datos iniciales)
│   └── test/
│       └── java/com/project/bcngroup/
│           ├── BcngroupApplicationTests.java
│           └── infraestructure/rest/
│               └── ProductoControllerTest.java   (Tests del controlador)
├── pom.xml                                        (Configuración Maven)
├── mvnw / mvnw.cmd                               (Maven Wrapper)
└── README.md                                      (Este archivo)
```

---

## 💾 Base de Datos

### Visión General

El proyecto utiliza **H2 Database** (embebida, en memoria). H2 es ideal para:
- ✅ Desarrollo local sin configuración externa
- ✅ Testing automatizado rápido
- ✅ Prototipos y POCs
- ✅ Independencia del entorno

**Ubicación de scripts:**
- `schema.sql`: Estructura de tablas (DDL)
- `data.sql`: Datos iniciales (DML)

### Tabla `prices` - Esquema Detallado

```sql
CREATE TABLE IF NOT EXISTS prices (
    PRICE_ID BIGINT PRIMARY KEY AUTO_INCREMENT,
    BRAND_ID INT NOT NULL,
    START_DATE TIMESTAMP NOT NULL,
    END_DATE TIMESTAMP NOT NULL,
    PRICE_LIST INT NOT NULL,
    PRODUCT_ID INT NOT NULL,
    PRIORITY INT NOT NULL,
    PRICE DECIMAL(10, 2) NOT NULL,
    CURR VARCHAR(10) NOT NULL
);
```

### Descripción Detallada de Campos

| Campo | Tipo | Restricciones | Descripción | Ejemplo |
|-------|------|-----------------|-------------|---------|
| **PRICE_ID** | BIGINT | PRIMARY KEY AUTO_INCREMENT | Identificador único auto-generado | 1, 2, 3, ... |
| **BRAND_ID** | INT | NOT NULL, ÍNDICE | Identificador de la cadena/marca comercial | 1 (Cadena 1), 2 (Cadena 2) |
| **START_DATE** | TIMESTAMP | NOT NULL, ÍNDICE | Fecha y hora de inicio de vigencia | 2020-06-14 00:00:00 |
| **END_DATE** | TIMESTAMP | NOT NULL, ÍNDICE | Fecha y hora de fin de vigencia | 2020-12-31 23:59:59 |
| **PRICE_LIST** | INT | NOT NULL | Identificador de lista de precios (tarifa) | 1, 2, 3... (diferentes tarifas) |
| **PRODUCT_ID** | INT | NOT NULL, ÍNDICE | Identificador único del producto (SKU) | 35455, 38423, etc. |
| **PRIORITY** | INT | NOT NULL | Prioridad de aplicación (conflictos) | 1 (baja), 2 (media), 3 (alta) |
| **PRICE** | DECIMAL(10,2) | NOT NULL | Precio del producto (10 dígitos, 2 decimales) | 29.99, 149.50, 0.99 |
| **CURR** | VARCHAR(10) | NOT NULL | Código de moneda (ISO 4217) | EUR, USD, GBP, JPY |

### Consulta SQL Generada Automáticamente

El adaptador genera una query similar a:

```sql
SELECT * FROM prices
WHERE PRODUCT_ID = ?
  AND BRAND_ID = ?
  AND START_DATE <= ?
  AND END_DATE >= ?
ORDER BY PRIORITY DESC
LIMIT 1;
```

**Con datos reales:**
```sql
SELECT * FROM prices
WHERE PRODUCT_ID = 35455
  AND BRAND_ID = 1
  AND START_DATE <= '2021-01-20 14:30:00'
  AND END_DATE >= '2021-01-20 14:30:00'
ORDER BY PRIORITY DESC
LIMIT 1;
```

### Carga de Datos en H2

**Durante el inicio de la aplicación:**

1. Spring Boot lee `application.yaml`
   ```yaml
   spring:
     jpa:
       hibernate:
         ddl-auto: none  # No auto-crea, usa scripts
   ```

2. Se ejecuta `schema.sql` para crear tabla
3. Se ejecuta `data.sql` para insertar datos
4. BD lista en memoria para transacciones

**Acceso a la consola H2:**
- URL: `http://localhost:8080/h2-console`
- Driver: `org.h2.Driver`
- URL JDBC: `jdbc:h2:mem:~/bcncgroup`
- Usuario: `sa`
- Contraseña: (vacía)

**Consultas útiles en consola H2:**
```sql
-- Ver todas las filas
SELECT * FROM prices;

-- Ver productos disponibles
SELECT DISTINCT PRODUCT_ID FROM prices;

-- Ver cadenas disponibles
SELECT DISTINCT BRAND_ID FROM prices;

-- Contar registros
SELECT COUNT(*) FROM prices;

-- Precios por product
SELECT PRODUCT_ID, COUNT(*) as total FROM prices GROUP BY PRODUCT_ID;
```

### Migraciones Futuras

Si necesitas cambiar de BD (PostgreSQL, MySQL):

```yaml
# Cambiar dependencia en pom.xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>

# Actualizar application.yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/bcncgroup
    username: usuario
    password: contraseña
    driver-class-name: org.postgresql.Driver
  jpa:
    database-platform: org.hibernate.dialect.PostgreSQLDialect
```

Los scripts SQL (`schema.sql`, `data.sql`) funcionarían con mínimas modificaciones.

---

## 🌐 API REST

### Descripción General

La API proporciona un único endpoint para consultar precios vigentes de productos. Utiliza una arquitectura REST pura con métodos HTTP estándar.

**Base URL:** `http://localhost:8080`  
**Versión API:** `v1`  
**Formato:** JSON

### Endpoint Único: Obtener Producto y su Precio

#### Definición

| Propiedad | Valor |
|-----------|-------|
| **Método HTTP** | `GET` |
| **Ruta** | `/api/v1/productos` |
| **Descripción** | Obtiene el precio vigente de un producto para una cadena en una fecha específica |
| **Autenticación** | No |
| **Rate Limiting** | No |

#### Parámetros de Consulta (Query Parameters)

```
GET /api/v1/productos?fechaAplicacion=2020-06-14T10:00:00&productoId=35455&cadenaId=1
```

| Parámetro | Tipo | Obligatorio | Descripción | Formato | Ejemplo |
|-----------|------|-------------|-------------|---------|---------|
| `fechaAplicacion` | String | ✅ Sí | Fecha de consulta para validar vigencia | ISO-8601 DateTime | `2020-06-14T10:00:00` |
| `productoId` | Integer | ✅ Sí | Identificador del producto (SKU) | Número entero > 0 | `35455` |
| `cadenaId` | Integer | ✅ Sí | Identificador de la cadena/marca | Número entero > 0 | `1`, `2`, `3` |

#### Reglas de Validación

```
✓ fechaAplicacion:
  - Obligatorio
  - Formato ISO-8601: YYYY-MM-DDTHH:mm:ss
  - Puede incluir zona horaria (Z, +02:00, etc.)
  - No puede ser nula

✓ productoId:
  - Obligatorio
  - Debe ser > 0
  - Tipo: Integer
  - Rango: 1 a 2,147,483,647

✓ cadenaId:
  - Obligatorio
  - Debe ser > 0
  - Tipo: Integer
  - Rango: 1 a 2,147,483,647
```

### Respuesta Exitosa (HTTP 200 OK)

**Estructura:**
```json
{
  "productoId": 35455,
  "cadenaId": 1,
  "priceList": 1,
  "precio": 29.99,
  "fechaInicio": "2020-06-14T10:00:00",
  "fechaFin": "2020-12-31T23:59:59"
}
```

**Descripción de campos de respuesta:**

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `productoId` | Integer | Identificador del producto solicitado |
| `cadenaId` | Integer | Identificador de la cadena solicitada |
| `priceList` | Integer | Lista de precios aplicada |
| `precio` | Double | Precio vigente en la fecha solicitada |
| `fechaInicio` | LocalDateTime | Fecha de inicio de vigencia |
| `fechaFin` | LocalDateTime | Fecha de fin de vigencia |

### Respuestas de Error

#### 400 Bad Request - Parámetro Inválido

```
GET /api/v1/productos?fechaAplicacion=fecha-invalida&productoId=35455&cadenaId=1
```

**Respuesta:**
```json
{
  "error": "Bad Request",
  "message": "Error parsing date. Expected format: ISO-8601 (YYYY-MM-DDTHH:mm:ss)",
  "timestamp": "2024-01-28T14:30:00"
}
```

#### 400 Bad Request - Parámetro Faltante

```
GET /api/v1/productos?productoId=35455&cadenaId=1
```

**Respuesta:**
```json
{
  "error": "Bad Request",
  "message": "Required parameter 'fechaAplicacion' is missing",
  "timestamp": "2024-01-28T14:30:00"
}
```

#### 400 Bad Request - Valor Negativo

```
GET /api/v1/productos?fechaAplicacion=2020-06-14T10:00:00&productoId=-35455&cadenaId=1
```

**Respuesta:**
```json
{
  "error": "Bad Request",
  "message": "productoId must be greater than 0",
  "timestamp": "2024-01-28T14:30:00"
}
```

#### 404 Not Found - Precio No Existe

```
GET /api/v1/productos?fechaAplicacion=2024-01-28T14:30:00&productoId=99999&cadenaId=1
```

**Respuesta:**
```json
{
  "error": "Not Found",
  "message": "No price found for product 99999, brand 1 on date 2024-01-28T14:30:00",
  "timestamp": "2024-01-28T14:30:00"
}
```

#### 500 Internal Server Error

```json
{
  "error": "Internal Server Error",
  "message": "An unexpected error occurred. Please contact support.",
  "timestamp": "2024-01-28T14:30:00"
}
```
---

## 📊 Documentación OpenAPI/Swagger

### Acceso a la Documentación Interactiva

1. **Swagger UI (Interfaz Gráfica):**
   - URL: `http://localhost:8080/swagger-ui.html`
   - Permite probar los endpoints directamente desde el navegador

2. **OpenAPI JSON:**
   - URL: `http://localhost:8080/v3/api-docs`
   - Útil para integración con herramientas de terceros

3. **Redoc (Documentación alternativa - opcional):**
   - URL: `http://localhost:8080/v3/api-docs.yaml`

### Configuración en `application.yaml`

```yaml
springdoc:
  swagger-ui:
    path: /swagger-ui.html
    enabled: true
    operations-sorter: method
    tags-sorter: alpha
    display-request-duration: true
  api-docs:
    path: /v3/api-docs
  show-actuator: false
```

---

### Ejecución de Tests

#### Ejecutar Todos los Tests

```bash
# Con Maven wrapper
./mvnw test

# Con Maven instalado
mvn test

# Con salida verbose
./mvnw test -X
```

#### Ejecutar Test Específico

```bash
# Por nombre de clase
./mvnw test -Dtest=ProductoControllerTest

# Por nombre de método
./mvnw test -Dtest=ProductoControllerTest#testObtenerProductos_Exitoso

# Por patrón
./mvnw test -Dtest=*Controller*
```

#### Ejecutar Tests con Reporte

```bash
# Generar reporte de cobertura con JaCoCo
./mvnw test jacoco:report

# Ver reporte en: target/site/jacoco/index.html

# Con Maven Surefire (reporte HTML)
./mvnw test surefire-report:report

# Ver reporte en: target/site/surefire-report.html
```

#### Tests en Modo Watch (Detecta cambios)

```bash
# Ejecutar tests automáticamente al guardar archivos
./mvnw test -Dmaven.surefire.debug

# O con IDE (si lo soporta):
# IntelliJ: Run → Rerun Failing Tests (Ctrl+F10)
# Eclipse: Right-click → Run Tests
```

### Casos de Prueba Cubiertos

#### Casos Exitosos ✓

- [x] Obtener precio válido con parámetros correctos
- [x] Obtener precio con diferentes cadenas
- [x] Obtener precio con diferentes productos
- [x] Obtener precio en fechas diferentes (con PRIORITY)
- [x] Respuesta serializada correctamente a JSON

#### Casos de Error ✓

- [x] Parámetro obligatorio faltante
- [x] Formato de fecha inválido
- [x] ID de producto negativo
- [x] ID de cadena negativo
- [x] Precio no encontrado para combinación
- [x] Respuesta HTTP 404
- [x] Respuesta HTTP 400

---

## ⚙️ Configuración

### Archivo: `application.yaml`

```yaml
spring:
  application:
    name: bcngroup
  datasource:
    driver-class-name: org.h2.Driver
    username: sa
    url: jdbc:h2:mem:~/bcncgroup
    password: ''
  h2:
    console:
      enabled: true
      path: /h2-console
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
    hibernate:
      ddl-auto: none

springdoc:
  swagger-ui:
    path: /swagger-ui.html
    enabled: true
```

**Propiedades importantes:**
- `spring.datasource.url`: URL de conexión H2 (en memoria)
- `spring.h2.console.enabled`: Habilita consola web de H2
- `spring.jpa.hibernate.ddl-auto`: `none` (scripts SQL manuales)
- `springdoc.swagger-ui.path`: Ruta personalizada de Swagger

---

## 🔧 Tecnologías Utilizadas

| Tecnología | Versión | Descripción |
|-----------|---------|-------------|
| **Java** | 21 | Lenguaje de programación |
| **Spring Boot** | 4.0.2 | Framework web |
| **Spring Data JPA** | 4.0.2 | Persistencia de datos |
| **H2 Database** | - | Base de datos embebida |
| **Lombok** | - | Generación de código |
| **Springdoc OpenAPI** | 2.8.13 | Documentación API/Swagger |
| **JUnit 5** | - | Framework de testing |
| **Mockito** | - | Mock objects para tests |
| **Maven** | 3.8+ | Gestor de dependencias |

---

---

## 🛠️ Guía de Desarrollo Local

### Convenciones de Código

#### Naming Conventions

```java
// Paquetes: todos minúsculas, separados por punto
package com.project.bcngroup.infraestructure.adapter;

// Clases: PascalCase, sustantivos
public class ProductoService { }
public class ProductoDtoMapper { }
public class IProductoPort { }  // Interfaces comienzan con I

// Métodos: camelCase, verbos
public ProductoDto obtenerProducto(String fecha, int productoId, int cadenaId) { }
public void crearProducto(ProductoDto producto) { }
public boolean validarFecha(LocalDateTime fecha) { }

// Constantes: UPPER_SNAKE_CASE
public static final String API_VERSION = "v1";
public static final int MAX_RETRY = 3;
public static final String DEFAULT_CURRENCY = "EUR";

// Variables: camelCase
LocalDateTime fechaAplicacion;
int productoId;
ProductoDto productoDto;
```

#### Anotaciones y Decoradores

```java
// Controlador
@RestController
@RequestMapping("/api/v1/productos")
@Tag(name = "Productos")
public class ProductoController { }

// Servicio
@Service
@AllArgsConstructor
public class ProductoService implements IProductoUseCase { }

// Adaptador
@Component
@AllArgsConstructor
public class ProductoJpaAdapter implements IProductoPort { }

// Repositorio
@Repository
public interface ProductoRepository extends JpaRepository<PrecioEntity, Long> { }

// Entidad
@Entity
@Table(name = "prices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrecioEntity { }

// Métodos
@Override
public ProductoDto obtenerProducto(String fecha, int id, int cadena) { }

@Query("SELECT p FROM PrecioEntity p WHERE p.productoId = :id")
public List<PrecioEntity> findByProductoId(@Param("id") int id);
```

#### Estructura de Métodos

```java
public ProductoDto obtenerProducto(String fechaAplicacion, int productoId, int cadenaId) {
    // 1. Validar parámetros
    if (fechaAplicacion == null || fechaAplicacion.isEmpty()) {
        throw new IllegalArgumentException("fechaAplicacion no puede ser nula");
    }
    if (productoId <= 0 || cadenaId <= 0) {
        throw new IllegalArgumentException("IDs deben ser mayores que 0");
    }
    
    // 2. Llamar a dependencias
    var mapper = new ProductoDtoMapper();
    var producto = productoJpaAdapter.obtenerProducto(fechaAplicacion, productoId, cadenaId);
    
    // 3. Transformar resultado
    return mapper.toDto(producto);
}
```

---

---

## 👤 Autor

**Brayan Gallardo Vidal**

Proyecto técnico de prueba de conocimiento.

**Contacto:**
- Email: brayan.20.bgv@gmail.com

---

