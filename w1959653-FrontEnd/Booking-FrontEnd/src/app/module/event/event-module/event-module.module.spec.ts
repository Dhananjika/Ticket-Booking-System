import { EventModuleModule } from './event-module.module';

describe('EventModuleModule', () => {
  let eventModuleModule: EventModuleModule;

  beforeEach(() => {
    eventModuleModule = new EventModuleModule();
  });

  it('should create an instance', () => {
    expect(eventModuleModule).toBeTruthy();
  });
});
