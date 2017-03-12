import {Component, OnInit} from "@angular/core";
import {Topic} from "../../model/topic.model";
import {TopicService} from "../../service/topic.service";

declare let $: any;

@Component({
  selector: 'forum-info',
  templateUrl: 'forum.component.html',
  styleUrls: ['forum.component.css']
})
export class ForumComponent implements OnInit {
  topics: Topic[];
  pageCount: number;

  ngOnInit(): void {
    this.topicService.getAll(1).subscribe((topics: Topic[]) => {
      this.topics = topics;
      console.log(topics)
    });
    this.topicService.getPageCount().subscribe((count) => this.pageCount = count);
  }

  constructor(private topicService: TopicService) {
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
