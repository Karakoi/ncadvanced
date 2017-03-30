import {Component, OnInit, ViewChild, Input, Output, EventEmitter} from "@angular/core";
import {ToastsManager} from "ng2-toastr";
import {Request} from "../../model/request.model";
import {RequestService} from "../../service/request.service";
import {RequestFormComponent} from "../../shared/request/request-form/request-form.component";
import {DeleteRequestComponent} from "./request-delete/delete-request.component";
import {AssignRequestComponent} from "./request-assign/assign-request.component";
import {JoinRequestComponent} from "./request-join/join-request.component";
import {CloseRequestComponent} from "./request-close/close-request.component";
import {ReopenRequestComponent} from "./request-reopen/reopen-request.component";
import {RequestSearchDTO} from "../../model/dto/request-seaarch-dto.model";

declare let $: any;

@Component({
  selector: 'r-table',
  templateUrl: 'request-table.component.html',
  styleUrls: ['request-table.component.css']
})
export class RequestTable {
  selected: Set<number>;
  checked: number[] = [];

  @Input() private requests: Request[];
  @Input() private requestsCount: number;
  @Output() paginationChange = new EventEmitter();
  @Output() selectedEvent: EventEmitter<any> = new EventEmitter();
  @Output() reopenEvent = new EventEmitter();
  @Output() perChangeLoad = new EventEmitter();
  private perPage: number = 20;
  term: any;
  orderType: boolean;
  orderField: string;
  searchTypes: any;

  reopen(){
    this.reopenEvent.emit();
  }

  @Input() settings = {
    delete: true,
    add: true,
    info: true,
    multiSelect: false,
    filterRow: true,
    assign: false,
    join: false,
    reopen: false,
    close: false,
    ajax: true,
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

  searchDTO : RequestSearchDTO;

  @ViewChild(RequestFormComponent)
  requestForm: RequestFormComponent;

  @ViewChild(AssignRequestComponent)
  assignForm: AssignRequestComponent;

  @ViewChild(JoinRequestComponent)
  joinRequestComponent: JoinRequestComponent;

  @ViewChild(CloseRequestComponent)
  closeRequestComponent:CloseRequestComponent;

  @ViewChild(DeleteRequestComponent)
  deleteRequestComponent: DeleteRequestComponent;

  @ViewChild(ReopenRequestComponent)
  reopenRequestComponent: ReopenRequestComponent;

  constructor(private requestService: RequestService,
              private toastr: ToastsManager) {
    this.searchDTO = {
      title: "",
      dateOfCreation: "",
      estimate: "",
      priority: "",
      progress: "",
      reporterName: "",
      assigneeName: "",
      limit: 20
    };

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

  currentPage : number = 1;

  changed(data) {
    this.currentPage = data.page;
    this.paginationChange.emit(data);
  }

  changeOrderParams(type, field) {
    this.orderType = type;
    this.orderField = field;
  }

  perPageChange(data) {
    this.perPage = data;
    let pageData = {"page" : this.currentPage, "size": data};
    this.perChangeLoad.emit(pageData);
  }

  check(data) {
    data = +data;
    if (!this.selected.has(data))
      this.selected.add(data);
    else {
      this.selected.delete(data);
    }
    this.selectedEvent.emit(this.selected);
    this.checked = Array.from(this.selected);
  }

  join() {
    this.joinRequestComponent.modal.open();
  }

  close(request:Request) {
    this.closeRequestComponent.request = request;
    this.closeRequestComponent.modal.open();
  }

  reOpen(request:Request) {
    this.reopenRequestComponent.request = request;
    this.reopenRequestComponent.modal.open();
  }

  isChecked(id) {
    return this.selected.has(id);
  }

  uncheck() {
    this.selected.clear();
    this.checked = [];
  }

  openDeleteRequestModal(request: Request, event): void {
    if(request.progressStatus.name === 'Free'){
      this.deleteRequestComponent.request = request;
      this.deleteRequestComponent.modal.open();
    } else {
      this.toastr.error('Can not delete not [Free] request', "Error!");
    }
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

  setTitleSearch(field, value) {
    switch (field) {
      case 'title':
        this.searchDTO.title = value;
        break;
      case 'dateOfCreation':
        this.searchDTO.dateOfCreation = value;
        break;
      case 'estimate':
        this.searchDTO.estimate = value;
        break;
      case 'priority':
        this.searchDTO.priority = value;
        break;
      case 'progress':
        this.searchDTO.progress = value;
        break;
      case 'reporterName':
        this.searchDTO.reporterName = value;
        break;
      case 'assigneeName':
        this.searchDTO.assigneeName = value;
        break;
      case 'limit':
        this.searchDTO.limit = value;
        break;
    }
    this.getSearchData(this.searchDTO);
    console.log(this.searchDTO)
  }

  getSearchData(searchDTO){
    this.requestService.searchAll(searchDTO).subscribe(requests => {
      console.log(requests);
      this.requests = requests;
    })
  }
}
