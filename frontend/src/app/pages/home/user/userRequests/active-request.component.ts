import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../../../service/auth.service";
import {EmployeeService} from "../../../../service/employee.service";
import {User} from "../../../../model/user.model";
import {Request} from "../../../../model/request.model";
import {ReportService} from "../../../../service/report.service";


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


  settings = {
    delete: true,
    add: true,
    info: true,
    multiSelect: false,
    filterRow: true,
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
              private employeeService: EmployeeService,
              private reportService: ReportService,) {
  }


  pageChange(data){
      this.employeeService.getRequestsByReporter(this.currentUser.id, data.page).subscribe(requests => {
        this.requests = requests.filter(r => r.progressStatus.name != "CLOSED");
      })
  }

  ngOnInit() {
    this.authService.currentUser.subscribe(u => {
      this.currentUser = u;
      this.employeeService.getRequestsByReporter(u.id, 1).subscribe(requests => {
        requests.forEach(r => {
          console.log(r.progressStatus.name);
        });
        this.requests = requests.filter(r => r.progressStatus.name != "CLOSED");
        this.employeeService.countRequestsByReporter(u.id).subscribe(count => {
          this.totalItems = count;
          this.loaded = true;
        })
      });
  })
  }


}
