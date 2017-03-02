import {Component} from "@angular/core";

@Component({
  selector: 'chat',
  templateUrl: 'chat.component.html',
  styleUrls: ['chat.component.css']
})
export class ChatComponent {

  messages = [
    {
      author: {
        firstName: "Evheniy",
        lastName: "Deyneka",
        img: "http://www.iconsfind.com/wp-content/uploads/2016/10/20161014_58006bf27a2d0.png"
      },
      time: "13:34",
      text: "Lorem ipsum, et iaculis purus.",
      type: "user-msg"
    },
    {
      author: {
        firstName: "Bohdan",
        lastName: "Bachkala",
        img: "http://www.iconsfind.com/wp-content/uploads/2016/10/20161014_58006bff8b1de.png"
      },
      time: "13:45",
      text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
      type: "friend-msg"
    },
    {
      author: {
        firstName: "Evheniy",
        lastName: "Deyneka",
        img: "http://www.iconsfind.com/wp-content/uploads/2016/10/20161014_58006bf27a2d0.png"
      },
      time: "14:11",
      text: "Lorem ipsum dolor sit amet.",
      type: "user-msg"
    },
    {
      author: {
        firstName: "Bohdan",
        lastName: "Bachkala",
        img: "http://www.iconsfind.com/wp-content/uploads/2016/10/20161014_58006bff8b1de.png"
      },
      time: "14:27",
      text: "Etiam at quam vitae diam euismod lacinia.",
      type: "friend-msg"
    },
    {
      author: {
        firstName: "Evheniy",
        lastName: "Deyneka",
        img: "http://www.iconsfind.com/wp-content/uploads/2016/10/20161014_58006bf27a2d0.png"
      },
      time: "15:11",
      text: "Aliquam erat volutpat.",
      type: "user-msg"
    },
    {
      author: {
        firstName: "Bohdan",
        lastName: "Bachkala",
        img: "http://www.iconsfind.com/wp-content/uploads/2016/10/20161014_58006bff8b1de.png"
      },
      time: "15:27",
      text: "Mauris et tellus ut neque vehicula maximus.",
      type: "friend-msg"
    },
    {
      author: {
        firstName: "Evheniy",
        lastName: "Deyneka",
        img: "http://www.iconsfind.com/wp-content/uploads/2016/10/20161014_58006bf27a2d0.png"
      },
      time: "16:01",
      text: "Morbi a elit tempor, venenatis justo euismod, gravida urna.",
      type: "user-msg"
    }
  ]
}
