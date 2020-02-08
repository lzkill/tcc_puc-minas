export interface IPublicoAlvo {
  id?: number;
  nome?: string;
  descricao?: any;
}

export class PublicoAlvo implements IPublicoAlvo {
  constructor(public id?: number, public nome?: string, public descricao?: any) {}
}
