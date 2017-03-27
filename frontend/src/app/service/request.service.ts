import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Response} from "@angular/http";
import "rxjs/Rx";
import {AuthHttp} from "angular2-jwt";
import {Request} from "../model/request.model";
import {ErrorService} from "./error.service";

const url = '/api/requests';

@Injectable()
export class RequestService {
  constructor(private authHttp:AuthHttp,
              private errorService:ErrorService) {
  }

  create(request:Request):Observable<Response> {
    return this.authHttp.post(url, request)
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  createSubRequest(subRequest:Request):Observable<Response> {
    return this.authHttp.post(`${url}/createSubRequest`, subRequest)
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  update(request:Request):Observable<Response> {
    return this.authHttp.put(url, request)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  delete(id: number): Observable<Response> {
    return this.authHttp.delete(`${url}/${id}`)
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  get(id: number): Observable<Request> {
    return this.authHttp.get(`${url}/${id}`)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getCountRequestsByPeriod(beginDate: string, endDate: string): Observable<number> {
    return this.authHttp.get(`${url}/getCountRequestsByPeriod?beginDate=${beginDate}&endDate=${endDate}`)
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getCountRequestsByStartDate(beginDate: Date, months: number): Observable<number[]> {
    return this.authHttp.get(`${url}/getCountRequestsByStartDate?beginDate=${beginDate}&months=${months}`)
      .map(resp => resp.json())
      .catch((error: any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }


  getAll(page:number):Observable<Request[]> {
    return this.authHttp.get(`${url}/fetch?page=` + page)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getPageCount():Observable<number> {
    return this.authHttp.get(`${url}/pageCount`)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getPageCountFree():Observable<number> {
    return this.authHttp.get(`${url}/pageCountFree`)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  // getQuantityRequest(): Observable<number[]> {
  //   return this.authHttp.get(`${url}/countRequestByProgressStatus`).map(resp => resp.json());
  // }

  getFree(page: number): Observable<Request[]> {
    return this.authHttp.get(`${url}/fetchFree?page=` + page)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getRequestCountByAssignee(assigneeId: number): Observable<number> {
    return this.authHttp.get(`${url}/pageCountByAssignee?assigneeId=${assigneeId}`)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getAllByAssignee(assigneeId: number, page: number): Observable<Request[]> {
    return this.authHttp.get(`${url}/fetchByAssignee?assigneeId=${assigneeId}&pageNumber=${page}`)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getInProgressAssigned(page:number, user_id:number):Observable<Request[]> {
    return this.authHttp.get(`${url}/inProgressRequestsByAssignee?page=` + page + `&manager=` + user_id)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getInProgressAssignedPageCount(managerId:number):Observable<number> {
    return this.authHttp.get(`${url}/countInProgressRequestsByAssignee?manager=` + managerId)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  assign(request:Request):Observable<Response> {
    return this.authHttp.put(url + '/assignRequest', request)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  close(request:Request):Observable<Request> {
    return this.authHttp.put(`${url}/closeRequest`, request)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  join(request:Request, checked:number[]):Observable<Request> {
    return this.authHttp.post(`${url}/join/${checked.join()}`, request)
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getQuantityForUser(userId: number): Observable<number[]> {
    return this.authHttp.get(`${url}/countRequestForUser?userId=` + userId).map(resp => resp.json())
  }

  getStatisticForAdminDashBoard(howLong: number): Observable<number[]> {
    return this.authHttp.get(`${url}/getStatisticForAdminDashBoard?howLong=` + howLong).map(resp => resp.json());
  }

  getStatisticForSixMonthsForUser(userId: number): Observable<number[]> {
    return this.authHttp.get(`${url}/getStatisticForSixMonthsByProgressStatusForUser?userId=` + userId).map(resp => resp.json());
  }

  // getQuantityRequestByPriority(): Observable<number[]> {
  //   return this.authHttp.get(`${url}/countRequestByPriorityStatus`).map(resp => resp.json());
  // }

  getSubRequests(id:number):Observable<Request[]> {
    return this.authHttp.get(`${url}/getSubRequests/${id}`)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getJoinedRequests(id:number):Observable<Request[]> {
    return this.authHttp.get(`${url}/getJoinedGroupRequests/${id}`)
      .map(resp => resp.json())
      .catch((error:any) => {
        this.errorService.processError(error);
        return Observable.throw(error);
      });
  }

  getTotalUsers(): Observable<number> {
    return this.authHttp.get(`${url}/getTotalUsers`).map(resp => resp.json());
  }

  getTotalRequests(): Observable<number> {
    return this.authHttp.get(`${url}/getTotalRequests`).map(resp => resp.json());
  }

  getRequestToday(): Observable<number> {
    return this.authHttp.get(`${url}/getRequestToday`).map(resp => resp.json());
  }

  getRunningToday(): Observable<number> {
    return this.authHttp.get(`${url}/getRunningToday`).map(resp => resp.json());
  }
}
