import {Component} from "@angular/core";
import {Request} from "../../../model/request.model";
import {RequestService} from "../../../service/request.service";

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

  constructor(private requestService: RequestService) {
  }

  ngOnInit() {
    this.requestService.getAll(1).subscribe((requests: Request[]) => {
      this.requests = requests;
    });
    this.requestService.getPageCount().subscribe((count) => this.pageCount = count);

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

  canUnite() {
    return this.checked.length > 1;
  }

  load(data) {
    $('.paginate_button').removeClass('active');
    let page = data.target.text;
    $(data.target.parentElement).addClass('active');
    this.requestService.getAll(page).subscribe((requests: Request[]) => {
      this.requests = requests;
    });
  }
}