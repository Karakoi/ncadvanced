import {Component,ViewChild} from "@angular/core";
import {Request} from "../../../model/request.model";
import {RequestService} from "../../../service/request.service";
import {AssignRequestComponent} from "../../../pages/home/manager/request-assign/assign-request.component"

declare let $: any;

@Component({
  selector: 'manager-home',
  templateUrl: 'manager.component.html',
  styleUrls: ['manager.component.css']
})
export class ManagerComponent {
  requests: Request[] = [];
  checked: number[] = [];
  pageCount: number;

  @ViewChild(AssignRequestComponent)
  assignRequestComponent: AssignRequestComponent;

  constructor(private requestService: RequestService) {
  }

  ngOnInit() {
    this.requestService.getFree(1).subscribe((requests: Request[]) => {
      this.requests = requests;
    });
    this.requestService.getPageCountFree().subscribe((count) => this.pageCount = count);

  }

  assign(request:Request) {
    request.estimateTimeInDays = 3;
    this.assignRequestComponent.request = request;
    this.assignRequestComponent.modal.open();
  }

  toggle(id) {
    if (this.checked.indexOf(id) >= 0) {
      this.checked.splice(this.checked.indexOf(id), 1);
    } else {
      this.checked.push(id);
    }
  }

  createRange(number) {
    let items: number[] = [];
    for (let i = 2; i <= number; i++) {
      items.push(i);
    }
    return items;
  }

  info() {
    console.log(this.checked);
  }

  isChecked(id) {
    return this.checked.indexOf(id) > -1;
  }

  uncheckAll() {
    this.checked = [];
  }

  canUnite() {
    return this.checked.length > 1;
  }

  load(data) {
    $('.paginate_button').removeClass('active');
    let page = data.target.text;
    $(data.target.parentElement).addClass('active');
    this.requestService.getFree(page).subscribe((requests: Request[]) => {
      this.requests = requests;
    });
  }
}
