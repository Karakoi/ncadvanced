import {User} from "./user.model";

export interface History {
  id?: number,
  columnName: string,
  oldValue: string,
  newValue: string,
  dateOfChanges: Date,
  changer: User,
  recordId: number
}
