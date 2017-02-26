import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {AuthService} from "../../service/auth.service";

@Component({
  selector: 'left-menu',
  templateUrl: 'left-menu.component.html',
  styleUrls: ['left-menu.component.css']
})
export class LeftMenuComponent implements OnInit {

  isSignedIn: boolean;

  constructor(private router: Router,
              private authService: AuthService) {
  }

  ngOnInit() {
    this.isSignedIn = this.authService.isSignedIn();
    this.authService.events.subscribe(() => {
      this.isSignedIn = this.authService.isSignedIn();
    });
  }

  // editProfile() {
  //   this.router.navigate(['/editProfile']);
  // }
}
