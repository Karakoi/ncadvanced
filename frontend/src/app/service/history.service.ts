import {Observable} from "rxjs";
import "rxjs/Rx";
import {AuthHttp} from "angular2-jwt";
import {ErrorService} from "./error.service";
import {History} from "../model/history.model";
import {Injectable} from "@angular/core";

const url = '/api/histories';

@Injectable()
export class HistoryService{
  constructor(private authHttp: AuthHttp,
              private errorService: ErrorService) {
  }

  getHistory(entityId: number): Observable<History[]> {
    return this.authHttp.get(`${url}/${entityId}`)
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }
}
