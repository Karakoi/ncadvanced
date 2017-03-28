import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Http, Response, URLSearchParams} from "@angular/http";
import "rxjs/Rx";
import {AuthHttp} from "angular2-jwt";
import {Topic} from "../model/topic.model";
import {Message} from "../model/message.model";
import {User} from "../model/user.model";

const url = '/api';

@Injectable()
export class ChatService {

  constructor(private authHttp: AuthHttp) {
  }

  getDialogMessages(senderId: number, recipientId: number): Observable<Message[]> {
    let params: URLSearchParams = new URLSearchParams();
    params.set('senderId', senderId.toString());
    params.set('recipientId', recipientId.toString());
    return this.authHttp.get(`${url}/messagesByDialog`, {search: params})
      .map(resp => resp.json());
  }

  getChatFriends(userId: number): Observable<User[]> {
    let params: URLSearchParams = new URLSearchParams();
    params.set('userId', userId.toString());
    return this.authHttp.get(`${url}/users/findUserChatPartners`, {search: params})
      .map(resp => resp.json());
  }
}
