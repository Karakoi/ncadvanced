import {Component} from "@angular/core";

@Component({
  selector: 'forum-info',
  templateUrl: 'forum.component.html',
  styleUrls: ['forum.component.css']
})
export class ForumComponent {

  topics = [
    {
      title: "Some general topic",
      role: "Employees",
      messages: 12,
      lastUpdate: {
        date: "2017-02-28 01:21",
        author: "Eugene Deyneka"
      }
    },
    {
      title: "Some flood topic",
      role: "Office managers",
      messages: 21,
      lastUpdate: {
        date: "2017-03-02 16:21",
        author: "Eugene Deyneka"
      }
    },
    {
      title: "Some development topic",
      role: "Office managers",
      messages: 34,
      lastUpdate: {
        date: "2017-03-02 17:33",
        author: "Bohdan Bachkala"
      }
    },
    {
      title: "Some random topic",
      role: "Employees",
      messages: 7,
      lastUpdate: {
        date: "2017-03-04 11:21",
        author: "Bohdan Bachkala"
      }
    },
    {
      title: "Some test topic",
      role: "Office managers",
      messages: 121,
      lastUpdate: {
        date: "2017-03-05 13:55",
        author: "Eugene Deyneka"
      }
    }
  ]
}
