import {Component, OnInit} from "@angular/core";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  register: boolean = false;
  recover: boolean = false;

  constructor() {
  }

  ngOnInit() {
  }

  toggleRegister(registerMode: boolean): void {
    this.register = registerMode;
  }

  toggleRecover(recoverMode: boolean): void {
    this.recover = recoverMode;
  }
}
