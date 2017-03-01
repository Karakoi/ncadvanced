import {Component, OnInit} from "@angular/core";
import {ToastsManager} from "ng2-toastr";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";
import {UserService} from "../../../service/user.service";
import {AuthService} from "../../../service/auth.service";
import {User} from "../../../model/user.model";

@Component({
  selector: 'overseer-edit-profile',
  templateUrl: 'edit-profile.html'
})
export class EditProfileComponent implements OnInit {
  editProfileForm: FormGroup;
  user: User;

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private authService: AuthService,
              private router: Router,
              private toastr: ToastsManager) {
  }

  ngOnInit(): void {
    this.initForm();
  }

  update(params): void {
    params.role = {
      id: "12",
      name: "employee"
    };

    this.userService.update(params).subscribe(() => {
      this.router.navigate(['/profile']);
    }, e => this.handleError(e));
  }

  cancel(): void {
    this.router.navigate(['/profile']);
  }

  validateField(field: string): boolean {
    return this.editProfileForm.get(field).valid || !this.editProfileForm.get(field).dirty;
  }

  private initForm(): void {
    this.editProfileForm = this.formBuilder.group({
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
