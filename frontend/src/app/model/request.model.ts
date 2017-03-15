import {User} from "./user.model";

export interface Request {
  id?: number,
  title: string,
  description: string,
  dateOfCreation: Date,
  priorityStatus?: PriorityStatus,
  progressStatus?: ProgressStatus,
  reporter: User,
  assignee?: User,
  parentId?: number,
  estimateTimeInDays: number,
  lastChanger: User
}

export interface PriorityStatus {
  id?: number,
  name: string,
  value: number
}

export interface ProgressStatus {
  id?: number,
  name: string,
  value: number
}
