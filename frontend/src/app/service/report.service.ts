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
    return this.authHttp.get(`${url}/pdf`, {headers: headers})
      .map((res: Response) => res.blob());
  }

  getAllStaticticsOfCreatedRequestsByPeriod(beginDate: Date, endDate: Date): Observable<RequestDTO[]> {
    return this.authHttp.get(`${url}/getAllStaticticsOfCreatedRequestsByPeriod?beginDate=${beginDate}&endDate=${endDate}`)
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getAllStaticticsOfClosedRequestsByPeriod(beginDate: Date, endDate: Date): Observable<RequestDTO[]> {
    return this.authHttp.get(`${url}/getAllStaticticsOfClosedRequestsByPeriod?beginDate=${beginDate}&endDate=${endDate}`)
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getListOfBestManagersWithClosedStatusByPeriod(beginDate: Date, endDate: Date): Observable<RequestDTO[]> {
    return this.authHttp.get(`${url}/getBestManagersWithClosedStatusByPeriod?beginDate=${beginDate}&endDate=${endDate}`)
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getListOfBestManagersWithFreeStatusByPeriod(beginDate: Date, endDate: Date): Observable<RequestDTO[]> {
    return this.authHttp.get(`${url}/getBestManagersWithFreeStatusByPeriod?beginDate=${beginDate}&endDate=${endDate}`)
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getManagerStatisticsOfClosedRequestsByPeriod(beginDate: Date, endDate: Date, id: number): Observable<RequestDTO[]> {
    return this.authHttp.get(`${url}/getManagerStatisticsOfClosedRequestsByPeriod?beginDate=${beginDate}&endDate=${endDate}&id=${id}`)
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

}
