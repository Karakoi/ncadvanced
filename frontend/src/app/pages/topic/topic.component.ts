import {Component} from "@angular/core";

@Component({
  selector: 'topic-info',
  templateUrl: 'topic.component.html',
  styleUrls: ['topic.component.css']
})
export class TopicComponent {

  messages = [
    {
      img: "http://www.iconsfind.com/wp-content/uploads/2016/10/20161014_58006bf27a2d0.png",
      title: "Some topic message",
      author: {
        firstName: "Eugene",
        lastName: "Deyneka",
        role: "Employee"
      },
      text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
      dateOfCreation: "2017-03-01 13:21"
    },
    {
      img: "http://www.iconsfind.com/wp-content/uploads/2016/10/20161014_58006bff8b1de.png",
      title: "Some topic message",
      author: {
        firstName: "Bohdan",
        lastName: "Bachkala",
        role: "Office manager"
      },
      text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
      dateOfCreation: "2017-03-01 13:33"
    },
    /*{
      img: "http://www.iconsfind.com/wp-content/uploads/2016/10/20161014_58006bf27a2d0.png",
      title: "Some topic message",
      author: {
        firstName: "Eugene",
        lastName: "Deyneka",
        role: "Employee"
      },
      text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
      dateOfCreation: "2017-03-01 14:02"
    },
    {
      img: "http://www.iconsfind.com/wp-content/uploads/2016/10/20161014_58006bff8b1de.png",
      title: "Some topic message",
      author: {
        firstName: "Bohdan",
        lastName: "Bachkala",
        role: "Office manager"
      },
      text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
      dateOfCreation: "2017-03-01 14:41"
    },
    {
      img: "http://www.iconsfind.com/wp-content/uploads/2016/10/20161014_58006bf27a2d0.png",
      title: "Some topic message",
      author: {
        firstName: "Eugene",
        lastName: "Deyneka",
        role: "Employee"
      },
      text: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
      dateOfCreation: "2017-03-01 15:22"
    }*/
  ]
}
