import {Component, OnInit, ViewChild} from "@angular/core";
import {UserService} from "../../../service/user.service";
import {User} from "../../../model/user.model";
import {ActivateUserComponent} from "./user-activate/activate-user.component";
declare var $: any;


@Component({
  selector: 'user-table-deactivated',
  templateUrl: 'user-table-deactivated.component.html',
  styleUrls: ['user-table-deactivated.component.css']
})
export class UserTableDeactivatedComponent implements OnInit {
  users: User[];
  pageNumber: number;
  orderType: boolean;
  orderField: string;
  searchTypes: any;

  @ViewChild(ActivateUserComponent)
  activateUserComponent: ActivateUserComponent;

  constructor(private userService: UserService) {
    this.orderType = true;
    this.orderField = 'firstName';
    this.searchTypes = {
      firstName: "",
      lastName: "",
      email: "",
      role: "",
      dateOfDeactivation: ""
    };
  }

  ngOnInit() {
    this.userService.getAllDeactivated(1).subscribe((users: User[]) => {
      this.users = users;
    });
    this.userService.getDeactivatedUsersPageCount().subscribe((count) => this.pageNumber = count);
  }

  changeOrderParams(type, field) {
    this.orderType = type;
    this.orderField = field;
  }

  openActivateUserModal(user: User): void {
    this.activateUserComponent.user = user;
    this.activateUserComponent.modal.open();
  }

  updateUsers(users: User[]) {
    this.users = users;
  }

  createRange(number) {
    let items: number[] = [];
    for (let i = 2; i <= number; i++) {
      items.push(i);
    }
    return items;
  }

  get sorted(): User[] {
    return this.users
      .map(user => user)
      .sort((a, b) => {
        if (a.id < b.id) return 1;
        else if (a.id > b.id) return -1;
        else return 0;
      });
  }

  load(data) {
    let page = data.target.text;
    $('.paginate_button').removeClass('active');
    $(data.target.parentElement).addClass('active');
    this.userService.getAllDeactivated(page).subscribe((users: User[]) => {
      this.users = users;
    });
  }

}
