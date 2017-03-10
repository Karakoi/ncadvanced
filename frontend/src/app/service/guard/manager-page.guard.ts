import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router";
import {AuthService} from "../auth.service";

@Injectable()
export class ManagerPageGuard implements CanActivate {

  constructor(private router: Router,
              private authService: AuthService) {
  }

  canActivate(): boolean {
    if (this.authService.role !== 'office manager') {
      this.router.navigate(['/home']);
    }
    console.log("Activating manager", this.authService.role === 'office manager');
    return this.authService.role === 'office manager';
  }

}