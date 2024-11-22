export class UserModule {
  username: string;
  password: string;
  userType: string;
  vendorID: string;
  name: string;
  email: string;
  userID: number;
  eventID: number;

  constructor() {
    this.username = null;
    this.password = null;
    this.userType = null;
    this.vendorID = null;
    this.name = null;
    this.email = null;
    this.userID = 0;
    this.eventID = 0;
  }
}
