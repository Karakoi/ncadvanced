import {Directive, OnInit, ElementRef} from "@angular/core";
import {AuthService, AuthEvent} from "../service/auth.service";
import {Subject} from "rxjs";

declare let $: any;

@Directive({
  selector: '[side-bar]'
})
export class SideBarDirective implements OnInit {
  private el: HTMLElement;

  constructor(ref: ElementRef, private authService: AuthService) {
    this.el = ref.nativeElement;
  }

  ngOnInit(): void {
    this.toggleOnClick();
    this.removeOnLogout();
  }

  private toggleOnClick(): void {
    this.el.addEventListener('click', e => {
      e.preventDefault();
      $('#wrapper').toggleClass("toggled");
    });
  }

  private removeOnLogout(): void {
    this.authService.events.subscribe((event: Subject<AuthEvent>) => {
      if (event.constructor.name === 'DidLogout') {
        $('#wrapper').addClass("toggled");
      }
    });
  }

}