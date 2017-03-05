import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Response} from "@angular/http";
import "rxjs/Rx";
import {AuthHttp} from "angular2-jwt";
import {Request} from "../model/request.model";

const url = '/api/request';

@Injectable()
export class RequestService {
  constructor(private authHttp: AuthHttp) {
  }

  create(request: Request): Observable<Response> {
    return this.authHttp.post(url, request);
  }

  update(request: Request): Observable<Response> {
    return this.authHttp.put(url, request).map(resp => resp.json());
  }

  get(id: number): Observable<Request> {
    return this.authHttp.get(`${url}/${id}`).map(resp => resp.json());
  }

  getAll(page: number): Observable<Request[]> {
    return this.authHttp.get(`${url}/fetch?page=`+page).map(resp => resp.json());
  }

  getPageCount(): Observable<number> {
    return this.authHttp.get(`${url}/pageCount`).map( resp => resp.json());
  }
}
