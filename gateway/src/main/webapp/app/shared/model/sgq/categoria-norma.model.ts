import { INorma } from 'app/shared/model/sgq/norma.model';

export interface ICategoriaNorma {
  id?: number;
  titulo?: string;
  descricao?: any;
  normas?: INorma[];
}

export class CategoriaNorma implements ICategoriaNorma {
  constructor(public id?: number, public titulo?: string, public descricao?: any, public normas?: INorma[]) {}
}
