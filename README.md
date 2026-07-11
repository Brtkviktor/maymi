# 🌸 Maymi

> Plataforma Java para integração entre Minecraft, Discord e Web.

## Tecnologias

- Java 21
- Maven
- Paper API
- JDA
- Spring Boot (em breve)
- PostgreSQL (em breve)

## Arquitetura

```text
Discord
      │
      ▼
 Maymi Core
      │
 Socket TCP
      │
      ▼
Plugin Paper
      │
Minecraft
```

## Módulos

```text
maymi-common
maymi-core
maymi-paper
```

## Roadmap

- [x] Multi Module Maven
- [x] Bot Discord
- [x] Plugin Paper
- [x] Comunicação via Socket TCP
- [ ] Sistema de Eventos
- [ ] Slash Commands
- [ ] Dashboard Web
- [ ] Integração com Banco de Dados
- [ ] IA para Assistente do Servidor

## Status

🚧 Em desenvolvimento