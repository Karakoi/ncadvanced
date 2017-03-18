import {Component, ViewChild, Input, Output, EventEmitter} from "@angular/core";
import {ToastsManager} from "ng2-toastr";
import {ModalComponent} from "ng2-bs3-modal/components/modal";
import {User} from "../../../../model/user.model";
import {UserService} from "../../../../service/user.service";

@Component({
  selector: 'activate-user',
  templateUrl: 'activate-user.component.html'
})
export class ActivateUserComponent {
  @Input()
  users: User[];
  @Output()
  updated: EventEmitter<any> = new EventEmitter();

  public user: User;

  @ViewChild('activateUserFormModal')
  modal: ModalComponent;

  constructor(private userService: UserService,
              private toastr: ToastsManager) {
  }

  activateUser() {
    this.user
    this.userService.activate(this.user.id).subscribe(() => {
      this.updateArray();
      this.modal.close();
      this.toastr.success("User was activated successfully", "Success!");
    }, e => this.handleErrorActivateUser(e));
  }

  private updateArray(): void {
    this.updated.emit(this.users.filter(u => u !== this.user));
  }

  private handleErrorActivateUser(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't activate user", 'Error');
    }
  }
}
