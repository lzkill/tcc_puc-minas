export interface IAnexo {
  id?: number;
  nomeArquivo?: string;
  conteudoContentType?: string;
  conteudo?: any;
}

export class Anexo implements IAnexo {
  constructor(public id?: number, public nomeArquivo?: string, public conteudoContentType?: string, public conteudo?: any) {}
}
