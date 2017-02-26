import {Component} from "@angular/core";

@Component({
  selector: 'request-info',
  templateUrl: 'request.component.html',
  styleUrls: ['request.component.css']
})
export class RequestComponent {
  request = {
    title: 'Reqest',
    description: "Description of request. There are some information.",
    progressStatus: "Some status",
    reporter: "reporter",
    assignedBy: "assigner",
    estimateTime: 9,
    dateOfCreation: new Date("1943-03-12")
  }

}
