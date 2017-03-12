import {Component, OnInit} from "@angular/core";
import {RequestService} from "../../service/request.service";
import {Request} from "../../model/request.model";
import {ActivatedRoute} from "@angular/router";
import {ToastsManager} from "ng2-toastr";

@Component({
  selector: 'request-profile',
  templateUrl: 'request-profile.component.html',
  styleUrls: ['request-profile.component.css']
})
export class RequestProfileComponent implements OnInit {

  request: Request;
  showDescription: boolean = true;
  showHistory: boolean = true;
  showSubRequests: boolean = true;
  showJoinedRequests: boolean = true;

  constructor(private requestService: RequestService,
              private route: ActivatedRoute,
              private toastr: ToastsManager) {
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      let id = +params['id'];
      this.requestService.get(id).subscribe((request: Request) => {
        this.request = request;
        console.log(request)
      });
    });
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

  updateRequest(value) {

    this.requestService.update(this.request)
      .subscribe(() => {
        this.toastr.success("Request updated", "Success")
      });
  }
}
