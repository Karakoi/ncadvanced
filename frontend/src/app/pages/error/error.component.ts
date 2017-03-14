import {Component, OnDestroy} from "@angular/core";
import {Subscription} from "rxjs";
import {ErrorService} from "../../service/error.service";

@Component({
  selector: 'error',
  styleUrls: ['error.component.css'],
  templateUrl: 'error.component.html'
})
export class ErrorComponent implements OnDestroy {

  error: any;
  subscription: Subscription;

  constructor(private errorService: ErrorService) {
    this.subscription = this.errorService.update$.subscribe(
      error => {
        this.error = error;
      });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
