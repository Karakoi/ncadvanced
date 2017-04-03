import {Component, OnInit, ViewChild, Output, EventEmitter} from "@angular/core";
import {RequestService} from "../../service/request.service";
import {Request} from "../../model/request.model";
import {ActivatedRoute} from "@angular/router";
import {ToastsManager} from "ng2-toastr";
import {AuthService} from "../../service/auth.service";
import {User} from "../../model/user.model";
import {Comment} from "../../model/comment.model";
import {HistoryService} from "../../service/history.service";
import {History} from "../../model/history.model";
import {DeleteSubRequestComponent} from "./sub-request-delete/delete-sub-request.component";
import {AddSubRequestComponent} from "./sub-request-add/add-sub-request.component";
import {SuscribeService} from "../../service/subscribe.service";
import {ReportService} from "../../service/report.service";
import * as FileSaver from "file-saver";
import {CommentService} from "../../service/comment.service";
import {FormGroup, Validators, FormBuilder} from "@angular/forms";
import {Response} from "@angular/http";
import {DeleteCommentComponent} from "./comment-delete/delete-comment.component";
import {CloseComponent} from "./close/close.component";
import {HistoryMessageDTO} from "../../model/dto/historyMessageDTO.model";

@Component({
  selector: 'request-profile',
  templateUrl: 'request-profile.component.html',
  styleUrls: ['request-profile.component.css']
})
export class RequestProfileComponent implements OnInit {
  followed: boolean = false;
  currentUser: User;
  request: Request;
  type: string;
  showDescription: boolean = true;
  showHistory: boolean = true;
  showFollowers: boolean = true;
  showSubRequests: boolean = true;
  showJoinedRequests: boolean = true;
  showComments: boolean = true;
  historyRecords: History[];
  historyDTOsRecords: HistoryMessageDTO[];
  subRequests: Request[];
  joinedRequests: Request[];
  followers: User[];
  comments: Comment[];
  role: string = 'employee';
  commentForm: FormGroup;
  comment: Comment;
  @Output()
  updated: EventEmitter<any> = new EventEmitter();

  @ViewChild(DeleteSubRequestComponent)
  deleteSubRequestComponent: DeleteSubRequestComponent;

  @ViewChild(DeleteCommentComponent)
  deleteCommentComponent: DeleteCommentComponent;

  @ViewChild(AddSubRequestComponent)
  addSubRequestComponent: AddSubRequestComponent;

  @ViewChild(CloseComponent)
  closeComponent: CloseComponent;

