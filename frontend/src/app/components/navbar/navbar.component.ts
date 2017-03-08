import {Component, OnInit} from "@angular/core";
import {Router} from "@angular/router";
import {AuthService} from "../../service/auth.service";

@Component({
  selector: 'overseer-navbar',
  templateUrl: 'navbar.component.html',
  styleUrls: ['navbar.component.css']
})
export class NavbarComponent implements OnInit {
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

  logout() {
    this.authService.logout();
    this.router.navigate(['/authentication/login']);
  }
}
