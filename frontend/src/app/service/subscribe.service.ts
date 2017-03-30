import {Injectable} from "@angular/core";
import {ErrorService} from "./error.service";
import {AuthHttp} from "angular2-jwt";
import {Observable} from "rxjs";

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
}
