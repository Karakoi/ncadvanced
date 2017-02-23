import {Component, OnInit, EventEmitter, Output} from "@angular/core";
import {FormGroup, FormBuilder, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";
import {AuthService} from "../service/auth.service";
import {ToastsManager} from "ng2-toastr";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html'
})
export class LoginComponent implements OnInit {
  @Output() registerMode = new EventEmitter();
  @Output() recoverMode = new EventEmitter();
  loginForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private authService: AuthService,
              private toastr: ToastsManager) {
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email: ['', [Validators.required, CustomValidators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  login(formValues) {
    this.authService.login(formValues.email, formValues.password)
      .subscribe(() => {
        this.router.navigate(['/profile']);
      }, e => this.handleError(e));
  }

  toggleRegister(): void {
    this.registerMode.emit(true);
  }

  toggleRecover(): void {
    this.recoverMode.emit(true);
  }

  validateField(field: string): boolean {
    return this.loginForm.get(field).valid || !this.loginForm.get(field).dirty;
  }

  handleError(error) {
    switch (error.status) {
      case 401:
        this.toastr.error('Email or password is wrong.');
    }
  }
}
