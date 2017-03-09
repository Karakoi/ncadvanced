import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {User} from "../../../model/user.model";
import {Message} from "../../../model/message.model";
import {UserService} from "../../../service/user.service";
import {AuthService} from "../../../service/auth.service";
import {ToastsManager} from "ng2-toastr";

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
              private authService: AuthService,
              private toastr: ToastsManager) {

  }

  ngOnInit() {
    this.messageForm = this.formBuilder.group({
      recipient: ['', [Validators.required]],
      text: ['', [Validators.required]]
    });
    this.authService.currentUser.subscribe((user: User) => {
      this.currentUser = user;
      this.message = {
        sender: user,
        recipient: null,
        text: null,
        topic: null,
        dateAndTime: null
      };
      if(this.authService.role === 'office manager') {
        this.userService.getPotentialRecipientForManager(user.id).subscribe((potential) => {
        this.potentialRecipients = potential;
        }) }
      else if(this.authService.role === 'employee') {
        this.userService.getPotentialRecipientForEmployee(user.id).subscribe((potential) => {
          this.potentialRecipients = potential;
        })
      }
    });
  }

  sendMessage(param) {
    this.message.text = param.text;
    this.message.recipient = this.potentialRecipients.filter(r => r.id == param.recipient).pop();
    this.message.dateAndTime = new Date();
    this.message.sender.password = "";
    this.message.recipient.password = "";
    this.userService.sendMessage(this.message).subscribe(() => {
      this.toastr.success("Message sent.", "Success");
    });
  }

  validateField(field: string): boolean {
    return this.messageForm.get(field).valid || !this.messageForm.get(field).dirty;
  }
}
