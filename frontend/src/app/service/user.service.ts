import {Injectable} from "@angular/core";
import {User} from "../model/user.model";
import {Observable} from "rxjs";
import {Http, Response} from "@angular/http";
import "rxjs/Rx";
import {AuthHttp} from "angular2-jwt";
import {CacheService} from "ionic-cache/ionic-cache";
import {Message} from "../model/message.model";
import {Role} from "../model/role.model";

const url = '/api/users';

@Injectable()
export class UserService {
  constructor(private http: Http,
              private authHttp: AuthHttp,
              private cache: CacheService) {
    this.cache = cache;
  }

  create(user: User): Observable<Response> {
    return this.http.post(url, user);
  }

  delete(id: number): Observable<Response> {
    return this.authHttp.delete(`${url}/${id}`);
  }

  update(user: User): Observable<Response> {
    return this.authHttp.put(`${url}/${user.id}`, user).map(resp => resp.json());
  }

  getRoles(): Observable<Role[]> {
    return this.authHttp.get(`${url}/roles`).map(resp => resp.json());
  }

  get(id: number): Observable<User> {
    let path = `${url}/${id}`;
    let cacheKey = path;
    let request = this.authHttp.get(path).map(res => res.json());

    return this.cache.loadFromObservable(cacheKey, request);
  }

  getAll(page: number): Observable<User[]> {
    return this.authHttp.get(`${url}?page=` + page).map(resp => resp.json()).publishReplay(1, 2000).refCount();
  }

  getPageCount(): Observable<number> {
    return this.authHttp.get(`${url}/pageCount`).map(resp => resp.json());
  }

  getPotentialRecipientForManager(managerId: number) {
    return this.authHttp.get(`${url}/empByManager?managerId=${managerId}`).map(resp => resp.json());
  }

  sendMessage(message: Message): Observable<Response> {
    return this.authHttp.post('/api/sendMessage', message);
  }
}
