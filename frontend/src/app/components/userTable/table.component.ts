import {Component, OnInit} from '@angular/core';
import {UserService} from "../../service/user.service";

@Component({
  selector: 'user-table',
  templateUrl: 'table.component.html',
  styleUrls: ['table.component.css']
})
export class UserTable implements OnInit {

  constructor(private userService: UserService) {
  }

  users = [
    {
      firstName: "Wayne",
      lastName: "Collins",
      secondName: "Wayneovich",
      email: "wcollins0@aboutads.info",
      birthDate: new Date("1985-12-12"),
      phoneNumber: "374-(251)803-9567",
      role: "Admin",
      password: null
    },
    {
      firstName: "Denise",
      lastName: "Day",
      secondName: "Deniseovich",
      email: "dday1@blogtalkradio.com",
      birthDate: new Date("1995-11-27"),
      phoneNumber: "241-(539)668-2990",
      role: "Admin",
      password: null
    },
    {
      firstName: "Dorothy",
      lastName: "Collins",
      secondName: "Dorothyovich",
      email: "dcollins7@ed.gov",
      birthDate: new Date("1980-10-12"),
      phoneNumber: "380-(874)642-9009",
      role: "Office Manager",
      password: null
    },
    {
      firstName: "Wayne",
      lastName: "Collins",
      secondName: "Wayneovich",
      email: "wcollins0@aboutads.info",
      birthDate: new Date("1985-12-12"),
      phoneNumber: "374-(251)803-9567",
      role: "Admin",
      password: null
    },
    {
      firstName: "Denise",
      lastName: "Day",
      secondName: "Deniseovich",
      email: "dday1@blogtalkradio.com",
      birthDate: new Date("1995-11-27"),
      phoneNumber: "241-(539)668-2990",
      role: "Admin",
      password: null
    },
    {
      firstName: "Wayne",
      lastName: "Collins",
      secondName: "Wayneovich",
      email: "wcollins0@aboutads.info",
      birthDate: new Date("1985-12-12"),
      phoneNumber: "374-(251)803-9567",
      role: "Admin",
      password: null
    },
    {
      firstName: "Denise",
      lastName: "Day",
      secondName: "Deniseovich",
      email: "dday1@blogtalkradio.com",
      birthDate: new Date("1995-11-27"),
      phoneNumber: "241-(539)668-2990",
      role: "Admin",
      password: null
    }];

    ngOnInit(){
      //this.users = this.userService.getAll();
    }
}
