import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../../../service/auth.service";
import {EmployeeService} from "../../../../service/employee.service";
import {User} from "../../../../model/user.model";
import {Request} from "../../../../model/request.model";


@Component({
  selector: 'user-home',
  templateUrl: 'active-request.component.html',
  styleUrls: ['active-request.component.css'],
})
export class ActiveRequest implements OnInit {
  private requests: Request[];
  private loaded: boolean = false;
  private currentUser: User;
  private totalItems: number;
  private per: number = 20;
  pageSize: number = 20;

  settings = {
    delete: true,
    add: true,
    info: true,
    multiSelect: false,
    filterRow: true,
    ajax: false,
    columns: {
      title: true,
      dateOfCreation: true,
      priorityStatus: true,
      progressStatus: true,
      reporter: false,
      assignee: true,
    }
  }

  constructor(private authService: AuthService,
              private employeeService: EmployeeService) {
  }


  pageChange(data){
      this.employeeService.getRequestsByReporter(this.currentUser.id, data.page, this.pageSize).subscribe(requests => {
        this.requests = requests.filter(r => r.progressStatus.name != "Closed");
      })
  }

  ngOnInit() {
    this.authService.currentUser.subscribe(u => {
      this.currentUser = u;
      this.employeeService.getRequestsByReporter(u.id, 1, this.pageSize).subscribe(requests => {
        this.requests = requests.filter(r => r.progressStatus.name != "Closed" && r.progressStatus.name != null);
        this.employeeService.countRequestsByReporter(u.id).subscribe(count => {
          this.totalItems = count;
          this.loaded = true;
        })
      });
  })
  }

  perChangeLoad(pageData) {
    this.pageSize = pageData.size;
    this.employeeService.getRequestsByReporter(this.currentUser.id, pageData.page, pageData.size).subscribe(requests => {
      this.requests = requests.filter(r => r.progressStatus.name != "Closed");
    })
  }
}
