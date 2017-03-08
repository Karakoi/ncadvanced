import {Component} from "@angular/core";

@Component({
  selector: 'delete-user',
  templateUrl: 'delete-user.component.html'
})
export class DeleteUserComponent {
  public visible = false;
  private visibleAnimate = false;

  public showDialog(): void {
    this.visible = true;
    setTimeout(() => this.visibleAnimate = true);
  }

  public hideDialog(): void {
    this.visibleAnimate = false;
    setTimeout(() => this.visible = false, 300);
  }
}
