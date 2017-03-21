import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Response} from "@angular/http";
import "rxjs/Rx";
import {AuthHttp} from "angular2-jwt";
import {Request} from "../model/request.model";
import {ErrorService} from "./error.service";

const url = '/api/requests';

@Injectable()
export class RequestService {
  constructor(private authHttp:AuthHttp,
              private errorService:ErrorService) {
  }

  create(request:Request):Observable<Response> {
    return this.authHttp.post(url, request)
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  createSubRequest(subRequest:Request):Observable<Response> {
    return this.authHttp.post(`${url}/createSubRequest`, subRequest)
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  update(request:Request):Observable<Response> {
    return this.authHttp.put(url, request)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  delete(id: number): Observable<Response> {
    return this.authHttp.delete(`${url}/${id}`)
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  get(id: number): Observable<Request> {
    return this.authHttp.get(`${url}/${id}`)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getCountRequestsByPeriod(beginDate: string, endDate: string): Observable<number> {
    return this.authHttp.get(`${url}/getCountRequestsByPeriod?beginDate=${beginDate}&endDate=${endDate}`)
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getCountRequestsByStartDate(beginDate: Date, months: number): Observable<number[]> {
    return this.authHttp.get(`${url}/getCountRequestsByStartDate?beginDate=${beginDate}&months=${months}`)
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }


  getAll(page:number):Observable<Request[]> {
    return this.authHttp.get(`${url}/fetch?page=` + page)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getPageCount():Observable<number> {
    return this.authHttp.get(`${url}/pageCount`)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getPageCountFree():Observable<number> {
    return this.authHttp.get(`${url}/pageCountFree`)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getFree(page:number):Observable<Request[]> {
    return this.authHttp.get(`${url}/fetchFree?page=` + page)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  join(request:Request, checked:number[]):Observable<Request> {
    return this.authHttp.post(`${url}/join/${checked.join()}`, request)
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getSubRequests(id:number):Observable<Request[]> {
    return this.authHttp.get(`${url}/getSubRequests/${id}`)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getJoinedRequests(id:number):Observable<Request[]> {
    return this.authHttp.get(`${url}/getJoinedGroupRequests/${id}`)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }
}
