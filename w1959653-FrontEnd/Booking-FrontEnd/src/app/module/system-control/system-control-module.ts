export class SystemControlModule {
  systemStatus: string;
  configurationStatus: string;
  systemStoppedReleasedTicketCount: number;
  systemStoppedPoolSize: number;

  constructor() {
    this.systemStatus = null;
    this.configurationStatus = null;
    this.systemStoppedReleasedTicketCount = 0;
    this.systemStoppedPoolSize = 0;
  }
}
