import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ResponseContentType} from "@angular/http";
import "rxjs/Rx";
import {AuthHttp} from "angular2-jwt";
import {ErrorService} from "./error.service";
import {RequestDTO} from "../model/dto/requestDTO.model";

const url = '/api/reports';

@Injectable()
export class ReportService {
  constructor(private authHttp: AuthHttp,
              private errorService: ErrorService) {

  }

  getPDFRequest(requestId: number): Observable<any> {
    return this.authHttp.get(`${url}/request?id=${requestId}`, {responseType: ResponseContentType.Blob})
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getAdminPDFReport(beginDate: Date, endDate: Date): Observable<any> {
    return this.authHttp.get(`${url}/adminPDFReport?beginDate=${beginDate}&endDate=${endDate}`, {responseType: ResponseContentType.Blob})
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getManagerPDFReport(beginDate: Date, endDate: Date): Observable<any> {
    return this.authHttp.get(`${url}/managerPDFReport?beginDate=${beginDate}&endDate=${endDate}`, {responseType: ResponseContentType.Blob})
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getAllStatisticsOfCreatedRequestsByPeriod(beginDate: Date, endDate: Date): Observable<RequestDTO[]> {
    return this.authHttp.get(`${url}/getAllStatisticsOfCreatedRequestsByPeriod?beginDate=${beginDate}&endDate=${endDate}`)
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getAllStatisticsOfClosedRequestsByPeriod(beginDate: Date, endDate: Date): Observable<RequestDTO[]> {
    return this.authHttp.get(`${url}/getAllStatisticsOfClosedRequestsByPeriod?beginDate=${beginDate}&endDate=${endDate}`)
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
