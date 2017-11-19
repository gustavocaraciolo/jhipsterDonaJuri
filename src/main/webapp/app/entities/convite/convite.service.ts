import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Convite } from './convite.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ConviteService {

    private resourceUrl = SERVER_API_URL + 'api/convites';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(convite: Convite): Observable<Convite> {
        const copy = this.convert(convite);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(convite: Convite): Observable<Convite> {
        const copy = this.convert(convite);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<Convite> {
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
     * Convert a returned JSON object to Convite.
     */
    private convertItemFromServer(json: any): Convite {
        const entity: Convite = Object.assign(new Convite(), json);
        entity.dataEnvio = this.dateUtils
            .convertDateTimeFromServer(json.dataEnvio);
        entity.dataAceitado = this.dateUtils
            .convertDateTimeFromServer(json.dataAceitado);
        entity.dataRejeitado = this.dateUtils
            .convertDateTimeFromServer(json.dataRejeitado);
        return entity;
    }

    /**
     * Convert a Convite to a JSON which can be sent to the server.
     */
    private convert(convite: Convite): Convite {
        const copy: Convite = Object.assign({}, convite);

        copy.dataEnvio = this.dateUtils.toDate(convite.dataEnvio);

        copy.dataAceitado = this.dateUtils.toDate(convite.dataAceitado);

        copy.dataRejeitado = this.dateUtils.toDate(convite.dataRejeitado);
        return copy;
    }
}
