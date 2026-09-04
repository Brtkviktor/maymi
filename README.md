# 🌸 Maymi

> Plataforma Java orientada a eventos para integração entre Minecraft, Discord e serviços externos.

![Java](https://img.shields.io/badge/Java-21-orange)
![Minecraft](https://img.shields.io/badge/Minecraft-1.21.1-green)
![NeoForge](https://img.shields.io/badge/NeoForge-21.1-blue)
![Discord](https://img.shields.io/badge/Discord-JDA-5865F2)
![Database](https://img.shields.io/badge/Database-SQLite-lightgrey)
![Status](https://img.shields.io/badge/status-em%20desenvolvimento-yellow)

---

## 📖 Sobre o projeto

**Maymi** é uma plataforma backend desenvolvida em Java para conectar servidores Minecraft a serviços externos, principalmente Discord.

O projeto começou como um bot para gerenciamento de uma comunidade Minecraft, mas evoluiu para uma arquitetura modular capaz de receber eventos do jogo, processá-los no **Maymi Core**, persistir informações dos jogadores e disponibilizar esses dados para outras integrações.

Atualmente, a comunicação entre Minecraft e o Core utiliza **sockets TCP e pacotes JSON**, mantendo a lógica principal desacoplada da implementação específica do servidor Minecraft.

A Maymi também funciona como um projeto de estudo e portfólio, explorando conceitos de arquitetura de software, comunicação entre aplicações, persistência, testes automatizados e sistemas orientados a eventos.

---

## 🏗️ Arquitetura

```text
                   ┌─────────────────────┐
                   │      Minecraft      │
                   │  NeoForge / Paper   │
                   └──────────┬──────────┘
                              │
                       Eventos do jogo
                              │
                              ▼
                   ┌─────────────────────┐
                   │    Socket / JSON    │
                   │   Packet Protocol   │
                   └──────────┬──────────┘
                              │
                              ▼
                 ┌─────────────────────────┐
                 │       MAYMI CORE        │
                 │                         │
                 │   Packet Dispatcher     │
                 │   Event Bus             │
                 │   Services              │
                 │   Progression           │
                 │   Achievements          │
                 │   Mod Integrations      │
                 └───────┬────────┬────────┘
                         │        │
                ┌────────┘        └─────────┐
                ▼                           ▼
        ┌───────────────┐           ┌───────────────┐
        │    SQLite     │           │    Discord    │
        │ Persistence   │           │      JDA      │
        └───────────────┘           └───────────────┘
```

Essa separação permite que o **Maymi Core não dependa diretamente das APIs internas do Minecraft**.

Os módulos responsáveis pelo jogo transformam eventos do Minecraft em mensagens que podem ser interpretadas pelo Core.

---

## 🧩 Módulos

### `maymi-common`

Biblioteca compartilhada entre os diferentes componentes da Maymi.

Responsável por:

- definição dos packets;
- tipos de mensagens;
- serialização e desserialização;
- protocolo JSON;
- estruturas compartilhadas de comunicação.

---

### `maymi-core`

É o núcleo da plataforma.

Responsável por:

- processamento dos packets;
- Event Bus;
- persistência;
- estatísticas dos jogadores;
- sistema de XP e níveis;
- achievements;
- histórico de eventos;
- integração com Discord;
- rankings e perfis;
- dashboard;
- detecção e integração de conteúdo de mods.

O Core foi projetado para permanecer independente da implementação utilizada pelo servidor Minecraft.

---

### `maymi-neoforge`

Integração atual da Maymi com **Minecraft 1.21.1 utilizando NeoForge**.

Captura eventos do jogo como:

- entrada de jogadores;
- saída de jogadores;
- mortes;
- mobs derrotados;
- blocos quebrados;
- blocos colocados.

Esses eventos são convertidos em packets e enviados ao Maymi Core.

---

### `maymi-paper`

Implementação original da integração com Minecraft utilizando **Paper API**.

O módulo continua no repositório como parte da evolução arquitetural do projeto, enquanto o desenvolvimento atual está sendo direcionado à integração com NeoForge.

---

## ⚙️ Principais funcionalidades

### 👤 Jogadores

A Maymi mantém informações persistentes relacionadas aos jogadores, incluindo:

- UUID;
- nome;
- quantidade de logins;
- sessões;
- estatísticas;
- progressão.

---

### 📊 Estatísticas

Eventos recebidos do Minecraft atualizam automaticamente estatísticas como:

- mortes;
- mobs derrotados;
- blocos quebrados;
- blocos colocados;
- tempo de jogo.

---

### ⭐ XP e níveis

A plataforma possui um sistema próprio de progressão.

Eventos de gameplay podem conceder XP e o Core é responsável por:

- calcular progressão;
- detectar level up;
- detectar level down;
- aplicar penalidades de XP;
- emitir eventos relacionados à progressão.

---

### 🏆 Achievements

Sistema de conquistas integrado às estatísticas dos jogadores.

O Core possui:

- catálogo de achievements;
- categorias;
- raridades;
- métricas;
- persistência;
- detecção de desbloqueio;
- notificações pelo Discord.

---

### 📜 Histórico

Eventos importantes são armazenados no histórico do jogador, como:

- login;
- logout;
- morte;
- level up;
- level down;
- achievement desbloqueado.

---

### 🔔 Event Bus

O Core utiliza um sistema interno de eventos para reduzir o acoplamento entre funcionalidades.

```text
Packet recebido
      │
      ▼
Packet Handler
      │
      ▼
Game Event
      │
      ▼
Maymi Event Bus
   │     │     │
   ▼     ▼     ▼
 Stats History Discord
```

Com isso, um mesmo evento pode ser processado por diferentes sistemas sem que o handler precise conhecer cada implementação.

---

## 💬 Discord

A integração utiliza **JDA (Java Discord API)**.

Entre os recursos desenvolvidos estão:

- bot Discord;
- slash commands;
- perfil de jogador;
- estatísticas;
- ranking;
- achievements;
- notificações de level up;
- notificações de level down;
- dashboard global.

---

## 🧱 Integração com mods

A arquitetura da Maymi permite identificar conteúdo modificado através dos **Registry IDs do Minecraft**.

Exemplo:

```text
minecraft:stone
create:andesite_casing
create:cogwheel
```

O namespace permite que o Core descubra qual integração deve processar determinado conteúdo sem possuir dependência direta do mod.

---

## ⚙️ Create Integration

A primeira integração específica em desenvolvimento é com o mod **Create**.

Blocos do Create podem ser classificados em categorias como:

```text
STRUCTURE
MECHANICAL
PROCESSING
LOGISTICS
FLUID
REDSTONE
OTHER
```

Por exemplo:

```text
create:andesite_casing
        ↓
STRUCTURE

create:cogwheel
        ↓
MECHANICAL
```

A Maymi também diferencia ações:

```text
PLACE
BREAK
```

Essa camada servirá como base para integrações mais avançadas com mods no futuro.

> A persistência específica das estatísticas do Create ainda está em desenvolvimento.

---

## 🌐 Comunicação

Minecraft e Maymi Core se comunicam utilizando **TCP Socket + JSON**.

Exemplo simplificado:

```json
{
  "type": "PLAYER_JOIN",
  "playerUuid": "uuid-do-jogador",
  "playerName": "Player"
}
```

O Core recebe a mensagem, identifica seu tipo e encaminha o packet para o handler correspondente através do `PacketDispatcher`.

---

## 🗄️ Persistência

Atualmente a Maymi utiliza **SQLite**.

A camada de persistência possui:

- inicialização automática;
- gerenciamento de conexão;
- migrations;
- repositories;
- services;
- entidades persistentes.

O banco de dados local não é versionado no repositório.

---

## 🧪 Testes

O projeto utiliza testes automatizados para validar componentes importantes.

No estado atual:

```text
maymi-common
14 testes executados
0 falhas

maymi-core
21 testes executados
0 falhas
```

Algumas áreas testadas incluem:

- desserialização de packets;
- Event Bus;
- achievements;
- repositories;
- progressão;
- estatísticas;
- Packet Dispatcher.

---

## 🛠️ Tecnologias

| Tecnologia | Utilização |
|---|---|
| Java 21 | Linguagem principal |
| Maven | Build dos módulos Common, Core e Paper |
| Gradle | Build da integração NeoForge |
| NeoForge | Integração atual com Minecraft |
| Paper API | Integração original/legada |
| JDA | Integração com Discord |
| Jackson | Serialização JSON |
| SQLite | Persistência |
| JDBC | Acesso ao banco |
| JUnit | Testes |
| Mockito | Testes e mocks |
| Git / GitHub | Versionamento |

---

## 📁 Estrutura

```text
maymi/
│
├── maymi-common/
│   └── protocolo e estruturas compartilhadas
│
├── maymi-core/
│   ├── achievement/
│   ├── configuration/
│   ├── discord/
│   ├── event/
│   ├── history/
│   ├── mod/
│   ├── network/
│   ├── persistence/
│   └── progression/
│
├── maymi-neoforge/
│   └── integração Minecraft / NeoForge
│
└── maymi-paper/
    └── integração original com Paper
```

---

## 🚧 Status

A Maymi está em **desenvolvimento ativo**.

### Implementado

- [x] Arquitetura modular
- [x] Maymi Core
- [x] Comunicação TCP
- [x] Protocolo JSON
- [x] Packet Dispatcher
- [x] Integração Discord
- [x] Slash Commands
- [x] Player Join / Quit
- [x] Death Events
- [x] Mob Kill Events
- [x] Block Break / Place Events
- [x] SQLite
- [x] Database Migrations
- [x] Estatísticas
- [x] XP e níveis
- [x] Achievements
- [x] Histórico
- [x] Event Bus
- [x] Perfis
- [x] Rankings
- [x] Dashboard Discord
- [x] Integração NeoForge
- [x] Detecção de conteúdo modded
- [x] Classificação inicial de blocos Create

### Em desenvolvimento

- [ ] Persistência completa das estatísticas do Create
- [ ] Expansão das integrações com mods
- [ ] Hardening da comunicação Core ↔ Minecraft
- [ ] Ampliação da cobertura de testes
- [ ] Preparação para distribuição/release

---

## 🎯 Objetivo

A Maymi foi criada como um projeto prático para aplicar conhecimentos de desenvolvimento backend Java em um ambiente real de integração.

O projeto explora principalmente:

- Programação Orientada a Objetos;
- arquitetura modular;
- separação de responsabilidades;
- sistemas orientados a eventos;
- comunicação entre processos;
- serialização;
- persistência;
- testes automatizados;
- integração entre APIs e plataformas diferentes.

Mais do que um bot para Discord ou um mod para Minecraft, a proposta da Maymi é funcionar como uma **camada central de integração entre o servidor do jogo e diferentes serviços externos**.

---

## 👨‍💻 Autor

**João Victor**

Projeto desenvolvido para estudo, experimentação e portfólio em desenvolvimento Java.

---

## 📌 Status do projeto

> 🚧 **Em desenvolvimento ativo — funcionalidades e arquitetura podem sofrer alterações durante a evolução do projeto.**