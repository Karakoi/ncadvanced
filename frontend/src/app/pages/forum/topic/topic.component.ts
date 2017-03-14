import {Component, OnInit} from "@angular/core";
import {TopicService} from "../../../service/topic.service";
import {Topic} from "../../../model/topic.model";
import {ActivatedRoute} from "@angular/router";
import {Message} from "../../../model/message.model";
import {FormGroup, Validators, FormBuilder} from "@angular/forms";
import {User} from "../../../model/user.model";
import {AuthService} from "../../../service/auth.service";
import {Response} from "@angular/http";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'topic-info',
  templateUrl: 'topic.component.html',
  styleUrls: ['topic.component.css']
})
export class TopicComponent implements OnInit {
  messageForm: FormGroup;
  topic: Topic;
  messages: Message[];
  message: Message;
  user: User;

  constructor(private topicService: TopicService,
              private route: ActivatedRoute,
              private formBuilder: FormBuilder,
              private authService: AuthService,
              private toastr: ToastsManager) {
  }

  ngOnInit(): void {
    this.message = {
      sender: this.user,
      text: null,
      dateAndTime: null
    };

    this.authService.currentUser.subscribe((user: User) => {
      this.message.sender = user;
    });

    this.messageForm = this.formBuilder.group({
      text: ['', Validators.required]
    });

    this.route.params.subscribe(params => {
      let id = +params['id'];
      this.topicService.get(id).subscribe((topic: Topic) => {
        console.log(topic);
        this.topic = topic;
      });
    });

    this.route.params.subscribe(params => {
      let id = +params['id'];
      this.topicService.getMessages(id).subscribe((messages: Message[]) => {
        console.log(messages);
        this.messages = messages;
      });
    });
  }

  createNewMessage(params) {
    this.message.text = params.text;
    this.message.dateAndTime = new Date();
    this.message.sender.password = "";
    console.log(this.message);
    this.topicService.createMessage(this.message).subscribe((resp: Response) => {
      this.toastr.success("Message sended", "Success")
    }, e => this.handleErrorCreateMessage(e));
  }

  private handleErrorCreateMessage(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't create message", 'Error');
    }
  }
}
