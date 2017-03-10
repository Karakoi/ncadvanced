import {Component, OnInit, ViewChild} from "@angular/core";
import {UserService} from "../../service/user.service";
import {AddUserComponent} from "./user-add/add-user.component";
import {User} from "../../model/user.model";
import {DeleteUserComponent} from "./user-delete/delete-user.component";
declare var $: any;


@Component({
  selector: 'user-table',
  templateUrl: 'user-table.component.html',
  styleUrls: ['user-table.component.css']
})
export class UserTableComponent implements OnInit {
  users: User[];
  pageNumber: number;

  @ViewChild(AddUserComponent)
  addUserComponent: AddUserComponent;

  @ViewChild(DeleteUserComponent)
  deleteUserComponent: DeleteUserComponent;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.userService.getAll(1).subscribe((users: User[]) => {
      this.users = users;
    });
    this.userService.getPageCount().subscribe((count) => this.pageNumber = count);
  }

  openAddUserModal(): void {
    this.addUserComponent.modal.open();
  }

  openDeleteUserModal(user: User): void {
    this.deleteUserComponent.user = user;
    this.deleteUserComponent.modal.open();
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
    this.userService.getAll(page).subscribe((users: User[]) => {
      this.users = users;
    });
  }

}
