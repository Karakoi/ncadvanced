import {Component, OnInit, EventEmitter, Output} from "@angular/core";
import {Validators, FormBuilder, FormGroup} from "@angular/forms";
import {User} from "../model/user.model";
import {CustomValidators} from "ng2-validation";
import {UserService} from "../service/user.service";
import {AuthService} from "../service/auth.service";
import {ToastsManager} from "ng2-toastr";
import {Router} from "@angular/router";
import {Response} from "@angular/http";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit {
  @Output() registerMode = new EventEmitter();
  registerForm: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private router: Router,
              private toastr: ToastsManager) {
  }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      secondName: '',
      email: ['', [Validators.required, CustomValidators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      birthDate: ['', CustomValidators.dateISO],
      phoneNumber: ['', CustomValidators.phone()]
    })
  }

  register(user: User): void {
    user.role = 'employee';
    this.userService.create(user)
      .subscribe(() => {
        this.router.navigate(['/profile']);
      }, e => this.handleError(e));
  }

  toggleLogin(): void {
    this.registerMode.emit(false);
  }

  validateField(field: string): boolean {
    return this.registerForm.get(field).valid || !this.registerForm.get(field).dirty;
  }

  private handleError(error) {
    switch (error.status) {
      case 400:
        this.toastr.error('This email is already taken.', 'Error.');
    }
  }
}
