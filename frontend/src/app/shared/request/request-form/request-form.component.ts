import {Component, OnInit, ViewChild, EventEmitter, Output, Input} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {ModalComponent} from "ng2-bs3-modal/components/modal";
import {RequestService} from "../../../service/request.service";
import {CustomValidators} from "ng2-validation";
import {User} from "../../../model/user.model";
import {AuthService} from "../../../service/auth.service";
import {ToastsManager} from "ng2-toastr";
import {UserService} from "../../../service/user.service";
import {ProgressStatus, Request} from "../../../model/request.model";
import {Role} from "../../../model/role.model";
import {DatePipe} from "@angular/common";
import {Response} from "@angular/http";
import {PriorityStatus} from "../../../model/priority.model";

declare let $: any;

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

  @Input()
  requests: Request[];
  @Output()
  updated: EventEmitter<any> = new EventEmitter();

  @ViewChild('requestFormModal')
  modal: ModalComponent;

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private requestService: RequestService,
              private authService: AuthService,
              private toastr: ToastsManager,
              private datePipe: DatePipe) {
  }

  ngOnInit(): void {
    this.requestForm = this.formBuilder.group({
      title: ['', [Validators.required, Validators.minLength(5), Validators.maxLength(45)]],
      priorityStatus: [null, Validators.required],
      description: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(200)]]
    });

    this.authService.currentUser.subscribe((user: User) => {
      this.request = {
        reporter: user,
        assignee: this.assignee,
        title: null,
        description: null,
        priorityStatus: null,
        estimateTimeInDays: null,
        dateOfCreation: null,
        lastChanger: user
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

  closeModal() {
    this.requestForm.reset();
  }

  validate(field: string): boolean {
    return this.requestForm.get(field).valid || !this.requestForm.get(field).dirty;
  }

  createNewRequest(params): void {
    this.request.dateOfCreation = new Date();
    this.request.title = params.title;
    this.request.description = params.description;
    this.request.priorityStatus = params.priorityStatus;
    this.request.progressStatus = this.progressStatus;
    this.request.reporter.password = "";
    this.requestService.create(this.request).subscribe((resp: Response) => {
      this.updateArray(<Request> resp.json());
      this.modal.close();
      this.toastr.success("Request was created successfully", "Success!");
    }, e => this.handleErrorCreateRequest(e));
  }

  private updateArray(request: Request): void {
    this.requests.unshift(request);
    this.updated.emit(this.requests);
  }

  private handleErrorCreateRequest(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't create request", 'Error');
    }
  }
}
