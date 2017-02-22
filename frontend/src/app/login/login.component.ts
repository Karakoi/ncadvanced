import {Component, OnInit, EventEmitter, Output} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  @Output() registerMode = new EventEmitter();
  loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, CustomValidators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  login(formValues): void {
    console.log(formValues);
  }

  toggleRegister(): void {
    this.registerMode.emit(true);
  }

  validateField(field: string): boolean {
    return this.loginForm.get(field).valid || !this.loginForm.get(field).dirty;
  }

}
