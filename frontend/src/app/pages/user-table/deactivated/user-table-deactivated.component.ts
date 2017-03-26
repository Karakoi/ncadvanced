import {Component, OnInit, ViewChild} from "@angular/core";
import {UserService} from "../../../service/user.service";
import {User} from "../../../model/user.model";
import {ActivateUserComponent} from "./user-activate/activate-user.component";
import {UserSearchDTO} from "../../../model/dto/user-search-dto.model";
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
  searchDTO : UserSearchDTO;
  settings = {
    ajax: false
  };

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
    this.searchDTO = {
      firstName: "",
      lastName: "",
      email: "",
      role: "",
      dateOfDeactivation: "",
      limit: 20
    };
  }

  ngOnInit() {
    this.userService.getAllDeactivated(1).subscribe((users: User[]) => {
      this.users = users;
      console.log(users)
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

  setTitleSearch(field, value) {
    switch (field) {
      case 'firstName':
        this.searchDTO.firstName = value;
        break;
      case 'lastName':
        this.searchDTO.lastName = value;
        break;
      case 'email':
        this.searchDTO.email = value;
        break;
      case 'role':
        this.searchDTO.role = value;
        break;
      case 'date':
        this.searchDTO.dateOfDeactivation = value;
        break;
      case 'limit':
        this.searchDTO.limit = value;
        break;
    }
    this.getSearchData(this.searchDTO);
    console.log(this.searchDTO)
  }

  getSearchData(searchDTO){
    this.userService.searchAll(searchDTO).subscribe(users => {
      console.log(users);
      this.users = users;
    })
  }
}
