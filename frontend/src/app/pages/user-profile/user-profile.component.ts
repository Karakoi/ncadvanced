import {Component, OnInit} from '@angular/core';
import {Response} from '@angular/http';
import {Router} from "@angular/router";
import {UserService} from "../../service/user.service";
import {User} from "../../model/user.model";
import {ErrorService} from "../../service/error.service";

@Component({
  selector: 'user',
  templateUrl: 'user-profile.component.html',
  providers: [UserService]
})
export class UserProfileComponent {

  user: User;

  constructor(private userService: UserService,
              private router: Router,
              private errorService: ErrorService) {
  }

  getFirstUser() {
    this.userService.get(1)
      .subscribe(
        data => this.user = data,
        error => {
          this.processError(error)
        }
      );
  }

  processError(error: any) {
    if (error instanceof Response) {
      let errorObject = error.json() || '';
      this.errorService.updateError(errorObject);
      this.router.navigate(['error']);
    }
  }
}
