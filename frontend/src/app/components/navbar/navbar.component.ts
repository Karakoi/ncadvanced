import {Component, OnInit, Output, EventEmitter} from "@angular/core";
import {Router} from "@angular/router";
import {AuthService} from "../../service/auth.service";
import {UserService} from "../../service/user.service";
import {ErrorService} from "../../service/error.service";
import {Message} from "../../model/message.model";
import {ChatService} from "../../service/chat.service";
import {User} from "../../model/user.model";
import {Response} from "@angular/http";
import {timer} from "rxjs/observable/timer";
import {Observable} from "rxjs";
import {isUndefined} from "util";

declare let $: JQueryStatic;

@Component({
  selector: 'overseer-navbar',
  templateUrl: 'navbar.component.html',
  styleUrls: ['navbar.component.css']
})
export class NavbarComponent implements OnInit {
  isSignedIn: boolean;
  unreadMessages: Message[] = [];
  currentUser: User;
  @Output()
  updated: EventEmitter<any> = new EventEmitter();
  connect: any;

  constructor(private router: Router,
              private authService: AuthService,
              private userService: UserService,
              private errorService: ErrorService,
              private chatService: ChatService) {
  }

  ngOnInit() {
    this.isSignedIn = this.authService.isSignedIn();

    this.authService.events.subscribe(() => {
      this.isSignedIn = this.authService.isSignedIn();
      if (this.isSignedIn) {
        this.authService.currentUser.subscribe(user => {
          this.currentUser = user;
          let timer = Observable.timer(2000, 5000);
          this.connect = timer.subscribe(t => this.loadUnreadMessages(this.currentUser.id));
        });
      }
    });

    if (this.isSignedIn) {
      this.authService.currentUser.subscribe(user => {
        this.currentUser = user;
        let timer = Observable.timer(2000, 5000);
        this.connect = timer.subscribe(t => this.loadUnreadMessages(this.currentUser.id));
      });
    }
  }

  loadUnreadMessages(recipientId) {
    this.chatService.getUnreadMessages(recipientId).subscribe((unreadMessages: Message[]) => {
      this.unreadMessages = unreadMessages;
    });
  }

  setMessageRead(message, event) {
    event.stopPropagation();
    message.id = null;
    message.read = true;
    this.userService.sendMessage(message).subscribe((resp: Response) => {
      this.loadUnreadMessages(this.currentUser.id);
    });
  }

  private updateArray(message) {
    this.unreadMessages = this.unreadMessages.filter(r => r.id !== message.id);
  }

  logout() {
    if (!isUndefined(this.connect)) {
      this.connect.unsubscribe();
    }
    this.authService.logout();
    this.router.navigate(['/authentication/login']);
  }

  errorTest() {
    this.userService.get(10000)
      .subscribe(
        data => {
          console.log("Success")
        },
        error => {
          this.errorService.updateError(error);
        }
      );
  }
}
