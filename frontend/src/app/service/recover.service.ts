import {Response, Http} from "@angular/http";
import {Observable} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable()
export class RecoverService {
  constructor(private http: Http) {
  }

  sendRecoverInfo(email: string): Observable<Response> {
    return this.http.post('/api/users/changePassword', email);
  }
}