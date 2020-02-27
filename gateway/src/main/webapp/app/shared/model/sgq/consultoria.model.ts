export interface IConsultoria {
  id?: number;
  nome?: string;
  urlIntegracao?: string;
  tokenAcesso?: string;
  habilitado?: boolean;
}

export class Consultoria implements IConsultoria {
  constructor(
    public id?: number,
    public nome?: string,
    public urlIntegracao?: string,
    public tokenAcesso?: string,
    public habilitado?: boolean
  ) {
    this.habilitado = this.habilitado || false;
  }
}
