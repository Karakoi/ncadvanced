import {Component, OnInit} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {RecoverService} from "../../service/recover.service";
import {ToastsManager} from "ng2-toastr";
import {CustomValidators} from "ng2-validation";

@Component({
  selector: 'overseer-recover',
  templateUrl: 'recover.component.html'
})
export class RecoverComponent implements OnInit {
  recoverForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private recoverService: RecoverService,
              private toastr: ToastsManager) {
  }

  ngOnInit() {
    this.recoverForm = this.formBuilder.group({
      email: ['', [Validators.required, CustomValidators.email]]
    })
  }

  recover(email) {
    this.recoverService.sendRecoverInfo(email)
      .subscribe(() => {
        this.toastr.success("Please check your email inbox", "Success!");
      }, e => {
        this.toastr.error("Email address is not found", "Error!");
      });
  }

  validateField(field: string): boolean {
    return this.recoverForm.get(field).valid || !this.recoverForm.get(field).dirty;
  }
}
