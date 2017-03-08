import {Component, OnInit, ViewChild} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {ModalComponent} from "ng2-bs3-modal/components/modal";
import {RequestService} from "../../../service/request.service";
import {CustomValidators} from "ng2-validation";
import {User} from "../../../model/user.model";
import {AuthService} from "../../../service/auth.service";

@Component({
  selector: 'request-form',
  templateUrl: 'request-form.component.html',
  styleUrls: ['request-form.component.css']
})
export class RequestFormComponent implements OnInit {
  requestForm: FormGroup;
  user: User;

  @ViewChild('requestFormModal')
  modal: ModalComponent;

  constructor(private formBuilder: FormBuilder,
              private requestService: RequestService,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    this.authService.currentUser.subscribe((user: User) => {
      this.user = user;
    });
    this.requestForm = this.formBuilder.group({
      title: ['', [Validators.required, Validators.maxLength(100)]],
      priorityStatus: [''],
      description: ['', [Validators.required, Validators.maxLength(255)]],
      estimateTimeInDays: ['', [CustomValidators.min(0), CustomValidators.max(30)]]
    });
  }

  createNewRequest(params): void {
    console.log(params);
  }

  validate(field: string): boolean {
    return this.requestForm.get(field).valid || !this.requestForm.get(field).dirty;
  }
}
