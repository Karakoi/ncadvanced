import {Component, OnInit, ViewChild} from "@angular/core";
import {FormGroup, Validators, FormBuilder} from "@angular/forms";
import {UserService} from "../../service/user.service";
import {AddUserComponent} from "./user-add/user.component";
import {User} from "../../model/user.model";
import {DeleteUserComponent} from "./user-delete/delete-user.component";
import {ToastsManager} from "ng2-toastr";
import {CustomValidators} from "ng2-validation";
import {Role} from "../../model/role.model";
declare var $: any;


@Component({
  selector: 'user-table',
  templateUrl: 'user-table.component.html',
  styleUrls: ['user-table.component.css']
})
export class UserTableComponent implements OnInit {
  @ViewChild(AddUserComponent)
  public readonly addUserModal: AddUserComponent;

  @ViewChild(DeleteUserComponent)
  public readonly deleteUserModal: DeleteUserComponent;

  addUserForm: FormGroup;
  users: User[];
  pageNumber: number;
  currentSelectedUser: User;
  // newUser: User;
  roles: Role[];

  constructor(private userService: UserService,
              private formBuilder: FormBuilder,
              private toastr: ToastsManager) {
  }

  private initForm(): void {
    this.addUserForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      secondName: '',
      email: ['', [Validators.required, CustomValidators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      // Add validator for confirm passwords
      confirmPassword: ['', [Validators.required, Validators.minLength(6)]],
      dateOfBirth: ['', CustomValidators.dateISO],
      phoneNumber: ['', CustomValidators.phone()],
      role: null
    });
  }

  ngOnInit() {
    this.userService.getAll(1).subscribe((users: User[]) => {
      this.users = users;
    });

    this.userService.getRoles().subscribe((roles: Role[]) => {
      this.roles = roles;
    });

    this.initForm();
    this.userService.getPageCount().subscribe((count) => this.pageNumber = count);
    console.log($('.paginate_button'));
  }

  // Methods for adding users
  showAddUserModal() {
    this.addUserModal.show();
  }

  validateField(field: string): boolean {
    return this.addUserForm.get(field).valid || !this.addUserForm.get(field).dirty;
  }

  addUser(newUser) {
    console.log(newUser);
    this.userService.create(newUser).subscribe(() => {
      this.toastr.success("User was created successfully", "Success!");
      this.addUserModal.hide();
    }, e => this.handleErrorCreateUser(e));
  }

  // Methods for deleting users
  showDeleteUserModal(user: User) {
    this.currentSelectedUser = user;
    this.deleteUserModal.showDialog();
  }

  deleteUser() {
    this.userService.delete(this.currentSelectedUser.id).subscribe(() => {
      this.toastr.success("User was deleted successfully", "Success!");
      this.deleteUserModal.hideDialog();
    }, e => this.handleErrorDeleteUser(e));

  }

  deleteRow(event) {
      event.toElement.parentElement.parentElement.remove();
  }

  // Methods for pagination
  createRange(number) {
    var items: number[] = [];
    for (var i = 2; i <= number; i++) {
      items.push(i);
    }
    return items;
  }

  get sorted(): User[] {
    return this.users
      .map(user => user)
      .sort((a, b) => {
        if (a.id > b.id) return 1;
        else if (a.id < b.id) return -1;
        else return 0;
      });
  }

  sortByRole() {
    this.users.map(user => user)
      .sort((a, b) => {
        if (a.role > b.role) return 1;
        else if (a.role < b.role) return -1;
        else return 0;
      })
  }


  load(data) {
    console.log(this.pageNumber);
    $('.paginate_button').removeClass('active')
    let page = data.target.text;
    console.log($(data.target.parentElement).addClass('active'))
    this.userService.getAll(page).subscribe((users: User[]) => {
      this.users = users;
    });
  }

  private handleErrorCreateUser(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't create user", 'Error');
    }
  }

  private handleErrorDeleteUser(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't delete user", 'Error');
    }
  }

}
