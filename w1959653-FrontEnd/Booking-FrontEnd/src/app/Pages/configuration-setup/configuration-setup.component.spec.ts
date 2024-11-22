import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigurationSetupComponent } from './configuration-setup.component';

describe('ConfigurationSetupComponent', () => {
  let component: ConfigurationSetupComponent;
  let fixture: ComponentFixture<ConfigurationSetupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ConfigurationSetupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigurationSetupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
