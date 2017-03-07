import {Injectable} from "@angular/core";
import {User} from "../model/user.model";
import {Observable} from "rxjs";
import {Http, Response} from "@angular/http";
import "rxjs/Rx";
import {AuthHttp} from "angular2-jwt";
import {CacheService} from "ionic-cache/ionic-cache";

const url = '/api/users';

@Injectable()
export class UserService {
  userData:Map<number,User[]>;

  constructor(private http:Http,
              private authHttp:AuthHttp,
              private cache:CacheService) {
    this.cache = cache;
  }

  create(user:User):Observable<Response> {
    return this.http.post(url, user);
  }

  update(user:User):Observable<Response> {
    return this.authHttp.put(url, user).map(resp => resp.json());
  }



  get(id:number):Observable<User> {
    let path = `${url}/${id}`;
    let cacheKey = path;
    let request = this.authHttp.get(path).map(res => res.json());
    
    return this.cache.loadFromObservable(cacheKey, request);
  }




  getAll(page:number):Observable<User[]> {

    return this.authHttp.get(`${url}?page=` + page).map(resp => resp.json()).publishReplay(1, 2000).refCount();
  }

  getPageCount():Observable<number> {
    return this.authHttp.get(`${url}/pageCount`).map(resp => resp.json());
  }
}
