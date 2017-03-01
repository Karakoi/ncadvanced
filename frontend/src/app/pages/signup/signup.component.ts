import {Component, OnInit} from "@angular/core";
import {ToastsManager} from "ng2-toastr";
import {Router} from "@angular/router";
import {AuthService} from "../../service/auth.service";
import {UserService} from "../../service/user.service";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";

@Component({
  selector: 'overseer-signup',
  templateUrl: './signup.component.html'
})
export class SignupComponent implements OnInit {
  registerForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private authService: AuthService,
              private router: Router,
              private toastr: ToastsManager) {
  }

  ngOnInit(): void {
    this.initForm();
  }

  register(params): void {
    this.userService.create(params)
      .mergeMap(() => {
        return this.authService.login(params.email, params.password);
      }).subscribe(() => {
      this.router.navigate(['/home']);
    }, e => this.handleError(e));
  }

  validateField(field: string): boolean {
    return this.registerForm.get(field).valid || !this.registerForm.get(field).dirty;
  }

  private initForm(): void {
    this.registerForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      secondName: '',
      email: ['', [Validators.required, CustomValidators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      birthDate: ['', CustomValidators.dateISO],
      phoneNumber: ['', CustomValidators.phone()]
    });
  }

  private handleError(error) {
    switch (error.status) {
      case 400:
        this.toastr.error('This email is already taken.', 'Error.');
    }
  }
}
