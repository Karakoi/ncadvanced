import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Response, Headers} from "@angular/http";
import "rxjs/Rx";
import {AuthHttp} from "angular2-jwt";
import {ErrorService} from "./error.service";
import {RequestDTO} from "../model/dto/requestDTO.model";

const url = '/api/reports';

@Injectable()
export class ReportService {
  constructor(private authHttp: AuthHttp,
              private errorService:ErrorService) {

  }

  getPDFReport(): Observable<any> {
    let headers = new Headers({'Accept': 'application/pdf'});
    return this.authHttp.get(`${url}`, {headers: headers})
      .map((res: Response) => res.blob());
  }

  getListCountRequestsByPeriod(beginDate: Date, endDate: Date): Observable<RequestDTO[]> {
    return this.authHttp.get(`${url}/getListCountRequestsByPeriod?beginDate=${beginDate}&endDate=${endDate}`)
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

}
