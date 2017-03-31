import {Injectable} from "@angular/core";
import {ErrorService} from "./error.service";
import {AuthHttp} from "angular2-jwt";
import {Observable} from "rxjs";
import {URLSearchParams} from "@angular/http";
import {User} from "../model/user.model";

const url = 'api/subscribe'

@Injectable()
export class SuscribeService {

  constructor(private authHttp: AuthHttp,
              private errorService: ErrorService){
  }

  toggleSubscribe(requestId, subscriberId): Observable<boolean> {
    let requestSubscriber = {
      "requestId" : requestId,
      "subscriberId" : subscriberId
    };
    return this.authHttp.post(url,requestSubscriber).map(resp => resp.json())
      .catch((error: any) => {
      this.errorService.processError(error);
      return Observable.throw(error);
    });
  }

  check(requestId, subscriberId): Observable<boolean> {
    let requestSubscriber = {
      "requestId" : requestId,
      "subscriberId" : subscriberId
    };
    return this.authHttp.post(`${url}/check`,requestSubscriber).map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getFollowers(requestId: number): Observable<User[]> {
    let params: URLSearchParams = new URLSearchParams();
    params.set('requestId', requestId.toString());
    return this.authHttp.get(url, { search: params }).map(resp => resp.json())
      .catch((error: any) => {
      this.errorService.processError(error);
      return Observable.throw(error);
    });
  }
}
