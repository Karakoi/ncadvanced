import {Component} from "@angular/core";
import {RequestService} from "../../service/request.service";
import {AuthService} from "../../service/auth.service";
import {Router} from '@angular/router';

@Component({
  selector: 'deadline',
  templateUrl: 'deadline.component.html',
  styleUrls: ['deadline.component.css']
})

export class DeadlineComponent {
  loaded = false;
  events:any[] = [];

  constructor(private requestService:RequestService,
              private authService:AuthService,
              private router:Router) {
  }

  ngOnInit() {
    this.authService.currentUser.subscribe(user => {
        this.requestService.getDeadlines(user.id).subscribe((items) => {
          for (var i = 0; i < items.length; i++) {
            var newEvent = {
              id: items[i].id,
              title: items[i].title,
              start: new Date(items[i].deadline)
            };
            this.events.push(newEvent);
          }
          this.loaded = true;
        });
      }
    );
  }

  calendarOptions:Object = {
    height: 490, 
    firstDay: 1,
    fixedWeekCount: false,
    defaultDate: new Date(),
    editable: false,
    eventLimit: true,
    aspectRatio: 1.35,
    events: this.events,
    timeFormat: ' ',
    eventClick: (calEvent) => {
      this.router.navigate(['/request', calEvent.id]);
    }
  };

}
