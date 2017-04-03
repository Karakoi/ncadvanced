import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Http, URLSearchParams} from "@angular/http";
import "rxjs/Rx";
import {AuthHttp} from "angular2-jwt";
import {Message} from "../model/message.model";
import {User} from "../model/user.model";
import {Chuck} from "../model/dto/chuck.model";

const url = '/api';

@Injectable()
export class ChatService {

  constructor(private authHttp: AuthHttp,
              private http: Http) {
  }

  getDialogMessages(senderId: number, recipientId: number): Observable<Message[]> {
    let params: URLSearchParams = new URLSearchParams();
    params.set('senderId', senderId.toString());
    params.set('recipientId', recipientId.toString());
    return this.authHttp.get(`${url}/messagesByDialog`, {search: params})
      .map(resp => resp.json());
  }

  getUnreadMessages(recipientId: number): Observable<Message[]> {
    let params: URLSearchParams = new URLSearchParams();
    params.set('recipientId', recipientId.toString());
    return this.authHttp.get(`${url}/unreadMessages`, {search: params})
      .map(resp => resp.json());
  }

  getChatFriends(userId: number): Observable<User[]> {
    let params: URLSearchParams = new URLSearchParams();
    params.set('userId', userId.toString());
    return this.authHttp.get(`${url}/users/findUserChatPartners`, {search: params})
      .map(resp => resp.json());
  }

  getChuckJoke(): Observable<Chuck> {
    return this.http.get(`https://api.chucknorris.io/jokes/random`)
      .map(resp => resp.json());
  }

  getUsersWithUnreadMessages(userId: number): Observable<User[]> {
    let params: URLSearchParams = new URLSearchParams();
    params.set('userId', userId.toString());
    return this.authHttp.get(`${url}/users/findUsersWithUnreadMessages`, {search: params})
      .map(resp => resp.json());
  }
}
