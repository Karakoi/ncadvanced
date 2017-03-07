import {Component, OnInit} from "@angular/core";
import {UserService} from "../../service/user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {User} from "../../model/user.model";
import {AuthService} from "../../service/auth.service";
import {Message} from "../../model/message.model";
import {createUrlResolverWithoutPackagePrefix} from "@angular/compiler";


@Component({
  selector: 'message-menu',
  templateUrl: 'message.component.html',
  styleUrls: ['message.component.css']
})
export class MessageComponent implements OnInit {
  currentUser: User;
  messageForm: FormGroup;
  message: Message;

  potentialRecipients: User[];

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private authService: AuthService) {

  }

  ngOnInit(){
    this.messageForm = this.formBuilder.group({
      recipient: ['', [Validators.required]],
      text: ['', [Validators.required]]
    })
    this.authService.currentUser.subscribe((user: User) => {
      this.currentUser = user;
      this.message = {
        id: null,
        sender: user,
        recipient: null,
        text: null,
        topic: null,
        creationDateTime: null
      }
      this.userService.getPotentialRecipientForManager(user.id).subscribe((potential) => {
          this.potentialRecipients = potential;
      })
    });

  }

  sendMessage(param){
    this.message.text = param.text;
    this.message.recipient = this.potentialRecipients.filter( r => r.id == param.recipient).pop();
    this.message.creationDateTime = new Date();
    this.message.sender.password = "";
    this.message.recipient.password = "";
    console.log(this.message.sender)
    this.userService.sendMessage(this.message).subscribe(() => {
      console.log("Send completed")
    });
  }

  validateField(field: string): boolean {
    return this.messageForm.get(field).valid || !this.messageForm.get(field).dirty;
  }
}
