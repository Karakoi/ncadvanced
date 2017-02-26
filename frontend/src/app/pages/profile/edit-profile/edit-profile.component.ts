import {Component, OnInit} from "@angular/core";
import {ToastsManager} from "ng2-toastr";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";
import {UserService} from "../../../service/user.service";
import {AuthService} from "../../../service/auth.service";

@Component({
  selector: 'overseer-edit-profile',
  templateUrl: 'edit-profile.html'
})
export class EditProfileComponent implements OnInit {
  editProfileForm: FormGroup;

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
    this.userService.create(params)
      .mergeMap(() => {
        return this.authService.login(params.email, params.password);
      }).subscribe(() => {
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
      firstName: ['Old First Name', Validators.required],
      lastName: ['Old Last Name', Validators.required],
      secondName: 'Old Second Name',
      email: ['Old Email', [Validators.required, CustomValidators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      birthDate: ['Old Date of Birth', CustomValidators.dateISO],
      phoneNumber: ['Old Phone', CustomValidators.phone()]
    });
  }

  private handleError(error) {
    switch (error.status) {
      case 400:
        this.toastr.error('This email is already taken.', 'Error.');
    }
  }
}
