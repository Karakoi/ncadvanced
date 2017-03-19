import { NgModule }      from '@angular/core';
import {RequestFilterPipe} from "./request-filter.pipe";
import {RequestSortPipe} from "./request-sort.pipe";
import {UserFilterPipe} from "./user-filter.pipe";
import {UserSortPipe} from "./user-sort.pipe";

@NgModule({
  imports:        [],
  declarations:   [RequestFilterPipe, RequestSortPipe, UserFilterPipe,UserSortPipe],
  exports:        [RequestFilterPipe, RequestSortPipe, UserFilterPipe,UserSortPipe],
})
export class PipeModule {
  static forRoot() {
    return {
      ngModule: PipeModule,
      providers: [],
    };
  }
}
