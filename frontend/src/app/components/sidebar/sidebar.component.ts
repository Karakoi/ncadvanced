import {Component, OnInit} from "@angular/core";
import {AuthService} from "../../service/auth.service";

@Component({
  selector: 'sidebar',
  templateUrl: 'sidebar.component.html',
  styleUrls: ['sidebar.component.css']
})
export class SideBarComponent implements OnInit {
  isSignedIn: boolean;

  constructor(private authService: AuthService) {
  }

  ngOnInit() {
    this.isSignedIn = this.authService.isSignedIn();
    this.authService.events.subscribe(() => {
      this.isSignedIn = this.authService.isSignedIn();
    });
  }
}
