import {JsonHttp} from "./json-http.service";
import {Injectable} from "@angular/core";
import {User} from "../model/user.model";
import {Observable} from "rxjs";
import {Response} from "@angular/http";
import "rxjs/Rx";

const url = '/api/user';

@Injectable()
export class UserService {
  constructor(private http: JsonHttp) {
  }

  create(user: User): Observable<Response> {
    return this.http.post(url, user);
  }

  get(id: number): Observable<User> {
    return this.http.get(`${url}/${id}`).map(res => res.json());
  }

}