export interface HistoryMessageDTO {
  id?: number,
  message: String,
  longMessage?: String,
  changerId: number,
  changerFirstName: String,
  changerLastName: String,
  dateOfChanges: Date
}
