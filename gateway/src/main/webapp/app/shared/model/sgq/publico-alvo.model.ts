export interface IPublicoAlvo {
  id?: number;
  nome?: string;
  descricao?: any;
  habilitado?: boolean;
}

export class PublicoAlvo implements IPublicoAlvo {
  constructor(public id?: number, public nome?: string, public descricao?: any, public habilitado?: boolean) {
    this.habilitado = this.habilitado || false;
  }
}
