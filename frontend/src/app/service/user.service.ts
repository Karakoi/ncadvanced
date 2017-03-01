import {Injectable} from "@angular/core";
import {User} from "../model/user.model";
import {Observable} from "rxjs";
import {Response, Http} from "@angular/http";
import "rxjs/Rx";
import {AuthHttp} from "angular2-jwt";

const url = '/api/users';

@Injectable()
export class UserService {
  constructor(private http: Http,
              private authHttp: AuthHttp) {
  }

  create(user: User): Observable<Response> {
    return this.http.post(url, user);
  }

  update(user: User): Observable<Response> {
    return this.http.put(url, user);
  }

  get(id: number): Observable<User> {
    return this.authHttp.get(`${url}/${id}`).map(res => res.json());
  }

  getAll(): Observable<User[]> {
    return this.authHttp.get(`${url}/getAll`).map(res => res.json());
  }
}
