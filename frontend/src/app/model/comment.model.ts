import {User} from "./user.model";
import {Request} from "@angular/http";

export interface Message {
  id?: number,
  sender: User,
  request: Request,
  text: string,
  dateAndTime: Date
}
