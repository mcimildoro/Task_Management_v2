# Task Management Application

Una aplicación full-stack para la gestión de tareas con autenticación de usuarios y operaciones CRUD.

## 🛠 Tecnologías Utilizadas

### Frontend
- **Next.js 14** - Framework de React con Server Side Rendering
- **TypeScript** - Superset de JavaScript con tipado estático
- **Redux Toolkit** - Manejo de estado global
- **TailwindCSS** - Framework de CSS para estilos
- **Shadcn/UI** - Componentes de UI reutilizables

#### Dependencias Frontend
```bash
# UI Components and Utilities
npx shadcn@latest init
npm i @radix-ui/react-slot
npm i class-variance-authority
npm i clsx (para construir cadenas de tipo className de forma condicional)
npm i tailwindcss-animated
npm i lucide-react

# State Management
npm i @reduxjs/toolkit react-redux

# Development Dependencies
npm i -D drizzle-kit @types/bcrypt pg
```

### Backend
- **Spring Boot** - Framework de Java para el backend
- **Spring Security** - Seguridad y autenticación
- **Spring Data JPA** - Persistencia de datos
- **PostgreSQL** - Base de datos relacional

## 🏗 Patrones de Diseño Implementados

### Frontend
- **Atomic Design** - Organización de componentes en átomos, moléculas y organismos
- **Custom Hooks** - Para lógica reutilizable (useAuth, useUser)
- **Redux Slice Pattern** - Para organizar la lógica de estado
- **Container/Presentational Pattern** - Separación de lógica y presentación

### Backend
- **DTO Pattern** - Para transferencia de datos entre capas
- **Repository Pattern** - Para abstracción de la capa de datos
- **Service Layer Pattern** - Para la lógica de negocio
- **Controller Pattern** - Para el manejo de endpoints

## 📁 Estructura del Proyecto

```
task-management/
├── frontend/
│   ├── app/
│   │   ├── components/
│   │   ├── lib/
│   │   └── pages/
│   ├── public/
│   └── package.json
│
└── backend/
    ├── src/
    │   ├── main/
    │   │   ├── java/
    │   │   └── resources/
    │   └── test/
    └── pom.xml
```

## 🚀 Características

- Autenticación de usuarios (login/registro)
- CRUD completo de tareas
- Interfaz responsiva y moderna
- Estado global con Redux
- Validación de formularios
- Manejo de errores
- Loading states y feedback visual
- Protección de rutas

## 🔒 Seguridad

- Autenticación basada en JWT
- Contraseñas hasheadas con bcrypt
- CORS configurado
- Protección contra XSS
- Validación de datos en frontend y backend

## 🌟 Mejores Prácticas Implementadas

- Clean Code
- DRY (Don't Repeat Yourself)
- SOLID Principles
- Error Handling
- TypeScript para type safety
- Componentes reutilizables
- Custom hooks para lógica compartida
- Manejo de estado predictible con Redux

## 📦 Instalación y Uso

1. Clonar el repositorio
```bash
git clone [url-del-repositorio]
```

2. Instalar dependencias del frontend
```bash
cd frontend
npm install
```

3. Configurar variables de entorno
```bash
# Frontend (.env.local)
NEXT_PUBLIC_API_URL=http://localhost:8080

# Backend (application.properties)
spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
```

4. Iniciar el proyecto
```bash
# Frontend
npm run dev

# Backend
./mvnw spring-boot:run
```

## 🤝 Contribuir

Las contribuciones son bienvenidas. Por favor, abre un issue primero para discutir los cambios que te gustaría hacer.

## 📝 Licencia

[MIT](LICENSE)
