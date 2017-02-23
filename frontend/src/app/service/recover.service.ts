import {Http, Headers, Response} from "@angular/http";
import {Subscription, Observable} from "rxjs";
import {Injectable} from "@angular/core";

@Injectable()
export class RecoverService {
  constructor(private http: Http) {
  }

  sendRecoverInfo(email: string): Observable<Response> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let options = {headers: headers};

    return this.http.post('/api/user/changePassword', email, options);
  }
}