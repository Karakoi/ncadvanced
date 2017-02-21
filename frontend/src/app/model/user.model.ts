export interface User {
  id?: number,
  firstName: string,
  lastName: string,
  secondName?: string,
  email: string,
  password: string,
  birthDate?: Date,
  phoneNumber?: number
}