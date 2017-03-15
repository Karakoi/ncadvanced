import {Component, OnInit} from "@angular/core";
import {LocalDataSource} from "ng2-smart-table";
import {AuthService} from "../../../../service/auth.service";
import {EmployeeService} from "../../../../service/employee.service";



@Component({
  selector: 'user-home',
  templateUrl: 'active-request.component.html',
  styleUrls: ['active-request.component.css'],
})
export class ActiveRequest implements OnInit {
  private totalItems: number;
  private per: number = 20;
  private data: Array<any> = new Array();
  private source: LocalDataSource = new LocalDataSource();


  constructor(private authService: AuthService,
              private employeeService: EmployeeService) {
  }

  changed(data) {
    this.authService.currentUser.subscribe(u => {
      u;
      this.employeeService.getRequestsByReporter(u.id, data.page).subscribe(requests => {
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
            "progress": r.progressStatus.name,
          })
        })
        this.source.load(this.data);
      })
      this.source.setPage(data.page)
    })
  }

  ngOnInit() {
    this.authService.currentUser.subscribe(u => {
      u;
      this.employeeService.getRequestsByReporter(u.id, 1).subscribe(requests => {
        requests.forEach(r => {
          if (r.assignee.firstName === null) {
            r.assignee.firstName = "";
            r.assignee.lastName = "";
          }
          this.data.push({
            "title": r.title,
            "description": r.description,
            "estimateTime": r.estimateTimeInDays,
            "dateOfCreation": r.dateOfCreation,
            "assignee": r.assignee.firstName + " " + r.assignee.lastName,
            "priority": r.priorityStatus.name,
            "progress": r.progressStatus.name,
          })
        })
        this.source.load(this.data);
      })
      this.employeeService.countRequestsByReporter(u.id).subscribe(count => {
          this.totalItems = count;
        })
    })
  }

  settings = {
    hideHeader: true,
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
