import {Component, OnInit, ViewChild} from "@angular/core";
import {Topic} from "../../model/topic.model";
import {TopicService} from "../../service/topic.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ToastsManager} from "ng2-toastr";
import {Response} from "@angular/http";
import {ModalComponent} from "ng2-bs3-modal/components/modal";

declare let $: any;

@Component({
  selector: 'forum-info',
  templateUrl: 'forum.component.html',
  styleUrls: ['forum.component.css']
})
export class ForumComponent implements OnInit {
  topicForm: FormGroup;
  topics: Topic[];
  topic: Topic;
  pageCount: number;

  @ViewChild('topicModal')
  modal: ModalComponent;

  ngOnInit(): void {
    this.topic = {
      id: null,
      title: null
    };
    this.topicForm = this.formBuilder.group({
      title: ['', Validators.required]
    });
    this.topicService.getAll(1).subscribe((topics: Topic[]) => {
      this.topics = topics;
      console.log(topics)
    });
    this.topicService.getPageCount().subscribe((count) => this.pageCount = count);
  }

  constructor(private topicService: TopicService,
              private formBuilder: FormBuilder,
              private toastr: ToastsManager) {
  }

  createNewTopic(params) {
    this.topic.title = params.title;
    this.topicService.create(this.topic).subscribe((resp: Response) => {
      this.toastr.success("Topic " + this.topic.title + " created", "Success")
      this.modal.close();
    }, e => this.handleErrorCreateTopic(e));
  }

  private handleErrorCreateTopic(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't create topic", 'Error');
    }
  }

  createRange(number) {
    let items: number[] = [];
    for (let i = 2; i <= number; i++) {
      items.push(i);
    }
    return items;
  }

  load(data) {
    $('.paginate_button').removeClass('active');
    let page = data.target.text;
    $(data.target.parentElement).addClass('active');
    this.topicService.getAll(page).subscribe((topics: Topic[]) => {
      this.topics = topics;
    });
  }
}
