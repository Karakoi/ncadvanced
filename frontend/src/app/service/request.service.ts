import {Injectable} from "@angular/core";
import {User} from "../model/user.model";
import {Observable} from "rxjs";
import {Http, Response} from "@angular/http";
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

  getAll(): Observable<Request[]> {
    return this.authHttp.get(`${url}/getJoinedGroups`).map(resp => resp.json());
  }
}
