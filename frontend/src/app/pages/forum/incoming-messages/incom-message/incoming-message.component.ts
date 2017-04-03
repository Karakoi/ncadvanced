import {Component, Input} from "@angular/core";
import {Message} from "../../../../model/message.model";
import {Router} from "@angular/router";

@Component({
  selector: 'message-item',
  templateUrl: 'incoming-message.component.html'
})
export class MessageItemComponent {
  @Input() message: Message;

  constructor(private router: Router) {
  }

  answer(sender) {
    //console.log(sender);
    this.router.navigate(['/forum/message']);
  }
}
