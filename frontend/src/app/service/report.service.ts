import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Response, Headers} from "@angular/http";
import "rxjs/Rx";
import {AuthHttp} from "angular2-jwt";

const url = '/api/reports';

@Injectable()
export class ReportService {
  constructor(private authHttp: AuthHttp) {

  }

  getPDFReport(): Observable<any> {
    let headers = new Headers({'Accept': 'application/pdf'});
    return this.authHttp.get(`${url}`, {headers: headers})
      .map((res: Response) => res.blob());
  }


}
