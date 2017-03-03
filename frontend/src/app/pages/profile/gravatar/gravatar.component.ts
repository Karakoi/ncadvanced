import {Component, OnInit, Input} from "@angular/core";
import {Md5} from 'ts-md5/dist/md5';

@Component({
  selector: 'gravatar',
  templateUrl: 'gravatar.component.html',
  styleUrls: ['../profile.component.css']
})
export class GravatarComponent implements OnInit{
  @Input('email') email: string;
  @Input('size') size: number = 500;

  constructor() {}
  ngOnInit() {}

  getImage(){
    return `https://s.gravatar.com/avatar/${Md5.hashStr(this.email)}?s=${this.size}`;
  }
}