  constructor(private requestService: RequestService,
              private route: ActivatedRoute,
              private reportService: ReportService,
              private toastr: ToastsManager,
              private authService: AuthService,
              private historyService: HistoryService,
              private subscribeService: SuscribeService,
              private commentService: CommentService,
              private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.commentForm = this.formBuilder.group({
      text: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(500)]]
    });

    this.authService.currentUser.subscribe((user: User) => {
      this.currentUser = user;
      this.role = user.role.name;

      this.route.params.subscribe(params => {
        let id = +params['id'];

        /*this.historyService.getHistory(id).subscribe((historyRecords: History[]) => {
          this.historyRecords = historyRecords;
        });*/
        this.historyService.getHistoryDTOs(id).subscribe((historyDtoRecords: HistoryMessageDTO[]) => {
          this.historyDTOsRecords = historyDtoRecords;
        });

        this.requestService.get(id).subscribe((request: Request) => {
          this.request = request;
          this.type = this.getRequestType(request);
          this.subscribeService.check(this.request.id, this.currentUser.id).subscribe(result => {
            this.followed = result;
          });
          this.commentService.getByRequest(this.request.id).subscribe(comments => {
            this.comments = comments;
          });
          this.comment = {
            text: "",
            sender: this.currentUser,
            request: this.request,
            createDateAndTime: null
          };
        });

        this.requestService.getSubRequests(id).subscribe((subRequests: Request[]) => {
          this.subRequests = subRequests;
        });

        this.requestService.getJoinedRequests(id).subscribe((joinedRequests: Request[]) => {
          this.joinedRequests = joinedRequests;
        });

        this.subscribeService.getFollowers(id).subscribe(followers => {
          this.followers = followers;
        });
      });
    });
  }

  createNewComment(params) {
    this.comment.text = params.text;
    this.comment.createDateAndTime = new Date();
    this.commentService.create(this.comment).subscribe((resp: Response) => {
      this.updateArray(<Comment> resp.json());
      this.commentForm.reset();
      this.toastr.success("Comment sended", "Success")
    }, e => this.handleErrorCreateMessage(e));
  }

  private updateArray(comment: Comment): void {
    this.comments.unshift(comment);
    this.updated.emit(this.comment);
  }

  private handleErrorCreateMessage(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't create message", 'Error');
    }
  }

  validate(field: string): boolean {
    return this.commentForm.get(field).valid || !this.commentForm.get(field).dirty;
  }

  /*showHistoryMessage(history: History): string {
    let text: string;
    switch (history.columnName) {

      case "title":
        text = "Title was changed from \"" + history.oldValue + "\" to \"" + history.newValue + "\"";
        break;
      case "estimate_time_in_days":
        text = "Estimate time (in day) was changed from \"" + history.oldValue + "\" to \"" + history.newValue + "\"";
        break;

      case "description":
        text = "Description was changed from \"" +
          history.demonstrationOfOldValue + "\" to \"" + history.demonstrationOfNewValue + "\"";
        break;
      case "priority_status_id":
        text = "Priority was changed from \"" +
          history.demonstrationOfOldValue + "\" to \"" + history.demonstrationOfNewValue + "\"";
        break;
      case "progress_status_id":
        text = "Progress status was changed from \"" +
          history.demonstrationOfOldValue + "\" to \"" + history.demonstrationOfNewValue + "\"";
        break;

      case "assignee_id":
        text = "This request was assigned";
        break;

      case "parent_id":
        if (history.newValue == null) {
          text = "This request was unjoined from \"" + history.demonstrationOfOldValue + "\" request";
        } else {
          text = "This request was joined in \"" + history.demonstrationOfNewValue + "\" request";
        }
        break;

      default:
        text = "Some changes";
    }

    return text;
  }*/


  openAddSubRequestModal(): void {
    this.addSubRequestComponent.modal.open();
  }

  openDeleteSubRequestModal(subRequest: Request): void {
    this.deleteSubRequestComponent.subRequest = subRequest;
    this.deleteSubRequestComponent.modal.open();
  }

  openDeleteCommentModal(comment: Comment): void {
    this.deleteCommentComponent.comment = comment;
    this.deleteCommentComponent.modal.open();
  }

  updateComments(comments: Comment[]) {
    this.comments = comments;
  }

  updateSubRequests(subRequests: Request[]) {
    this.subRequests = subRequests;
  }

  changeShowDescription() {
    this.showDescription = !this.showDescription;
  }

  changeShowHistory() {
    this.showHistory = !this.showHistory;
  }

  changeShowSubRequests() {
    this.showSubRequests = !this.showSubRequests;
  }

  changeShowJoinedRequests() {
    this.showJoinedRequests = !this.showJoinedRequests;
  }

  toggleFollowersShowing() {
    this.showFollowers = !this.showFollowers;
  }

  changeShowComments() {
    this.showComments = !this.showComments;
  }

  updateRequest() {
    this.request.parentId = null;
    if (this.request.assignee.id === 0) {
      this.request.assignee = <User>{};
    }
    this.request.lastChanger = this.currentUser;
    this.requestService.update(this.request)
      .subscribe(() => {
        this.toastr.success("Request updated", "Success")
      });
  }

  getRequestType(request): string {
    if (request.progressStatus.name == 'Null' && request.priorityStatus.name == null) {
      return "Sub request"
    } else if (request.progressStatus.name == 'Joined') {
      return "Joined request";
    } else {
      return "Request"
    }
  }

  updateComment(comment) {
    comment.id = null;
    comment.updateDateAndTime = new Date();
    this.commentService.create(comment).subscribe(() => {
      this.commentService.getByRequest(this.request.id).subscribe(comments => {
        this.comments = comments;
      });
      this.toastr.success("Comment updated", "Success")
    });
  }

  isFree(request): boolean {
      return request.progressStatus.name == 'Free';
  }

  isInProgress(request): boolean {
    if (request.progressStatus.name == 'In progress') {
      return true;
    } else {
      return false;
    }
  }

  isAdmin(): boolean {
    return this.role != 'admin';
  }

  isAssignee(request): boolean {
    return request.assignee.id !== this.currentUser.id;
  }

  follow() {
    this.subscribeService.toggleSubscribe(this.request.id, this.currentUser.id).subscribe(resp => {
      this.followed = resp;
      this.subscribeService.getFollowers(this.request.id).subscribe(followers => {
        this.followers = followers;
      });
    });
  }

  isAssigneeOfRequest() {
    return this.request.assignee.id === this.currentUser.id
  }

  isClosed(request): boolean {
      return request.progressStatus.name == 'Closed';
  }

  isEmployee(): boolean {
    return this.currentUser.role.name === 'employee';
  }

  isManager(): boolean {
    return this.currentUser.role.name === 'office manager';
  }

  getPDF() {
    this.reportService.getPDFRequest(this.request.id).subscribe(
      (res: any) => {
        let blob = res.blob();
        let filename = 'request_' + this.request.id + '.pdf';
        FileSaver.saveAs(blob, filename);
      }
    );
  }

  close(request:Request) {
    this.closeComponent.request = this.request;
    this.closeComponent.modal.open();
  }

  update(request:Request) {
    this.request = request;
  }
}
