import {Component, OnInit} from "@angular/core";
import {ToastsManager} from "ng2-toastr";
import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CustomValidators} from "ng2-validation";
import {UserService} from "../../service/user.service";
import {AuthService} from "../../service/auth.service";
import {User} from "../../model/user.model";
import {CacheService} from "ionic-cache";

@Component({
  selector: 'overseer-profile',
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.css']
})
export class ProfileComponent implements OnInit {
  profileForm: FormGroup;
  user: User;
  showPass: boolean = false;

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private authService: AuthService,
              private router: Router,
              private cach: CacheService,
              private toastr: ToastsManager) {
  }

  ngOnInit(): void {
    this.authService.currentUser.subscribe((user: User) => {
      this.user = user;
    });
    this.initForm();
  }

  update(): void {
    this.userService.update(this.user).subscribe( () => {
        this.toastr.success('Your profile has been updated');
        this.cach.clearAll();
      }, e => this.toastr.error('Enter ur password','Wrong')
    );
  }
  updatePass(newPass, confirmPass): void {

    if (newPass!=confirmPass) {
      this.toastr.error('Passwords do not match');
      return;
    }

    this.user.password = newPass;
    this.userService.update(this.user).subscribe(() => {
        this.toastr.success("Your password has been changed");
      }, e => this.toastr.error("Wrong")
    );
  }

  cancel(): void {
    this.router.navigate(['/home']);
  }

  showPassword(){
    this.showPass = !this.showPass;
  }

  validateField(field: string): boolean {
    return this.profileForm.get(field).valid || !this.profileForm.get(field).dirty;
  }

  private initForm(): void {
    this.profileForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      secondName: '',
      email: ['', [Validators.required, CustomValidators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      dateOfBirth: ['', CustomValidators.dateISO],
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
