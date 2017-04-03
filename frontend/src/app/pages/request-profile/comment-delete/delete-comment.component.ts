import {Component, ViewChild, Input, Output, EventEmitter} from "@angular/core";
import {ToastsManager} from "ng2-toastr";
import {ModalComponent} from "ng2-bs3-modal/components/modal";
import {Comment} from "../../../model/comment.model";
import {CommentService} from "../../../service/comment.service";

@Component({
  selector: 'delete-comment',
  templateUrl: 'delete-comment.component.html'
})
export class DeleteCommentComponent {

  @Input()
  comments: Comment[];

  @Output()
  updated: EventEmitter<any> = new EventEmitter();

  public comment: Comment;

  @ViewChild('deleteCommentFormModal')
  modal: ModalComponent;

  constructor(private commentService: CommentService,
              private toastr: ToastsManager) {
  }

  deleteComment() {
    this.commentService.delete(this.comment.id).subscribe(() => {
      this.updateArray();
      this.modal.close();
      this.toastr.success("Comment was deleted successfully", "Success!");
    }, e => this.handleErrorDeleteComment(e));
  }

  private updateArray(): void {
    this.updated.emit(this.comments.filter(r => r !== this.comment));
  }

  private handleErrorDeleteComment(error) {
    switch (error.status) {
      case 500:
        this.toastr.error("Can't delete comment", 'Error');
    }
  }
}
