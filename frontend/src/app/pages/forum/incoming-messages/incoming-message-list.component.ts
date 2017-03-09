import {Component} from "@angular/core";
import {Message} from "../../../model/message.model";
import {UserService} from "../../../service/user.service";
import {AuthService} from "../../../service/auth.service";

@Component({
  selector: 'message-list',
  templateUrl: 'incoming-message-list.component.html'
})
export class IncomingMessageListComponent {
  messages: Message[];

  constructor(private userService: UserService,
              private authService: AuthService) {
  }

  ngOnInit() {
    this.authService.currentUser.subscribe((user) => {
      this.userService.getMyMessages(user.id, 1).subscribe((messages) => {
        this.messages = messages;
      })
    })
  }
}
