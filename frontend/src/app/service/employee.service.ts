import {CacheService} from "ionic-cache";
import {Http, URLSearchParams, Response} from "@angular/http";
import {AuthHttp} from "angular2-jwt";
import "rxjs/Rx";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ErrorService} from "./error.service";
import {Request} from "../model/request.model";

const url = '/api/requests';

@Injectable()
export class EmployeeService {

  constructor(private http: Http,
              private authHttp: AuthHttp,
              private cache: CacheService,
              private errorService: ErrorService) {
    this.cache = cache;
  }

  getRequestsByReporter(reporterId: number, page: number): Observable<Request[]>{
    let params: URLSearchParams = new URLSearchParams();
    params.set('userId', reporterId.toString());
    params.set('pageNumber', page.toString());
    return this.authHttp.get(`${url}/requestsByReporter`, {search: params})
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getClosedRequestsByReporter(reporterId: number, page: number): Observable<Request[]>{
    let params: URLSearchParams = new URLSearchParams();
    params.set('userId', reporterId.toString());
    params.set('pageNumber', page.toString());
    return this.authHttp.get(`${url}/closedRequestsByReporter`, {search: params})
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  countRequestsByReporter(reporterId: number): Observable<number> {
    let params: URLSearchParams = new URLSearchParams();
    params.set('reporterId', reporterId.toString());
    return this.authHttp.get(`${url}/countRequestsByReporter`, {search: params})
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  countClosedRequestsByReporter(reporterId: number): Observable<number> {
    let params: URLSearchParams = new URLSearchParams();
    params.set('reporterId', reporterId.toString());
    return this.authHttp.get(`${url}/countClosedRequestsByReporter`, {search: params})
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  createEmployeeRequest(request: Request): Observable<Response> {
    return this.authHttp.post(`${url}/employeeRequest`, request);
  }

  reopenRequests(requestId: number[]): Observable<Response>{
    return this.authHttp.post(`${url}/reopen`,requestId);
  }
}
