import {Component, OnInit} from "@angular/core";
import {LocalDataSource} from "ng2-smart-table";
import {AuthService} from "../../../../service/auth.service";
import {EmployeeService} from "../../../../service/employee.service";
import {FormGroup } from "@angular/forms";
import {User} from "../../../../model/user.model";
import {ToastsManager} from "ng2-toastr";
import {Request} from "../../../../model/request.model";


@Component({
  selector: 'user-home',
  templateUrl: 'active-request.component.html',
  styleUrls: ['active-request.component.css'],
})
export class ActiveRequest implements OnInit {
  private currentUser: User;
  private totalItems: number;
  private per: number = 20;
  private data: Array<any> = new Array();
  private source: LocalDataSource = new LocalDataSource();
  private userFormGroup: FormGroup;


  constructor(private authService: AuthService,
              private employeeService: EmployeeService,
              private toastr: ToastsManager) {
  }

  changed(data) {
    this.authService.currentUser.subscribe(u => {
      this.currentUser = u;
      this.employeeService.getRequestsByReporter(u.id, data.page).subscribe(requests => {
        requests.forEach(r => {
          if (r.assignee.firstName === null) {
            r.assignee.firstName = "";
            r.assignee.lastName = "";
          }
          this.data.shift();
          this.data.push(this.mapRequestToReadable(r))
        });
        this.source.load(this.data);
      });
      this.source.setPage(data.page)
    })
  }

  ngOnInit() {
    this.authService.currentUser.subscribe(u => {
      this.currentUser = u;
      this.employeeService.getRequestsByReporter(u.id, 1).subscribe(requests => {
        requests.forEach(r => {
          if (r.assignee.firstName === null) {
            r.assignee.firstName = "";
            r.assignee.lastName = "";
          }
          this.data.push(this.mapRequestToReadable(r))
        });
        this.source.load(this.data);
      });
      this.employeeService.countRequestsByReporter(u.id).subscribe(count => {
        this.totalItems = count;
      })
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

  addToTable(request: Request){
    this.data.unshift(request);
    this.source.load((this.data));
  }

  mapRequestToReadable(request: Request){
    if (request.assignee.firstName === null) {
      request.assignee.firstName = "";
      request.assignee.lastName = "";
    }
    return {
      "title": request.title,
      "description": request.description,
      "estimateTime": request.estimateTimeInDays,
      "dateOfCreation": request.dateOfCreation,
      "assignee": request.assignee.firstName + " " + request.assignee.lastName,
      "priority": request.priorityStatus.name,
      "progress": request.progressStatus.name,
    }
  }

  validate(field: string): boolean {
    return this.userFormGroup.get(field).valid || !this.userFormGroup.get(field).dirty;
  }
}
