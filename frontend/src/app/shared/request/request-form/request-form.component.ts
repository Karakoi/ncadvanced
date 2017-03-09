import {Component, OnInit, ViewChild} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {ModalComponent} from "ng2-bs3-modal/components/modal";
import {RequestService} from "../../../service/request.service";
import {CustomValidators} from "ng2-validation";
import {User} from "../../../model/user.model";
import {AuthService} from "../../../service/auth.service";
import {ToastsManager} from "ng2-toastr";
import {UserService} from "../../../service/user.service";
import {PriorityStatus, ProgressStatus, Request} from "../../../model/request.model";
import {Role} from "../../../model/role.model";

@Component({
  selector: 'request-form',
  templateUrl: 'request-form.component.html',
  styleUrls: ['request-form.component.css']
})
export class RequestFormComponent implements OnInit {
  requestForm: FormGroup;
  priorityStatuses: PriorityStatus[];
  request: Request;
  priorityStatus: PriorityStatus;
  progressStatus: ProgressStatus;
  assignee: User;
  role: Role;

  @ViewChild('requestFormModal')
  modal: ModalComponent;

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private requestService: RequestService,
              private authService: AuthService,
              private toastr: ToastsManager) {
  }

  ngOnInit(): void {
    this.requestForm = this.formBuilder.group({
      title: ['', [Validators.required, Validators.maxLength(100)]],
      priorityStatus: [null, Validators.required],
      description: ['', [Validators.required, Validators.maxLength(255)]],
      estimateTimeInDays: ['', [CustomValidators.min(0), CustomValidators.max(30)]]
    });

    this.authService.currentUser.subscribe((user: User) => {
      this.request = {
        reporter: user,
        assignee: this.assignee,
        title: null,
        description: null,
        priorityStatus: null,
        estimateTimeInDays: null,
        dateOfCreation: null
      };
    });

    this.role = {
      name: ""
    };

    this.assignee = {
      firstName: "",
      lastName: "",
      email: "",
      password: "",
      role: this.role
    };

    this.progressStatus = {
      id: 5,
      name: "Free",
      value: 300
    };

    this.userService.getPriorityStatuses().subscribe((priorityStatuses: PriorityStatus[]) => {
      this.priorityStatuses = priorityStatuses;
    });
  }

  createNewRequest(params): void {
    console.log(params.priorityStatus);
    this.request.dateOfCreation = new Date();
    this.request.title = params.title;
    this.request.description = params.description;
    this.request.estimateTimeInDays = params.estimateTimeInDays;
    this.request.priorityStatus = params.priorityStatus;
    this.request.progressStatus = this.progressStatus;
    this.request.reporter.password = "";
    console.log(this.request);
    this.requestService.create(this.request).subscribe(() => {
      this.toastr.success("Request was created successfully", "Success!");
    }, e => this.handleErrorCreateRequest(e));
  }

  private handleErrorCreateRequest(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't create request", 'Error');
    }
  }

  validate(field: string): boolean {
    return this.requestForm.get(field).valid || !this.requestForm.get(field).dirty;
  }
}
