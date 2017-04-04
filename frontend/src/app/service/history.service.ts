import {Observable} from "rxjs";
import "rxjs/Rx";
import {AuthHttp} from "angular2-jwt";
import {ErrorService} from "./error.service";
import {History} from "../model/history.model";
import {HistoryMessageDTO} from "../model/dto/historyMessageDTO.model";
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

  getHistoryDTOs(entityId: number): Observable<HistoryMessageDTO[]> {
    return this.authHttp.get(`${url}/dto/${entityId}`)
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  /*getFullHistoryDTO(entityId: number): Observable<HistoryMessageDTO> {
    return this.authHttp.get(`${url}/dto/longValue/${entityId}`)
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }*/
}
