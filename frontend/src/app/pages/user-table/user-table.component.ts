import {Component, OnInit, ViewChild} from "@angular/core";
import {FormGroup, Validators, FormBuilder} from "@angular/forms";
import {UserService} from "../../service/user.service";
import {AddUserComponent} from "./user-add/user.component";
import {User} from "../../model/user.model";
declare var $:any;



@Component({
  selector: 'user-table',
  templateUrl: 'user-table.component.html',
  styleUrls: ['user-table.component.css']
})
export class UserTableComponent implements OnInit {
  @ViewChild(AddUserComponent)
  public readonly modal: AddUserComponent;

  userForm: FormGroup;
  users: User[];
  pageNumber: number;

  constructor(private userService: UserService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.userService.getAll(1).subscribe((users: User[]) => {
      this.users = users;
    });
    this.initForm();
    this.userService.getPageCount().subscribe((count) => this.pageNumber = count);
    console.log($('.paginate_button'));
  }

  showModal() {
    this.modal.show();
  }

  createRange(number){
    var items: number[] = [];
    for(var i = 2; i <= number; i++){
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

  deleteRow(event) {
    event.toElement.parentElement.parentElement.remove()
  }

  validateField(field: string): boolean {
    return this.userForm.get(field).valid || !this.userForm.get(field).dirty;
  }

  private initForm(): void {
    this.userForm = this.formBuilder.group({
      firstName: ['', Validators.required]
    });
  }

  load(data){
    console.log(this.pageNumber);
    $('.paginate_button').removeClass('active')
    let page = data.target.text;
    console.log($(data.target.parentElement).addClass('active'))
    this.userService.getAll(page).subscribe((users: User[]) => {
      this.users = users;
    });
  }
}
