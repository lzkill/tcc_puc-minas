import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEmpresaConsultoria } from 'app/shared/model/sgq/empresa-consultoria.model';

type EntityResponseType = HttpResponse<IEmpresaConsultoria>;
type EntityArrayResponseType = HttpResponse<IEmpresaConsultoria[]>;

@Injectable({ providedIn: 'root' })
export class EmpresaConsultoriaService {
  public resourceUrl = SERVER_API_URL + 'services/sgq/api/empresa-consultorias';

  constructor(protected http: HttpClient) {}

  create(empresaConsultoria: IEmpresaConsultoria): Observable<EntityResponseType> {
    return this.http.post<IEmpresaConsultoria>(this.resourceUrl, empresaConsultoria, { observe: 'response' });
  }

  update(empresaConsultoria: IEmpresaConsultoria): Observable<EntityResponseType> {
    return this.http.put<IEmpresaConsultoria>(this.resourceUrl, empresaConsultoria, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEmpresaConsultoria>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEmpresaConsultoria[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
