import {Component, OnInit, ViewChild} from "@angular/core";
import {FormGroup, Validators, FormBuilder} from "@angular/forms";
import {UserService} from "../../service/user.service";
import {AddUserComponent} from "./user-add/user.component";
import {User} from "../../model/user.model";


@Component({
  selector: 'user-table',
  templateUrl: 'user-table.component.html',
  styleUrls: ['user-table.component.css']
})
export class UserTable implements OnInit {
  constructor(private userService: UserService, private formBuilder: FormBuilder) {
  }

  @ViewChild(AddUserComponent)
  public readonly modal: AddUserComponent;

  userForm: FormGroup;


  ngOnInit() {
    //this.users = this.userService.getAll();
    this.userForm = this.formBuilder.group({
      firstName: ['', Validators.required]
    })
    this.modal.show();
  }

  showModal() {
    this.modal.show();
  }

  get sorted(): User[] {
    return this.users
      .map(user => user)
      .sort((a, b) => {
        if (a.lastName > b.lastName) return 1;
        else if (a.lastName < b.lastName) return -1;
        else return 0;
      })
      .sort((a, b) => {
        if (a.firstName > b.firstName) return 1;
        else if (a.firstName < b.firstName) return -1;
        else return 0;
      })
  }

  sortByRole() {
    this.users.map(user => user)
      .sort((a, b) => {
        if (a.role > b.role) return 1;
        else if (a.role < b.role) return -1;
        else return 0;
      })
  }

  deleteRow(event) {
    event.toElement.parentElement.parentElement.remove()
  }


  validateField(field: string): boolean {
    return this.userForm.get(field).valid || !this.userForm.get(field).dirty;
  }

  users: User[] = [
    {
      id: 1,
      firstName: "Ayne",
      lastName: "Bollins",
      secondName: "Wayneovich",
      email: "wcollins0@aboutads.info",
      birthDate: new Date("1985-12-12"),
      phoneNumber: "374-(251)803-9567",
      role: "Admin",
      password: null
    },
    {
      id: 2,
      firstName: "Benise",
      lastName: "Day",
      secondName: "Deniseovich",
      email: "dday1@blogtalkradio.com",
      birthDate: new Date("1995-11-27"),
      phoneNumber: "241-(539)668-2990",
      role: "Admin",
      password: null
    },
    {
      id: 7,
      firstName: "Corothy",
      lastName: "Collins",
      secondName: "Dorothyovich",
      email: "dcollins7@ed.gov",
      birthDate: new Date("1980-10-12"),
      phoneNumber: "380-(874)642-9009",
      role: "Office Manager",
      password: null
    },
    {
      id: 4,
      firstName: "Wayne",
      lastName: "Vollins",
      secondName: "Wayneovich",
      email: "wcollins0@aboutads.info",
      birthDate: new Date("1985-12-12"),
      phoneNumber: "374-(251)803-9567",
      role: "Admin",
      password: null
    },
    {
      id: 3,
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
      id: 6,
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
      id: 5,
      firstName: "Denise",
      lastName: "Way",
      secondName: "Deniseovich",
      email: "dday1@blogtalkradio.com",
      birthDate: new Date("1995-11-27"),
      phoneNumber: "241-(539)668-2990",
      role: "Admin",
      password: null
    }];
}
