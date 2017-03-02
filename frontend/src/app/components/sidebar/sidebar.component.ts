import {Component, OnInit} from "@angular/core";
import {AuthService, AuthEvent, DidLogin} from "../../service/auth.service";
import {Subject} from "rxjs";

@Component({
  selector: 'overseer-sidebar',
  templateUrl: 'sidebar.component.html',
  styleUrls: ['sidebar.component.css']
})
export class SideBarComponent implements OnInit {
  role: string;

  constructor(private authService: AuthService) {
  }

  ngOnInit() {
    this.role = this.authService.role;
    this.authService.events.subscribe((event: Subject<AuthEvent>) => {
      if (event.constructor.name === 'DidLogin') {
        this.role = this.authService.role;
      }
    });
  }

  isAdmin(): boolean {
    return this.role === 'admin';
  }

  isManager(): boolean {
    return this.role === 'office manager'
  }
}
