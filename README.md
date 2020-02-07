# TCC | PUC Minas  - Sistema de Gestão da Qualidade

Este repositório contém os artefatos do TCC apresentado para conclusão do curso [**Pós-graduação em Arquitetura de Software Distribuído (2018)**](https://www.pucminas.br/PucVirtual/Pos-Graduacao/Paginas/Arquitetura-de-Software-Distribuido.aspx?moda=1&polo=1&area=11&curso=2096&situ=1). Mais especificamente, trata-se de um protótipo de aplicação para apoio ao *Sistema de Gestão da Qualidade* de uma empresa do setor automotivo. O objetivo do trabalho foi avaliar a viabilidade da arquitetura proposta (microserviços).

<!--<img alt="Diagrama de arquitetura" src="doc/arquitetura.png" style="height:75%; width:75%;">-->

**Vídeo de apresentação:**\
[![Vídeo de apresentação](https://raw.githubusercontent.com/lzkill/tcc_puc-minas/develop/doc/dev_monkey.png)](https://www.youtube.com/watch?v=_LmJVLnPJeo)

## Por onde começar

As instruções a seguir permitem que uma cópia da solução seja configurada localmente para avaliação e desenvolvimento.

### Pré-requisitos

As seguintes ferramentas são necessárias:

- [Java](https://adoptopenjdk.net/) (versão 11, no mínimo)
- [Node.js](https://nodejs.org/) (qualquer versão LTS)
- [Docker](https://docs.docker.com/install/) / [docker-compose](https://docs.docker.com/compose/install/)
- [JHipster](https://www.jhipster.tech/installation/)

**Observação:** as ferramentas [SDKMAN!](https://sdkman.io/) e [nvm](https://github.com/nvm-sh/nvm) permitem a instalação/manutenção do Java e do Node.js/npm de forma bastante flexível.

### Execução

- `./mvnw package -Pprod verify jib:dockerBuild`
- `docker-compose up -d`

### Desenvolvimento

A codificação em cada um dos projetos da solução pode se apoiar no mecanismo de *hot reload* para uma maior produtividade. Para tanto, execute os seguintes comandos na raiz do projeto em desenvolvimento:

- `./mvnw`
- `npm start`

## Tecnologias

Todas as tecnologias utilizadas neste trabalho são disponibilizadas pelo meta framework [JHipster](https://www.jhipster.tech/tech-stack/). Por favor acesse sua documentação para mais informações.

## Licença

Este trabalho é licenciado sob a licença MIT - veja o arquivo [LICENSE.md](LICENSE.md) para detalhes.

## To do

- Configurar volumes dos containers
- Reorganizar menus do frontend
- Obter usuários nos forms (veja [Missing User class...](https://stackoverflow.com/a/43405851))
- Exibir o plano de auditoria do ano corrente na página inicial (pública)
- Implementar integrações
- Diferenciar papéis do SGQ

## Issues

* `jhipster-generator`
  - O arquivo `docker-compose/docker_compose.yml` repete a porta pública 3306 quando vários MariaDBs estão presentes
  - Relacionamentos *ManyToMany* com `CategoriaPublicacao` geram testes quebrados (`PublicacaoFeedResourceIT` / `BoletimInformativoResourceIT`)
  - Relacionamentos com `User` contendo *injected fields* geram arquivos quebrados quando a autenticação é oauth2 (`getUser()`)
  - Hazelcast não é incluído no arquivo `docker-compose/docker_compose.yml`
