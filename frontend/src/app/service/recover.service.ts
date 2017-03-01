import {Response} from "@angular/http";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";
import {JsonHttp} from "./json-http.service";

@Injectable()
export class RecoverService {
  constructor(private http: JsonHttp) {
  }

  sendRecoverInfo(email: string): Observable<Response> {
    return this.http.post('/api/users/recoverPassword', email);
  }
}