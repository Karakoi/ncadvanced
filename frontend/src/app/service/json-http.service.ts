import {Injectable} from "@angular/core";
import {RequestOptionsArgs, RequestOptions, Headers, Response, Http} from "@angular/http";
import {Observable} from "rxjs";

const mergeAuthToken = (options: RequestOptionsArgs = {}) => {
  let newOptions = new RequestOptions({}).merge(options);
  let newHeaders = new Headers(newOptions.headers);
  const jwt = localStorage.getItem('jwt');
  if (jwt) {
    newHeaders.set('authorization', `Bearer ${jwt}`);
  }
  newOptions.headers = newHeaders;
  return newOptions;
};

@Injectable()
export class JsonHttp {

  constructor(private http: Http) {
  }

  get(url: string, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.get(url, mergeAuthToken(options));
  }

  post(url: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.post(url, body, mergeAuthToken(options));
  }

  put(url: string, body: any, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.put(url, body, mergeAuthToken(options));
  }

  delete(url: string, options?: RequestOptionsArgs): Observable<Response> {
    return this.http.delete(url, mergeAuthToken(options));
  }
}