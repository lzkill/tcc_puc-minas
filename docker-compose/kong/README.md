# Kong API Gateway

## Overview
Este projeto agrupa os artefatos necessários para instanciar o [Kong API Gateway](https://konghq.com/kong/), bem como a interface gráfica [Konga](https://pantsel.github.io/konga/) para manipulação de sua API administrativa.

*Nota:* a primeira execução desse stack requer algumas modificações no `docker-comose-.yml`:

* o serviço *kong-migrations* deve estar descomentado;
* o parâmetro `NODE_ENV` do serviço *konga* deve ter o valor `development`.
