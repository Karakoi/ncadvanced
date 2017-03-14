import {Component, OnInit} from "@angular/core";
import {RequestService} from "../../../../service/request.service";
import {LocalDataSource} from "ng2-smart-table";



@Component({
  selector: 'user-home',
  templateUrl: 'closed-request.component.html',
  styleUrls: ['closed-request.component.css']
})
export class ClosedRequest implements OnInit {
  private totalItems: number = 100;
  private per: number = 20;
  private data:Array<any> = new Array();
  private source: LocalDataSource = new LocalDataSource() ;

  constructor(private requestService: RequestService) {
  }

  changed(data){
    this.requestService.getAll(data.page).subscribe(requests => {
      requests.forEach(r => {
        if (r.assignee.firstName === null) {
          r.assignee.firstName = "";
          r.assignee.lastName = "";
        }
        this.data.shift()
        this.data.push({
          "title": r.title,
          "description": r.description,
          "estimateTime": r.estimateTimeInDays,
          "dateOfCreation": r.dateOfCreation,
          "assignee": r.assignee.firstName + " " + r.assignee.lastName,
          "priority": r.priorityStatus.name,
          "progress": r.progressStatus.name
        })
      })
      this.source.load(this.data);
    })

    this.source.setPage(data.page)
  }

  ngOnInit() {
    this.requestService.getAll(1).subscribe(requests => {
      requests.forEach(r => {
        if(r.assignee.firstName === null) {
          r.assignee.firstName = "";
          r.assignee.lastName = "";
        }
        this.data.push({
          "title": r.title,
          "description" : r.description,
          "estimateTime": r.estimateTimeInDays,
          "dateOfCreation" : r.dateOfCreation,
          "assignee": r.assignee.firstName + " " + r.assignee.lastName,
          "priority" : r.priorityStatus.name,
          "progress" : r.progressStatus.name
        })
      })
      this.source.load(this.data);
    })
  }
  settings = {
    actions: {
      edit: false,
      delete: false,
      add: false
    },
    pager: {
      display: false,
      perPage: 20,
    },
    columns: {
      title: {
        title: 'Title'
      },
      estimateTime: {
        title: 'Estimate time in days'
      },
      assignee: {
        title: 'Assignee'
      },
      priority: {
        title: 'Priority'
      },
      progress: {
        title: 'Progress status'
      },
    },
  };

}
