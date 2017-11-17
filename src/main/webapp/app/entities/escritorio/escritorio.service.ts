import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { Escritorio } from './escritorio.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EscritorioService {

    private resourceUrl = SERVER_API_URL + 'api/escritorios';

    constructor(private http: Http) { }

    create(escritorio: Escritorio): Observable<Escritorio> {
        const copy = this.convert(escritorio);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(escritorio: Escritorio): Observable<Escritorio> {
        const copy = this.convert(escritorio);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Escritorio> {
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
     * Convert a returned JSON object to Escritorio.
     */
    private convertItemFromServer(json: any): Escritorio {
        const entity: Escritorio = Object.assign(new Escritorio(), json);
        return entity;
    }

    /**
     * Convert a Escritorio to a JSON which can be sent to the server.
     */
    private convert(escritorio: Escritorio): Escritorio {
        const copy: Escritorio = Object.assign({}, escritorio);
        return copy;
    }
}
