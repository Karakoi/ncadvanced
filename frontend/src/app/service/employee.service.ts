import {CacheService} from "ionic-cache";
import {Http} from "@angular/http";
import {AuthHttp} from "angular2-jwt";
import "rxjs/Rx";
import {Injectable} from "@angular/core";

@Injectable()
export class EmployeeService {

  constructor(private http: Http,
              private authHttp: AuthHttp,
              private cache: CacheService) {
    this.cache = cache;
  }

}
