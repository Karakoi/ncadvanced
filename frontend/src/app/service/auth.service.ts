import {Injectable} from "@angular/core";
import {Response, Headers} from "@angular/http";
import {Observable} from "rxjs";
import {JsonHttp} from "./json-http.service";
import 'rxjs/Rx';

@Injectable()
export class AuthService {
  constructor(private http: JsonHttp) {
  }

  login(email: string, password: string): Observable<Response> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let options = {headers: headers};

    let body = {
      email: email,
      password: password
    };
    return this.http.post('/api/auth', body, options)
      .do((response: Response) => {
        localStorage.setItem('jwt', response.json().token);
      });
  }

  logout(): void {
    localStorage.removeItem('jwt');
  }

  isSignedIn(): boolean {
    return localStorage.getItem('jwt') == null;
  }
}