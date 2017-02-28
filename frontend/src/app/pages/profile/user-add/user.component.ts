import {Component} from "@angular/core";

@Component({
  selector: 'add-user',
  templateUrl: './user.component.html'
})
export class AddUserComponent {
  public visible = false;
  private visibleAnimate = false;

  public show(): void {
    this.visible = true;
    setTimeout(() => this.visibleAnimate = true);
  }

  public hide(): void {
    this.visibleAnimate = false;
    setTimeout(() => this.visible = false, 300);
  }
}
