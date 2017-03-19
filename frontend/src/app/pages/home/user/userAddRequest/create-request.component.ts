import {Component, OnInit, ViewChild, Output, EventEmitter} from "@angular/core";
import {UserService} from "../../../../service/user.service";
import {PriorityStatus} from "../../../../model/priority.model";
import {ModalDirective} from "ng2-bootstrap";
import {FormBuilder, Validators, FormGroup} from "@angular/forms";
import {Response} from "@angular/http";
import {ToastsManager} from "ng2-toastr";
import {EmployeeService} from "../../../../service/employee.service";
import {AuthService} from "../../../../service/auth.service";
import {User} from "../../../../model/user.model";
import {Request} from "../../../../model/request.model";

@Component({
  selector: 'create-user',
  templateUrl: 'create-request.component.html',
  styleUrls: ['create-request.component.css']
})
export class CreateEmployeeRequest implements OnInit {
  private currentUser: User;
  private priorities: PriorityStatus[];
  private userFormGroup: FormGroup;
  @Output()  requestAdd: EventEmitter<Request> = new EventEmitter();
  @ViewChild('staticModal') public staticModal:ModalDirective;

  constructor(private userService: UserService,
              private formBuilder: FormBuilder,
              private authService: AuthService,
              private toastr: ToastsManager,
              private employeeService: EmployeeService) {
  }

  ngOnInit(){
    this.authService.currentUser.subscribe(user => {
      this.currentUser = user;
    })
    this.userService.getPriorityStatuses().subscribe(priorities => {
      this.priorities = priorities;
    })
    this.userFormGroup = this.formBuilder.group({
      title: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(45)]],
      priorityStatus: [null, Validators.required],
      description: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(200)]],
    });
  }

  validate(field: string): boolean {
    return this.userFormGroup.get(field).valid || !this.userFormGroup.get(field).dirty;
  }

  createNewSimpleRequest(param){
    console.log("trying to send");
    param.lastChanger = this.currentUser;
    param.reporter = this.currentUser;
    this.employeeService.createEmployeeRequest(param).subscribe(
      (resp) => {
        this.requestAdd.emit(param);
        this.staticModal.hide();
        this.toastr.success("Request have been added");
      },
      (err) => { // on error console.log(err);
        this.toastr.error("Something gone wrong");
      }
    );
  }

}
