import {Component, Input} from "@angular/core";
import {Message} from "../../../../model/message.model";

@Component({
  selector: 'message-item',
  templateUrl: 'incoming-message.component.html'
})
export class MessageItemComponent {
  @Input() message: Message;

}
