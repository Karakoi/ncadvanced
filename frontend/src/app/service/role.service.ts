import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Http, Response} from "@angular/http";
import "rxjs/Rx";
import {AuthHttp} from "angular2-jwt";
import {Role} from "../model/role.model";
import {ErrorService} from "./error.service";

const url = '/api/roles';

@Injectable()
export class RoleService {
  constructor(private http: Http,
              private authHttp: AuthHttp,
              private errorService: ErrorService) {

  }

  create(role: Role): Observable<Response> {
    return this.http.post(url, role)
      .catch((error: any) => {
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

  update(role: Role): Observable<Response> {
    return this.authHttp.put(`${url}/${role.id}`, role).map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getAll(): Observable<Role[]> {
    return this.authHttp.get(`${url}`).map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }
}
