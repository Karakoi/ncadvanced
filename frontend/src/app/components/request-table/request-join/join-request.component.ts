import {Component, OnInit, ViewChild, Input, Output, EventEmitter} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {ToastsManager} from "ng2-toastr";
import {CustomValidators} from "ng2-validation";
import {ModalComponent} from "ng2-bs3-modal/components/modal";
import {Request} from "../../../model/request.model";
import {RequestService} from "../../../service/request.service";
import {AuthService} from "../../../service/auth.service";
import {User} from "../../../model/user.model";

//TODO Implement new joining request mechanism

@Component({
  selector: 'join-request',
  templateUrl: 'join-request.component.html'
})
export class JoinRequestComponent implements OnInit {
  requestForm: FormGroup;
  @Input()
  requests:Request[];
  @Input()
  checked:number[];
  @Output()
  updated:EventEmitter<any> = new EventEmitter();

  @ViewChild('joinRequestFormModal')
  modal:ModalComponent;

  public request:Request;

  constructor(private formBuilder: FormBuilder,
              private requestService:RequestService,
              private toastr:ToastsManager,
              private authService:AuthService) {
  }

  ngOnInit(): void {
    this.requestForm = this.formBuilder.group({
      title: ['', [Validators.required, Validators.maxLength(100)]],
      description: ['', [Validators.required, Validators.maxLength(255)]],
      estimateTimeInDays: ['', [Validators.required, CustomValidators.min(0), CustomValidators.max(30)]]
    });

    this.authService.currentUser.subscribe((user: User) => {
      this.request = {
        reporter: user,
        assignee: user,
        title: null,
        description: null,
        priorityStatus: null,
        estimateTimeInDays: null,
        dateOfCreation: null,
        lastChanger: user
      };
    });
  }

  joinRequests(params) {
    console.log("Checked : " + this.checked);
    this.authService.currentUser.subscribe((user:User) => {
      this.request.assignee = user;
      this.request.parentId = null;
      this.request.reporter = user;
      this.request.lastChanger = user;
      this.request.title = params.title;
      this.request.description = params.description;
      this.request.estimateTimeInDays = params.estimateTimeInDays;
      this.requests = this.requests.filter(item => this.checked.indexOf(item["id"]) < 0);
      this.requestService.join(this.request, this.checked).subscribe((request) => {
        this.toastr.success("Requests was joined successfully", "Success!");
        this.modal.close();
        this.updated.emit(this.requests);
      }, e => this.handleErrorJoinRequests(e));
    });

  }

  get getChecked(){
    return this.requests.filter(request => this.checked.indexOf(request.id) > -1);
  }

  validate(field: string): boolean {
    return this.requestForm.get(field).valid || !this.requestForm.get(field).dirty;
  }

  private handleErrorJoinRequests(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't delete user", 'Error');
    }
  }
}
