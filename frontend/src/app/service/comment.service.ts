import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Http, Response} from "@angular/http";
import "rxjs/Rx";
import {AuthHttp} from "angular2-jwt";
import {Comment} from "../model/comment.model";

const url = '/api';

@Injectable()
export class CommentService {

  constructor(private authHttp: AuthHttp) {
  }

  create(comment: Comment): Observable<Response> {
    return this.authHttp.post(`${url}/sendComment`, comment);
  }

  delete(id: number): Observable<Response> {
    return this.authHttp.delete(`${url}/comments/${id}`);
  }

  getByRequest(requestId: number): Observable<Comment[]> {
    return this.authHttp.get(`${url}/commentsByRequest?requestId=` + requestId).map(resp => resp.json());
  }
}
