import {JsonHttp} from "./json-http.service";
import {Injectable} from "@angular/core";
import {User} from "../model/user.model";
import {Observable} from "rxjs";
import {Response, Headers} from "@angular/http";
import {AuthService} from "./auth.service";
import "rxjs/Rx";

const url = '/api/user';

@Injectable()
export class UserService {
  constructor(private http: JsonHttp,
              private auth: AuthService) {
  }

  create(user: User): Observable<Response> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let options = {headers: headers};

    return this.http.post(url + '/register', user, options)
      .do(() => {
        this.auth.login(user.email, user.password)
          .subscribe();
      })
  }

}