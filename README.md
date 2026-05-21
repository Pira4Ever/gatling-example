# Gatling-example

Projeto demonstrando a ferramenta de teste de carga Gatling com uma aplicação de exemplo em Go (Gin).

## 📋 Sobre o Projeto

Este repositório contém uma aplicação backend simples em Go com Gin e uma configuração completa de teste de performance usando Gatling (Java). O objetivo é servir como exemplo prático de como configurar, executar e monitorar testes de carga.

### O que o projeto inclui?

- Aplicação backend (Go + Gin):
  - Endpoint `POST /user/login` para autenticação simples.
  - Métricas Prometheus integradas (`/metrics`).

- Testes de carga (Gatling):
  - Simulação [`BasicSimulation.java`](test/simulations/BasicSimulation.java) com ramp-up progressivo até 5000 usuários/segundo.
  - Custom metrics para rastrear latência e taxa de sucesso/erro.

Ambiente Docker completo:
Aplicação Go.
Prometheus + cAdvisor para monitoramento.
Suporte a Gatling (via volume ou container).

## 🚀 Como Executar

1. Com Docker Compose (recomendado)

  ```bash
  docker compose up -d
  ```
  
&emsp;&emsp;Isso inicia a aplicação em `http://localhost:8080`.

2. Executar os testes Gatling
&emsp;&emsp;

&emsp;&emsp;Entre na pasta test/ e execute a simulação:

```bash
# Exemplo (ajuste conforme sua instalação do Gatling)
mvn gatling:test
```

## 🧪 Simulação

A simulação atual realiza:

- Ramp-up gradual de usuários (0 → 1000 → 2000 → ... → 5000 usuários/segundo).
- Teste de login com credenciais corretas.
- Métricas customizadas de latência e sucesso.

Credenciais de teste:

- Username: Pira
- Password: s3cr3t_p@ss

## 📊 Monitoramento

- Prometheus: Configurado para scrapear a aplicação e cAdvisor.
- Gatling Reports: Resultados são gerados na pasta results/.

## 🧪 Resultados

- [V1 ( 0.5 CPU e 512 MB)](https://pira4ever.github.io/gatling-example/results/basicsimulation-v1/)
- [V2 ( 1.0 CPU e 1024 MB)](https://pira4ever.github.io/gatling-example/results/basicsimulation-v2/)

## 📁 Estrutura do Projeto

```text
/
├── main.go                 # Aplicação Go com Gin
├── Dockerfile
├── docker-compose.yaml
├── prometheus.yml
├── test/
│   ├── simulations/
│   │   └── BasicSimulation.java
│   └── metrics/            # Métricas customizadas
├── results/                # Relatórios do Gatling
└── go.mod
```

## Tecnologias Utilizadas

- Backend: Go + Gin + Prometheus
- Load Testing: Gatling (Java)
- Containerização: Docker + Docker Compose
- Monitoramento: Prometheus + cAdvisor

## Próximos Passos / Melhorias Possíveis

- Adicionar mais cenários (GETs, múltiplos endpoints, autenticação token, etc.)
- Integração com CI/CD (GitHub Actions)
- Testes com diferentes padrões de carga
- Gatling Enterprise ou Grafana dashboards

## 👥 Contribuidores

[<img src="https://avatars.githubusercontent.com/u/59701790?size=100" width="80" height="80" alt="Octavio Piratininga" style="border-radius:50%;">](https://github.com/Pira4Ever)
[<img src="https://avatars.githubusercontent.com/u/269061644?size=100" width="80" height="80" alt="Yuri Tavares" style="border-radius:50%;">](https://github.com/YuriTV07)
[<img src="https://avatars.githubusercontent.com/u/269567747?size=100" width="80" height="80" alt="Gustavo Henrique Teles" style="border-radius:50%;">](https://github.com/Gustavera5)
[<img src="https://avatars.githubusercontent.com/u/269211557?size=100" width="80" height="80" alt="Lopes" style="border-radius:50%;">](https://github.com/daviponteslopes12-web)
