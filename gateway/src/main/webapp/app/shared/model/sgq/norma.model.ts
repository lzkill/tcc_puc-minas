import { Moment } from 'moment';

export interface INorma {
  id?: number;
  orgao?: string;
  titulo?: string;
  descricao?: any;
  versao?: string;
  numeroEdicao?: number;
  dataEdicao?: Moment;
  dataInicioValidade?: Moment;
  categoria?: string;
  urlDownload?: string;
}

export class Norma implements INorma {
  constructor(
    public id?: number,
    public orgao?: string,
    public titulo?: string,
    public descricao?: any,
    public versao?: string,
    public numeroEdicao?: number,
    public dataEdicao?: Moment,
    public dataInicioValidade?: Moment,
    public categoria?: string,
    public urlDownload?: string
  ) {}
}
