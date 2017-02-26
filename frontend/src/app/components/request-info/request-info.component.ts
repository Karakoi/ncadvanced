import { Component } from '@angular/core';

@Component({
  selector: 'request-info',
  templateUrl: './request-info.component.html',
  styleUrls: ['./request-info.component.css']
})
export class RequestInfo{
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
