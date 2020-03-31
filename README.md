# TCC | PUC Minas  - Sistema de Gestão da Qualidade

Este repositório contém os artefatos do TCC apresentado para conclusão do curso [**Pós-graduação em Arquitetura de Software Distribuído (2018)**](https://www.pucminas.br/PucVirtual/Pos-Graduacao/Paginas/Arquitetura-de-Software-Distribuido.aspx?moda=1&polo=1&area=11&curso=2096&situ=1). Mais especificamente, trata-se de um protótipo de aplicação para apoio ao *Sistema de Gestão da Qualidade* de uma empresa do setor automotivo. O objetivo do trabalho foi avaliar a viabilidade da arquitetura proposta (microserviços).

**Vídeo de apresentação:**\
[![Vídeo de apresentação](https://raw.githubusercontent.com/lzkill/tcc_puc-minas/develop/doc/frontpage.png)](https://www.youtube.com/watch?v=_LmJVLnPJeo)

## Por onde começar

As instruções a seguir permitem que uma cópia da solução seja configurada localmente para avaliação e desenvolvimento.

### Pré-requisitos

As seguintes ferramentas são necessárias:

- [Java](https://adoptopenjdk.net/) (versão 11, no mínimo)
- [Node.js](https://nodejs.org/) (qualquer versão LTS)
- [Docker](https://docs.docker.com/install/) / [docker-compose](https://docs.docker.com/compose/install/)
- [JHipster](https://www.jhipster.tech/installation/)

**Observação:** as ferramentas [SDKMAN!](https://sdkman.io/) e [nvm](https://github.com/nvm-sh/nvm) permitem a instalação/manutenção do Java e do Node.js/npm de forma bastante flexível.


### Desenvolvimento

A codificação em cada um dos projetos da solução pode se apoiar no mecanismo de *hot reload* para uma maior produtividade. Para tanto, execute os seguintes comandos na raiz do projeto em desenvolvimento:

- `./mvnw -P-webpack`
- `npm start` (apenas para aplicações do tipo *gateway*)

### Produção

#### Build

```
cd sgq && ./mvnw -Pprod verify package jib:dockerBuild && \
cd ../gateway && ./mvnw -Pprod verify package jib:dockerBuild && \
cd ../mock/normas && ./mvnw -Pprod verify package jib:dockerBuild && \
cd ../consultoria && ./mvnw -Pprod verify package jib:dockerBuild && \
cd ../..
```

#### Execução

```
docker-compose -f docker-compose/docker-compose.yml --project-name sgq up -d --remove-orphans; \
docker-compose -f mock/docker-compose/docker-compose.yml --project-name mock up -d --remove-orphans
```

## Tecnologias

Todas as tecnologias utilizadas neste trabalho são disponibilizadas pelo meta framework [JHipster](https://www.jhipster.tech/tech-stack/). Por favor acesse sua documentação para mais informações.

## Licença

Este trabalho é licenciado sob a licença MIT - veja o arquivo [LICENSE.md](LICENSE.md) para detalhes.

## To do

Veja [Milestones](https://github.com/lzkill/tcc_puc-minas/milestones).

## Issues

Veja [Bugs](https://github.com/lzkill/tcc_puc-minas/issues?q=is%3Aopen+is%3Aissue+label%3Abug).
