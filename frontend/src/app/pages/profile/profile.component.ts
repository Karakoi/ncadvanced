import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";
import {User} from "../../model/user.model";

@Component({
  selector: 'overseer-profile',
  templateUrl: 'profile.component.html',
  styleUrls: ['profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: User;

  constructor(private authService: AuthService,
              private router: Router) {
  }

  ngOnInit() {
    this.authService.currentUser.subscribe((user: User) => {
      this.user = user;
    });
  }

  edit(): void {
    this.router.navigate(['/profile/edit']);
  }
}
