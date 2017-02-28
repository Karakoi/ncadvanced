import {Injectable} from "@angular/core";
import {Response} from "@angular/http";
import {Observable, Subject} from "rxjs";
import {JsonHttp} from "./json-http.service";
import "rxjs/Rx";

@Injectable()
export class AuthService {
  private authEvents: Subject<AuthEvent>;

  constructor(private http: JsonHttp) {
    this.authEvents = new Subject<AuthEvent>();
  }

  login(email: string, password: string): Observable<Response> {
    let body = {
      email: email,
      password: password
    };
    return this.http.post('/api/auth', body).do((resp: Response) => {
     localStorage.setItem('jwt', resp.json().token);
     this.authEvents.next(new DidLogin());
     });
  }

  logout(): void {
    localStorage.removeItem('jwt');
    this.authEvents.next(new DidLogout());
  }

  isSignedIn(): boolean {
    return localStorage.getItem('jwt') != null;
  }

  get events(): Observable<AuthEvent> {
    return this.authEvents;
  }
}

export class DidLogin {
}
export class DidLogout {
}

export type AuthEvent = DidLogin | DidLogout;
