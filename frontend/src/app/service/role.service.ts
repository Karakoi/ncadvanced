import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Http, Response} from "@angular/http";
import "rxjs/Rx";
import {AuthHttp} from "angular2-jwt";
import {Role} from "../model/role.model";

const url = '/api/roles';

@Injectable()
export class RoleService {
  constructor(private http: Http,
              private authHttp: AuthHttp) {

  }

  create(role: Role): Observable<Response> {
    return this.http.post(url, role);
  }

  delete(id: number): Observable<Response> {
    return this.authHttp.delete(`${url}/${id}`);
  }

  update(role: Role): Observable<Response> {
    return this.authHttp.put(`${url}/${role.id}`, role).map(resp => resp.json());
  }

  getAll(): Observable<Role[]> {
    return this.authHttp.get(`${url}`).map(resp => resp.json());
  }


}
