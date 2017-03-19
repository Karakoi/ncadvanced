import {Component, ViewChild, OnInit, EventEmitter, Output, Input} from "@angular/core";
import {ModalComponent} from "ng2-bs3-modal/components/modal";
import {FormGroup, Validators, FormBuilder} from "@angular/forms";
import {ToastsManager} from "ng2-toastr";
import {Response} from "@angular/http";
import {RequestService} from "../../../service/request.service";
import {Request, ProgressStatus} from "../../../model/request.model";
import {User} from "../../../model/user.model";
import {AuthService} from "../../../service/auth.service";
import {PriorityStatus} from "../../../model/priority.model";

@Component({
  selector: 'add-sub-request',
  templateUrl: 'add-sub-request.component.html'
})
export class AddSubRequestComponent implements OnInit {

  addSubRequestForm: FormGroup;
  currentUser: User;

  @Input()
  parentRequest: number;

  @Input()
  subRequests: Request[];

  @Output()
  updated: EventEmitter<any> = new EventEmitter();

  @ViewChild('addSubRequestFormModal')
  modal: ModalComponent;

  constructor(private requestService: RequestService,
              private formBuilder: FormBuilder,
              private toastr: ToastsManager,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.addSubRequestForm = this.formBuilder.group({
      title: ['', Validators.required],
      description: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(200)]]
    });
    this.authService.currentUser.subscribe((user: User) => {
      this.currentUser = user;
    });
  }

  addSubRequest(newSubRequest) {
    newSubRequest.dateOfCreation = new Date();
    newSubRequest.reporter = this.currentUser;
    newSubRequest.lastChanger = this.currentUser;
    newSubRequest.priorityStatus = <PriorityStatus>{};
    newSubRequest.progressStatus = <ProgressStatus>{};
    newSubRequest.assignee = <User>{};
    newSubRequest.parentId = 115;
    this.requestService.createSubRequest(newSubRequest).subscribe((resp: Response) => {
      this.updateArray(<Request> resp.json());
      this.modal.close();
      this.toastr.success("Sub request was created successfully", "Success!");
    }, e => this.handleErrorCreateUser(e));
  }

  private updateArray(subRequest: Request): void {
    this.subRequests.unshift(subRequest);
    this.updated.emit(this.subRequests);
  }

  closeModal() {
    this.addSubRequestForm.reset();
  }

  validateField(field: string): boolean {
    return this.addSubRequestForm.get(field).valid || !this.addSubRequestForm.get(field).dirty;
  }

  private handleErrorCreateUser(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't create user", 'Error');
    }
  }
}
