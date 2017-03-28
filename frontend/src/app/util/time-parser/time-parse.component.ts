import {Component, OnInit, Input} from "@angular/core";

@Component({
  selector: 'time-parse',
  templateUrl: 'time-parse.component.html'
})
export class TimeParseComponent implements OnInit{
  @Input('date') date: Date;

  constructor() {}
  ngOnInit() {}
}

