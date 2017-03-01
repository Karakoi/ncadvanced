import {Component} from "@angular/core";

@Component({
  selector: 'request-info',
  templateUrl: 'request.component.html',
  styleUrls: ['request.component.css']
})
export class RequestComponent {
  showDescription: boolean = true;
  showHistory: boolean = true;

  changeShowDescription() {
    this.showDescription = !this.showDescription;
  }

  changeShowHistory() {
    this.showHistory = !this.showHistory;
  }

  request = {
    title: 'Implement request profile',
    type: 'Request',
    description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam justo ex, placerat id iaculis vitae, eleifend nec dolor. Duis nec efficitur eros, vitae pellentesque sapien. Nulla in dolor et risus maximus accumsan. Morbi laoreet eu ante quis vulputate. Duis sed interdum ligula, vel sodales magna. Nullam commodo nec ex venenatis rhoncus. Maecenas efficitur condimentum venenatis. Maecenas tempor gravida quam, at mollis eros hendrerit ac.",
    progressStatus: "In progress",
    priorityStatus: "High",
    reporter: {
      firstName: "Bohdan",
      lastName: "Bachkala"
    },
    assignee: {
      firstName: "Evgeniy",
      lastName: "Deyneka"
    },
    estimateTime: 9,
    dateOfCreation: "2017-02-27 19:21",
    historyDetails: [
      {
        columnName: "Progress status",
        changer: {
          firstName: "Evgeniy",
          lastName: "Deyneka"
        },
        oldValue: "Open",
        newValue: "In progress",
        dateOfChange: "2017-02-28 01:23"
      },
      {
        columnName: "Title",
        changer: {
          firstName: "Evgeniy",
          lastName: "Deyneka"
        },
        oldValue: "Implement",
        newValue: "Implement profile",
        dateOfChange: "2017-02-28 00:48"
      },
      {
        columnName: "Title",
        changer: {
          firstName: "Bohdan",
          lastName: "Bachkala"
        },
        oldValue: "Implement profile",
        newValue: "Implement request profile",
        dateOfChange: "2017-02-28 01:14"
      }
    ]
  }
}
