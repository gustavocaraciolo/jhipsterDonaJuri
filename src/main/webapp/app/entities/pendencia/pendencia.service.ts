import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Pendencia } from './pendencia.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PendenciaService {

    private resourceUrl = SERVER_API_URL + 'api/pendencias';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(pendencia: Pendencia): Observable<Pendencia> {
        const copy = this.convert(pendencia);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(pendencia: Pendencia): Observable<Pendencia> {
        const copy = this.convert(pendencia);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Pendencia> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Pendencia.
     */
    private convertItemFromServer(json: any): Pendencia {
        const entity: Pendencia = Object.assign(new Pendencia(), json);
        entity.dataInicial = this.dateUtils
            .convertDateTimeFromServer(json.dataInicial);
        entity.dataFinal = this.dateUtils
            .convertDateTimeFromServer(json.dataFinal);
        return entity;
    }

    /**
     * Convert a Pendencia to a JSON which can be sent to the server.
     */
    private convert(pendencia: Pendencia): Pendencia {
        const copy: Pendencia = Object.assign({}, pendencia);

        copy.dataInicial = this.dateUtils.toDate(pendencia.dataInicial);

        copy.dataFinal = this.dateUtils.toDate(pendencia.dataFinal);
        return copy;
    }
}
