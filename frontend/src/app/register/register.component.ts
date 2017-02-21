import {Component, OnInit, EventEmitter, Output} from "@angular/core";
import {Validators, FormBuilder, FormGroup} from "@angular/forms";
import {User} from "../model/user.model";
import {CustomValidators} from "ng2-validation";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html'
})
export class RegisterComponent implements OnInit {
  @Output() registerMode = new EventEmitter();
  registerForm: FormGroup;

  constructor(private formBuilder: FormBuilder) {
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
    console.log(user);
  }

  toggleLogin(): void {
    this.registerMode.emit(false);
  }

  validateField(field: string): boolean {
    return this.registerForm.get(field).valid || !this.registerForm.get(field).dirty;
  }
}
