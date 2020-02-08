export interface IEmpresaConsultoria {
  id?: number;
  nome?: string;
  urlIntegracao?: string;
  tokenAcesso?: string;
}

export class EmpresaConsultoria implements IEmpresaConsultoria {
  constructor(public id?: number, public nome?: string, public urlIntegracao?: string, public tokenAcesso?: string) {}
}
