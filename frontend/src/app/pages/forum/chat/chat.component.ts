import {Component, OnInit, OnDestroy, Input, Output, EventEmitter} from "@angular/core";
import {Message} from "../../../model/message.model";
import {ChatService} from "../../../service/chat.service";
import {AuthService} from "../../../service/auth.service";
import {User} from "../../../model/user.model";
import {FormGroup, Validators, FormBuilder} from "@angular/forms";
import {Response} from "@angular/http";
import {TopicService} from "../../../service/topic.service";
import {UserService} from "../../../service/user.service";
import {UserSearchDTO} from "../../../model/dto/user-search-dto.model";
import {Observable} from "rxjs";
import {isUndefined} from "util";
import {ActivatedRoute, Params} from "@angular/router";
import {Chuck} from "../../../model/dto/chuck.model";

declare let $ : JQueryStatic;

@Component({
  selector: 'chat',
  templateUrl: 'chat.component.html',
  styleUrls: ['chat.component.css']
})
export class ChatComponent implements OnInit, OnDestroy {
  @Input()
  messages: Message[];
  @Output()
  updated: EventEmitter<any> = new EventEmitter();
  currentUser: User;
  chatFriend: User;
  chatFriends: User[];
  findedUsers: User[];
  messageForm: FormGroup;
  message: Message;
  searchDTO : UserSearchDTO;
  connect: any;
  showFriendsList: boolean = true;

  constructor(private chatService: ChatService,
              private authService: AuthService,
              private formBuilder: FormBuilder,
              private userService: UserService,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.searchDTO = {
      firstName: "",
      lastName: "",
      email: "",
      role: "",
      dateOfDeactivation: "",
      limit: 10,
      isDeactivated: "false"
    };

    this.messageForm = this.formBuilder.group({
      text: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(500)]]
    });

    this.authService.currentUser.subscribe(user => {
      this.currentUser = user;

      this.chatService.getChatFriends(this.currentUser.id).subscribe((users: User[]) => {
        this.chatFriends = users;
      });

        this.activatedRoute.queryParams.subscribe((params: Params) => {
          let userId = params['userId'];
          if (userId) {
            this.userService.get(userId).subscribe(user => {
              this.updateFindedUsersArray(user);
            });
          }
        });
      });
    });
  }

  ngOnDestroy(): void {
    if (!isUndefined(this.connect)) {
      this.connect.unsubscribe();
    }
  }

  createNewMessage(params) {
    if (params.text.startsWith("/chuck")) {
      this.chatService.getChuckJoke().subscribe(joke => {
        this.message.text = joke.value;
        this.message.dateAndTime = new Date();
        this.userService.get(this.chatFriend.id).subscribe((user: User) => {
          this.chatFriend  = user;
          this.message.recipient = this.chatFriend;
          this.userService.sendMessage(this.message).subscribe((resp: Response) => {
            this.updateArray(<Message> resp.json());
            this.messageForm.reset();
          }, e => this.handleErrorCreateMessage(e));
        });
        $('#msg-container').animate({ scrollTop: $('#msg-container')[0].scrollHeight}, 2000);
      });
    } else {
      this.message.text = params.text;
      this.message.dateAndTime = new Date();
      this.userService.get(this.chatFriend.id).subscribe((user: User) => {
        this.chatFriend  = user;
        this.message.recipient = this.chatFriend;
        this.userService.sendMessage(this.message).subscribe((resp: Response) => {
          this.updateArray(<Message> resp.json());
          this.messageForm.reset();
        }, e => this.handleErrorCreateMessage(e));
      });
      $('#msg-container').animate({ scrollTop: $('#msg-container')[0].scrollHeight}, 2000);
    }
  }

  checkChuck(text) {
    if (text.includes('Chuck') || text.includes('chuck') || text.includes('Norris') || text.includes('norris')) {
      return true;
    } else return false;
  }

  loadUserMessages(user) {
    this.chatFriend = user;
    this.chatService.getDialogMessages(this.currentUser.id, user.id).subscribe((messages: Message[]) => {
      this.messages = messages;
      this.message = {
        sender: this.currentUser,
        text: null,
        dateAndTime: null
      };
    });
    $('#msg-container').animate({ scrollTop: $('#msg-container')[0].scrollHeight}, 2000);
    let timer = Observable.timer(2000, 3000);
    this.connect = timer.subscribe(t => this.reloadData(this.currentUser.id, this.chatFriend.id));
  }

  reloadData(userId, friendId) {
    this.chatService.getDialogMessages(userId, friendId).subscribe((messages: Message[]) => {
      this.messages = messages;
    });
  }

  private updateArray(message: Message): void {
    this.messages.push(message);
    this.updated.emit(this.messages);
  }

  private updateFindedUsersArray(user) {
    this.chatFriends.push(user);
    this.updated.emit(this.chatFriends);
    this.clear();
    this.searchDTO.firstName = "";
    this.loadUserMessages(user);
  }

  validate(field: string): boolean {
    return this.messageForm.get(field).valid || !this.messageForm.get(field).dirty;
  }

  private handleErrorCreateMessage(error) {
    switch (error.status) {
      case 500:
        // this.toastr.error("Can't create message", 'Error');
    }
  }

  setTitleSearch(value) {
    if (value != "") {
      this.searchDTO.lastName = value;
      this.getSearchData(this.searchDTO);
    }
  }

  getSearchData(searchDTO){
    this.userService.searchAll(searchDTO).subscribe(users => {
      console.log(users);
      this.findedUsers = users;
    })
  }

  clear() {
    this.findedUsers = [];
  }

  changeShowFriendsList() {
    this.showFriendsList = !this.showFriendsList;
  }
}
