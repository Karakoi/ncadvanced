import {Component, OnInit, ViewChild, Input, Output, EventEmitter} from "@angular/core";
import {Request} from "../../model/request.model";
import {RequestService} from "../../service/request.service";
import {RequestFormComponent} from "../../shared/request/request-form/request-form.component";
import {DeleteRequestComponent} from "./request-delete/delete-request.component";
import {AssignRequestComponent} from "./request-assign/assign-request.component";
import {JoinRequestComponent} from "./request-join/join-request.component";



declare let $: any;

@Component({
  selector: 'r-table',
  templateUrl: 'request-table.component.html',
  styleUrls: ['request-table.component.css']
})
export class RequestTable {
  selected: Set<number>;

  @Input() private requests: Request[];
  @Input() private requestsCount: number;
  @Output() paginationChange = new EventEmitter();
  @Output() selectedEvent: EventEmitter<any> = new EventEmitter();
  private perPage: number = 20;
  term: any;
  orderType: boolean;
  orderField: string;
  searchTypes: any;

  @Input() settings = {
    delete: true,
    add: true,
    info: true,
    multiSelect: false,
    filterRow: true,
    assign: false,
    join: false,
    columns: {
      title: true,
      estimate: true,
      dateOfCreation: true,
      priorityStatus: true,
      progressStatus: true,
      reporter: true,
      assignee: true,
    }
  }


  @ViewChild(RequestFormComponent)
  requestForm: RequestFormComponent;

  @ViewChild(AssignRequestComponent)
  assignForm: AssignRequestComponent;

  @ViewChild(JoinRequestComponent)
  joinRequestComponent: JoinRequestComponent;

  @ViewChild(DeleteRequestComponent)
  deleteRequestComponent: DeleteRequestComponent;

  constructor(private requestService: RequestService) {
    this.orderType = true;
    this.orderField = 'title';
    this.searchTypes = {
      title: "",
      priorityStatus: "",
      progressStatus: "",
      reporterName: "",
      assigneeName: "",
      estimateTime: "",
      date: ""
    };
    this.selected = new Set();
  }


  changed(data){
    this.paginationChange.emit(data);
  }

  changeOrderParams(type, field) {
    this.orderType = type;
    this.orderField = field;
  }

  perPageChange(data){
    this.perPage = data;
  }

  check(data){
    data = +data;
    if(!this.selected.has(data))
      this.selected.add(data);
    else {
      this.selected.delete(data);
    }
    this.selectedEvent.emit(this.selected);
  }

  openDeleteRequestModal(request: Request,event): void {
    this.deleteRequestComponent.request = request;
    this.deleteRequestComponent.modal.open();
  }

  openAssignRequestModal(request: Request) {
    request.estimateTimeInDays = 3;
    this.assignForm.request = request;
    this.assignForm.modal.open();
  }

  get sorted(): Request[] {
    return this.requests
      .map(request => request)
      .sort((a, b) => {
        if (a.dateOfCreation > b.dateOfCreation) return 1;
        else if (a.dateOfCreation < b.dateOfCreation) return -1;
        else return 0;
      });
  }

  updateRequests(request: Request[]) {
    this.requests = request;
  }

  openFormModal(): void {
    this.requestForm.modal.open();
  }

}
