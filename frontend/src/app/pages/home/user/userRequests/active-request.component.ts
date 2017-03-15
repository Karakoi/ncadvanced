import {Component, OnInit, ViewChild} from "@angular/core";
import {LocalDataSource} from "ng2-smart-table";
import {AuthService} from "../../../../service/auth.service";
import {EmployeeService} from "../../../../service/employee.service";
import {PriorityStatus} from "../../../../model/priority.model";
import {UserService} from "../../../../service/user.service";
import {FormGroup, Validators, FormBuilder} from "@angular/forms";
import {Response} from "@angular/http";
import {User} from "../../../../model/user.model";
import {ModalDirective} from "ng2-bootstrap";
import {ToastsManager} from "ng2-toastr";


@Component({
  selector: 'user-home',
  templateUrl: 'active-request.component.html',
  styleUrls: ['active-request.component.css'],
})
export class ActiveRequest implements OnInit {
  private currentUser: User;
  @ViewChild('staticModal') public staticModal:ModalDirective;
  private totalItems: number;
  private per: number = 20;
  private data: Array<any> = new Array();
  private source: LocalDataSource = new LocalDataSource();
  private priorities: PriorityStatus[];
  private userFormGroup: FormGroup;


  constructor(private authService: AuthService,
              private employeeService: EmployeeService,
              private formBuilder: FormBuilder,
              private userService: UserService,
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
    this.userFormGroup = this.formBuilder.group({
      title: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(45)]],
      priorityStatus: [null, Validators.required],
      description: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(200)]],
    });

    this.userService.getPriorityStatuses().subscribe(priorities => {
      this.priorities = priorities;
    });

    this.authService.currentUser.subscribe(u => {
      this.currentUser = u;
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

  createNewSimpleRequest(param){
    console.log("trying to send");
    param.lastChanger = this.currentUser;
    param.reporter = this.currentUser;
    this.employeeService.createEmployeeRequest(param).subscribe(
      (resp: Response) => {
        this.staticModal.hide();
        this.toastr.success("Request have been added");
      },
      (err) => { // on error console.log(err);
        this.toastr.error("Something gone wrong");
      }
    );

  }

  validate(field: string): boolean {
    return this.userFormGroup.get(field).valid || !this.userFormGroup.get(field).dirty;
  }
}
