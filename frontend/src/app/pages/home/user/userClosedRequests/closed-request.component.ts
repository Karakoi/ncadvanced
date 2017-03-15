import {Component, OnInit, ViewChild} from "@angular/core";
import {RequestService} from "../../../../service/request.service";
import {LocalDataSource} from "ng2-smart-table";
import {AuthService} from "../../../../service/auth.service";
import {EmployeeService} from "../../../../service/employee.service";
import {PriorityStatus} from "../../../../model/priority.model";
import {UserService} from "../../../../service/user.service";
import {FormBuilder, Validators, FormGroup} from "@angular/forms";
import {User} from "../../../../model/user.model";
import {Response} from "@angular/http";
import {ToastsManager} from "ng2-toastr";
import {ModalDirective} from "ng2-bootstrap";
import {Request} from "../../../../model/request.model";



@Component({
  selector: 'user-home',
  templateUrl: 'closed-request.component.html',
  styleUrls: ['closed-request.component.css']
})
export class ClosedRequest implements OnInit {
  @ViewChild('staticModal') public staticModal:ModalDirective;
  private selected: number[];
  private priorities: PriorityStatus[];
  private currentUser: User;
  private totalItems: number = 20;
  private per: number = 20;
  private data:Array<any> = new Array();
  private source: LocalDataSource = new LocalDataSource() ;
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
      this.employeeService.getClosedRequestsByReporter(u.id, data.page).subscribe(requests => {
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
    this.userFormGroup = this.formBuilder.group({
      title: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(45)]],
      priorityStatus: ['', Validators.required],
      description: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(200)]],
    });

    this.userService.getPriorityStatuses().subscribe(priorities => {
      this.priorities = priorities;
    });
    this.authService.currentUser.subscribe(u => {
      this.currentUser = u;
      this.employeeService.getClosedRequestsByReporter(u.id, 1).subscribe(requests => {
        requests.forEach(r => {
          if (r.assignee.firstName === null) {
            r.assignee.firstName = "";
            r.assignee.lastName = "";
          }
          this.data.push(this.mapRequestToReadable(r))
        });
        this.source.load(this.data);
      });
      this.employeeService.countClosedRequestsByReporter(u.id).subscribe(count => {
        this.totalItems = count;
      })
    })
  }
  settings = {
    selectMode: 'multi',

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

  onSelect(data){
    this.selected = data.selected.map(e => e.id)
    console.log(this.selected)
  }

  reopen(data){
    if(this.selected.length > 0){
      this.employeeService.reopenRequests(this.selected).subscribe(resp => {
        console.log("it's good")
      })
    }
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
