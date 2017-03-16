import {Component, OnInit, Input} from "@angular/core";
import {Md5} from 'ts-md5/dist/md5';

@Component({
  selector: 'date-parse',
  templateUrl: 'date-parse.component.html'
})
export class DateParseComponent implements OnInit{
  @Input('date') date: Date;

  constructor() {}
  ngOnInit() {}
}

